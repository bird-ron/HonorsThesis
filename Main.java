package honorsThesis;

import static honorsThesis.QuizClient.promptUserToTakeQuiz;

public class Main
{
	public static void main(String[] args)
	{
		Boolean quizTakenSuccessfully = null;
		do 
		{
			quizTakenSuccessfully = promptUserToTakeQuiz();
		} 
		while (quizTakenSuccessfully);
	}
}
