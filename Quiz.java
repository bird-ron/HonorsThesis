package honorsThesis;

public class Quiz 
{
	private Question[] questions;
	
	public Quiz(Question[] questions)
	{
		this.questions = questions;
	}
	
	public Question[] getQuestions() 
	{
		return this.questions;
	}
}
