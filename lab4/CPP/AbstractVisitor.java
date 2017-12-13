package CPP;
import CPP.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Program */
    public R visit(CPP.Absyn.Prog p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(CPP.Absyn.Program p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Main */
    public R visit(CPP.Absyn.DMain p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(CPP.Absyn.Main p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Def */
    public R visit(CPP.Absyn.DDef p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(CPP.Absyn.Def p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Exp */
    public R visit(CPP.Absyn.EVar p, A arg) { return visitDefault(p, arg); }
    public R visit(CPP.Absyn.EInt p, A arg) { return visitDefault(p, arg); }

    public R visit(CPP.Absyn.EApp p, A arg) { return visitDefault(p, arg); }

    public R visit(CPP.Absyn.EAdd p, A arg) { return visitDefault(p, arg); }
    public R visit(CPP.Absyn.ESub p, A arg) { return visitDefault(p, arg); }
    public R visit(CPP.Absyn.ELt p, A arg) { return visitDefault(p, arg); }

    public R visit(CPP.Absyn.EIf p, A arg) { return visitDefault(p, arg); }
    public R visit(CPP.Absyn.EAbs p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(CPP.Absyn.Exp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
