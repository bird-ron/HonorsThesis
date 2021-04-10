package honorsThesis;

import static honorsThesis.UserIO.print;
import static honorsThesis.Util.newline;
import static honorsThesis.Util.emptyString;
import static honorsThesis.Util.whitespaceRegex;

public class QuizClient 
{
	public static final String quizFolder = "Quizzes";
	public static final String quizFileExt = ".txt";
	private static String quizSelectionMessageHeader = "Select a quiz, or type -1 to quit.";
	private static String correctMessage = "Correct!";
	private static String incorrectMessage = "Incorrect.";

	public static boolean promptUserToTakeQuiz() 
	{
		String quizName = promptUserForQuizName();
		boolean selectionIsValid = quizName != null;
		if (selectionIsValid)
		{
			parseAndTake(quizName);
		}
		return selectionIsValid;
	}
	
	private static void parseAndTake(String quizName)
	{
		try
		{
			String quizPath = String.format("%s/%s", quizFolder, quizName);
			Quiz quiz = QuizParser.parseFromPath(quizPath);
			takeQuiz(quiz);	
		}
		catch (UnexpectedTokenException quizParseException)
		{
			print(quizParseException + newline);
		}
		catch (EOFNotAllowedException eofNotAllowedException)
		{
			print(eofNotAllowedException + newline);
		}
	}
	
	private static void takeQuiz(Quiz quiz)
	{
		for (int i = 0; i < quiz.length; i++) 
		{
			Question question = quiz.getQuestion(i);
			String response = UserIO.promptUserForString(question.description);
			checkAndPrint(question.answer, response, question.feedback);
		}	
	}

	private static void checkAndPrint(String answer, String response, String feedback)
	{
		boolean isCorrect = checkResponse(answer, response);
		if (isCorrect)
		{
			print(correctMessage);
		}
		else 
		{
			print(incorrectMessage);
			if (feedback != null)
			{
				print(feedback);
			}
		}
		print();
	}
	
	private static boolean checkResponse(String answer, String response)
	{
		String sanitizedAnswer = sanitizeString(answer);
		String sanitizedResponse = sanitizeString(response);
		return sanitizedAnswer.equals(sanitizedResponse);
	}
	
	private static String sanitizeString(String string)
	{
		String sanitizedString = string.toLowerCase().replaceAll(whitespaceRegex, emptyString);
		return sanitizedString;
	}
	
	private static String promptUserForQuizName()
	{
		String[] quizNames = FileIO.findFilenames(quizFolder, quizFileExt);
		String quizSelectionMessage = findQuizSelectionMessage(quizNames);
		Integer quizIndex = UserIO.promptUserForIntegerIncl(0, quizNames.length - 1, quizSelectionMessage);
		String quizName = null;
		if (quizIndex != null)
		{
			quizName = quizNames[quizIndex];
		}
		return quizName;
	}
	
	private static String findQuizSelectionMessage(String[] quizNames)
	{
		String quizSelectionMessage = quizSelectionMessageHeader + newline;
		for (int i = 0; i < quizNames.length; i++) 
		{
			String labeledQuizName = String.format("%d. %s", i, quizNames[i]);
			quizSelectionMessage += labeledQuizName + newline;
		}
		return quizSelectionMessage;
	}
}