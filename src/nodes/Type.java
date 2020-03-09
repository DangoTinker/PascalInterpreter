package nodes;

import interpreter.Token;

public class Type extends AST {

    private Token token;
    private String value;

    public Type(Token token) {
        this.token = token;
        this.value = (String) token.getValue();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
