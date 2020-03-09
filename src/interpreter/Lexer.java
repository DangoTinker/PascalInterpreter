package interpreter;

public class Lexer {

    private String text;

    private int pos=0;

    private Token current_token;


    private int lineno=0;
    private int colomn=0;



    private String[] RESERVED_KEYWORDS={
            "BEGIN","END","PROGRAM","DIV","INTEGER","REAL","VAR","PROCEDURE"
    };


    public Lexer(String text) throws Exception {
        this.text = text;
        current_token = get_next_token();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    public void eat(String type) throws Exception{

        if(pos==(text.length())){
            return;
        }

        if(current_token.getType().equals(type)){
            current_token=get_next_token();
            return;
        }
        System.out.println(current_token.getType()+" "+current_token.getValue()+" "+type);

        throw new Exception("error");

    }


    public Token id(){
        char temp=text.charAt(pos);
        String res="";
        while((temp>=65&&temp<=90)||(temp>=97&&temp<=122)||(temp>=48&&temp<=57)){
            res=res+String.valueOf(text.charAt(pos));
            pos++;
            colomn+=1;
            if(pos<text.length())
                temp=text.charAt(pos);
            else
                break;
        }

        for (int i = 0; i <RESERVED_KEYWORDS.length; i++) {
            if(RESERVED_KEYWORDS[i].equals(res)){
                return new Token(res,res);
            }
        }
        return new Token("ID",res);

    }



    public Character peek(){
        while(pos+1 < text.length()){
            if(text.charAt(pos+1)!=' '){
                return text.charAt(pos+1);
            }
            else{
                pos++;
            }
        }
        return null;
    }


    public Token getCurrent_token() {
        return current_token;
    }

    public void setCurrent_token(Token current_token) {
        this.current_token = current_token;
    }

    public void skip_whitespace(){
        while(text.charAt(pos)==' '){
            pos++;
            if(pos>=text.length()){
                return;
            }
        }

    }
    public void skip_comment(){

        while(text.charAt(pos)!='}'){
            pos++;
        }
        pos++;

    }



    public Token get_next_token() throws Exception{

        while(pos<text.length()){

            char s=text.charAt(pos);
//            System.out.println(s);


            if(s=='+'){
                pos++;
                colomn+=1;
                return new Token("PLUS","+");
            }
            else if(s=='-'){
                pos++;
                colomn+=1;
                return new Token("MINUS","-");
            }
            else if(s=='*'){
                pos++;
                colomn+=1;
                return new Token("MUL","*");
            }
            else if(s=='/'){
                pos++;
                colomn+=1;
                return new Token("FLOAT_DIV","/");
            }
            else if(s==' '){
                skip_whitespace();
                continue;
            }else if(s=='('){
                pos++;
                colomn+=1;
                return new Token("LPAREN","(");
            }else if(s==')'){
                pos++;
                colomn+=1;
                return new Token("RPAREN",")");
            }
            else if((s>=65&&s<=90)||(s>=97&&s<=122)){
                return id();
            }
            else if(s=='.'){
                pos++;
                colomn+=1;
                return new Token("DOT",".");
            }else if(s==';'){
                pos++;
                colomn+=1;
                return new Token("SEMI",";");
            }else if(s==':' && peek()=='='){
                pos++;
                pos++;
                colomn+=1;
                colomn+=1;
                return new Token("ASSIGN",":=");
            }
            else if(s=='\n'){
                pos++;

                colomn=0;
                lineno+=1;
            }
            else if(s=='{'){
                pos++;
                colomn+=1;
                skip_comment();
            }
            else if(s==','){
                pos++;
                colomn+=1;
                return new Token("COMMA",",");
            }
            else if(s==':'){
                pos++;
                colomn+=1;
                return new Token("COLON",":");
            }



            else if(Integer.valueOf(String.valueOf(s)) instanceof Integer){
                int temp=pos;
                String t="" +
                        "";
                for (int i = pos; i <text.length(); i++) {
                    char a=text.charAt(i);
                    if((!Character.isDigit(text.charAt(i)))&&(text.charAt(i)!='.')){
                        temp=i;
                        break;
                    }else{
                        t=t+String.valueOf(text.charAt(i));
                    }
                }


                pos=temp;

                colomn+=1;

                if(t.contains(".")){
                    return new Token("REAL_CONST", Double.valueOf(t));
                }
                else{
                    return new Token("INTEGER_CONST", Integer.valueOf(t));
                }

            }

            else{
                throw new Exception("error");
            }
        }
        System.out.println(text.charAt(text.length()-1)+" "+pos+" "+text.length()+" "+current_token.getValue());
        throw new Exception("error");

    }

}
