package nodes;

import interpreter.Token;

public class Assign extends AST {

    private AST left;
    private Token op;
    private AST right;

    public Assign(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public AST getLeft() {
        return left;
    }

    public void setLeft(AST left) {
        this.left = left;
    }

    public Token getOp() {
        return op;
    }

    public void setOp(Token op) {
        this.op = op;
    }

    public AST getRight() {
        return right;
    }

    public void setRight(AST right) {
        this.right = right;
    }
}
