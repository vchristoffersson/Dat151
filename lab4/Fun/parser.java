
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Dec 18 16:09:09 CET 2017
//----------------------------------------------------

package Fun;


/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Dec 18 16:09:09 CET 2017
  */
public class parser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\024\000\002\002\004\000\002\002\004\000\002\003" +
    "\007\000\002\004\006\000\002\005\002\000\002\005\005" +
    "\000\002\006\002\000\002\006\004\000\002\007\003\000" +
    "\002\007\003\000\002\007\005\000\002\010\004\000\002" +
    "\010\003\000\002\011\005\000\002\011\005\000\002\011" +
    "\005\000\002\011\003\000\002\012\010\000\002\012\006" +
    "\000\002\012\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\053\000\006\017\ufffd\023\ufffd\001\002\000\004\002" +
    "\055\001\002\000\006\017\011\023\007\001\002\000\004" +
    "\005\054\001\002\000\006\004\ufffb\023\050\001\002\000" +
    "\004\002\000\001\002\000\004\004\012\001\002\000\004" +
    "\020\013\001\002\000\010\013\017\022\016\023\015\001" +
    "\002\000\004\005\046\001\002\000\026\005\ufff9\006\ufff9" +
    "\007\ufff9\010\ufff9\013\ufff9\014\ufff9\015\ufff9\021\ufff9\022" +
    "\ufff9\023\ufff9\001\002\000\026\005\ufff8\006\ufff8\007\ufff8" +
    "\010\ufff8\013\ufff8\014\ufff8\015\ufff8\021\ufff8\022\ufff8\023" +
    "\ufff8\001\002\000\014\011\020\013\017\016\022\022\016" +
    "\023\015\001\002\000\004\023\043\001\002\000\004\014" +
    "\042\001\002\000\010\013\017\022\016\023\015\001\002" +
    "\000\026\005\ufff5\006\ufff5\007\ufff5\010\ufff5\013\ufff5\014" +
    "\ufff5\015\ufff5\021\ufff5\022\ufff5\023\ufff5\001\002\000\026" +
    "\005\ufff1\006\ufff1\007\ufff1\010\ufff1\013\017\014\ufff1\015" +
    "\ufff1\021\ufff1\022\016\023\015\001\002\000\014\005\uffee" +
    "\006\030\007\027\010\026\014\uffee\001\002\000\010\013" +
    "\017\022\016\023\015\001\002\000\010\013\017\022\016" +
    "\023\015\001\002\000\010\013\017\022\016\023\015\001" +
    "\002\000\026\005\ufff4\006\ufff4\007\ufff4\010\ufff4\013\017" +
    "\014\ufff4\015\ufff4\021\ufff4\022\016\023\015\001\002\000" +
    "\026\005\ufff6\006\ufff6\007\ufff6\010\ufff6\013\ufff6\014\ufff6" +
    "\015\ufff6\021\ufff6\022\ufff6\023\ufff6\001\002\000\026\005" +
    "\ufff3\006\ufff3\007\ufff3\010\ufff3\013\017\014\ufff3\015\ufff3" +
    "\021\ufff3\022\016\023\015\001\002\000\026\005\ufff2\006" +
    "\ufff2\007\ufff2\010\ufff2\013\017\014\ufff2\015\ufff2\021\ufff2" +
    "\022\016\023\015\001\002\000\012\006\030\007\027\010" +
    "\026\021\036\001\002\000\010\013\017\022\016\023\015" +
    "\001\002\000\012\006\030\007\027\010\026\015\040\001" +
    "\002\000\014\011\020\013\017\016\022\022\016\023\015" +
    "\001\002\000\006\005\ufff0\014\ufff0\001\002\000\026\005" +
    "\ufff7\006\ufff7\007\ufff7\010\ufff7\013\ufff7\014\ufff7\015\ufff7" +
    "\021\ufff7\022\ufff7\023\ufff7\001\002\000\004\012\044\001" +
    "\002\000\014\011\020\013\017\016\022\022\016\023\015" +
    "\001\002\000\006\005\uffef\014\uffef\001\002\000\004\002" +
    "\uffff\001\002\000\004\004\052\001\002\000\006\004\ufffb" +
    "\023\050\001\002\000\004\004\ufffa\001\002\000\014\011" +
    "\020\013\017\016\022\022\016\023\015\001\002\000\004" +
    "\005\ufffe\001\002\000\006\017\ufffc\023\ufffc\001\002\000" +
    "\004\002\001\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\053\000\006\002\003\005\004\001\001\000\002\001" +
    "\001\000\006\003\007\004\005\001\001\000\002\001\001" +
    "\000\004\006\046\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\007\013\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\012\007" +
    "\022\010\023\011\024\012\020\001\001\000\002\001\001" +
    "\000\002\001\001\000\010\007\022\010\023\011\034\001" +
    "\001\000\002\001\001\000\004\007\031\001\001\000\002" +
    "\001\001\000\006\007\022\010\033\001\001\000\006\007" +
    "\022\010\032\001\001\000\006\007\022\010\030\001\001" +
    "\000\004\007\031\001\001\000\002\001\001\000\004\007" +
    "\031\001\001\000\004\007\031\001\001\000\002\001\001" +
    "\000\010\007\022\010\023\011\036\001\001\000\002\001" +
    "\001\000\012\007\022\010\023\011\024\012\040\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\012\007\022\010\023\011\024\012\044\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\006" +
    "\050\001\001\000\002\001\001\000\012\007\022\010\023" +
    "\011\024\012\052\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



  public Fun.Absyn.Program pProgram() throws Exception
  {
	java_cup.runtime.Symbol res = parse();
	return (Fun.Absyn.Program) res.value;
  }

public <B,A extends java.util.LinkedList<? super B>> A cons_(B x, A xs) { xs.addFirst(x); return xs; }

public void syntax_error(java_cup.runtime.Symbol cur_token)
{
	report_error("Syntax Error, trying to recover and continue parse...", cur_token);
}

public void unrecovered_syntax_error(java_cup.runtime.Symbol cur_token) throws java.lang.Exception
{
	throw new Exception("Unrecoverable Syntax Error");
}


}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {
  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // Exp ::= Exp1 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // Exp ::= _SYMB_5 _IDENT_ _SYMB_6 Exp 
            {
              Fun.Absyn.Exp RESULT =null;
		String p_2 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Exp p_4 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.EAbs(p_2,p_4); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // Exp ::= _SYMB_10 Exp1 _SYMB_13 Exp1 _SYMB_9 Exp 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_2 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-4)).value;
		Fun.Absyn.Exp p_4 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Exp p_6 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.EIf(p_2,p_4,p_6); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // Exp1 ::= Exp2 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp1",7, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // Exp1 ::= Exp1 _SYMB_4 Exp2 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Exp p_3 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.ELt(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp1",7, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // Exp1 ::= Exp1 _SYMB_3 Exp2 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Exp p_3 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.ESub(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp1",7, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // Exp1 ::= Exp1 _SYMB_2 Exp2 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Exp p_3 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.EAdd(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp1",7, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // Exp2 ::= Exp3 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp2",6, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // Exp2 ::= Exp2 Exp3 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_1 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		Fun.Absyn.Exp p_2 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.EApp(p_1,p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp2",6, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // Exp3 ::= _SYMB_7 Exp _SYMB_8 
            {
              Fun.Absyn.Exp RESULT =null;
		Fun.Absyn.Exp p_2 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = p_2; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp3",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // Exp3 ::= _INTEGER_ 
            {
              Fun.Absyn.Exp RESULT =null;
		Integer p_1 = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.EInt(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp3",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // Exp3 ::= _IDENT_ 
            {
              Fun.Absyn.Exp RESULT =null;
		String p_1 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.EVar(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Exp3",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // ListIdent ::= _IDENT_ ListIdent 
            {
              Fun.Absyn.ListIdent RESULT =null;
		String p_1 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		Fun.Absyn.ListIdent p_2 = (Fun.Absyn.ListIdent)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_2; p_2.addFirst(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListIdent",4, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // ListIdent ::= 
            {
              Fun.Absyn.ListIdent RESULT =null;
		 RESULT = new Fun.Absyn.ListIdent(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListIdent",4, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // ListDef ::= ListDef Def _SYMB_1 
            {
              Fun.Absyn.ListDef RESULT =null;
		Fun.Absyn.ListDef p_1 = (Fun.Absyn.ListDef)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Def p_2 = (Fun.Absyn.Def)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = p_1; p_1.addLast(p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListDef",3, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // ListDef ::= 
            {
              Fun.Absyn.ListDef RESULT =null;
		 RESULT = new Fun.Absyn.ListDef(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListDef",3, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // Def ::= _IDENT_ ListIdent _SYMB_0 Exp 
            {
              Fun.Absyn.Def RESULT =null;
		String p_1 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		Fun.Absyn.ListIdent p_2 = (Fun.Absyn.ListIdent)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		Fun.Absyn.Exp p_4 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.DDef(p_1,p_2,p_4); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Def",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // Main ::= _SYMB_11 _SYMB_0 _SYMB_12 Exp3 _SYMB_1 
            {
              Fun.Absyn.Main RESULT =null;
		Fun.Absyn.Exp p_4 = (Fun.Absyn.Exp)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new Fun.Absyn.DMain(p_4); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Main",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // Program ::= ListDef Main 
            {
              Fun.Absyn.Program RESULT =null;
		Fun.Absyn.ListDef p_1 = (Fun.Absyn.ListDef)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		Fun.Absyn.Main p_2 = (Fun.Absyn.Main)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Fun.Absyn.Prog(p_1,p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Program",0, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= Program EOF 
            {
              Object RESULT =null;
		Fun.Absyn.Program start_val = (Fun.Absyn.Program)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}
