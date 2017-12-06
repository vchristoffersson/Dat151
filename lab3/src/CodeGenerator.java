import CPP.Absyn.*;
import CPP.VisitSkel;

import java.util.*;

public class CodeGenerator {

    public enum TypeCode
    {
        INT , DOUBLE , BOOL , VOID
    }

    private LinkedList<String> emits;

    private void emit(String s)
    {
        emits.add(s + "\n");
    }

    public static class Env {
        private LinkedList<HashMap<String,Integer>> vars;
        private Integer maxvar;
        private Map<String, FunType> signature;

        public Env() {
            vars = new LinkedList<>();
            maxvar = 0;
        }

        public void addVar(String x, TypeCode t) {

            HashMap<String,Integer > map = vars.getFirst();
            map.put(x, maxvar);

            if(t == TypeCode.DOUBLE) {
                maxvar += 2;
            }
            else {
                maxvar += 1;
            }
        }

        public Integer lookupVar (String id) {

            Integer i = 0 ;

            for(HashMap<String ,Integer> map : vars) {

                i = map.get(id);

                if(i != null)
                    return i;
            }

            return i;
        }

        public void addScope(){
            vars.addFirst(new HashMap<String, Integer>());
        }

        public void removeScope(){
            vars.removeFirst();
        }

    }

    private Env env = new Env();

    //Class to handle funtion type, arguments and head in JVM
    private class FunType {
        String jvmId;
        LinkedList<Type> args;
        Type retType;

        FunType(String jvmId, LinkedList<Type> args, Type retType){
            this.jvmId = jvmId;
            this.args = args;
            this.retType = retType;
        }

        String generateJVM(){
            String funArg = "(";
            for (Type t : args){
                funArg = funArg + compileType(t, null);
                }
            funArg = funArg + ")";
            funArg = funArg + compileType(retType, null);

            return funArg;
        }

        String compileType(Type t, Object arg){
            return t.accept(new TypeCompiler(), arg);
        }
        class TypeCompiler implements Type.Visitor<String,Object>
        {
            public String visit(CPP.Absyn.Type_bool p, Object arg)
            {
                return "Z";
            }
            public String visit(CPP.Absyn.Type_int p, Object arg)
            {
                return "I";
            }
            public String visit(CPP.Absyn.Type_double p, Object arg)
            {
                return "D";
            }
            public String visit(CPP.Absyn.Type_void p, Object arg)
            {
                return "V";
            }
        }
    }

    //Program entry
    public LinkedList<String> generateCode(Program p, String name){
        emits = new LinkedList<>();

        emit(".class public " + name);
        emit(".super java/lang/Object");
        emit(".method public <init>()V");
        emit("aload_0");
        emit("invokenonvirtual java/lang/Object/&lt;init>()V");
        emit("return");
        emit(".end method");

        //add main function
        emit(".method public static main([Ljava/lang/String;)V");
        emit("invokestatic " + name + "/main()I");
        emit("pop");
        emit("return");
        emit(".end method");

        //add predefined functions
        //emit("invokestatic runtime/printInt(I)V");
        //emit("invokestatic runtime/readInt()I");

        //add program functions
        env.signature = new TreeMap<>();
        LinkedList<Type> args = new LinkedList<>();
        args.add(new Type_int());
        env.signature.put("printInt", new FunType("Runtime/printInt", args , new Type_void()));
        env.signature.put("printInt", new FunType("Runtime/readInt", new LinkedList<>() , new Type_int()));


        //Add functions to signature
        for (Def def: ((PDefs)p).listdef_) {
            DFun fun = (DFun) def;
            LinkedList<Type> funArgs = new LinkedList<>();
            for (Arg a : fun.listarg_) {
                ADecl decl = (ADecl) a;
                funArgs.add(decl.type_);
            }

            env.signature.put(fun.id_, new FunType(name + "/" + fun.id_ , funArgs, fun.type_));
        }

        compileProgram(p, null);

        return emits;
    }

    //Compile all functions in a program
    private void compileProgram(Program p, Object arg) {
        p.accept(new ProgramCompiler(), arg);
    }

    private class ProgramCompiler implements Program.Visitor<Object, Object> {

        @Override
        public Object visit(PDefs p, Object arg) {
            for (Def def : p.listdef_) {
                compileDef(def, arg);
            }

            return null;
        }
    }

    //Compile function
    private void compileDef(Def d, Object arg){
        d.accept(new DefCompiler(), arg);
    }

    private class DefCompiler implements Def.Visitor<Object, Object> {

        @Override
        public Object visit(DFun p, Object arg) {

            //new scope?
            env.addScope();


            //find value for limit locals and limit stack
            emit(".method public static " + p.id_ + env.signature.get(p.id_).generateJVM());
            emit(".limit locals 100");
            emit(".limit stack 100");


            //compile function
            for (Arg a : p.listarg_) {
                compileArg(a, arg);
            }
            for (Stm stm : p.liststm_) {
                compileStm(stm, arg);
            }


            //also check for main function to change return?

            emit("return");
            emit(".end method");

            env.removeScope();
            return null;
        }
    }

    //Adds function argument to context
    private void compileArg(Arg a, Object arg){
        a.accept(new ArgCompiler(), arg);
    }

    private class ArgCompiler implements Arg.Visitor<Object,Object> {
        @Override
        public Object visit(ADecl p, Object arg) {
            env.addVar(p.id_, typeCodeExp(p.type_));
            return null;
        }
    }

    //Compiles all statements
    private void compileStm(Stm st, Object arg) {
        st.accept(new StmCompiler(), arg);
    }

    private class StmCompiler implements Stm.Visitor<Object,Object> {


        @Override
        public Object visit(SExp p, Object arg) {
            compileExp(p.exp_, env);
            emit("pop");
            return null;
        }

        @Override
        public Object visit(SDecls p, Object arg) {
            for(String id : p.listid_) {
                env.addVar(id, typeCodeExp(p.type_));
            }
            return null;
        }

        @Override
        public Object visit(SInit p, Object arg) {
            compileExp(p.exp_, arg);
            env.addVar(p.id_, typeCodeExp(p.type_));
            emit("istore" + env.lookupVar(p.id_));
            return null;
        }

        @Override
        public Object visit(SReturn p, Object arg) {
            compileExp(p.exp_, arg);
            emit("ireturn");
            return null;
        }

        @Override
        public Object visit(SWhile p, Object arg) {
            //new startlabel
            //new endlabel
            emit("WHILE:");
            compileExp(p.exp_, arg);
            emit("ifeq END");
            compileStm(p.stm_, arg);
            emit("goto WHILE");
            emit("END");
            return null;
        }

        @Override
        public Object visit(SBlock p, Object arg) {
            env.addScope();

            for (Stm blockStm : p.liststm_) {
                compileStm(blockStm, arg);
            }
            env.removeScope();
            return null;
        }

        @Override
        public Object visit(SIfElse p, Object arg) {
            compileExp(p.exp_, arg);
            emit("ifeq END");
            compileStm(p.stm_1, arg);
            emit("goto TRUE");
            emit("FALSE:");
            compileStm(p.stm_2, arg);
            emit("TRUE:");
            return null;
        }
    }

    private Object compileExp(Exp e, Object arg) {
        return e.accept(new ExpCompiler(), arg);
    }

    private class ExpCompiler implements Exp.Visitor<Object, Object> {

        @Override
        public Object visit(ETrue p, Object arg) {

            emit("ldc 1");

            return null;
        }

        @Override
        public Object visit(EFalse p, Object arg) {

            emit("ldc 0");

            return null;
        }

        @Override
        public Object visit(EInt p, Object arg) {

            emit("ldc " + p.integer_);

            return null;
        }

        @Override
        public Object visit(EDouble p, Object arg) {

            emit("ldc2_w " + p.double_);

            return null;
        }

        @Override
        public Object visit(EId p, Object arg) {

            Integer i = env.lookupVar(p.id_);
            emit("iload " + i);

            return null;
        }

        @Override
        public Object visit(EApp p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EPostIncr p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EPostDecr p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EPreIncr p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EPreDecr p, Object arg) {
            return null;
        }

        @Override
        public Object visit(ETimes p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("imul");

            return null;
        }

        @Override
        public Object visit(EDiv p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("idiv");

            return null;
        }

        @Override
        public Object visit(EPlus p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("iadd");

            return null;
        }

        @Override
        public Object visit(EMinus p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("isub");

            return null;
        }

        @Override
        public Object visit(ELt p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            Integer trueInt = env.maxvar;
            env.maxvar++;

            Integer eInt = env.maxvar;
            env.maxvar++;

            emit("if_icmplt L" + trueInt);
            emit("iconst_0");
            emit("goto L" + eInt);
            emit("L" + trueInt + ":");
            emit("iconst_1");
            emit("L" + eInt + ":");


            return null;
        }

        @Override
        public Object visit(EGt p, Object arg) {
            return null;
        }

        @Override
        public Object visit(ELtEq p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EGtEq p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EEq p, Object arg) {
            return null;
        }

        @Override
        public Object visit(ENEq p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EAnd p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EOr p, Object arg) {
            return null;
        }

        @Override
        public Object visit(EAss p, Object arg) {
            return null;
        }
    }

    private TypeCode typeCodeExp(Type t) {
        return t.accept(new TypeCoder() , null);
    }

    private class TypeCoder implements Type.Visitor<TypeCode , Object> {

        public TypeCode visit(Type_bool t, Object arg) {
            return TypeCode.BOOL ;
        }

        public TypeCode visit(Type_int t, Object arg) {
            return TypeCode.INT ;
        }

        public TypeCode visit(Type_double t, Object arg) {
            return TypeCode.DOUBLE ;
        }

        public TypeCode visit(Type_void t, Object arg) {
            return TypeCode.VOID ;
        }
    }

}

