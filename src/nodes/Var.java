package nodes;

import interpreter.Token;

public class Var extends AST {

    private Token var;

    public Var(Token var) {
        this.var = var;
    }

    public Token getVar() {
        return var;
    }

    public void setVar(Token var) {
        this.var = var;
    }
}
