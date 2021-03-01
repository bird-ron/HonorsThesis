package honorsThesis;

import java.util.Arrays;
import java.util.ArrayList;

public class QuizParser {
	private ArrayList<String> quizTokens;
	private ArrayList<String> descriptionLabels;
	private ArrayList<String> answerLabels;
	private ArrayList<String> feedbackLabels;
	private boolean isInErrorState;

	public static Quiz tryToParseQuiz(String quizData) {
		QuizParser quizParser = new QuizParser();
		quizParser.quizTokens = new ArrayList<String>(Arrays.asList(quizData.split("\\s+")));
		quizParser.descriptionLabels = new ArrayList<String>(Arrays.asList("<description>", "<desc>", "<d>"));
		quizParser.answerLabels = new ArrayList<String>(Arrays.asList("<answer>", "<ans>", "<a>"));
		quizParser.feedbackLabels = new ArrayList<String>(Arrays.asList("<feedback>", "<fdbk>", "<f>"));
		quizParser.isInErrorState = false;
		return quizParser.parseQuiz();
	}
	
	private QuizParser() {
		quizTokens = null;
		descriptionLabels = null;
		answerLabels = null;
		feedbackLabels = null;
		isInErrorState = false;
	}
	
	private String getToken() {
		try {
			return quizTokens.remove(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private void ungetToken(String token) {
		quizTokens.add(0, token);
	}
	
	private Quiz parseQuiz() {
		return new Quiz(parseQuestions());
	}
	
	private ArrayList<Question> parseQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		while (!quizTokens.isEmpty()) questions.add(parseQuestion());
		if (isInErrorState) questions.clear();
		return questions;
	}
	
	private Question parseQuestion() {
		Question question = new Question();
		question.setDescription(parseTextBetween(descriptionLabels, answerLabels));
		question.setAnswer(parseTextBetween(answerLabels, feedbackLabels));
		question.setFeedback(parseTextBetween(feedbackLabels, descriptionLabels));
		return question;
	}
	
	private String parseTextBetween(ArrayList<String> startTokens, ArrayList<String> endTokens) { 
		String text = null;
		String token = getToken();
		if (startTokens.contains(token)) text = parseTextLoop(endTokens);
		else parseTextError(startTokens, token);
		return text;
	}
	
	private String parseTextLoop(ArrayList<String> endTokens) {
		StringBuffer textBuffer = new StringBuffer();
		String token = getToken();
		String delimiter = " ";
		while (!endTokens.contains(token) && token != null) {
			textBuffer.append(token + delimiter);
			token = getToken();
		}
		if (token != null) ungetToken(token);
		else textBuffer.append(token + " ");
		textBuffer = textBuffer.delete(textBuffer.length() - delimiter.length(), textBuffer.length());
		return textBuffer.toString();
	}
	
	private void parseTextError(ArrayList<String> startTokens, String token) {
		String startTokensString = String.join(", ", startTokens);
		System.out.println(String.format("Error while parsing: expected %s, got %s", startTokensString, token));
		isInErrorState = true;
		quizTokens.clear();
	}
}