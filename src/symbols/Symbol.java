package symbols;

public class Symbol {

    private String name;
    private Symbol type;


    public Symbol(String name, Symbol type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getType() {
        return type;
    }

    public void setType(Symbol type) {
        this.type = type;
    }
}
