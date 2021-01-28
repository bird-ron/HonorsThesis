package honorsThesis;

import java.util.Arrays;
import java.util.ArrayList;

public class QuizParser {
	private ArrayList<String> quizTokens;
	private String descriptionLabelFormat;
	private String answerLabelFormat;
	private String feedbackLabelFormat;
	private boolean isInErrorState;
	
	public static Quiz tryToParseQuiz(String quizData) {
		QuizParser quizParser = new QuizParser();
		quizParser.quizTokens = new ArrayList<String>(Arrays.asList(quizData.split("\\s+")));
		quizParser.descriptionLabelFormat = "(Q%d)";
		quizParser.answerLabelFormat = "(A%d)";
		quizParser.feedbackLabelFormat = "(F%d)";
		quizParser.isInErrorState = false;
		return quizParser.parseQuiz();
	}
	
	private QuizParser() {
		quizTokens = null;
		descriptionLabelFormat = null;
		answerLabelFormat = null;
		feedbackLabelFormat = null;
	}
	
	private String getToken() {
		return quizTokens.remove(0);
	}
	
	private void ungetToken(String token) {
		quizTokens.add(0, token);
	}
	
	private boolean outOfTokens() {
		return quizTokens.isEmpty();
	}
	
	private Quiz parseQuiz() {
		return new Quiz(parseQuestions());
	}
	
	private ArrayList<Question> parseQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		int questionNumber = 1;
		while (!quizTokens.isEmpty()) {
			questions.add(parseQuestion(questionNumber));
			questionNumber++;
		}
		if (isInErrorState) questions.clear();
		return questions;
	}
	
	private Question parseQuestion(int questionNumber) {
		Question question = new Question();
		String descriptionLabel = String.format(descriptionLabelFormat, questionNumber);
		String answerLabel = String.format(answerLabelFormat, questionNumber);
		String feedbackLabel = String.format(feedbackLabelFormat, questionNumber);
		String nextDescriptionLabel = String.format(descriptionLabelFormat, questionNumber + 1);
		question.setDescription(parseTextBetween(descriptionLabel, answerLabel));
		question.setAnswer(parseTextBetween(answerLabel, feedbackLabel));
		question.setFeedback(parseTextBetween(feedbackLabel, nextDescriptionLabel));
		return question;
	}
	
	private String parseTextBetween(String currentLabel, String nextLabel) { 
		String text = null;
		if (!isInErrorState) {
			String token = getToken();
			if (currentLabel.equals(token)) text = parseTextBetweenUnsafe(currentLabel, nextLabel);
			else parseTextError(currentLabel, token);
		}
		return text;
	}
	
	private String parseTextBetweenUnsafe(String currentLabel, String nextLabel) {
		StringBuffer textBuffer = new StringBuffer();
		String token = getToken();
		while (!token.equals(nextLabel) && !outOfTokens()) {
			textBuffer.append(token + " ");
			token = getToken();
		}
		if (!outOfTokens()) ungetToken(token);
		else textBuffer.append(token + " ");
		textBuffer = textBuffer.deleteCharAt(textBuffer.length() - 1); // delete trailing space
		return textBuffer.toString();
	}
	
	private void parseTextError(String currentLabel, String token) {
		System.out.println(String.format("Error while parsing: expected %s, got %s\n", currentLabel, token));
		isInErrorState = true;
		quizTokens.clear();
	}
}