import CPP.Absyn.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

public class Interpreter {

    private Map<String,Exp> signature = new TreeMap<>();
    private Boolean callByName = false;

    public void interpret(Program p, Boolean callByName) {
        this.callByName = callByName;
        Value val = evalProg(p);
        System.out.println(val.asInt());
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

            ListIterator<String> it = p.listident_.listIterator(p.listident_.size()+1);
            while(it.hasPrevious()){
                exp = new EAbs(it.previous(), exp);
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

            return v;
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
            return clos.eval();
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

        public Map<String , Value> values;

        public Env() {
            values = new HashMap<>();
        }

        //not needed?
        public void addVar(String id, Value v) {
            values.put(id,v);
        }

        public Value getValue(String id) {
            return values.get(id);
        }
    }

    abstract class Value {
        abstract int asInt();
        abstract Value getValue();
        abstract Value eval();
    }

    class VInt extends Value {
        int value;

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
        Value eval() {
            throw new RuntimeException("not a function");
        }
    }

    class VClosure extends Value {
        Exp exp;
        Env env;

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
        Value eval() {
            if(exp instanceof EAbs){
                EAbs eAbs = (EAbs) exp;
                return evalExp(eAbs, env);
            }
            throw new RuntimeException("not a function");
        }
    }



}
