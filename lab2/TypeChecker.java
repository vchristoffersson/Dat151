import CPP.Absyn.*;
import java.util.*;

public class TypeChecker {

	public static class FunType {
		public LinkedList<Type> args;
		public Type val;
	}
	
	//Types
	public static enum TypeCode {CInt, CDouble, CBool, CVoid};
	
	public static class Env {
	
		public HashMap<String, FunType> signature;
		public LinkedList<HashMap<String, TypeCode>> contexts;
		
		public Env() {
	            contexts = new LinkedList<HashMap<String,TypeCode>>();
	            signature = new HashMap<String,FunType> () ; 
	           // returnFlag = 0 ; 
	           // enterScope(); 
		}
		
		public TypeCode lookupVar(String id) {
			
			for(HashMap<String, TypeCode> c : contexts) {
				TypeCode t = c.get(id);
				
				if(t != null) {
					return t;
				}
				
			}
			
			throw new TypeException(id + "non existing");
		}
		
		public FunType lookupFun(String id) {
			
			FunType f = signature.get(id);
			
			if(f != null) {
				return f;
			}
			
			throw new TypeException(id + "not bound");
		}
		
		public void updateVar(String id, TypeCode ty) {
			
			for(HashMap<String, TypeCode> c : contexts) {
				c.put(id, ty);
			}
		}
		
		public void addVar(String id, TypeCode t) {
			
			Map<String, TypeCode> map = contexts.getFirst();
			
			if(map.containsKey(id)) {
				throw new TypeException("variable already exists");
			}
			
			map.put(id, t);
		}
		
	}
	
	public TypeCode convertTypeCode(Type t) {
		return t.accept(new TypeCodes(), null);
	}
	
	//Check entire program
	public void typecheck(Program p) {
    	p.accept(new ProgVisit(), null);
	}
	
	//Program
	public class ProgVisit implements Program.Visitor<Void,Void> {
				
		public Void visit(CPP.Absyn.PDefs p, Void arg) {
			return null;
		}
	}
	
	 private void checkStm (Stm stm , Env env) {
	        stm.accept(new StmVisit() , env);
	 }
	
	public class StmVisit implements Stm.Visitor<Env,Env> {
		
		public Env visit(CPP.Absyn.SExp p, Env env) {
			
		}
		
	    public Env visit(CPP.Absyn.SDecls p, Env env) {
	    	
	    	for(String id : p.listid_) {
	    		env.addVar(id, convertTypeCode(p.type_));
	    	}
	    	
	    	return null;
	    }
	    
	    public Env visit(CPP.Absyn.SInit p, Env env) {
	    	
	    }
	    
	    public Env visit(CPP.Absyn.SReturn p, Env env) {
	    	
	    }
	    
	    public Env visit(CPP.Absyn.SWhile p, Env env) {
	    	

	    }
	    public Env visit(CPP.Absyn.SBlock p, Env env) {
	    	
	    }
	    
	    public Env visit(CPP.Absyn.SIfElse p, Env env) {
	    	
	    }
	}
	
	//Used to determine exp type
	public TypeCode checkExp(Exp exp , Env env)
    {
        return exp.accept(new CheckExp() , env );
    }
	
	//Expressions
	public class CheckExp implements Exp.Visitor<TypeCode,Env> {

		public TypeCode visit(CPP.Absyn.ETrue p, Env env) {
			return TypeCode.CBool;
		}
		
	    public TypeCode visit(CPP.Absyn.EFalse p, Env env) {
	    	return TypeCode.CBool;
	    }
	    
	    public TypeCode visit(CPP.Absyn.EInt p, Env env) {
	    	return TypeCode.CInt;
	    }
	    
	    public TypeCode visit(CPP.Absyn.EDouble p, Env env) {
	    	return TypeCode.CDouble;
	    }
	    
	    public TypeCode visit(CPP.Absyn.EId p, Env env) {
	    	return env.lookupVar(p.id_);
	    }
	    
	    public TypeCode visit(CPP.Absyn.EApp p, Env env) {
	    	p.
	    }
	    
	    public TypeCode visit(CPP.Absyn.EPostIncr p, Env env) {
	    	
	    	TypeCode t = checkExp(p, env);
	    	
	    	if((t == TypeCode.CVoid) || (t == TypeCode.CBool)) {
	    		throw new TypeException("Cant Incr Void or Boolean");
	    	}
	    	
	    	return t;
	    }
	    
	    public TypeCode visit(CPP.Absyn.EPostDecr p, Env env) {
	    	
	    	TypeCode t = checkExp(p, env);
	    	
	    	if((t == TypeCode.CVoid) || (t == TypeCode.CBool)) {
	    		throw new TypeException("Cant Decr Void or Boolean");
	    	}
	    	
	    	return t;
	    }
	    
	    public TypeCode visit(CPP.Absyn.EPreIncr p, Env env) {

	    	TypeCode t = checkExp(p, env);
	    	
	    	if((t == TypeCode.CVoid) || (t == TypeCode.CBool)) {
	    		throw new TypeException("Cant Incr Void or Boolean");
	    	}
	    	
	    	return t;
	    }
	    
	    public TypeCode visit(CPP.Absyn.EPreDecr p, Env env) {
	    	
	    	TypeCode t = checkExp(p, env);
	    	
	    	if((t == TypeCode.CVoid) || (t == TypeCode.CBool)) {
	    		throw new TypeException("Cant Decr Void or Boolean");
	    	}
	    	
	    	return t;
	    }
	    
	    public TypeCode visit(CPP.Absyn.ETimes p, Env env) {
	    	
	    	TypeCode t1 = checkExp(p.exp_1, env);
	    	TypeCode t2 = checkExp(p.exp_2, env);
	    	
	    	if(t1 == t2) {
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CBool || t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CVoid) {
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
	    		if(t1 == TypeCode.CVoid) {
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
	    		if(t1 != TypeCode.CBool) {
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
	    		if(t1 != TypeCode.CBool) {
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

	
	public class TypeCodes implements Type.Visitor<TypeCode,Object> {
		
		public TypeCode visit(CPP.Absyn.Type_bool p, Object arg) {
			return TypeCode.CBool;
		}
		
	    public TypeCode visit(CPP.Absyn.Type_int p, Object arg) {
	    	return TypeCode.CInt;
	    }
	    
	    public TypeCode visit(CPP.Absyn.Type_double p, Object arg) {
	    	return TypeCode.CDouble;
	    }
	    
	    public TypeCode visit(CPP.Absyn.Type_void p, Object arg) {
	    	return TypeCode.CVoid;
	    }
	}

}

