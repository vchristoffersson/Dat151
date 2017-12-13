package CPP;

import CPP.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  CPP.Absyn.Program.Visitor<R,A>,
  CPP.Absyn.Main.Visitor<R,A>,
  CPP.Absyn.Def.Visitor<R,A>,
  CPP.Absyn.Exp.Visitor<R,A>
{}
