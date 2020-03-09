package symbols;

import java.util.HashMap;

public class ScopedSymbolTable {

    private HashMap<String,Symbol> table;

    private String scope_name;
    private int scope_level;

    private ScopedSymbolTable enclosing_scope;



    public HashMap<String, Symbol> getTable() {
        return table;
    }

    public String getScope_name() {
        return scope_name;
    }

    public int getScope_level() {
        return scope_level;
    }

    public ScopedSymbolTable(String a, int b,ScopedSymbolTable enclosing_scope) {
        table=new HashMap<String, Symbol>();
        scope_name=a;
        scope_level=b;
        if(b==0)
            init_builtins();
        this.enclosing_scope=enclosing_scope;
    }

    public ScopedSymbolTable getEnclosing_scope() {
        return enclosing_scope;
    }

    public void setEnclosing_scope(ScopedSymbolTable enclosing_scope) {
        this.enclosing_scope = enclosing_scope;
    }

    public void init_builtins(){
        table.put("INTEGER",new BuiltinTypeSymbol("INTEGER",null));
        table.put("REAL",new BuiltinTypeSymbol("REAL",null));
    }

    public Symbol lookup(String name){
        Symbol symbol=table.get(name);
        if(symbol!=null){
            System.out.println("---------");
            System.out.println("scope:"+scope_name);
            System.out.println("var:"+symbol.getName());
            System.out.println("---------");
            return symbol;
        }
        if(enclosing_scope!=null){
            return enclosing_scope.lookup(name);
        }
        return null;

    }

    public Symbol insert(Symbol symbol){
        return table.put(symbol.getName(),symbol);
    }



}
