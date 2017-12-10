import CPP.Absyn.*;

import java.util.*;
import java.io.*;

public class CodeGenerator
{
    public enum TypeCode
    {
        INT , DOUBLE , BOOL , VOID
    }

    public static class Env {
        private LinkedList<HashMap<String,Integer>> vars;
        private Integer maxvar;
        private Map<String, FunType> signature;
        private int currentLabelId;

        public Env() {
            signature = new TreeMap<>();
            vars = new LinkedList<>();
            vars.addFirst(new HashMap<String, Integer>());
            maxvar = 0;
            currentLabelId = 0;
        }

        public void addVar(String x) {

            HashMap<String,Integer > map = vars.getFirst();
            map.put(x, maxvar);

            maxvar++;

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

        public void addScope() {
            vars.addFirst(new HashMap<String, Integer>());
        }

        public void removeScope() {
            vars.removeFirst();
        }

        public FunType lookupFun (String id) {
            FunType t = signature.get(id) ;
            return t ;
        }

        public int nextLabel(){
            return currentLabelId++;
        }

    }

    private Type typeCodeToType(TypeCode t) {

        switch(t) {

            case INT:
                return new Type_int();


            case BOOL:
                return new Type_bool();


            case VOID:
                return new Type_void();

            default:
                return null;
        }

    }

    //Class to handle funtion type, arguments and head in JVM
    private class FunType {
        String jvmId;
        LinkedList<TypeCode> args;
        TypeCode retType;

        FunType(String jvmId, LinkedList<TypeCode> args, TypeCode retType){
            this.jvmId = jvmId;
            this.args = args;
            this.retType = retType;
        }

        String generateJVM(){

            String funArg = "(";

            for (TypeCode t : args){
                Type tmp = typeCodeToType(t);
                funArg = funArg + compileType(tmp, null);
            }

            funArg = funArg + ")";
            funArg = funArg + compileType(typeCodeToType(retType), null);

            return funArg;
        }

        String compileType(Type t, Object arg){
            return t.accept(new TypeCompiler(), arg);
        }

        class TypeCompiler implements Type.Visitor<String,Object> {
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


    private Env env = new Env() ;
    private LinkedList<String> emits = new LinkedList<>();

    private void emit(String s) {
        emits.add(s + "\n");
    }

    //Program entry
    public void generateCode(Program p, String name, String filepath){
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
        env.signature = new HashMap<>();

        LinkedList<TypeCode> args = new LinkedList<>();
        args.add(TypeCode.INT);

        env.signature.put("printInt", new FunType("Runtime/printInt", args , TypeCode.VOID));

        args = new LinkedList<>();

        env.signature.put("readInt", new FunType("Runtime/readInt", args , TypeCode.INT));


        //Add functions to signature
        for (Def def: ((PDefs)p).listdef_) {
            DFun fun = (DFun) def;
            LinkedList<TypeCode> funArgs = new LinkedList<>();
            for (Arg a : fun.listarg_) {
                ADecl decl = (ADecl) a;
                funArgs.add(typeCodeExp(decl.type_));
            }

            env.signature.put(fun.id_, new FunType(name + "/" + fun.id_ , funArgs, typeCodeExp(fun.type_)));
        }

        compileProgram(p, null);

        writeOut(filepath);
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

    private void writeOut(String filepath){
        try {
            FileWriter writer = new FileWriter(filepath + ".j");
            for (String s : emits) {
                writer.write(s);
            }
            writer.close();
        }
        catch (IOException e) {
            System.err.print("something went wrong when writing to .j file");
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


            FunType ft = env.lookupFun(p.id_);

            //find value for limit locals and limit stack
            emit(".method public static " + p.id_ + ft.generateJVM());
            emit(".limit locals 100");
            emit(".limit stack 100");

            env.addScope();
            env.maxvar = 0;
            //compile function
            for (Arg a : p.listarg_) {
                compileArg(a, arg);
            }
            for (Stm stm : p.liststm_) {
                compileStm(stm);
            }


            if(ft.retType.equals(TypeCode.INT)) {
                emit("iconst_0");
                emit("ireturn");
            }

            else {
                emit("return");
            }

            emit(".end method");

            env.maxvar = 0;
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
            env.addVar(p.id_);
            return null;
        }
    }

    //Compiles all statements
    private void compileStm(Stm st) {
        st.accept(new StmCompiler(), null);
    }

    private class StmCompiler implements Stm.Visitor<Object,Object> {


        @Override
        public Object visit(SExp p, Object arg) {
            compileExp(p.exp_);
            emit("pop");
            return null;
        }

        @Override
        public Object visit(SDecls p, Object arg) {
            for(String id : p.listid_) {
                env.addVar(id);
                if(p.type_.equals(new Type_int())) {
                    emit("iconst_0");
                    emit("istore " + env.lookupVar(id));
                }
            }
            return null;
        }

        @Override
        public Object visit(SInit p, Object arg) {
            compileExp(p.exp_);
            env.addVar(p.id_);
            emit("istore " + env.lookupVar(p.id_));
            return null;
        }

        @Override
        public Object visit(SReturn p, Object arg) {
            compileExp(p.exp_);
            emit("ireturn");
            return null;
        }

        @Override
        public Object visit(SWhile p, Object arg) {
            env.addScope();
            int labelId = env.nextLabel();
            emit("WHILE" + labelId + ":");
            compileExp(p.exp_);
            emit("ifeq END" + labelId);
            compileStm(p.stm_);
            emit("goto WHILE" + labelId);
            emit("END" + labelId + ":");
            env.removeScope();
            return null;
        }

        @Override
        public Object visit(SBlock p, Object arg) {
            env.addScope();

            for (Stm blockStm : p.liststm_) {
                compileStm(blockStm);
            }
            env.removeScope();
            return null;
        }

        @Override
        public Object visit(SIfElse p, Object arg) {
            compileExp(p.exp_);

            int labelId = env.nextLabel();
            emit("ifeq FALSE" + labelId);
            compileStm(p.stm_1);

            emit("goto TRUE" + labelId);

            emit("FALSE" + labelId + ":");
            compileStm(p.stm_2);

            emit("TRUE" + labelId + ":");
            return null;
        }
    }

    private Integer compileExp(Exp e) {
        return e.accept(new ExpCompiler() , null );
    }

    private class ExpCompiler implements Exp.Visitor<Integer, Object> {

        @Override
        public Integer visit(ETrue p, Object arg) {

            emit("ldc 1");

            return null;
        }

        @Override
        public Integer visit(EFalse p, Object arg) {

            emit("ldc 0");

            return null;
        }

        @Override
        public Integer visit(EInt p, Object arg) {

            emit("ldc " + p.integer_);

            return null;
        }

        @Override
        public Integer visit(EDouble p, Object arg) {

            return null;
        }

        @Override
        public Integer visit(EId p, Object arg) {

            Integer i = env.lookupVar(p.id_);
            emit("iload " + i);

            return null;
        }

        @Override
        public Integer visit(EApp p, Object arg) {

            for(Exp e : p.listexp_)
                compileExp(e);

            FunType ft = env.lookupFun(p.id_);

            emit("invokestatic " + ft.jvmId + ft.generateJVM());

            if(ft.retType.equals(TypeCode.VOID))
                emit("iconst_0");

            return null;
        }

        @Override
        public Integer visit(EPostIncr p, Object arg) {

            Integer i = env.lookupVar(p.id_);

            emit("iload " + i);
            emit("dup");

            emit("ldc 1");
            emit("iadd");

            emit("istore " + i);

            return null;
        }

        @Override
        public Integer visit(EPostDecr p, Object arg) {

            Integer i = env.lookupVar(p.id_);

            emit("iload " + i);
            emit("dup");

            emit("ldc 1");
            emit("isub");

            emit("istore " + i);

            return null;
        }

        @Override
        public Integer visit(EPreIncr p, Object arg) {

            Integer i = env.lookupVar(p.id_);
            emit("iload " + i);

            emit("ldc 1");
            emit("iadd");

            emit("istore " + i);
            emit("iload " + i);

            return null;
        }

        @Override
        public Integer visit(EPreDecr p, Object arg) {

            Integer i = env.lookupVar(p.id_);
            emit("iload " + i);

            emit("ldc 1");
            emit("isub");

            emit("istore " + i);
            emit("iload " + i);

            return null;
        }

        @Override
        public Integer visit(ETimes p, Object arg) {

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            emit("imul");

            return null;
        }

        @Override
        public Integer visit(EDiv p, Object arg) {

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            emit("idiv");

            return null;
        }

        @Override
        public Integer visit(EPlus p, Object arg) {

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            emit("iadd");

            return null;
        }

        @Override
        public Integer visit(EMinus p, Object arg) {

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            emit("isub");

            return null;
        }

        @Override
        public Integer visit(ELt p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            int labelId = env.nextLabel();
            emit("if_icmplt EXIT" + labelId);
            emit("pop");
            emit("ldc 0");
            emit("EXIT" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(EGt p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            int labelId = env.nextLabel();

            emit("if_icmpgt EXIT" + labelId);
            emit("pop");
            emit("ldc 0");
            emit("EXIT" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(ELtEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1);
            compileExp(p.exp_2);
            int labelId = env.nextLabel();

            emit("if_icmple EXIT" + labelId);
            emit("pop");
            emit("ldc 0");
            emit("EXIT" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(EGtEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1);
            compileExp(p.exp_2);
            int labelId = env.nextLabel();

            emit("if_icmpge DONE" + labelId);
            emit("pop");
            emit("ldc 0");
            emit("DONE" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(EEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            int labelId = env.nextLabel();

            emit("if_icmpeq DONE" + labelId);
            emit("pop");
            emit("ldc 0");
            emit("DONE" + labelId + ":");
            return null;
        }

        @Override
        public Integer visit(ENEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1);
            compileExp(p.exp_2);

            int labelId = env.nextLabel();

            emit("if_icmpne DONE" + labelId);
            emit("pop");
            emit("ldc 0");
            emit("DONE" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(EAnd p, Object arg) {

            int labelId = env.nextLabel();
            compileExp(p.exp_1);
            emit("ifeq Lf" + labelId);

            compileExp(p.exp_2);
            emit("ifeq Lf" + labelId);

            emit("ldc 1");
            emit("goto LEnd" + labelId);

            emit("Lf" + labelId + ":");
            emit("ldc 0");

            emit("LEnd" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(EOr p, Object arg) {
            int labelId = env.nextLabel();
            compileExp(p.exp_1);
            emit("ifne LTrue" + labelId);

            compileExp(p.exp_2);
            emit("ifne LTrue" + labelId);

            emit("ldc 0");
            emit("goto LEnd" + labelId);
            emit("LTrue" + labelId + ":");
            emit("ldc 1");
            emit("LEnd" + labelId + ":");

            return null;
        }

        @Override
        public Integer visit(EAss p, Object arg) {

            compileExp(p.exp_);

            Integer i = env.lookupVar(p.id_);

            emit("istore " + i);
            emit("iload " + i);

            return null;
        }
    }

    private TypeCode typeCodeExp(Type t) {
        return t.accept(new TypeCoder() , null);
    }

    private class TypeCoder implements Type.Visitor<TypeCode , Object> {
        public TypeCode visit(Type_bool t, Object arg)
        {
            return TypeCode.BOOL ;
        }
        public TypeCode visit(Type_int t, Object arg)
        {
            return TypeCode.INT ;
        }
        public TypeCode visit(Type_double t, Object arg)
        {
            return TypeCode.DOUBLE ;
        }
        public TypeCode visit(Type_void t, Object arg)
        {
            return TypeCode.VOID ;
        }
    }

}

