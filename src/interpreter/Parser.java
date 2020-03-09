package interpreter;

import nodes.*;

import java.util.LinkedList;

public class Parser {

        private Lexer lexer;


    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public AST factor() throws Exception{
            Token token=lexer.getCurrent_token();
            if(token.getType().equals("INTEGER_CONST")) {
                lexer.eat("INTEGER_CONST");
                return new Num(token);
            }
            else if (token.getType().equals("REAL_CONST")){
                lexer.eat("REAL_CONST");
                return new Num(token);
            }

            else if(token.getType().equals("LPAREN")){
                lexer.eat("LPAREN");
                AST temp= expr();
                lexer.eat("RPAREN");
                return temp;
            }
            else if(token.getType().equals("MINUS")){
                lexer.eat("MINUS");
//                nodes.AST ast= factor();
//                interpreter.Token t=((nodes.Num)ast).getToken();
//                t.setValue(-((int)t.getValue()));
//
//                ast=new nodes.Num(t);
                AST ast=new UnaryOp(token,factor());

                return ast;
            }
            else if(token.getType().equals("PLUS")){
                lexer.eat("PLUS");
                AST ast=new UnaryOp(token,factor());
                return ast;
            }
            else if(token.getType().equals("ID")){

                AST ast=variable();
                return ast;
            }




            else{
                return null;
            }
        }






        public AST term() throws Exception{
//        current_token=get_next_token();
            AST res=factor();
//            System.out.println(lexer.getCurrent_token().getType());
            while(lexer.getCurrent_token().getType()=="MUL"||lexer.getCurrent_token().getType()=="FLOAT_DIV"||lexer.getCurrent_token().getType().equals("DIV")){

                if(lexer.getCurrent_token().getType()=="MUL"){
                    lexer.eat("MUL");
//
                    res=new BinOp(res,new Token("MUL","*"),factor());
//                    System.out.println(lexer.getCurrent_token().getType());
//                    System.out.println(lexer.getCurrent_token().getType()=="PLUS");
                }else if(lexer.getCurrent_token().getType()=="FLOAT_DIV"){
                    lexer.eat("FLOAT_DIV");
                    res=new BinOp(res,new Token("FLOAT_DIV","/"),factor());
                }
                else if(lexer.getCurrent_token().getType().equals("DIV")){
//                    System.out.println("111");
                    lexer.eat("DIV");
                    res=new BinOp(res,new Token("DIV","DIV"),factor());
                }

            }

            return res;

        }




        public AST expr() throws Exception {

//        if(pos==(text.length()-1)){
//            return term();
//        }
//        current_token = get_next_token();

            AST res = term();

            while (lexer.getCurrent_token().getType() == "PLUS" || lexer.getCurrent_token().getType() == "MINUS") {

                if (lexer.getCurrent_token().getType() == "PLUS") {
                    lexer.eat("PLUS");
                    res = new BinOp(res,new Token("PLUS","+"),term());
                } else if (lexer.getCurrent_token().getType() == "MINUS") {
                    lexer.eat("MINUS");
                    res = new BinOp(res,new Token("MINUS","-"),term());
                }


            }
            return res;

        }

        public AST program() throws  Exception{
            lexer.eat("PROGRAM");
            AST var=variable();
            lexer.eat("SEMI");
            Block ast=block();
            lexer.eat("DOT");
            return new Program((String) ((Var)var).getVar().getValue(),ast);
//            AST res=compound_statement();
//            lexer.eat("DOT");
//            return res;
        }

        public AST compound_statement() throws Exception {
            lexer.eat("BEGIN");
            LinkedList<AST> asts=statement_list();
            lexer.eat("END");
            Compound compound=new Compound();
            for (AST a:
                 asts) {
                compound.addChild(a);
            }
            return compound;


        }

        public LinkedList<AST> statement_list() throws Exception{
            LinkedList<AST> linkedList=new LinkedList<AST>();
            AST first=statement();
            linkedList.add(first);
            while(lexer.getCurrent_token().getType().equals("SEMI")){
                lexer.eat("SEMI");
                AST temp=statement();
                linkedList.add(temp);
            }
            return linkedList;

        }

        public AST statement() throws Exception{
            if(lexer.getCurrent_token().getType().equals("BEGIN")){
                return compound_statement();
            }
            else if(lexer.getCurrent_token().getType().equals("ID")){
                if(lexer.peek()=='('){
                    return proccall_statement();
                }
                else {
                    System.out.println(lexer.peek());
                    return assignment_statement();
                }

            }else{
                return empty();
            }
        }

        public AST assignment_statement() throws Exception{
            AST left=variable();
            Token token=lexer.getCurrent_token();
            lexer.eat("ASSIGN");
            AST right=expr();

            return new Assign(left,token,right);

        }

        public AST variable() throws Exception{
            Token token=lexer.getCurrent_token();
            lexer.eat("ID");
            return new Var(token);
        }

        public AST empty() throws Exception{
            return new NoOp();
        }


        public Block block() throws Exception {
            LinkedList<AST> node=declarations();
            AST node2=compound_statement();

            return new Block(node,node2);

        }


        public LinkedList<AST> declarations() throws Exception {
            LinkedList<AST> asts=new LinkedList<AST>();
            if(lexer.getCurrent_token().getType().equals("VAR")){
                lexer.eat("VAR");
                while(lexer.getCurrent_token().getType().equals("ID")){
                    LinkedList<VarDecl> list=variable_declaration();
                    for (int i = 0; i < list.size(); i++) {
                        asts.add(list.get(i));
                    }
                    lexer.eat("SEMI");
                }
            }
            while(lexer.getCurrent_token().getType().equals("PROCEDURE")){
                lexer.eat("PROCEDURE");
                Token token=lexer.getCurrent_token();
                lexer.eat("ID");
                LinkedList<Param> params=new LinkedList<Param>();
                if(lexer.getCurrent_token().getType().equals("LPAREN")){
                    lexer.eat("LPAREN");

                    params=formal_parameter_list();

                    lexer.eat("RPAREN");
                }

                lexer.eat("SEMI");
                AST res=block();
                AST temp=new ProcedureDecl((String) token.getValue(),res,params);
                asts.add(temp);
                lexer.eat("SEMI");
            }


            return asts;

        }

        public LinkedList<Param> formal_parameter_list() throws Exception {
            LinkedList<Param> list=new LinkedList<Param>();
            LinkedList<Param>  temp=formal_parameter();
            for (int i = 0; i < temp.size(); i++) {
                list.add(temp.get(i));
            }
            while(lexer.getCurrent_token().getType().equals("SEMI")){
                lexer.eat("SEMI");
                LinkedList<Param>  param=formal_parameter();
                for (int i = 0; i < param.size(); i++) {
                    list.add(param.get(i));
                }
            }
            return list;

        }

        public LinkedList<Param> formal_parameter() throws Exception {
            LinkedList<Param>  param=new LinkedList<Param>();
            LinkedList<Token> tokens=new LinkedList<Token>();
            Token token=lexer.getCurrent_token();
            lexer.eat("ID");
            tokens.add(token);
            while(lexer.getCurrent_token().getType().equals("COMMA")){
                lexer.eat("COMMA");
                tokens.add(lexer.getCurrent_token());
                lexer.eat("ID");
            }
            lexer.eat("COLON");
            Type type=type_spec();
            for (Token t :
                tokens)  {
                param.add(new Param(new Var(token),type));

            }
            return param;

        }



        public LinkedList<VarDecl> variable_declaration() throws Exception {
            Token id=lexer.getCurrent_token();
            lexer.eat("ID");
            LinkedList<VarDecl> list=new LinkedList<VarDecl>();
            list.add(new VarDecl((String)id.getValue(),null));
            while(lexer.getCurrent_token().getType().equals("COMMA")){
                lexer.eat("COMMA");
                Token temp=lexer.getCurrent_token();
                list.add(new VarDecl((String) temp.getValue(),null));
                lexer.eat("ID");
            }
            lexer.eat("COLON");
            Type type=type_spec();

            for (VarDecl v :
                    list) {
                v.setType(type);
            }

            return list;

        }

        public Type type_spec() throws Exception {

            Token token=lexer.getCurrent_token();

            if(token.getType().equals("INTEGER")){
                lexer.eat("INTEGER");
                return new Type(token);
            }else if(token.getType().equals("REAL")){
                lexer.eat("REAL");
                return new Type(token);
            }


            return null;

        }


        public AST proccall_statement() throws Exception {

            Token token=lexer.getCurrent_token();
            lexer.eat("ID");
            lexer.eat("LPAREN");
            LinkedList<AST> list=new LinkedList<AST>();

            while(!lexer.getCurrent_token().getType().equals("RPAREN")){

                AST temp=expr();
                list.add(temp);
                if(lexer.getCurrent_token().getType().equals("COMMA")){
                    lexer.eat("COMMA");
                }

            }

            lexer.eat("RPAREN");
            return new ProcedureCall((String) token.getValue(),list,token,null);

        }






}
