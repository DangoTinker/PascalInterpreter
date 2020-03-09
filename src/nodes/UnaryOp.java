package nodes;

import interpreter.Token;

public class UnaryOp extends AST{
    private Token op;
    private AST expr;

    public UnaryOp(Token op, AST expr) {
        this.op = op;
        this.expr = expr;
    }

    public Token getOp() {
        return op;
    }

    public void setOp(Token op) {
        this.op = op;
    }

    public AST getExpr() {
        return expr;
    }

    public void setExpr(AST expr) {
        this.expr = expr;
    }
}
