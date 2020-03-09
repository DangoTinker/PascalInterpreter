package nodes;

import java.util.LinkedList;

public class Compound extends AST {
    private LinkedList<AST> children;

    public Compound(LinkedList<AST> children) {
        this.children = children;
    }

    public Compound() {
        children=new LinkedList<AST>();
    }

    public void addChild(AST ast){
        children.add(ast);
    }

    public LinkedList<AST> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<AST> children) {
        this.children = children;
    }
}
