package honorsThesis;

import java.util.List;
import static honorsThesis.Token.nullToken;

public class UnexpectedTokenException extends Exception
{
	private static final long serialVersionUID = 3927360262055725741L;
	private final String errorTemplate = "Parsing error on line %d, column %d: expected %s, got %s";
	private final String nullErrorTemplate = "Parsing error: expected %s, got end of file";
	private final String expected;
	private final Token token;
	
	public UnexpectedTokenException(List<String> expected, Token token)
	{
		this.expected = expected.toString();
		this.token = token;
	}
	
	public UnexpectedTokenException(String expected, Token token)
	{
		this.expected = expected;
		this.token = token;
	}
	
	@Override
	public String toString()
	{
		String returnString = null;
		if (token != nullToken)
		{
			returnString = errorTemplate.formatted(token.lineNumber, token.columnNumber, expected, token.lexeme);
		}
		else
		{
			returnString = nullErrorTemplate.formatted(expected);
		}
		return returnString;
	}
}
