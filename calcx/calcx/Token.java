package calcx;

// Base class for tokens, can be extended by subclasses for each particular token
public class Token {
    TokenType thisToken;
    
    public Token() {
        thisToken = TokenType.NULL_TOKEN;
    }
    
    public Token(TokenType inputToken) {
        thisToken = inputToken;
    }
    
    public TokenType returnType() {
        return thisToken;
    }
    
    public void print() {
        switch(thisToken) {
            case NUM: System.out.println("Number Token."); break;
            case INT_VAR: System.out.println("Identifier Token."); break;
			case COLON: System.out.println("Colon Token."); break;
			case SEMICOLON: System.out.println("Semicolon Token."); break;
			case EQUALS: System.out.println("Equals Token."); break;
			case PLUS: System.out.println("Plus Token."); break;
			case MINUS: System.out.println("Minus Token."); break;
			case EXPONENTIATION: System.out.println("Exponentiation Token."); break;
			case ABSOLUTEDIFF: System.out.println("Absolute Difference Token."); break;
			case INT_KEYWORD: System.out.println("INT Keyword Token."); break;
			case END_OF_FILE: System.out.println("End_Of_File Token."); break;
			case NULL_TOKEN: System.out.println("Null Token."); break;
			default: System.out.println("Unknown Token type.");
        }
    }
}