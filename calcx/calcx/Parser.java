
package calcx;

import java.util.Hashtable;
import java.util.ArrayList;
import java.lang.Math;

public class Parser {
    public ArrayList<Token> tokens;  // Array of tokens to be parsed
    public int position;    // Current position in array
    
    Hashtable<String, VarToken> vars;         // Hashtable of all identifiers
    
    // Constructor for Parser class. Sequence of tokens and a hashtable of identifiers should be passed. 
    public Parser(ArrayList<Token> tokenSeq, Hashtable<String, VarToken> variables) {
        tokens = tokenSeq;  
        position = 0;       // Current position in sequence
        vars = variables;
    }
     
    // This method is called when a parse error occurs on some token
    private void parseError() {
        System.out.print("Parse Error on:");
        tokens.get(position).print();
    }
    
    // This method checks if the next token is of type tokType and if so moves on to the next token 
    private void match(TokenType tokType) {
        if(tokens.get(position).returnType() != tokType) {
    		System.out.println(position);
            parseError();
        }
        position++;
    }
    
    // This method determines if the next token is of type tokType (returning a boolean)  
    private boolean check(TokenType tokType) {
        if(tokens.get(position).returnType() != tokType) {
            return false;
        }
        return true;
    }
    
    /*
     * These next methods implement the Context Free Grammar / Syntax Directed Translation Scheme. There is a method
     * for each nonterminal of the grammar and production rules determine the operations to be carried out.
     */
    
    // Start to parse the program
    public void prog() {
    	
        // First parse declarations
        decls();
        // Next parse expression
        ArrayList<Integer> values = new ArrayList<Integer>(); 
        values = exprs(values);
        for(int i = 0; i < values.size(); i++) {
        	System.out.print(values.get(i) + " ");
        }
        System.out.println();
       
        
        //int value = expr();
        // Finally parse the end of file character
        match(TokenType.END_OF_FILE);
        //System.out.println("Value of expression is: " + value);
    }
    
    public ArrayList<Integer> exprs(ArrayList<Integer> values) {
    	
    	
    	if(check(TokenType.COLON)) {
	    	values.add(expr());
	    	
	    	exprs(values);
    	}
    	else {
    		//empty
    	}
    	return values;
    }
    
    
    public void decls() {
    	if(check(TokenType.INT_KEYWORD)) {
    		decl();
    		decls();
    	}
    	else {
    		// Do nothing, epsilon production in CFG
    	}
    }
    
    public void decl() {    	
    	if(check(TokenType.INT_KEYWORD)) {
    		match(TokenType.INT_KEYWORD);
    		IntVarToken nextIntVar = (IntVarToken) tokens.get(position);
    		match(TokenType.INT_VAR);
    		match(TokenType.EQUALS);
    		NumToken nextInteger = (NumToken) tokens.get(position);
    		match(TokenType.NUM);
    		match(TokenType.SEMICOLON);
    		// Now do the semantic action
    		nextIntVar.setValue(nextInteger.getValue());
    	} 
    	else {
    		parseError();
    	}
    }
    
    public int expr() {
    	match(TokenType.COLON);
    	int value = expr1();
 
    	return value;
    }
    
    public int expr1() {
    	int value = 0;
    	if (check(TokenType.INT_VAR)) {
    		value = ((IntVarToken) tokens.get(position)).getValue();
    		match(TokenType.INT_VAR);
    		value = expr2(value);
    	}
    	else if(check(TokenType.NUM)) {
    		value = ((NumToken) tokens.get(position)).getValue();
    		match(TokenType.NUM);
    		value =  expr2(value);
    	}
    	return value;
    }
    
    public int expr2(int value) {
    	int value1 = value;
		switch(tokens.get(position).returnType()) {
			case PLUS : op(); int value2 = expr1(); value1 = value + value2; break; // Semantic action 
			case MINUS: op(); int value2b = expr1(); value1 = value - value2b; break; // Semantic action 
			case EXPONENTIATION: op(); int value2c = expr1(); value1 = (int) Math.pow(value, value2c); break; // Semantic action 
			case ABSOLUTEDIFF: op(); int value2d = expr1(); value1 = Math.abs(value - value2d); break; // Semantic action 
			default: // Do nothing if the next token is not an operation (op)
		}
		return value1;
    }
    
    public void op() {
    	if(check(TokenType.MINUS)) {
    		match(TokenType.MINUS);
    	}
    	else if(check(TokenType.PLUS)) {
    		match(TokenType.PLUS);
    	}
    	else if(check(TokenType.EXPONENTIATION)) {
    		match(TokenType.EXPONENTIATION);
    	}
    	else if(check(TokenType.ABSOLUTEDIFF)) {
    		match(TokenType.ABSOLUTEDIFF);
    	}
    	
    	else {
    		parseError();
    		// op should be "+", "-", "^" or "~"
    	}
    }
}
