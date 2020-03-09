package interpreter;

import nodes.AST;
import symbols.ScopedSymbolTable;
import symbols.Symbol;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {


        Lexer lexer=new Lexer("PROGRAM Main;\n" +
                "   PROCEDURE AlphaA(a : INTEGER ; b : INTEGER);\n" +
                "      VAR x : INTEGER;\n" +
                "   BEGIN { AlphaA }\n" +
                "       x := (a + b) * 2;\n" +
                "   END;  { AlphaA }\n" +
                "\n" +
                "BEGIN { Main }\n" +
                "   AlphaA (3+5,7);\n" +
                "END.");
        Parser parser=new Parser(lexer);
        AST res=parser.program();

        SemanticAnalyzer.visit(res);

        System.out.println("---------------");

        Interpreter.visit(res);
//        Map<String,Double> map=Interpreter.getGlobalMemory();
//        Map<String, Symbol> map= SemanticAnalyzer.getTable().getTable();
//        System.out.println(SemanticAnalyzer.getTable().getScope_name()+" "+SemanticAnalyzer.getTable().getScope_level());
//        for(Map.Entry<String, Symbol> entry : map.entrySet()){
////            System.out.println("---");
//            System.out.print(entry.getKey()+"=");
//            if (entry.getValue().getType()!=null){
//                System.out.println(entry.getValue().getType().getName());
//            }
//            else{
//                System.out.println(entry.getValue().getName());
//            }
//        }


//        interpreter.Parser parser=new interpreter.Parser(lexer);
//        nodes.AST res=parser.expr();
//        int r= interpreter.Interpreter.visit(res);
//        System.out.println();
//        System.out.println(r);


    }
}
