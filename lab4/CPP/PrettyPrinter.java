package CPP;
import CPP.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  private static final int INDENT_WIDTH = 2;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + INDENT_WIDTH;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       int t;
       _n_ = _n_ - INDENT_WIDTH;
       for(t=0; t<INDENT_WIDTH; t++) {
         backup();
       }
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(CPP.Absyn.Program foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(CPP.Absyn.Program foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(CPP.Absyn.Main foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(CPP.Absyn.Main foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(CPP.Absyn.Def foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(CPP.Absyn.Def foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(CPP.Absyn.ListDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(CPP.Absyn.ListDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(CPP.Absyn.ListIdent foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(CPP.Absyn.ListIdent foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(CPP.Absyn.Exp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(CPP.Absyn.Exp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(CPP.Absyn.Program foo, int _i_)
  {
    if (foo instanceof CPP.Absyn.Prog)
    {
       CPP.Absyn.Prog _prog = (CPP.Absyn.Prog) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_prog.listdef_, 0);
       pp(_prog.main_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(CPP.Absyn.Main foo, int _i_)
  {
    if (foo instanceof CPP.Absyn.DMain)
    {
       CPP.Absyn.DMain _dmain = (CPP.Absyn.DMain) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("main");
       render("=");
       render("print");
       pp(_dmain.exp_, 3);
       render(";");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(CPP.Absyn.Def foo, int _i_)
  {
    if (foo instanceof CPP.Absyn.DDef)
    {
       CPP.Absyn.DDef _ddef = (CPP.Absyn.DDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ddef.ident_, 0);
       pp(_ddef.listident_, 0);
       render("=");
       pp(_ddef.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(CPP.Absyn.ListDef foo, int _i_)
  {
     for (java.util.Iterator<Def> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), _i_);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }  }

  private static void pp(CPP.Absyn.ListIdent foo, int _i_)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), _i_);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }  }

  private static void pp(CPP.Absyn.Exp foo, int _i_)
  {
    if (foo instanceof CPP.Absyn.EVar)
    {
       CPP.Absyn.EVar _evar = (CPP.Absyn.EVar) foo;
       if (_i_ > 3) render(_L_PAREN);
       pp(_evar.ident_, 0);
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.EInt)
    {
       CPP.Absyn.EInt _eint = (CPP.Absyn.EInt) foo;
       if (_i_ > 3) render(_L_PAREN);
       pp(_eint.integer_, 0);
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.EApp)
    {
       CPP.Absyn.EApp _eapp = (CPP.Absyn.EApp) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_eapp.exp_1, 2);
       pp(_eapp.exp_2, 3);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.EAdd)
    {
       CPP.Absyn.EAdd _eadd = (CPP.Absyn.EAdd) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_eadd.exp_1, 1);
       render("+");
       pp(_eadd.exp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.ESub)
    {
       CPP.Absyn.ESub _esub = (CPP.Absyn.ESub) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_esub.exp_1, 1);
       render("-");
       pp(_esub.exp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.ELt)
    {
       CPP.Absyn.ELt _elt = (CPP.Absyn.ELt) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_elt.exp_1, 1);
       render("<");
       pp(_elt.exp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.EIf)
    {
       CPP.Absyn.EIf _eif = (CPP.Absyn.EIf) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("if");
       pp(_eif.exp_1, 1);
       render("then");
       pp(_eif.exp_2, 1);
       render("else");
       pp(_eif.exp_3, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof CPP.Absyn.EAbs)
    {
       CPP.Absyn.EAbs _eabs = (CPP.Absyn.EAbs) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("\\");
       pp(_eabs.ident_, 0);
       render("->");
       pp(_eabs.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(CPP.Absyn.Program foo)
  {
    if (foo instanceof CPP.Absyn.Prog)
    {
       CPP.Absyn.Prog _prog = (CPP.Absyn.Prog) foo;
       render("(");
       render("Prog");
       render("[");
       sh(_prog.listdef_);
       render("]");
       sh(_prog.main_);
       render(")");
    }
  }

  private static void sh(CPP.Absyn.Main foo)
  {
    if (foo instanceof CPP.Absyn.DMain)
    {
       CPP.Absyn.DMain _dmain = (CPP.Absyn.DMain) foo;
       render("(");
       render("DMain");
       sh(_dmain.exp_);
       render(")");
    }
  }

  private static void sh(CPP.Absyn.Def foo)
  {
    if (foo instanceof CPP.Absyn.DDef)
    {
       CPP.Absyn.DDef _ddef = (CPP.Absyn.DDef) foo;
       render("(");
       render("DDef");
       sh(_ddef.ident_);
       render("[");
       sh(_ddef.listident_);
       render("]");
       sh(_ddef.exp_);
       render(")");
    }
  }

  private static void sh(CPP.Absyn.ListDef foo)
  {
     for (java.util.Iterator<Def> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(CPP.Absyn.ListIdent foo)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(CPP.Absyn.Exp foo)
  {
    if (foo instanceof CPP.Absyn.EVar)
    {
       CPP.Absyn.EVar _evar = (CPP.Absyn.EVar) foo;
       render("(");
       render("EVar");
       sh(_evar.ident_);
       render(")");
    }
    if (foo instanceof CPP.Absyn.EInt)
    {
       CPP.Absyn.EInt _eint = (CPP.Absyn.EInt) foo;
       render("(");
       render("EInt");
       sh(_eint.integer_);
       render(")");
    }
    if (foo instanceof CPP.Absyn.EApp)
    {
       CPP.Absyn.EApp _eapp = (CPP.Absyn.EApp) foo;
       render("(");
       render("EApp");
       sh(_eapp.exp_1);
       sh(_eapp.exp_2);
       render(")");
    }
    if (foo instanceof CPP.Absyn.EAdd)
    {
       CPP.Absyn.EAdd _eadd = (CPP.Absyn.EAdd) foo;
       render("(");
       render("EAdd");
       sh(_eadd.exp_1);
       sh(_eadd.exp_2);
       render(")");
    }
    if (foo instanceof CPP.Absyn.ESub)
    {
       CPP.Absyn.ESub _esub = (CPP.Absyn.ESub) foo;
       render("(");
       render("ESub");
       sh(_esub.exp_1);
       sh(_esub.exp_2);
       render(")");
    }
    if (foo instanceof CPP.Absyn.ELt)
    {
       CPP.Absyn.ELt _elt = (CPP.Absyn.ELt) foo;
       render("(");
       render("ELt");
       sh(_elt.exp_1);
       sh(_elt.exp_2);
       render(")");
    }
    if (foo instanceof CPP.Absyn.EIf)
    {
       CPP.Absyn.EIf _eif = (CPP.Absyn.EIf) foo;
       render("(");
       render("EIf");
       sh(_eif.exp_1);
       sh(_eif.exp_2);
       sh(_eif.exp_3);
       render(")");
    }
    if (foo instanceof CPP.Absyn.EAbs)
    {
       CPP.Absyn.EAbs _eabs = (CPP.Absyn.EAbs) foo;
       render("(");
       render("EAbs");
       sh(_eabs.ident_);
       sh(_eabs.exp_);
       render(")");
    }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
     if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}

