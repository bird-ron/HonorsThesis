package honorsThesis;

public class Token 
{
	public final String lexeme;
	public final int tokenNumber;
	public final int lineNumber;
	
	public Token(String lexeme, int tokenNumber, int lineNumber)
	{
		this.lexeme = lexeme;
		this.tokenNumber = tokenNumber;
		this.lineNumber = lineNumber;
	}
	
	@Override
	public String toString()
	{
		return "(%s, %d, %d)".formatted(this.lexeme, this.tokenNumber, this.lineNumber);
	}
}
