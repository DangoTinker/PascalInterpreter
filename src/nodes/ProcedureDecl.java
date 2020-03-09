package nodes;

import java.util.LinkedList;

public class ProcedureDecl extends AST {

    private String name;
    private AST block;

    private LinkedList<Param> params;


    public ProcedureDecl(String name, AST block, LinkedList<Param> params) {
        this.name = name;
        this.block = block;
        this.params = params;
    }

    public LinkedList<Param> getParams() {
        return params;
    }

    public void setParams(LinkedList<Param> params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AST getBlock() {
        return block;
    }

    public void setBlock(AST block) {
        this.block = block;
    }
}
