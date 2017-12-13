package CPP.Absyn; // Java Package generated by the BNF Converter.

public class DMain extends Main {
  public final Exp exp_;
  public DMain(Exp p1) { exp_ = p1; }

  public <R,A> R accept(CPP.Absyn.Main.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof CPP.Absyn.DMain) {
      CPP.Absyn.DMain x = (CPP.Absyn.DMain)o;
      return this.exp_.equals(x.exp_);
    }
    return false;
  }

  public int hashCode() {
    return this.exp_.hashCode();
  }


}
