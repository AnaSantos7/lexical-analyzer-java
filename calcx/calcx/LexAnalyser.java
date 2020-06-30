
package calcx;

import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;

public class LexAnalyser {
    public int lineNumber = 1;
    private char peek = ' ';
    private Hashtable<String, VarToken> identifiers;
    public ArrayList<Token> tokenSequence = new ArrayList<Token>();
    int currentToken = 0;
    
    // Constructor for LexAnalyser class
    public LexAnalyser() {
    	identifiers = new Hashtable<String, VarToken>();
    }
    
    // This next method will add a variable identifier token to the hashtable
    public void addIdentifier(VarToken t) { identifiers.put(t.getIdName(), t); }
    
    // This method tries to find a variable identifier token of the given name 
    public VarToken findIdentifier(String identifierName) { return identifiers.get(identifierName); }
    
    // This method returns the hashtable of all identifiers
    public Hashtable<String, VarToken> getIdentifiers() {
        return identifiers;
    }
    
    // This method returns the sequence of tokens
    public ArrayList<Token> getTokens() {
        return tokenSequence;
    }

    
    /* This next method will read in a single integer value from user input and return it
     */
    public int readInt() throws IOException {
    	if(Character.isDigit(peek)) { 
            int readInt = 0;
            do {
            	readInt = 10*readInt + Character.digit(peek, 10);
                peek = (char)System.in.read();
            } while(Character.isDigit(peek));
            return readInt; // readInt stores the integer value of the token
        }
    	else {
    		System.out.println("Error.");
    		return 0;
    	}
    }
    
    /* This next method will read in the next string value and return it
     */
    public String readString() throws IOException {
    	StringBuffer b = new StringBuffer();
        do {
            b.append(peek);
            peek = (char)System.in.read();
        } while(Character.isLetterOrDigit(peek));
        String s = b.toString(); // s now stores the next lexeme as a string
        return s;
    }
    
    /* This next method removes whitespace (newlines, carriage returns, tabs or spaces)
     */    
    public void removeWhitespace() throws IOException {
    	for( ; ; peek = (char)System.in.read()) {
            if(peek == ' ' || peek == '\t' || peek == '\r') continue;
            else if(peek=='\n') lineNumber=lineNumber+1;
            else break;
        }
    }
    
    // This method adds a given token to the array of tokens
    public void addTokenToArray(Token tokenToAdd) {
    	tokenSequence.add(tokenToAdd);
		currentToken++;
    }

    /* This next method performs lexical analysis by continually reading in lexemes and storing them in
     * an array of Tokens tokenSequence. The method returns once it finds an error or sees the end of file symbol '$'.
     * scan() returns the number of tokens found.
     */
    public int scan() throws IOException {
    	Token nextToken;
    	do {
    		nextToken = new Token(TokenType.NULL_TOKEN); // Initialise nextToken to have type NULL
	        
    		// First we may read characters until end of whitespace (spaces, tabs or newlines)...
    		removeWhitespace();
	               
	        // Now determine what type of token we have and add it to the array
	    	
	    	// Check if next lexeme is a number (beginning with a digit)
	    	if(Character.isDigit(peek)) { 
    			int readValue = readInt();
    			nextToken = new NumToken(readValue);
    			addTokenToArray(nextToken);
   				continue; // Restart the loop to find next token
	    	}
	    	
	    	// Otherwise, check if the next token is a valid identifier or else a keyword
	    	else if(Character.isLetter(peek)) {
	            String nextWord = readString();
	            
	            if(nextWord.equals("INT")) {
	                // s represents an INT keyword
	            	nextToken = new Token(TokenType.INT_KEYWORD);
	            	addTokenToArray(nextToken);
	            	// Since this is an INT keyword, the next token must be an integer variable
	            	removeWhitespace();
	            	nextWord = readString();
	            	nextToken = new IntVarToken(nextWord);
	            	addTokenToArray(nextToken);
	            	addIdentifier((IntVarToken) nextToken);
	            	continue;
	            }
	            else {
	            	nextToken = findIdentifier(nextWord);
	            	if(nextToken != null) {
	            		addTokenToArray(nextToken);
	            		continue;
	            	}
	            	else {
	            		System.out.println("Identifier not found.");
	            		nextToken = new Token(TokenType.NULL_TOKEN);
	            		break; // Stop lexical analysis
	            	}
	            }
	        }
	    	// Check if the next token an operator or end of line/end of file symbol
	        switch(peek) {
	            case(';'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.SEMICOLON); 
		            addTokenToArray(nextToken); 
		            break; // Leave the switch statement (not the do..while loop)
	            case('+'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.PLUS); 
		            addTokenToArray(nextToken); 
		            break;
	            case('-'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.MINUS); 
		            addTokenToArray(nextToken); 
		            break;
	            case(':'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.COLON); 
		            addTokenToArray(nextToken); 
		            break;
	            case('='): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.EQUALS); 
		            addTokenToArray(nextToken); 
		            break;
	            case('^'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.EXPONENTIATION); 
		            addTokenToArray(nextToken); 
		            break;
	            case('~'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.ABSOLUTEDIFF); 
		            addTokenToArray(nextToken); 
		            break;
	            case('$'): 
	            	peek = ' '; 
		            nextToken = new Token(TokenType.END_OF_FILE); 
		            addTokenToArray(nextToken); 
		            break;
	            default: nextToken = new Token(TokenType.NULL_TOKEN); 
	            	System.out.println("Unknown token.");
	        }	        
    	} while (nextToken.returnType() != TokenType.NULL_TOKEN && nextToken.returnType() != TokenType.END_OF_FILE);

        if(nextToken.returnType() == TokenType.NULL_TOKEN) {
        	System.out.println("Lexical analysis unsuccessful.");
        	return -1; // To indicate an error
        }
        else {
        	System.out.println("Lexical analysis successful with " + currentToken + " tokens.");
        	return currentToken;
        }
    } 
}
