package honorsThesis;

import java.util.ArrayList;
import static honorsThesis.UserIO.print;
import static honorsThesis.FileIO.findLines;
import static honorsThesis.Util.arrayContains;
import static honorsThesis.Util.addArrayToList;
import static honorsThesis.Util.whitespaceRegex;
import static honorsThesis.Util.emptyString;

public class QuizParser 
{
	private static String[] descriptionTags = {"<description>", "<desc>", "<d>"};
	private static String[] answerTags = {"<answer>", "<ans>", "<a>"};
	private static String[] feedbackTags = {"<feedback>", "<fdbk>", "<f>"};
	private static String textItemDelimiter = " ";
	private final Token[] tokens;
	private int tokenIndex = 0;

	private QuizParser(Token[] tokens) 
	{
		this.tokens = tokens;
	}
	
	public static void main(String[] args)
	{
		String path = "quizzes\\quiz.txt";
		Token[] tokens = tokensFromPath(path);
		for (Token token : tokens)
		{
			print(token);
		}
	}
	
	public static Quiz parseQuiz(String path) 
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
			int tokenNumber = i + 1;
			lineTokens[i] = new Token(lexeme, tokenNumber, lineNumber);
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
			tokenIndex++;
		}
		return token;
	}
	
	private void ungetToken()
	{
		if (this.tokenIndex > 0)
		{
			tokenIndex--;
		}
		// else throw exception
	}
	
	private Quiz parseQuiz() 
	{
		Question[] questions = this.parseQuestions();
		return new Quiz(questions);
	}
	
	private Question[] parseQuestions() 
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
	{
		String description = this.parseTextBetween(descriptionTags, answerTags);
		String answer = this.parseTextBetween(answerTags, feedbackTags);
		String feedback = this.parseTextBetween(feedbackTags, descriptionTags);
		Question question = new Question(description, answer, feedback);
		return question;
	}
	
	private String parseTextBetween(String[] startTags, String[] endTags)
	{ 
		String text = null;
		Token token = this.getToken();
		if (arrayContains(startTags, token.lexeme))
		{
			text = this.parseUntil(endTags);
		}
		// else throw exception
		return text;
	}
	
	private String parseUntil(String[] endTags)
	{
		String text = emptyString;
		Token token = this.getToken();
		while (token != null && !arrayContains(endTags, token.lexeme))
		{
			text += token.lexeme + textItemDelimiter;
			token = this.getToken();
		}
		if (token != null)
		{
			this.ungetToken();
		}
		return text;
	}
}
