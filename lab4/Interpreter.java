import CPP.Absyn.*;

public class Interpreter {
 
    //TODO implement entry
    public void interpret(Program p){
        Value val = evalProg(p);
    }
    
    //TODO implement program/visitor
    Value evalProg(Program p){
        return p.accept(new ProgramVisitor(), null);
    }
    
    class ProgramVisitor implements Program.Visitor<Value, Object>{

        @Override
        public Value visit(Prog p, Object obj) {
            //TODO defs
            return evalMain(p.main_);
        }
    }

    //TODO implement main/visitor
    Value evalMain(Main p){
        return p.accept(new MainVisitor(), null);
    }

    class MainVisitor implements Main.Visitor<Value, Object>{

        @Override
        public Value visit(DMain p, Object arg) {
            //TODO env?
            return evalExp(p.exp_, null);
        }
    }

    //TODO implement defs/visitor
    void evalDef(Def p){
        p.accept(new DefVisitor(), null);
    }

    class DefVisitor implements Def.Visitor<Object, Object>{

        @Override
        public Object visit(DDef p, Object arg) {
            return null;
        }
    }

    //TODO implement expressions/visitor
    Value evalExp(Exp p, Env env){
        return p.accept(new ExpVisitor(), env); 
    }
    
    class ExpVisitor implements Exp.Visitor<Value, Env>{

        @Override
        public Value visit(EVar p, Env env) {
            return null;
        }

        @Override
        public Value visit(EInt p, Env env) {
            return null;
        }

        @Override
        public Value visit(EApp p, Env env) {
            return null;
        }

        @Override
        public Value visit(EAdd p, Env env) {
            return null;
        }

        @Override
        public Value visit(ESub p, Env env) {
            return null;
        }

        @Override
        public Value visit(ELt p, Env env) {
            return null;
        }

        @Override
        public Value visit(EIf p, Env env) {
            return null;
        }

        @Override
        public Value visit(EAbs p, Env env) {
            return null;
        }
    }
    

    abstract class Value {
        abstract int asInt();
        abstract Value getValue();
    }
    
    //TODO implement environment
    class Env {
        
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
    }
    
    
    
}