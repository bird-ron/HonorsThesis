package honorsThesis;

public class Question {
	private String description;
	private String answer;
	private String feedback;
	
	public Question() {
		this.description = null;
		this.answer = null;
		this.feedback = null;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String getFeedback() {
		return feedback;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	@Override
	public String toString() {
		return String.format("description = %s\nanswer = %s\nfeedback = %s\n", description, answer, feedback);
	}
}