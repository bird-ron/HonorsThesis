package honorsThesis;

import static honorsThesis.Util.arrayToTupleString;

public class UnexpectedTokenException extends Exception
{
	private static final long serialVersionUID = 3927360262055725741L;
	private static final String errorTemplate = "Parsing error on line %d, column %d: expected %s, got %s";
	private final String expectedLexemesTupleString;
	private final Token badToken;
	
	public UnexpectedTokenException(String[] expectedLexemes, Token badToken)
	{
		this.expectedLexemesTupleString = arrayToTupleString(expectedLexemes);
		this.badToken = badToken;
	}
	
	@Override
	public String toString()
	{
		return errorTemplate.formatted(this.badToken.lineNumber, this.badToken.columnNumber, this.expectedLexemesTupleString, 
				this.badToken.lexeme);
	}
}
