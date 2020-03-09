package symbols;

import nodes.AST;
import nodes.Param;
import nodes.Var;

import java.util.LinkedList;

public class ProcedureSymbol extends Symbol {

    private LinkedList<VarSymbol> params;

    private AST block_ast;

    public ProcedureSymbol(String name, LinkedList<VarSymbol> params) {
        super(name, null);
        if(params==null){
            this.params=new LinkedList<VarSymbol>();
        }
        else {
            this.params = params;
        }
    }


    public AST getBlock_ast() {
        return block_ast;
    }

    public void setBlock_ast(AST block_ast) {
        this.block_ast = block_ast;
    }

    public LinkedList<VarSymbol> getParams() {
        return params;
    }

    public void setParams(LinkedList<VarSymbol> params) {
        this.params = params;
    }
}
