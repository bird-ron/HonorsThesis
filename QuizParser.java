package honorsThesis;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import static honorsThesis.ModeParser.parseMode;
import static honorsThesis.VariableParser.parseVariables;
import static honorsThesis.TextParser.parseDescription;
import static honorsThesis.TextParser.parseFeedback;
import static honorsThesis.AnswerParser.parseAnswer;
import static honorsThesis.Token.nullToken;

public class QuizParser 
{
	private final TokenStream tokenStream;
	
	public static Quiz parse(String path) throws UnexpectedTokenException
	{
		TokenStream tokenStream = new TokenStream(path);
		QuizParser quizParser = new QuizParser(tokenStream);
		return quizParser.parseQuiz();
	}
	
	private QuizParser(TokenStream tokenStream)
	{
		this.tokenStream = tokenStream;
	}
	
	private Quiz parseQuiz() throws UnexpectedTokenException
	{
		boolean feedbackIsImmediate = parseMode(tokenStream);
		List<Question> questions = parseQuestions();
		return new Quiz(feedbackIsImmediate, questions);
	}
	
	private List<Question> parseQuestions() throws UnexpectedTokenException
	{
		List<Question> questions = new ArrayList<Question>();
		while (tokenStream.peek() != nullToken)
		{
			Question question = parseQuestion();
			questions.add(question);
		}
		return unmodifiableList(questions);
	}
	
	private Question parseQuestion() throws UnexpectedTokenException
	{
		HashMap<String, Double> variables = parseVariables(tokenStream);
		String description = parseDescription(tokenStream, variables);
		Double answer = parseAnswer(tokenStream, variables);
		String feedback = parseFeedback(tokenStream, variables);
		Question question = new Question(description, answer, feedback);
		return question;
	}
}	
