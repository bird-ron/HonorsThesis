package HonorsThesis;

import java.util.ArrayList;

public class Quiz {
	// merge these into question object
	private final ArrayList<String> questions;
	private final ArrayList<String> answers;
	
	public Quiz(ArrayList<String> questions, ArrayList<String> answers) {
		this.questions = questions;
		this.answers = answers;
	}
	
	public ArrayList<String> getQuestions() {
		return questions;
	}
	
	public String score(ArrayList<String> userAnswers) {
		int correct = 0;
		for (int i = 0; i < answers.size(); i++) 
			if (answers.get(i).equalsIgnoreCase(userAnswers.get(i))) 
				correct++;
		return "Score: " + correct + "/" + answers.size();
	}
	
	// question could have question, answer, preferred feedback
	public ArrayList<String> grade(ArrayList<String> userAnswers) {
		final ArrayList<String> gradedAnswers = new ArrayList<String>();
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i).equalsIgnoreCase(userAnswers.get(i))) 
				gradedAnswers.add(createAnswerCorrectString(i));
			else 
				gradedAnswers.add(createAnswerIncorrectString(i, userAnswers.get(i)));
		}
		return gradedAnswers;
	}
	
	private String createAnswerCorrectString(int index) {
		return createQuestionBullet(index) + " Correct!";
	}
	
	private String createAnswerIncorrectString(int index, String userAnswer) {
		return createQuestionBullet(index) + " Incorrect. Your answer: [" + userAnswer + "] Actual answer: [" + answers.get(index) + "]";
	}
	
	public static String createQuestionBullet(final int index) {
		return createBullet("Q", index);
	}
	
	public static String createAnswerBullet(final int index) {
		return createBullet("A", index);
	}
	
	public static String createBullet(final String label, final int index) {
		return "(" + label + index + ")";
	}
}