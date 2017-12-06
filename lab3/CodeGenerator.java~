import CPP.Absyn.*;
import CPP.VisitSkel;

import java.util.HashMap;
import java.util.LinkedList;

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

    public void generateCode(Program p, String name){
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
        emit("invokestatic runtime/printInt(I)V");
        emit("invokestatic runtime/readInt()I");

        //add program functions
        //@TODO add missing parts
        compileProgram(p, null);

    }

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

    private void compileDef(Def d, Object arg){
        d.accept(new DefCompiler(), arg);
    }

    private class DefCompiler implements Def.Visitor<Object, Object> {

        @Override
        public Object visit(DFun p, Object arg) {

            //new scope?
            //find value for limit locals and limit stack
            emit(".method public static " + p.id_ + "JVMfuntype");
            emit(".limit locals " + "calc(ll)");
            emit(".limit stack " + "calc(ls)");

            //compile function
            for (Stm stm : p.liststm_) {
                compileStm(stm, arg);
            }


            //also check for main function to change return
            emit("return");

            emit(".end method");
            return null;
        }
    }

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
            emit("istore " + env.lookupVar(p.id_));

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

    private Integer compileExp(Exp e, Object arg) {
        return e.accept(new ExpCompiler(), arg);
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

            emit("ldc2_w " + p.double_);

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

            Integer i = compileExp(p, arg);

            emit("ldc 1");
            emit("isub");

            emit("istore " + i);

            return null;
        }

        @Override
        public Integer visit(EPreIncr p, Object arg) {

            Integer i = compileExp(p, arg);

            emit("ldc 1");
            emit("iadd");

            emit("istore " + i);
            emit("iload " + i);

            return null;
        }

        @Override
        public Integer visit(EPreDecr p, Object arg) {

            Integer i = compileExp(p, arg);

            emit("ldc 1");
            emit("isub");

            emit("istore " + i);
            emit("iload " + i);

            return null;
        }

        @Override
        public Integer visit(ETimes p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("imul");

            return null;
        }

        @Override
        public Integer visit(EDiv p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("idiv");

            return null;
        }

        @Override
        public Integer visit(EPlus p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("iadd");

            return null;
        }

        @Override
        public Integer visit(EMinus p, Object arg) {

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("isub");

            return null;
        }

        @Override
        public Integer visit(ELt p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("if_icmplt L");
            emit("pop");
            emit("ldc 0");
            emit("L" + ":");

            return null;
        }

        @Override
        public Integer visit(EGt p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("if_icmpgt L");
            emit("pop");
            emit("ldc 0");
            emit("L" + ":");

            return null;
        }

        @Override
        public Integer visit(ELtEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("if_icmple L");
            emit("pop");
            emit("ldc 0");
            emit("L" + ":");

            return null;
        }

        @Override
        public Integer visit(EGtEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("if_icmpge L");
            emit("pop");
            emit("ldc 0");
            emit("L" + ":");

            return null;
        }

        @Override
        public Integer visit(EEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("if_icmpeq L");
            emit("pop");
            emit("ldc 0");
            emit("L" + ":");
            return null;
        }

        @Override
        public Integer visit(ENEq p, Object arg) {

            emit("ldc 1");

            compileExp(p.exp_1, arg);
            compileExp(p.exp_2, arg);

            emit("if_icmpne L");
            emit("pop");
            emit("ldc 0");
            emit("L" + ":");

            return null;
        }

        @Override
        public Integer visit(EAnd p, Object arg) {



            return null;
        }

        @Override
        public Integer visit(EOr p, Object arg) {
            return null;
        }

        @Override
        public Integer visit(EAss p, Object arg) {

            compileExp(p.exp_, arg);

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


