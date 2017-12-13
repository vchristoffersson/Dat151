package CPP.Absyn; // Java Package generated by the BNF Converter.

public abstract class Exp implements java.io.Serializable {
  public abstract <R,A> R accept(Exp.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(CPP.Absyn.EVar p, A arg);
    public R visit(CPP.Absyn.EInt p, A arg);
    public R visit(CPP.Absyn.EApp p, A arg);
    public R visit(CPP.Absyn.EAdd p, A arg);
    public R visit(CPP.Absyn.ESub p, A arg);
    public R visit(CPP.Absyn.ELt p, A arg);
    public R visit(CPP.Absyn.EIf p, A arg);
    public R visit(CPP.Absyn.EAbs p, A arg);

  }

}