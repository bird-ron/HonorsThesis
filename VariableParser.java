package honorsThesis;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static honorsThesis.Util.listContains;
import static honorsThesis.Util.truncateToTwoPlaces;
import static honorsThesis.AnswerParser.answerFirst;
import static honorsThesis.TextParser.descriptionFirst;
import static honorsThesis.TextParser.feedbackFirst;
import static honorsThesis.Token.nullToken;

public class VariableParser 
{
	private static final List<String> first = generateFirst();
	private static final List<String> follows = generateFollows();
	private static final List<String> forbidden = generateForbidden();
	
	private static final String nameExpected = "variable name";
	private static final String assignmentOperator = "=";
	private static final String rangeIndicator = "to";
	
	private final TokenStream tokenStream;
	
	private static List<String> generateFirst()
	{
		String[] variablesFirstArray = {"<variables>", "<vars>", "<v>"};
		return unmodifiableList(asList(variablesFirstArray));
	}
	
	private static List<String> generateFollows()
	{
		return descriptionFirst;
	}
	
	private static List<String> generateForbidden()
	{
		List<String> variablesForbidden = new ArrayList<String>();
		variablesForbidden.addAll(first);
		variablesForbidden.addAll(follows);
		variablesForbidden.addAll(answerFirst);
		variablesForbidden.addAll(feedbackFirst);
		variablesForbidden.add(nullToken.lexeme);
		return unmodifiableList(variablesForbidden);
	}
	
	private VariableParser(TokenStream tokenStream)
	{
		this.tokenStream = tokenStream;
	}
	
	public static HashMap<String, Double> parseVariables(TokenStream tokenStream) throws UnexpectedTokenException
	{
		VariableParser variableParser = new VariableParser(tokenStream);
		return variableParser.parseVariables();
	}
	
	private HashMap<String, Double> parseVariables() throws UnexpectedTokenException
	{
		HashMap<String, Double> variables = null;
		boolean variablesPresent;
		try
		{
			tokenStream.expect(first);
			variablesPresent = true;
		}
		catch (UnexpectedTokenException unexpectedTokenException)
		{
			tokenStream.ungetToken();
			variablesPresent = false;
		}
		if (variablesPresent)
		{
			variables = new HashMap<String, Double>();
			while (!listContains(follows, tokenStream.peek().lexeme))
			{
				String name = forbid(nameExpected).lexeme;
				tokenStream.expect(assignmentOperator);
				Double value = parseRange();
				variables.put(name, value);
			}
		}
		return variables;
	}
	
	private Double parseRange() throws UnexpectedTokenException
	{
		Double min = tokenStream.expectDouble();
		tokenStream.expect(rangeIndicator);
		Double max = tokenStream.expectDouble();
		Double value = ThreadLocalRandom.current().nextDouble(min, max);
		return truncateToTwoPlaces(value);
	}
	
	private Token forbid(String expected) throws UnexpectedTokenException
	{
		return tokenStream.forbid(expected, forbidden);
	}
}
