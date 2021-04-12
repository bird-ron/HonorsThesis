package honorsThesis;

public class Token 
{
	public static final Token nullToken = new Token("end of file", null, null);
	public final String lexeme;
	public final Integer columnNumber;
	public final Integer lineNumber;
	
	public Token(String lexeme, Integer columnNumber, Integer lineNumber)
	{
		this.lexeme = lexeme;
		this.columnNumber = columnNumber;
		this.lineNumber = lineNumber;
	}
	
	@Override
	public String toString()
	{
		return lexeme;
	}
}
