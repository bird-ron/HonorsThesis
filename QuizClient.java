package honorsThesis;

import java.util.ArrayList;
import static honorsThesis.UserIO.print;

public class QuizClient 
{
	public static final String quizFolder = "Quizzes";
	public static final String quizFileExt = ".txt";
	private static String quizSelectionMessageHeader = "Select a quiz, or type -1 to quit.";
	private static String correctMessage = "Correct!";
	private static String incorrectMessage = "Incorrect.";

	
	public boolean promptUserToTakeQuiz() 
	{
		Boolean quizTakenSuccessfully = null;
		ArrayList<String> quizNames = FileIO.findFileNames(quizFolder, quizFileExt);
		String quizSelectionMessage = findQuizSelectionMessage(quizNames);
		Integer quizIndex = UserIO.promptUserForIntegerIncl(0, quizNames.size() - 1, quizSelectionMessage);
		if (quizIndex != null) 
		{
			String quizName = quizNames.get(quizIndex);
			String quizPath = String.format("%s/%s%s", quizFolder, quizName, quizFileExt);
			String quizData = FileIO.findFileContents(quizPath);
			Quiz quiz = QuizParser.tryToParseQuiz(quizData);
			if (quiz != null)
			{
				takeQuiz(quiz);
				quizTakenSuccessfully = true;
			}
			else quizTakenSuccessfully = false;			
		}
		else 
		{
			quizTakenSuccessfully = false;
		}
		return quizTakenSuccessfully;
	}
	
	private static String findQuizSelectionMessage(ArrayList<String> quizNames) 
	{
		String quizSelectionMessage = quizSelectionMessageHeader + "\n";
		for (int i = 0; i < quizNames.size(); i++) 
		{
			String labeledQuizName = String.format("%d. %s", i, quizNames.get(i));
			quizSelectionMessage += labeledQuizName + "\n";
		}
		return quizSelectionMessage;
	}
	
	private static void takeQuiz(Quiz quiz)
	{
		Question[] questions = quiz.getQuestions();
		for (int i = 0; i < questions.length; i++) 
		{
			Question question = questions[i];
			String description = question.getDescription();
			String answer = question.getAnswer();
			String feedback = question.getFeedback();
			String userResponse = UserIO.promptUserForString(description);
			checkAndPrint(answer, userResponse, feedback);
		}	
	}

	private static void checkAndPrint(String answer, String userResponse, String feedback)
	{
		boolean userResponseIsCorrect = checkResponse(answer, userResponse);
		if (userResponseIsCorrect)
		{
			print(correctMessage);
		}
		else 
		{
			print(incorrectMessage);
			print(feedback);
		}
		print();
	}
	
	private static boolean checkResponse(String answer, String userResponse)
	{
		return answer.equalsIgnoreCase(userResponse);
	}
}