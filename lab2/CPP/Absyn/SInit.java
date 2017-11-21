package CPP.Absyn; // Java Package generated by the BNF Converter.

public class SInit extends Stm {
  public final Type type_;
  public final String id_;
  public final Exp exp_;

  public SInit(Type p1, String p2, Exp p3) { type_ = p1; id_ = p2; exp_ = p3; }

  public <R,A> R accept(CPP.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof CPP.Absyn.SInit) {
      CPP.Absyn.SInit x = (CPP.Absyn.SInit)o;
      return this.type_.equals(x.type_) && this.id_.equals(x.id_) && this.exp_.equals(x.exp_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.type_.hashCode())+this.id_.hashCode())+this.exp_.hashCode();
  }


}
