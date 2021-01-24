package HonorsThesis;

public class Question {
	private String questionText;
	private String answerText;
	private String feedbackText;
	
	public Question(String questionText, String answerText, String feedbackText) {
		this.questionText = questionText;
		this.answerText = answerText;
		this.feedbackText = feedbackText;
	}
	
	public String getQuestionText() {
		return questionText;
	}
	
	public String getAnswerText() {
		return answerText;
	}
	
	public String getFeedbackText() {
		return feedbackText;
	}
	
	@Override
	public String toString() {
		return String.format("questionText = %s\nanswerText = %s\nfeedbackText = %s\n", questionText, answerText, feedbackText);
	}
}