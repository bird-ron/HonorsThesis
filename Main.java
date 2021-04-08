package honorsThesis;

public class Main
{
	public static void main(String[] args)
	{
		QuizClient quizClient = new QuizClient();
		Boolean quizTakenSuccessfully = null;
		do 
		{
			quizTakenSuccessfully = quizClient.promptUserToTakeQuiz();
		} 
		while (quizTakenSuccessfully);
	}
}
