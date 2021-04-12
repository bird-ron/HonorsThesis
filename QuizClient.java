package honorsThesis;

import java.util.List;
import static honorsThesis.FileIO.findFilenames;
import static honorsThesis.UserIO.print;
import static honorsThesis.UserIO.promptUserForDouble;
import static honorsThesis.UserIO.promptUserForIntegerIncl;
import static honorsThesis.Util.newline;
import static honorsThesis.Util.emptyString;
import static honorsThesis.Util.closeEnough;

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
			Quiz quiz = QuizParser.parse(quizPath);
			takeQuiz(quiz);	
		}
		catch (UnexpectedTokenException quizParseException)
		{
			print(quizParseException + newline);
		}
	}
	
	private static void takeQuiz(Quiz quiz)
	{
		String messageBuffer = emptyString;
		for (int i = 0; i < quiz.questions.size(); i++)
		{
			Question question = quiz.questions.get(i);
			Double response = promptUserForDouble(question.description);
			if (closeEnough(question.answer, response))
			{
				messageBuffer += (i + 1) + ". " + correctMessage + "\n";
			}
			else 
			{
				messageBuffer += (i + 1) + ". " + incorrectMessage + "\n";
				if (question.feedback != null)
				{
					messageBuffer += question.feedback + "\n";
				}
			}
			if (quiz.feedbackIsImmediate)
			{
				print(messageBuffer);
				messageBuffer = emptyString;
			}
			else
			{
				print();
			}
		}
		if (!messageBuffer.isEmpty())
		{
			print(messageBuffer);
		}
	}
	
	private static String promptUserForQuizName()
	{
		List<String> quizNames = findFilenames(quizFolder, quizFileExt);
		String quizSelectionMessage = findQuizSelectionMessage(quizNames);
		Integer quizIndex = promptUserForIntegerIncl(0, quizNames.size() - 1, quizSelectionMessage);
		String quizName = null;
		if (quizIndex != null)
		{
			quizName = quizNames.get(quizIndex);
		}
		return quizName;
	}
	
	private static String findQuizSelectionMessage(List<String> quizNames)
	{
		String quizSelectionMessage = quizSelectionMessageHeader + newline;
		int quizNameCount = quizNames.size();
		for (int i = 0; i < quizNameCount; i++) 
		{
			String labeledQuizName = String.format("%d. %s", i, quizNames.get(i));
			quizSelectionMessage += labeledQuizName + newline;
		}
		return quizSelectionMessage;
	}
}