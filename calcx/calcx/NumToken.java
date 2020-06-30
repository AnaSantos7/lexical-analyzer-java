
package calcx;

public class NumToken extends Token {
    public int intValue;
    
    public NumToken(int value) {
        super(TokenType.NUM);
        intValue = value;
    }
    
    public int getValue() {
        return intValue;
    }
    
    public void print() {
        System.out.println("Number Token: " + intValue);
    }
}
