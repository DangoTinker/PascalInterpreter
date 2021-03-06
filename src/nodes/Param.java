package nodes;

public class Param  extends AST {

    private Var var;
    private Type type;

    public Param(Var var, Type type) {
        this.var = var;
        this.type = type;
    }

    public Var getVar() {
        return var;
    }

    public void setVar(Var var) {
        this.var = var;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
