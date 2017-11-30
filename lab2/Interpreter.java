import CPP.Absyn.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Interpreter {

    Scanner scanner = new Scanner(System.in);

    public static class FunInter {
        public LinkedList<String> args;
        public LinkedList<Stm> stmlist;
        public Val returnVal;
    }

    public static class Env {
        public HashMap<String, FunInter> signature;
        public LinkedList<HashMap<String, Val>> contexts;

        public Env(){
            signature = new HashMap<String, FunInter>();
            contexts = new LinkedList<>();
            addScope();
        }

        public void addScope(){
            contexts.addFirst(new HashMap<String , Val>());
        }

        public void removeScope() {
            contexts.removeFirst();
        }

        public Val findVar(String id_) {
            for (HashMap<String, Val> context: contexts) {
                Val val = context.get(id_);
                if(val != null){
                    return val;
                }
            }
            throw new RuntimeException("variable not found");
        }

        public void updateVar(String id_, Val val){
            for (HashMap<String, Val> context: contexts) {
                if(context.containsKey(id_)){
                    context.put(id_, val);
                    return;
                }
            }
        }

        public void addVar(String id) {
            contexts.getFirst().put(id, null);
        }

        public FunInter findFun(String id){
            FunInter funInter = signature.get(id);
            if(funInter == null){
                throw new RuntimeException("Function not found:" + id);
            }
            return funInter;
        }
    }


    public void interpret(Program p) {
        p.accept(new ProgramVisitor(), null);
    }

    public class ProgramVisitor implements Program.Visitor<Void,Void> {
        public Void visit(CPP.Absyn.PDefs p, Void voidarg) {
            Env env = new Env();
            env.addScope();

            for (Def def : p.listdef_) {
                def.accept(new DefVisitor(), env);
            }

            FunInter mainFun =  env.signature.get("main");
            for (Stm stm : mainFun.stmlist) {
                Val val = stmInter(stm, env);
                if(val != null){
                    return null;
                }
            }

            return null;
        }

    }
    public class DefVisitor implements Def.Visitor<Object, Env> {
        public Object visit(DFun p, Env env) {

            FunInter funInter = new FunInter();
            funInter.stmlist = p.liststm_;
            funInter.args = new LinkedList<>();
            for (Arg arg : p.listarg_) {
                ADecl aDecl = (ADecl) arg;
                funInter.args.addLast(aDecl.id_);
            }
            env.signature.put(p.id_, funInter);

            return null;
        }

    }

        /*
        public class ArgVisitor<R,A> implements Arg.Visitor<R,A>
        {
            public R visit(CPP.Absyn.ADecl p, A arg)
            {
            /// Code For ADecl Goes Here

                p.type_.accept(new CPP.VisitSkel.TypeVisitor<R,A>(), arg);
                //p.id_;

                return null;
            }

        }
        */

    private Val stmInter (Stm stm, Env env){
        return stm.accept(new StmVisitor(), env);
    }

    public class StmVisitor implements Stm.Visitor<Val, Env> {
        @Override
        public Val visit(SExp p, Env env) {
            expInter(p.exp_, env);
            return null;
        }

        @Override
        public Val visit(SDecls p, Env env) {
            for (String id : p.listid_) {
                env.addVar(id);
            }
            return null;
        }

        @Override
        public Val visit(SInit p, Env env) {
            String id = p.id_;
            env.addVar(id);
            env.updateVar(id, expInter(p.exp_, env));
            return null;
        }

        @Override
        public Val visit(SReturn p, Env env) {
            return expInter(p.exp_, env);
        }

        @Override
        public Val visit(SWhile p, Env env) {

            /*
            while(true){
                Val val = expInter(p.exp_, env);
                if(val.getBoolean()){
                    env.addScope();
                    Val stmval = stmInter(p.stm_, env);
                    env.removeScope();
                    if(stmval != null){
                        return stmval;
                    }
                } else {
                    break;
                }
            }

            return null;
        */
            //cleaner solution
            Val exp = expInter(p.exp_, env);
            if(exp.getBoolean()){
                env.addScope();
                Val stm = stmInter(p.stm_, env);
                env.removeScope();

                if(stm != null){
                    return stm;
                } else {
                    stmInter(p, env);
                }

            }
            return null;
        }

        @Override
        public Val visit(SBlock p, Env env) {
            env.addScope();
            for (Stm stm : p.liststm_) {
                Val val = stmInter(stm, env);
                if(val != null){
                    env.removeScope();
                    return val;
                }
            }
            env.removeScope();
            return null;
        }

        @Override
        public Val visit(SIfElse p, Env env) {
            Val val = expInter(p.exp_, env);
            env.addScope();
            if(val.getBoolean()){
                Val valif = stmInter(p.stm_1, env);
                env.removeScope();
                return valif;
            } else {
                Val valelse = stmInter(p.stm_2, env);
                env.removeScope();
                return valelse;
            }
        }
    }

    private Val expInter(Exp exp, Env env){
        return exp.accept(new ExpVisitor(), env);
    }

    public class ExpVisitor implements Exp.Visitor<Val, Env> {

        @Override
        public Val visit(ETrue p, Env env) {
            return new Val.VBoolean(true);
        }

        @Override
        public Val visit(EFalse p, Env env) {
            return new Val.VBoolean(false);
        }

        @Override
        public Val visit(EInt p, Env env) {
            return new Val.VInt(p.integer_);
        }

        @Override
        public Val visit(EDouble p, Env env) {
            return new Val.VDouble(p.double_);
        }

        @Override
        public Val visit(EId p, Env env) {
            return env.findVar(p.id_);
        }

        @Override
        public Val visit(EApp p, Env env) {
            String id = p.id_;
            if(id.equals("printInt") || id.equals("printDouble")){
                Val val = expInter(p.listexp_.getFirst() ,env);
                System.out.println(val.toString());
                return new Val.VVoid();
            } else if(id.equals("readInt")){
                int i = scanner.nextInt();
                //System.out.println("reading int: " + i);

                return new Val.VInt(i);
            } else if(id.equals("readDouble")){
                double d = scanner.nextDouble();
                return new Val.VDouble(d);
            } else {
                FunInter funInter = env.findFun(p.id_);
                LinkedList<Val> evalList = new LinkedList<>();
                for (Exp exp : p.listexp_) {
                    evalList.addLast(expInter(exp, env));
                }
                env.addScope();
                for (String s : funInter.args) {
                    env.addVar(s);
                    env.updateVar(s, evalList.remove());
                }
                for (Stm stm : funInter.stmlist) {
                    Val val = stmInter(stm, env);
                    if (val != null) {
                        env.removeScope();
                        return val;
                    }
                }
            }
            env.removeScope();

            return null;
        }

        @Override
        public Val visit(EPostIncr p, Env env) {
            String id = p.id_;
            Val val = env.findVar(id);
            if(val.isInt()){
                env.updateVar(id, new Val.VInt(val.getInt()+1));
            } else {
                env.updateVar(id, new Val.VDouble(val.getDouble()+1.0));
            }
            return val;
        }

        @Override
        public Val visit(EPostDecr p, Env env) {
            String id = p.id_;
            Val val = env.findVar(id);
            if(val.isInt()){
                env.updateVar(id, new Val.VInt(val.getInt()-1));
            } else {
                env.updateVar(id, new Val.VDouble(val.getDouble()-1.0));
            }
            return val;
        }

        @Override
        public Val visit(EPreIncr p, Env env) {
            String id = p.id_;
            Val val = env.findVar(id);
            if(val.isInt()){
                env.updateVar(id, new Val.VInt(val.getInt()+1));
            } else {
                env.updateVar(id, new Val.VDouble(val.getDouble()+1.0));
            }
            return env.findVar(id);
        }

        @Override
        public Val visit(EPreDecr p, Env env) {
            String id = p.id_;
            Val val = env.findVar(id);
            if(val.isInt()){
                env.updateVar(id, new Val.VInt(val.getInt()-1));
            } else {
                env.updateVar(id, new Val.VDouble(val.getDouble()-1.0));
            }
            return env.findVar(id);
        }

        @Override
        public Val visit(ETimes p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VInt(val1.getInt() * val2.getInt());
            } else {
                return new Val.VDouble(val1.getDouble() * val2.getDouble());
            }
        }

        @Override
        public Val visit(EDiv p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VInt(val1.getInt() / val2.getInt());
            } else {
                return new Val.VDouble(val1.getDouble() / val2.getDouble());
            }
        }

        @Override
        public Val visit(EPlus p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VInt(val1.getInt() + val2.getInt());
            } else {
                return new Val.VDouble(val1.getDouble() + val2.getDouble());
            }
        }

        @Override
        public Val visit(EMinus p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VInt(val1.getInt() - val2.getInt());
            } else {
                return new Val.VDouble(val1.getDouble() - val2.getDouble());
            }            }

        @Override
        public Val visit(ELt p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VBoolean(val1.getInt() < val2.getInt());
            } else {
                return new Val.VBoolean(val1.getDouble() < val2.getDouble());
            }
        }

        @Override
        public Val visit(EGt p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VBoolean(val1.getInt() > val2.getInt());
            } else {
                return new Val.VBoolean(val1.getDouble() > val2.getDouble());
            }
        }

        @Override
        public Val visit(ELtEq p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VBoolean(val1.getInt() <= val2.getInt());
            } else {
                return new Val.VBoolean(val1.getDouble() <= val2.getDouble());
            }
        }

        @Override
        public Val visit(EGtEq p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VBoolean(val1.getInt() >= val2.getInt());
            } else {
                return new Val.VBoolean(val1.getDouble() >= val2.getDouble());
            }
        }

        @Override
        public Val visit(EEq p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VBoolean(val1.getInt() == val2.getInt());
            } else {
                return new Val.VBoolean(val1.getDouble() == val2.getDouble());
            }
        }

        @Override
        public Val visit(ENEq p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            Val val2 = expInter(p.exp_2, env);
            if(val1.isInt()){
                return new Val.VBoolean(val1.getInt() != val2.getInt());
            } else {
                return new Val.VBoolean(val1.getDouble() != val2.getDouble());
            }
        }

        @Override
        public Val visit(EAnd p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            if(val1.getBoolean()){
                Val val2 = expInter(p.exp_2, env);
                if(val2.getBoolean()){
                    return new Val.VBoolean(true);
                }
            }
            return new Val.VBoolean(false);
        }

        @Override
        public Val visit(EOr p, Env env) {
            Val val1 = expInter(p.exp_1, env);
            if(val1.getBoolean()) {
                return new Val.VBoolean(true);
            } else {
                Val val2 = expInter(p.exp_2, env);
                if(val2.getBoolean()){
                    return new Val.VBoolean(true);
                }
            }
            return new Val.VBoolean(false);
        }

        @Override
        public Val visit(EAss p, Env env) {
            Val val = expInter(p.exp_, env);
            env.updateVar(p.id_, val);
            return val;
        }
    }

    public class TypeVisitor<R,A> implements Type.Visitor<R,A>
    {
        public R visit(CPP.Absyn.Type_bool p, A arg)
        {
      /* Code For Type_bool Goes Here */


            return null;
        }
        public R visit(CPP.Absyn.Type_int p, A arg)
        {
      /* Code For Type_int Goes Here */


            return null;
        }
        public R visit(CPP.Absyn.Type_double p, A arg)
        {
      /* Code For Type_double Goes Here */


            return null;
        }
        public R visit(CPP.Absyn.Type_void p, A arg)
        {
      /* Code For Type_void Goes Here */


            return null;
        }

    }

    private static abstract class Val {

        public boolean isInt(){
            return false;
        }

        public int getInt(){
            throw new RuntimeException("not an int");
        }
        public double getDouble(){
            throw new RuntimeException("not a double");
        }
        public boolean getBoolean(){
            throw new RuntimeException("not a boolean");
        }
        public void getVoid(){
            throw new RuntimeException("not a void");
        }

        public static class VBoolean extends Val{

            private boolean b;

            public VBoolean(boolean b){
                this.b = b;
            }

            public boolean getBoolean(){
                return b;
            }

        }

        public static class VInt extends Val{

            private int i;

            public VInt(int i){
                this.i = i;
            }

            public int getInt(){
                return i;
            }

            public boolean isInt(){
                return true;
            }

            public String toString(){
                return String.valueOf(i);
            }
        }

        public static class VDouble extends Val{

            private double d;

            public VDouble(double d){
                this.d = d;
            }

            public double getDouble(){
                return d;
            }

            public String toString(){
                return String.valueOf(d);
            }
        }

        public static class VVoid extends Val {
            public void getVoid() {
            }
        }


    }

}
