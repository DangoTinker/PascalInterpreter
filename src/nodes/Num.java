package nodes;

import interpreter.Token;

public class Num extends AST {

    private Token token;
    private Double value;

    public Num(Token token) {
        this.token = token;
        if(token.getValue() instanceof Integer)
            this.value = Double.valueOf((Integer) token.getValue());
        else
            this.value= (Double) token.getValue();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
