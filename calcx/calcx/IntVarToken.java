
package calcx;

public class IntVarToken extends VarToken {
    public int value;
    
    public IntVarToken(String identName) {
        super(identName, TokenType.INT_VAR);
        value = 0;
    }
    
    public int getValue() {
        return value;
    }
        
    public void setValue(int newValue) {
        value = newValue;
    }
    
    public void print() {
        System.out.println("Integer Variable Token: " + identifierName);
    }
}
