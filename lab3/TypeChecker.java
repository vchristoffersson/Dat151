import CPP.Absyn.*;
import java.util.HashMap;
import java.util.LinkedList;

public class TypeChecker {

    public static class FunType {
        public LinkedList<TypeCode> args;
        public TypeCode val;
    }

    public static enum TypeCode {Type_int , Type_double , Type_bool , Type_void}

    public static class Env {

        public HashMap<String , FunType> signature ;
        public LinkedList<HashMap<String , TypeCode>> contexts ;

        public Env() {

            contexts = new LinkedList<>();
            signature = new HashMap<> () ;

            contexts.addFirst(new HashMap<String , TypeCode>());
        }

        public TypeCode lookupVar(String id) {

            for (HashMap<String , TypeCode> context : contexts) {

                TypeCode t = context.get(id) ;

                if(t != null)
                    return t;
            }

            throw new TypeException("No variable found");
        }

        public void addVar(String id , TypeCode Ty) {

            HashMap<String , TypeCode> map = contexts.getFirst() ;

            TypeCode typeCode = map.get(id);

            if(typeCode == null) {
                map.put(id, Ty);
            }

            else {
                throw new TypeException("Variable already declared" );
            }
        }

        public FunType lookupFun (String id) {
            FunType t = signature.get(id) ;
            if (t == null)
                throw new TypeException("There is no [" + id + "] function");
            else
                return t ;
        }
    }

    // entry point of type checker
    public void typecheck(Program p) {

        Env env = new Env() ;

        FunType readInt = new FunType();
        readInt.val = TypeCode.Type_int ;
        readInt.args = new LinkedList<>() ;
        env.signature.put("readInt", readInt) ;

        FunType readDouble = new FunType();
        readDouble.val = TypeCode.Type_double ;
        readDouble.args = new LinkedList<>() ;
        env.signature.put("readDouble", readDouble) ;

        FunType printInt = new FunType();
        printInt.val = TypeCode.Type_void ;
        printInt.args = new LinkedList<>() ;
        printInt.args.addLast(TypeCode.Type_int);
        env.signature.put("printInt", printInt) ;

        FunType printDouble = new FunType();
        printDouble.val = TypeCode.Type_void ;
        printDouble.args = new LinkedList<>() ;
        printDouble.args.addLast(TypeCode.Type_double);
        env.signature.put("printDouble", printDouble) ;

        for(Def d : ((PDefs) p).listdef_ ) {
            d.accept(new FunctionVisitor() , env);
        }

        for(Def d : ((PDefs) p).listdef_) {
            d.accept(new FuncStmVisitor() , env);
        }
    }

    public class FunctionVisitor implements Def.Visitor<Object , Env> {

        public Object visit(CPP.Absyn.DFun p, Env env) {

            if(env.signature.containsKey(p.id_))
                throw new TypeException("function identifier declared");

            FunType func = new FunType() ;
            func.val = toTypeCode(p.type_);
            func.args = new LinkedList<>() ;

            for (Arg arg : p.listarg_) {
                func.args.addLast(toTypeCode(((ADecl)arg).type_)) ;
            }

            env.signature.put(p.id_ , func) ;
            return null;
        }
    }

    public class FuncStmVisitor implements Def.Visitor<Object , Env> {

        public Object visit(CPP.Absyn.DFun p , Env env) {

            env.contexts.addFirst(new HashMap<String , TypeCode>());

            for (Arg arg : p.listarg_) {
                ADecl decl = (ADecl)arg ;
                env.addVar(decl.id_ , toTypeCode( decl.type_));
            }

            TypeCode val = toTypeCode(p.type_);

            env.addVar("return" , val) ;

            for (Stm stm : p.liststm_) {
                checkStm(stm , env);
            }

            env.contexts.removeFirst();

            return null;
        }
    }

    //Statements
    public void checkStm (Stm stm , Env env) {
        stm.accept(new StmVisit() , env);
    }

    //Statement Visitor
    public class StmVisit implements Stm.Visitor<Env,Env> {

        public Env visit(CPP.Absyn.SExp p, Env env) {
            checkExp(p.exp_, env);
            return null;
        }

        public Env visit(CPP.Absyn.SDecls p, Env env) {

            for(String id : p.listid_) {
                env.addVar(id, toTypeCode(p.type_));
            }

            return null;
        }

        public Env visit(CPP.Absyn.SInit p, Env env) {
            TypeCode expType = checkExp(p.exp_, env);
            TypeCode type = toTypeCode(p.type_);
            if(expType == type){
                env.addVar(p.id_, type);
                return null;
            }
            throw new TypeException("type must be same as variable");
        }

        public Env visit(CPP.Absyn.SReturn p, Env env) {

            TypeCode typeCode = checkExp(p.exp_, env);
            TypeCode returnCode = env.lookupVar("return");
            if(typeCode != TypeCode.Type_void) {
                if (typeCode != returnCode){
                    throw new TypeException("return not of same type as funcreturn");
                }
                return null;
            }
            throw new TypeException("val can not be void");
        }

        public Env visit(CPP.Absyn.SWhile p, Env env) {
            TypeCode typeCode = checkExp(p.exp_, env);
            if(typeCode == TypeCode.Type_bool){
                checkStm(p.stm_, env);
                return null;
            }
            throw new TypeException("expression in while must be of type boolean");

        }

        public Env visit(CPP.Absyn.SBlock p, Env env) {
            env.contexts.addFirst(new HashMap<String, TypeCode>());

            for (Stm stm: p.liststm_) {
                checkStm(stm, env);
            }
            env.contexts.removeFirst();
            return null;
        }

        public Env visit(CPP.Absyn.SIfElse p, Env env) {
            TypeCode typeCode = checkExp(p.exp_, env);
            if(typeCode == TypeCode.Type_bool){
                checkStm(p.stm_1, env);
                checkStm(p.stm_2, env);
                return null;
            }
            throw new TypeException("Expression must be of type boolean");
        }
    }

    // check expressions
    public TypeCode checkExp(Exp exp , Env env)
    {
        return exp.accept(new ExpVisit() , env );
    }

    //Expressions Visitor
    public class ExpVisit implements Exp.Visitor<TypeCode,Env> {

        public TypeCode visit(CPP.Absyn.ETrue p, Env env) {
            return TypeCode.Type_bool;
        }

        public TypeCode visit(CPP.Absyn.EFalse p, Env env) {
            return TypeCode.Type_bool;
        }

        public TypeCode visit(CPP.Absyn.EInt p, Env env) {
            return TypeCode.Type_int;
        }

        public TypeCode visit(CPP.Absyn.EDouble p, Env env) {
            return TypeCode.Type_double;
        }

        public TypeCode visit(CPP.Absyn.EId p, Env env) {
            return env.lookupVar(p.id_);
        }

        public TypeCode visit(CPP.Absyn.EApp p, Env env) {

            FunType funType = env.lookupFun(p.id_);

            LinkedList<TypeCode> funcList = funType.args;
            LinkedList<Exp> decList = p.listexp_;

            if (funcList.size() == decList.size()){

                for (int i=0; i < decList.size(); i++) {

                    TypeCode funcType = funcList.get(i);
                    TypeCode decType = checkExp(decList.get(i), env);

                    if(funcType != decType) {
                        throw new TypeException("argument type does not match functype");
                    }
                }
                return funType.val;
            }

            throw new TypeException("length of argument list not the same");

        }

        public TypeCode visit(CPP.Absyn.EPostIncr p, Env env) {

            TypeCode t = checkExp(p, env);

            if((t == TypeCode.Type_void) || (t == TypeCode.Type_bool)) {
                throw new TypeException("Cant Incr Void or Boolean");
            }

            return t;
        }

        public TypeCode visit(CPP.Absyn.EPostDecr p, Env env) {

            TypeCode t = checkExp(p, env);

            if((t == TypeCode.Type_void) || (t == TypeCode.Type_bool)) {
                throw new TypeException("Cant Decr Void or Boolean");
            }

            return t;
        }

        public TypeCode visit(CPP.Absyn.EPreIncr p, Env env) {

            TypeCode t = checkExp(p, env);

            if((t == TypeCode.Type_void) || (t == TypeCode.Type_bool)) {
                throw new TypeException("Cant Incr Void or Boolean");
            }

            return t;
        }

        public TypeCode visit(CPP.Absyn.EPreDecr p, Env env) {

            TypeCode t = checkExp(p, env);

            if((t == TypeCode.Type_void) || (t == TypeCode.Type_bool)) {
                throw new TypeException("Cant Decr Void or Boolean");
            }

            return t;
        }

        public TypeCode visit(CPP.Absyn.ETimes p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant mul Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant mul different types");

        }

        public TypeCode visit(CPP.Absyn.EDiv p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant div Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant div different types");
        }

        public TypeCode visit(CPP.Absyn.EPlus p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant add Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant add different types");
        }

        public TypeCode visit(CPP.Absyn.EMinus p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant negate Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant negate different types");
        }

        public TypeCode visit(CPP.Absyn.ELt p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant compare Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant compare different types");
        }

        public TypeCode visit(CPP.Absyn.EGt p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant compare Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant compare different types");
        }

        public TypeCode visit(CPP.Absyn.ELtEq p, Env env) {
            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant negate Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant negate different types");
        }

        public TypeCode visit(CPP.Absyn.EGtEq p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_bool || t1 == TypeCode.Type_void) {
                    throw new TypeException("Cant negate Void or Boolean");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant negate different types");
        }

        public TypeCode visit(CPP.Absyn.EEq p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_void) {
                    throw new TypeException("Void");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant equals different types");
        }

        public TypeCode visit(CPP.Absyn.ENEq p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 == TypeCode.Type_void) {
                    throw new TypeException("Void");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant inequal different types");
        }

        public TypeCode visit(CPP.Absyn.EAnd p, Env env) {
            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 != TypeCode.Type_bool) {
                    throw new TypeException("conjunction for Boolean only");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant conjuct different types");
        }

        public TypeCode visit(CPP.Absyn.EOr p, Env env) {

            TypeCode t1 = checkExp(p.exp_1, env);
            TypeCode t2 = checkExp(p.exp_2, env);

            if(t1 == t2) {
                if(t1 != TypeCode.Type_bool) {
                    throw new TypeException("disjunction for Boolean only");
                }
                else {
                    return t1;
                }
            }

            throw new TypeException("Cant disjuct different types");

        }

        public TypeCode visit(CPP.Absyn.EAss p, Env env) {

            TypeCode expType = checkExp(p.exp_, env);
            TypeCode idType = env.lookupVar(p.id_);

            if(expType == idType) {
                return expType;
            }

            throw new TypeException("not the same type as id");
        }

    }

    // function for TypeCode
    public TypeCode toTypeCode(Type t) {
        return t.accept(new TypeCoder() , null);
    }

    //TypeCoder Visitor
    public class TypeCoder implements Type.Visitor<TypeCode , Object> {
        public TypeCode visit(Type_bool t, Object arg)
        {
            return TypeCode.Type_bool ;
        }
        public TypeCode visit(Type_int t, Object arg)
        {
            return TypeCode.Type_int ;
        }
        public TypeCode visit(Type_double t, Object arg)
        {
            return TypeCode.Type_double ;
        }
        public TypeCode visit(Type_void t, Object arg)
        {
            return TypeCode.Type_void ;
        }

    }
}
