package nodes;

import interpreter.Token;
import symbols.ProcedureSymbol;

import java.util.LinkedList;

public class ProcedureCall extends AST {

    private String name;
    private LinkedList<AST> params;
    private Token token;
    private ProcedureSymbol procedureSymbol;


    public ProcedureCall(String name, LinkedList<AST> params, Token token,ProcedureSymbol procedureSymbol) {
        this.name = name;
        this.params = params;
        this.token = token;
        this.procedureSymbol=procedureSymbol;
    }

    public ProcedureSymbol getProcedureSymbol() {
        return procedureSymbol;
    }

    public void setProcedureSymbol(ProcedureSymbol procedureSymbol) {
        this.procedureSymbol = procedureSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<AST> getParams() {
        return params;
    }

    public void setParams(LinkedList<AST> params) {
        this.params = params;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
