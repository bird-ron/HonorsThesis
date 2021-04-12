package honorsThesis;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static honorsThesis.Util.emptyString;
import static honorsThesis.Util.textItemDelimiter;
import static honorsThesis.Util.listContains;
import static honorsThesis.AnswerParser.answerFirst;
import static honorsThesis.Token.nullToken;

public class TextParser 
{
	public static final List<String> descriptionFirst = generateDescriptionFirst();
	public static final List<String> feedbackFirst = generateFeedbackFirst();
	
	public static final List<String> descriptionFollows = generateDescriptionFollows();
	public static final List<String> feedbackFollows = generateFeedbackFollows();
	
	public static final List<String> descriptionForbidden = generateDescriptionForbidden();
	public static final List<String> feedbackForbidden = generateFeedbackForbidden();
	
	public static final boolean descriptionOptional = false;
	public static final boolean feedbackOptional = true;
	
	private final TokenStream tokenStream;
	private final HashMap<String, Double> variables;
	private final List<String> first;
	private final List<String> follows;
	private final List<String> forbidden;
	private final boolean optional;
	
	private static List<String> generateDescriptionFirst()
	{
		String[] descriptionFirstArray = {"<description>", "<desc>", "<d>"};
		return unmodifiableList(asList(descriptionFirstArray));
	}
	
	private static List<String> generateDescriptionFollows()
	{
		List<String> descriptionFollows = new ArrayList<String>();
		descriptionFollows.addAll(answerFirst);
		return unmodifiableList(descriptionFollows);
	}
	
	private static List<String> generateDescriptionForbidden()
	{
		List<String> descriptionForbidden = new ArrayList<String>();
		descriptionForbidden.addAll(descriptionFirst);
		descriptionForbidden.addAll(feedbackFirst);
		descriptionForbidden.add(nullToken.lexeme);
		return unmodifiableList(descriptionForbidden);
	}
	
	private static List<String> generateFeedbackFirst()
	{
		String[] feedbackFirstArray = {"<feedback>", "<fdbk>", "<f>"};
		return unmodifiableList(asList(feedbackFirstArray));
	}
	
	private static List<String> generateFeedbackFollows()
	{
		List<String> feedbackFollows = new ArrayList<String>();
		feedbackFollows.addAll(descriptionFirst);
		feedbackFollows.add(nullToken.lexeme);
		return unmodifiableList(feedbackFollows);
	}
	
	private static List<String> generateFeedbackForbidden()
	{
		List<String> feedbackForbidden = new ArrayList<String>();
		feedbackForbidden.addAll(answerFirst);
		feedbackForbidden.addAll(feedbackFirst);
		return unmodifiableList(feedbackForbidden);
	}
	
	private TextParser(TokenStream tokenStream, HashMap<String, Double> variables, List<String> first, List<String> follows, 
			List<String> forbidden, boolean optional)
	{
		this.tokenStream = tokenStream;
		this.variables = variables;
		this.first = first;
		this.follows = follows;
		this.forbidden = forbidden;
		this.optional = optional;
	}
	
	public static String parseDescription(TokenStream tokenStream, HashMap<String, Double> variables) throws UnexpectedTokenException
	{
		TextParser textParser = new TextParser(tokenStream, variables, descriptionFirst, descriptionFollows, descriptionForbidden, 
				descriptionOptional);
		return textParser.parse();
	}
	
	public static String parseFeedback(TokenStream tokenStream, HashMap<String, Double> variables) throws UnexpectedTokenException
	{
		TextParser textParser = new TextParser(tokenStream, variables, feedbackFirst, feedbackFollows, feedbackForbidden, 
				feedbackOptional);
		return textParser.parse();
	}
	
	private String parse() throws UnexpectedTokenException
	{ 
		String text = null;
		try 
		{
			tokenStream.expect(first);
			text = parseUntil();
		}
		catch (UnexpectedTokenException unexpectedTokenException)
		{
			if (optional)
			{
				tokenStream.ungetToken();
			}
			else
			{
				throw unexpectedTokenException;
			}
		}
		return text;
	}
	
	private String parseUntil() throws UnexpectedTokenException
	{
		String text = emptyString;
		Token token = tokenStream.forbid(follows, forbidden);
		while (!listContains(follows, token.lexeme))
		{	
			if (variables != null && variables.containsKey(token.lexeme))
			{
				text += variables.get(token.lexeme);
			}
			else
			{
				text += token.lexeme;
			}
			text += textItemDelimiter;
			token = tokenStream.forbid(follows, forbidden);
		}
		tokenStream.ungetToken();
		return text;
	}
}
