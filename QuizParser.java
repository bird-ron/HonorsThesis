package honorsThesis;

import java.util.ArrayList;
import static honorsThesis.FileIO.findLines;
import static honorsThesis.Util.addArrays;
import static honorsThesis.Util.emptyString;
import static honorsThesis.Util.arrayContains;
import static honorsThesis.Util.addArrayToList;
import static honorsThesis.Util.whitespaceRegex;

public class QuizParser 
{
	private static String[] descriptionTags = {"<description>", "<desc>", "<d>"};
	private static String[] answerTags = {"<answer>", "<ans>", "<a>"};
	private static String[] feedbackTags = {"<feedback>", "<fdbk>", "<f>"};
	
	private static String[] descriptionFollows = answerTags;
	private static String[] answerFollows = addArrays(feedbackTags, descriptionTags, String.class);
	private static String[] feedbackFollows = descriptionTags;
	
	private static String[] descriptionForbidden = addArrays(descriptionTags, feedbackTags, String.class);
	private static String[] answerForbidden = answerTags;
	private static String[] feedbackForbidden = addArrays(feedbackTags, answerTags, String.class);
	
	private static boolean descriptionAllowsEOF = false;
	private static boolean answerAllowsEOF = true;
	private static boolean feedbackAllowsEOF = true;
	
	private static String textItemDelimiter = " ";
	private final Token[] tokens;
	private int tokenIndex = 0;

	private QuizParser(Token[] tokens) 
	{
		this.tokens = tokens;
	}
	
	public static Quiz parseFromPath(String path) 
			throws UnexpectedTokenException, EOFNotAllowedException
	{
		Token[] tokens = tokensFromPath(path);
		QuizParser quizParser = new QuizParser(tokens);
		return quizParser.parseQuiz();
	}
	
	private static Token[] tokensFromPath(String path)
	{
		ArrayList<Token> tokenList = new ArrayList<Token>();
		String[] lines = findLines(path);
		for (int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			int lineNumber = i + 1;
			Token[] lineTokens = tokensFromLine(line, lineNumber);
			addArrayToList(tokenList, lineTokens);
		}
		int tokenCount = tokenList.size();
		Token[] pathTokens = tokenList.toArray(new Token[tokenCount]);
		return pathTokens;
	}
	
	private static Token[] tokensFromLine(String fileLine, int lineNumber) 
	{
		String[] lexemes = fileLine.split(whitespaceRegex);
		int tokenCount = lexemes.length;
		Token[] lineTokens = new Token[tokenCount];
		for (int i = 0; i < tokenCount; i++)
		{
			String lexeme = lexemes[i];
			int columnNumber = i + 1;
			lineTokens[i] = new Token(lexeme, columnNumber, lineNumber);
		}
		return lineTokens;
	}
	
	private Token getToken() 
	{
		Token token = null;
		int tokenCount = this.tokens.length;
		if (this.tokenIndex < tokenCount)
		{
			token = this.tokens[tokenIndex];
			this.tokenIndex++;
		}
		return token;
	}
	
	private void ungetToken()
	{
		if (this.tokenIndex > 0)
		{
			this.tokenIndex--;
		}
	}
	
	private Token peek()
	{
		Token token = this.getToken();
		if (token != null)
		{
			this.ungetToken();
		}
		return token;
	}
	
	private Quiz parseQuiz()
			throws UnexpectedTokenException, EOFNotAllowedException
	{
		Question[] questions = this.parseQuestions();
		return new Quiz(questions);
	}
	
	private Question[] parseQuestions()
			throws UnexpectedTokenException, EOFNotAllowedException
	{
		ArrayList<Question> questionList = new ArrayList<Question>();
		int tokenCount = this.tokens.length;
		while (this.tokenIndex < tokenCount)
		{
			Question question = this.parseQuestion();
			questionList.add(question);
		}
		int questionCount = questionList.size();
		Question[] questions = questionList.toArray(new Question[questionCount]);
		return questions;
	}
	
	private Question parseQuestion()
			throws UnexpectedTokenException, EOFNotAllowedException
	{
		String description = this.parseTextBetween(descriptionTags, descriptionFollows, descriptionForbidden, descriptionAllowsEOF);
		String answer = this.parseTextBetween(answerTags, answerFollows, answerForbidden, answerAllowsEOF);
		String feedback = null;
		Token token = peek();
		if (token != null && !arrayContains(descriptionTags, token.lexeme))
		{
			feedback = this.parseTextBetween(feedbackTags, feedbackFollows, feedbackForbidden, feedbackAllowsEOF);
		}
		Question question = new Question(description, answer, feedback);
		return question;
	}
	
	private String parseTextBetween(String[] startTags, String[] endTags, String[] forbiddenTags, boolean eofAllowed)
			throws UnexpectedTokenException, EOFNotAllowedException
	{ 
		String text = null;
		Token token = this.getToken();
		if (token == null)
		{
			if (!eofAllowed)
			{
				throw new EOFNotAllowedException(startTags);
			}
		}
		else if (arrayContains(startTags, token.lexeme))
		{
			text = this.parseUntil(endTags, forbiddenTags, eofAllowed);
		}
		else
		{
			throw new UnexpectedTokenException(startTags, token);
		}
		return text;
	}
	
	private String parseUntil(String[] endTags, String[] forbiddenTags, boolean eofAllowed) 
			throws UnexpectedTokenException, EOFNotAllowedException
	{
		String text = emptyString;
		Token token = this.getToken();
		while (token != null && !arrayContains(endTags, token.lexeme))
		{
			if (arrayContains(forbiddenTags, token.lexeme)) 
			{
				throw new UnexpectedTokenException(endTags, token);
			}
			text += token.lexeme + textItemDelimiter;
			token = this.getToken();
		}
		if (token != null)
		{
			this.ungetToken();
		}
		else if (!eofAllowed)
		{
			throw new EOFNotAllowedException(endTags);
		}
		return text;
	}
}
