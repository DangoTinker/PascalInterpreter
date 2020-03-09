package interpreter;

import nodes.*;
import symbols.ProcedureSymbol;
import symbols.ScopedSymbolTable;
import symbols.Symbol;
import symbols.VarSymbol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SemanticAnalyzer {
    private static ScopedSymbolTable current_scope=new ScopedSymbolTable("buildin",0,null);

//    private static HashMap<String,AST> AST_map =new HashMap<String, AST>();


    public static ScopedSymbolTable getTable() {
        return current_scope;
    }

    public static void visit(AST node) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class clazz= SemanticAnalyzer.class;
        Method method=clazz.getMethod("visit_"+node.getClass().getSimpleName(), AST.class);

        method.invoke(method,node);
    }

    public static void visit_UnaryOp(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        visit(((UnaryOp)node).getExpr() );
    }

    public static void visit_BinOp(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        visit(((BinOp)node).getLeft());
        visit(((BinOp)node).getRight());
    }

    public static void visit_Num(AST node){

    }

    public static void visit_Compound(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(node instanceof Compound){
            for (AST ast:
                    ((Compound) node).getChildren()) {
                visit(ast);
            }
        }
    }

    public static void visit_Assign(AST node) throws Exception {
        if(node instanceof Assign){
            Assign ast=(Assign)node;
            Var v= (Var) ast.getLeft();
            Symbol symbol= current_scope.lookup((String) v.getVar().getValue());


            if(symbol==null){
                throw new Exception("NameError:"+v.getVar().getValue());
            }
            visit(ast.getRight());

        }

    }

    public static void visit_Var(AST node) throws Exception {
        String var= (String) ((Var)node).getVar().getValue();

        Symbol symbol= current_scope.lookup(var);


        if(symbol==null){
            throw new Exception("NameError:"+var);
        }
    }

    public static void visit_NoOp(AST node){
        return;
    }

    public static void visit_Program(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        System.out.println("enter global");
        ScopedSymbolTable global_scope =new ScopedSymbolTable("global",1,current_scope);
        current_scope=global_scope;
        if(node instanceof Program){
            visit(((Program) node).getBlock());
        }

        Map<String, Symbol> map= global_scope.getTable();
        for(Map.Entry<String, Symbol> entry : map.entrySet()){
            System.out.print(entry.getKey()+"=");
            if (entry.getValue().getType()!=null){
                System.out.println(entry.getValue().getType().getName());
            }
            else{
                System.out.println(entry.getValue().getName());
            }
        }

        current_scope=current_scope.getEnclosing_scope();

        System.out.println("level:"+global_scope.getScope_level());

        System.out.println("enclosing_scope:"+global_scope.getEnclosing_scope());



        System.out.println("leave global");
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
        String name=((VarDecl)node).getName();
        Type type=((VarDecl)node).getType();

        Symbol symbol= current_scope.lookup(type.getValue());

        current_scope.insert(new Symbol(name,symbol));


    }
    public static void visit_Type(AST node){
        //TODO
    }

    public static void visit_ProcedureDecl(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //TODO

        ProcedureDecl procedureDecl= (ProcedureDecl) node;
        ProcedureSymbol procedureSymbol=new ProcedureSymbol(procedureDecl.getName(),null);
        procedureSymbol.setBlock_ast(procedureDecl.getBlock());

        current_scope.insert(procedureSymbol);

        System.out.println("enter scope:"+procedureSymbol.getName());


        ScopedSymbolTable produce_scope=new ScopedSymbolTable(procedureDecl.getName(),current_scope.getScope_level()+1,current_scope);
        current_scope=produce_scope;

        for (Param a :
                procedureDecl.getParams()) {
            Symbol type=current_scope.lookup(a.getType().getValue());
            VarSymbol varSymbol=new VarSymbol((String) a.getVar().getVar().getValue(),type);
            current_scope.insert(varSymbol);
            procedureSymbol.getParams().add(varSymbol);
        }

        visit(procedureDecl.getBlock());


//        AST_map.put(current_scope.getScope_name(),node);


        current_scope=current_scope.getEnclosing_scope();

        Map<String, Symbol> map= produce_scope.getTable();
        for(Map.Entry<String, Symbol> entry : map.entrySet()){
            System.out.print(entry.getKey()+"=");
            if (entry.getValue().getType()!=null){
                System.out.println(entry.getValue().getType().getName());
            }
            else{
                System.out.println(entry.getValue().getName());
            }
        }
        System.out.println("level:"+produce_scope.getScope_level());
        System.out.println("enclosing_scope:"+produce_scope.getEnclosing_scope().getScope_name());
        System.out.println("leave "+procedureSymbol.getName());


    }

    public static void visit_ProcedureCall(AST node) throws Exception {
        ProcedureCall procedureCall= (ProcedureCall) node;

//        int n= ((ProcedureDecl)AST_map.get(procedureCall.getName())).getParams().size();

        int n=((ProcedureSymbol)current_scope.lookup(procedureCall.getName())).getParams().size();
        procedureCall.setProcedureSymbol((ProcedureSymbol) current_scope.lookup(procedureCall.getName()));

        if(n!=procedureCall.getParams().size()){
            throw new Exception("params error");
        }

        for (AST ast :
                procedureCall.getParams()) {
            visit(ast);

        }
    }




}
