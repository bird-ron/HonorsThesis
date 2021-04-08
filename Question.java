package honorsThesis;

public class Question 
{
	private String description;
	private String answer;
	private String feedback;
	
	public Question() 
	{
		this.description = null;
		this.answer = null;
		this.feedback = null;
	}
	
	public String getDescription() 
	{
		return this.description;
	}
	
	public String getAnswer() 
	{
		return this.answer;
	}
	
	public String getFeedback() 
	{
		return this.feedback;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public void setAnswer(String answer) 
	{
		this.answer = answer;
	}
	
	public void setFeedback(String feedback) 
	{
		this.feedback = feedback;
	}
}
