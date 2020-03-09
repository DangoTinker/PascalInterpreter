package interpreter;

import memory.ActivationRecord;
import nodes.*;
import symbols.ProcedureSymbol;
import symbols.VarSymbol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Interpreter {


    private Parser parser;


//    private static Map<String,Double> GLOBAL_MEMORY =new HashMap<String,Double>();

    private static Stack<ActivationRecord> call_stack=new Stack<ActivationRecord>();


//    public static Map<String, Double> getGlobalMemory() {
//        return GLOBAL_MEMORY;
//    }

    public static Stack<ActivationRecord> getCall_stack() {
        return call_stack;
    }

    public Interpreter(Parser parser) throws Exception {
        this.parser=parser;
    }

    public static Double visit(AST node) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class clazz= Interpreter.class;
//        System.out.println("visit_"+node.getClass().getSimpleName());
        Method method=clazz.getMethod("visit_"+node.getClass().getSimpleName(), AST.class);

//        if(node instanceof nodes.BinOp){
//            System.out.print(((nodes.BinOp) node).getOp().getValue());
//        }else if(node instanceof nodes.Num){
//            System.out.print(((nodes.Num) node).getValue());
//        }

        return (Double) method.invoke(method,node);
    }

    public static Double visit_UnaryOp(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(((UnaryOp)node).getOp().getType().equals("PLUS")){
            return visit(((UnaryOp) node).getExpr());
        }else if(((UnaryOp)node).getOp().getType().equals("MINUS")){
            return -visit(((UnaryOp) node).getExpr());
        }else {
            return null;
        }
    }

    public static Double visit_BinOp(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(((BinOp)node).getOp().getValue().equals("+") ){
            Double a=visit(((BinOp) node).getLeft());
            Double b=visit(((BinOp) node).getRight());
            return a+ b;
        }else if(((BinOp)node).getOp().getValue().equals("-") ){
            Double a=visit(((BinOp) node).getLeft());
            Double b=visit(((BinOp) node).getRight());
            return a- b;
        }else if(((BinOp)node).getOp().getValue().equals("*") ){
            Double a=visit(((BinOp) node).getLeft());
            Double b=visit(((BinOp) node).getRight());
            return a* b;
        }else if(((BinOp)node).getOp().getType().equals("FLOAT_DIV") ){
            Double a=visit(((BinOp) node).getLeft());
            Double b=visit(((BinOp) node).getRight());
            return a/ b;
        }else if(((BinOp)node).getOp().getType().equals("DIV") ){
            Double a=visit(((BinOp) node).getLeft());
            Double b=visit(((BinOp) node).getRight());
            int temp=Integer.valueOf((int) (a/b));

            return Double.valueOf(temp);
        }
        else{
            return null;
        }
    }

    public static Double visit_Num(AST node){
        if(node instanceof BinOp){
//            System.out.print(((BinOp) node).getOp().getValue());
        }else if(node instanceof Num){
//            System.out.print(((Num) node).getValue());
        }
        return ((Num)node).getValue();
    }

    public static void visit_Compound(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(node instanceof Compound){
            for (AST ast:
                    ((Compound) node).getChildren()) {
                visit(ast);
            }
        }
    }

    public static void visit_Assign(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(node instanceof Assign){
            Assign ast=(Assign)node;
//            GLOBAL_MEMORY.put((String)(((Var)(ast.getLeft())).getVar().getValue()),visit(ast.getRight()));

            ActivationRecord ar=call_stack.peek();
            ar.getMembers().put((String)(((Var)(ast.getLeft())).getVar().getValue()),visit(ast.getRight()));

        }

    }

    public static Double visit_Var(AST node){
        String var= (String) ((Var)node).getVar().getValue();
        if(var!=null)
//            return GLOBAL_MEMORY.get(var);
              return call_stack.peek().getMembers().get(var);
        else
            return null;
    }

    public static void visit_NoOp(AST node){
        return;
    }

    public static void visit_Program(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(node instanceof Program){

            Program program= (Program) node;

            ActivationRecord ar=new ActivationRecord(program.getName(),"PROGRAM",1);

            call_stack.push(ar);

            visit(((Program) node).getBlock());




            System.out.println();
            System.out.println("----------------");
            System.out.println("call_stack_pop:"+ar.getName());
            for(Map.Entry<String, Double> entry : ar.getMembers().entrySet()){
                System.out.println("asd");
                System.out.println(entry.getKey()+"="+entry.getValue());
            }

            System.out.println("----------------");
            System.out.println();


            call_stack.pop();
        }
    }

    public static void visit_Block(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        if(node instanceof Block){

            Block block=(Block)node;

            for (AST v :
                    block.getDeclarations()) {



                visit(v);

            }

            visit(block.getCompound());

        }

    }


    public static void visit_VarDecl(AST node){

        //TODO
    }
    public static void visit_Type(AST node){
        //TODO
    }

    public static void visit_ProcedureDecl(AST node){
        //TODO
    }


    public static void visit_ProcedureCall(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String name=((ProcedureCall)node).getName();

        ActivationRecord ar=new ActivationRecord(name,"PROCEDURE",2);

        HashMap<String,Double> map=ar.getMembers();

        ProcedureSymbol procedureSymbol=((ProcedureCall)node).getProcedureSymbol();

        LinkedList<VarSymbol> formal_params=procedureSymbol.getParams();
        LinkedList<AST> actual_params=((ProcedureCall)node).getParams();


        for (int i = 0; i < formal_params.size(); i++) {
            //TODO maybe error
            map.put(formal_params.get(i).getName(),visit(actual_params.get(i)));
        }

        call_stack.push(ar);

        visit(procedureSymbol.getBlock_ast());

        System.out.println();
        System.out.println("----------------");
        System.out.println("call_stack_pop:"+ar.getName());
        for(Map.Entry<String, Double> entry : ar.getMembers().entrySet()){
//            System.out.println("asd");
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

        System.out.println("----------------");
        System.out.println();



        call_stack.pop();


    }


}
