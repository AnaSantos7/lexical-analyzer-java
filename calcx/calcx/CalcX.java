/*
 * CalcX Language Skeleton Java program
 * You may use these .java files as a start for your coursework
 * All other coding should be your own (individual coursework)
 */
package calcx;

import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;

public class CalcX {
    public static ArrayList<Token> tokenSequence = new ArrayList<Token>();
    public static int numOfTokens=0;
    public static Hashtable<String, VarToken> idents;

    public static void main(String[] args) throws IOException {
    	System.out.println("--- Beginning Lexical analysis ---");
        LexAnalyser lex = new LexAnalyser();        
        numOfTokens = lex.scan();
        
        if(numOfTokens <= 0)
        	return; // Lexical analysis failed
        
        tokenSequence = lex.getTokens();
        idents = lex.getIdentifiers();
        for(int j = 0; j< numOfTokens; j++) {
        	tokenSequence.get(j).print();
        }
        System.out.println("--- Finished lexical analysis ---");
        // Lexical analysis complete, now on to parsing..
        System.out.println("--- Beginning Parsing ---");
        // This next declaration passes the sequence of tokens and hashtable of identifiers to the parser
        Parser pars = new Parser(tokenSequence, idents);
        pars.prog();
        System.out.println("--- Ending Parsing ---");
    }
}
