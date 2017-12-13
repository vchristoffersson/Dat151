package CPP;
import CPP.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  CPP.Absyn.Program.Visitor<CPP.Absyn.Program,A>,
  CPP.Absyn.Main.Visitor<CPP.Absyn.Main,A>,
  CPP.Absyn.Def.Visitor<CPP.Absyn.Def,A>,
  CPP.Absyn.Exp.Visitor<CPP.Absyn.Exp,A>
{
/* Program */
    public Program visit(CPP.Absyn.Prog p, A arg)
    {
      ListDef listdef_ = new ListDef();
      for (Def x : p.listdef_)
      {
        listdef_.add(x.accept(this,arg));
      }
      Main main_ = p.main_.accept(this, arg);
      return new CPP.Absyn.Prog(listdef_, main_);
    }
/* Main */
    public Main visit(CPP.Absyn.DMain p, A arg)
    {
      Exp exp_ = p.exp_.accept(this, arg);
      return new CPP.Absyn.DMain(exp_);
    }
/* Def */
    public Def visit(CPP.Absyn.DDef p, A arg)
    {
      String ident_ = p.ident_;
      ListIdent listident_ = p.listident_;
      Exp exp_ = p.exp_.accept(this, arg);
      return new CPP.Absyn.DDef(ident_, listident_, exp_);
    }
/* Exp */
    public Exp visit(CPP.Absyn.EVar p, A arg)
    {
      String ident_ = p.ident_;
      return new CPP.Absyn.EVar(ident_);
    }    public Exp visit(CPP.Absyn.EInt p, A arg)
    {
      Integer integer_ = p.integer_;
      return new CPP.Absyn.EInt(integer_);
    }    public Exp visit(CPP.Absyn.EApp p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);
      return new CPP.Absyn.EApp(exp_1, exp_2);
    }    public Exp visit(CPP.Absyn.EAdd p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);
      return new CPP.Absyn.EAdd(exp_1, exp_2);
    }    public Exp visit(CPP.Absyn.ESub p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);
      return new CPP.Absyn.ESub(exp_1, exp_2);
    }    public Exp visit(CPP.Absyn.ELt p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);
      return new CPP.Absyn.ELt(exp_1, exp_2);
    }    public Exp visit(CPP.Absyn.EIf p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);
      Exp exp_3 = p.exp_3.accept(this, arg);
      return new CPP.Absyn.EIf(exp_1, exp_2, exp_3);
    }    public Exp visit(CPP.Absyn.EAbs p, A arg)
    {
      String ident_ = p.ident_;
      Exp exp_ = p.exp_.accept(this, arg);
      return new CPP.Absyn.EAbs(ident_, exp_);
    }
}