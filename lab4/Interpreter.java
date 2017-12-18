import Fun.Absyn.*;
import java.util.*;

public class Interpreter {

    private Map<String,Exp> signature = new TreeMap<>();
    private Boolean callByName = false;

    public Interpreter(Boolean strategy){
        callByName = strategy;
    }

    public void interpret(Program p) {
        Value val = evalProg(p);
        if(val instanceof VInt)
            System.out.println(val.asInt());
        else
            throw new RuntimeException("main should evaluate to int");
    }

    Value evalProg(Program p) {
        return p.accept(new ProgramVisitor(), null);
    }

    class ProgramVisitor implements Program.Visitor<Value, Object> {

        @Override
        public Value visit(Prog p, Object obj) {
            for(Def def : p.listdef_){
                evalDef(def);
            }

            return evalMain(p.main_);
        }
    }

    Value evalMain(Main p) {
        return p.accept(new MainVisitor(), null);
    }

    class MainVisitor implements Main.Visitor<Value, Object> {

        @Override
        public Value visit(DMain p, Object arg) {
            return evalExp(p.exp_, new Env());
        }
    }

    void evalDef(Def p) {
        p.accept(new DefVisitor(), null);
    }

    class DefVisitor implements Def.Visitor<Object, Object> {

        @Override
        public Object visit(DDef p, Object arg) {
            Exp exp = p.exp_;

            Iterator<String> it = p.listident_.descendingIterator();

            while(it.hasNext()){
                exp = new EAbs(it.next(), exp);
            }

            signature.put(p.ident_, exp);

            return null;
        }
    }

    Value evalExp(Exp p, Env env){
        return p.accept(new ExpVisitor(), env);
    }

    public class ExpVisitor implements Exp.Visitor<Value, Env>{

        @Override
        public Value visit(EVar p, Env env) {

            Value v = env.getValue(p.ident_);
            if(v == null){
                Exp exp = signature.get(p.ident_);
                if(exp == null){
                    throw new RuntimeException("not a variable nor function");
                }
                return evalExp(exp, new Env());
            }
            return v.getValue();
        }

        @Override
        public Value visit(EInt p, Env env) {

            VInt v = new VInt(p.integer_);

            return v;
        }

        @Override
        public Value visit(EApp p, Env env) {
            Value clos = evalExp(p.exp_1, env);
            Value val;

            if(callByName){
                val = new VClosure(p.exp_2, env);
            } else {
                val = evalExp(p.exp_2, env);
            }
            return clos.eval(val);
        }

        @Override
        public Value visit(EAdd p, Env env) {

            Value v1 = evalExp(p.exp_1, env);
            Value v2 = evalExp(p.exp_2, env);

            Integer add = v1.asInt() + v2.asInt();

            VInt total = new VInt(add);

            return total;
        }

        @Override
        public Value visit(ESub p, Env env) {

            Value v1 = evalExp(p.exp_1, env);
            Value v2 = evalExp(p.exp_2, env);

            Integer sub = v1.asInt() - v2.asInt();

            VInt total = new VInt(sub);

            return total;
        }

        @Override
        public Value visit(ELt p, Env env) {

            Value v1 = evalExp(p.exp_1, env);
            Value v2 = evalExp(p.exp_2, env);

            Integer i;

            if(v1.asInt() < v2.asInt()) {
                i = 1;
            }

            else {
                i = 0;
            }

            return new VInt(i);
        }

        @Override
        public Value visit(EIf p, Env env) {

            Value v1 = evalExp(p.exp_1, env);

            Value res;

            if(v1.asInt() == 1) {
                res = evalExp(p.exp_2, env);
            }

            else {
                res = evalExp(p.exp_3, env);
            }

            return res;
        }

        @Override
        public Value visit(EAbs p, Env env) {
            return new VClosure(p, env);
        }
    }



    public class Env {

        Map<String, Value> values;

        Env() {
            values = new HashMap<>();
        }

        Env (Map<String, Value> values, String id, Value value){
            this.values = new HashMap<>(values);
            this.values.put(id, value);
        }

        Value getValue(String id) {
            return values.get(id);
        }

        Env extInstance(String id, Value val){
            return new Env(values, id, val);
        }
    }

    abstract class Value {
        abstract int asInt();
        abstract Value getValue();
        abstract Value eval(Value val);
    }

    class VInt extends Value {
        final int value;

        VInt(int value){
            this.value = value;
        }

        @Override
        int asInt() {
            return value;
        }

        @Override
        Value getValue() {
            return this;
        }

        @Override
        Value eval(Value val) {
            throw new RuntimeException("not a function");
        }
    }

    class VClosure extends Value {
        final Exp exp;
        final Env env;

        VClosure(Exp exp, Env env){
            this.exp = exp;
            this.env = env;
        }

        @Override
        int asInt() {
            throw new RuntimeException("not an int");
        }

        @Override
        Value getValue() {
            return evalExp(exp, env);
        }

        @Override
        Value eval(Value val) {
            if(exp instanceof EAbs){
                EAbs fun = (EAbs) exp;
                return evalExp(fun.exp_, env.extInstance(fun.ident_, val));
            }
            throw new RuntimeException("not a function");
        }
    }
}
