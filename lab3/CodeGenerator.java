import CPP.Absyn.*;

import java.util.HashMap;
import java.util.LinkedList;

public class CodeGenerator {

    public enum TypeCode
    {
        INT , DOUBLE , BOOL , VOID
    }

    private LinkedList<String> emits = new LinkedList<>();

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
    }

    private Env env = new Env();

    private void compileStm(Stm st, Object arg) {
        st.accept(new StmCompiler(), arg);
    }

    private class StmCompiler implements Stm.Visitor<Object,Object> {


        @Override
        public Object visit(SExp p, Object arg) {
            return null;
        }

        @Override
        public Object visit(SDecls p, Object arg) {
            return null;
        }

        @Override
        public Object visit(SInit p, Object arg) {
            return null;
        }

        @Override
        public Object visit(SReturn p, Object arg) {
            return null;
        }

        @Override
        public Object visit(SWhile p, Object arg) {
            return null;
        }

        @Override
        public Object visit(SBlock p, Object arg) {
            return null;
        }

        @Override
        public Object visit(SIfElse p, Object arg) {
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

