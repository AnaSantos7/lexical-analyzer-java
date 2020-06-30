package calcx;

/*
 * This class represents a variable in CalcX. Currently the only variable type is INTEGER.
 */
public abstract class VarToken extends Token {
	String identifierName; // All variables have a name

	public VarToken(String identName, TokenType variableType) {
		super(variableType);
        identifierName = identName;
    }
	
	public String getIdName() {
        return identifierName;
    }
}
