package honorsThesis;

public class Token 
{
	public final String lexeme;
	public final int columnNumber;
	public final int lineNumber;
	
	public Token(String lexeme, int columnNumber, int lineNumber)
	{
		this.lexeme = lexeme;
		this.columnNumber = columnNumber;
		this.lineNumber = lineNumber;
	}
	
	@Override
	public String toString()
	{
		return "(%s, %d, %d)".formatted(this.lexeme, this.columnNumber, this.lineNumber);
	}
}
