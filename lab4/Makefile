JAVAC=javac
JAVAC_FLAGS=-sourcepath .
JAVA=java
JAVA_FLAGS=
CUP=java_cup.Main
CUPFLAGS=-nopositions -expect 100
JLEX=JLex.Main
all: test

test: absyn CPP/Yylex.class CPP/PrettyPrinter.class CPP/Test.class CPP/ComposVisitor.class CPP/AbstractVisitor.class CPP/FoldVisitor.class CPP/AllVisitor.class CPP/parser.class CPP/sym.class CPP/Test.class

.PHONY: absyn

%.class: %.java
	${JAVAC} ${JAVAC_FLAGS} $^

absyn: CPP/Absyn/Program.java CPP/Absyn/Prog.java CPP/Absyn/Main.java CPP/Absyn/DMain.java CPP/Absyn/Def.java CPP/Absyn/DDef.java CPP/Absyn/ListDef.java CPP/Absyn/ListIdent.java CPP/Absyn/Exp.java CPP/Absyn/EVar.java CPP/Absyn/EInt.java CPP/Absyn/EApp.java CPP/Absyn/EAdd.java CPP/Absyn/ESub.java CPP/Absyn/ELt.java CPP/Absyn/EIf.java CPP/Absyn/EAbs.java
	${JAVAC} ${JAVAC_FLAGS} $^

CPP/Yylex.java: CPP/Yylex
	${JAVA} ${JAVA_FLAGS} ${JLEX} CPP/Yylex

CPP/sym.java CPP/parser.java: CPP/CPP.cup
	${JAVA} ${JAVA_FLAGS} ${CUP} ${CUPFLAGS} CPP/CPP.cup
	mv sym.java parser.java CPP/

CPP/Yylex.class: CPP/Yylex.java CPP/sym.java

CPP/sym.class: CPP/sym.java

CPP/parser.class: CPP/parser.java CPP/sym.java

CPP/PrettyPrinter.class: CPP/PrettyPrinter.java

clean:
	rm -f CPP/Absyn/*.class CPP/*.class

distclean: vclean

vclean:
	 rm -f CPP/Absyn/Program.java CPP/Absyn/Prog.java CPP/Absyn/Main.java CPP/Absyn/DMain.java CPP/Absyn/Def.java CPP/Absyn/DDef.java CPP/Absyn/ListDef.java CPP/Absyn/ListIdent.java CPP/Absyn/Exp.java CPP/Absyn/EVar.java CPP/Absyn/EInt.java CPP/Absyn/EApp.java CPP/Absyn/EAdd.java CPP/Absyn/ESub.java CPP/Absyn/ELt.java CPP/Absyn/EIf.java CPP/Absyn/EAbs.java CPP/Absyn/Program.class CPP/Absyn/Prog.class CPP/Absyn/Main.class CPP/Absyn/DMain.class CPP/Absyn/Def.class CPP/Absyn/DDef.class CPP/Absyn/ListDef.class CPP/Absyn/ListIdent.class CPP/Absyn/Exp.class CPP/Absyn/EVar.class CPP/Absyn/EInt.class CPP/Absyn/EApp.class CPP/Absyn/EAdd.class CPP/Absyn/ESub.class CPP/Absyn/ELt.class CPP/Absyn/EIf.class CPP/Absyn/EAbs.class
	 rm -f CPP/Absyn/*.class
	 rmdir CPP/Absyn/
	 rm -f CPP/Yylex CPP/CPP.cup CPP/Yylex.java CPP/VisitSkel.java CPP/ComposVisitor.java CPP/AbstractVisitor.java CPP/FoldVisitor.java CPP/AllVisitor.java CPP/PrettyPrinter.java CPP/Skeleton.java CPP/Test.java CPP/sym.java CPP/parser.java CPP/*.class
	rm -f Makefile
	rmdir -p CPP/

