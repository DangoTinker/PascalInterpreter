package nodes;

import java.util.LinkedList;

public class Block  extends AST {

    private LinkedList<AST> declarations;

    private AST compound;

    public Block(LinkedList<AST> declarations, AST compound) {
        this.declarations = declarations;
        this.compound = compound;
    }

    public LinkedList<AST> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(LinkedList<AST> declarations) {
        this.declarations = declarations;
    }

    public AST getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }
}
