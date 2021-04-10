package honorsThesis;

public class Quiz 
{
	private final Question[] questions;
	public final int length;
	
	public Quiz(Question[] questions)
	{
		this.questions = questions;
		this.length = questions.length;
	}
	
	public Question getQuestion(int index)
	{
		return this.questions[index];
	}
}
