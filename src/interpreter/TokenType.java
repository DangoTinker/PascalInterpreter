package interpreter;

public enum  TokenType {
    PLUS("+","PLUS"),
    MINUS("-","MINUS"),
    MUL("*","MUL"),
    FLOAT_DIV("/","FLOAT_DIV"),
    LPAREN("(","LPAREN"),
    RPAREN(")","RPAREN"),
    SEMI(";","SEMI"),
    DOT(".","DOT"),
    COLON(":","COLON"),
    COMMA(",","COMMA"),

    PROGRAM("PROGRAM","PROGRAM"),
    INTEGER("INTEGER","INTEGER"),
    REAL("REAL","REAL"),
    INTEGER_DIV("INTEGER_DIV","INTEGER_DIV"),
    VAR("VAR","VAR"),
    PROCEDURE("PROCEDURE","PROCEDURE"),
    BEGIN("BEGIN","BEGIN"),
    END("END","END"),

//    ID("","ID"),
//    INTEGER_CONST("","INTEGER_CONST"),
//    REAL_CONST("","REAL_CONST"),
//    ASSIGN("","ASSIGN")
//    EOF
    ;



    private String value;
    private String type;

    private TokenType(String value,String type) {
        this.value=value;
        this.type=type;
    }

    public String getType(String value){
        for (TokenType t :
                TokenType.values()) {
            if(t.value.equals(value)){
                return t.type;
            }

        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
