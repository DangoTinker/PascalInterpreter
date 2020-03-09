package symbols;

import java.util.HashMap;
import java.util.LinkedList;

public class SymbolTable {

    private HashMap<String,Symbol> table;



    public SymbolTable() {
        table=new HashMap<String, Symbol>();
        init_builtins();
    }

    public void init_builtins(){
        table.put("INTEGER",new BuiltinTypeSymbol("INTEGER",null));
        table.put("REAL",new BuiltinTypeSymbol("REAL",null));
    }

    public Symbol lookup(String name){

        return table.get(name);

    }

    public Symbol define(Symbol symbol){
        return table.put(symbol.getName(),symbol);
    }


}
