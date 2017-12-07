import java_cup.runtime.*;
import CPP.*;
import CPP.Absyn.*;
import java.io.*;

public class lab3 {
    public static void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Usage: lab3 <SourceFile>");
            System.exit(1);
        }

        Yylex l = null;
        try {
            l = new Yylex(new FileReader(args[0]));
            parser p = new parser(l);
            CPP.Absyn.Program parse_tree = p.pProgram();
            new TypeChecker().typecheck(parse_tree);
            String filename = args[0];
            String filepath = args[0];
            int a = filename.lastIndexOf("/");
            filename = filename.substring(a+1 , filename.length() - 3);
            filepath = filepath.substring(0 , filepath.length() -3 );
            new CodeGenerator().generateCode(parse_tree , filename, filepath);

        } catch (TypeException e) {
            System.out.println("TYPE ERROR");
            System.err.println(e.toString());
            System.exit(1);
        } catch (RuntimeException e) {
            //            System.out.println("RUNTIME ERROR");
            System.err.println(e.toString());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        } catch (Throwable e) {
            System.out.println("SYNTAX ERROR");
            System.out.println("At line " + String.valueOf(l.line_num())
                    + ", near \"" + l.buff() + "\" :");
            System.out.println("     " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
