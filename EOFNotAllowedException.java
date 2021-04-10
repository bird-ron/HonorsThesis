package honorsThesis;

import static honorsThesis.Util.arrayToTupleString;

public class EOFNotAllowedException extends Exception
{
	private static final long serialVersionUID = 659974917399954415L;
	private static final String errorTemplate = "Parsing Error: expected %s, got end of file";
	private final String expectedLexemesTupleString;
	
	public EOFNotAllowedException(String[] expectedLexemes)
	{
		this.expectedLexemesTupleString = arrayToTupleString(expectedLexemes);
	}
	
	@Override
	public String toString()
	{
		return errorTemplate.formatted(this.expectedLexemesTupleString);
	}
}
