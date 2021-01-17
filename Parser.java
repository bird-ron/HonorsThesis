package HonorsThesis;

import java.util.ArrayList;
import java.util.function.Function;
import static HonorsThesis.Quiz.createQuestionBullet;
import static HonorsThesis.Quiz.createAnswerBullet;


public class Parser {
	private String quizData;
	
	public Parser(String quizData) {
		this.quizData = quizData;
	}
	
	public static Quiz parseQuiz(String quizData) {
		return new Parser(quizData).parseQuiz();
	}
	
	public Quiz parseQuiz() {
		return new Quiz(parseQuestions(), parseAnswers());
	}
	
	private ArrayList<String> parseQuestions() {
		return parseUntilNull(this::parseQuestion, 1);
	}
	
	private ArrayList<String> parseAnswers() {
		return parseUntilNull(this::parseAnswer, 1);
	}
	
	private ArrayList<String> parseUntilNull(Function<Integer, String> parseFunction, int startIndex) {
		ArrayList<String> parsedStrings = new ArrayList<String>();
		int index = startIndex;
		String parsedString = parseFunction.apply(index);
		
		while (parsedString != null) {
			parsedStrings.add(parsedString);
			index++;
			parsedString = parseFunction.apply(index);
		}
		
		return parsedStrings;
	}
	
	private String parseQuestion( int index) {
		int left = quizData.indexOf(createQuestionBullet(index));
		int right = quizData.indexOf(createAnswerBullet(index));
		return determineQuestion(left, right);
	}
	
	// need both to be valid, otherwise null
	private String determineQuestion(int left, int right) {
		String question;
		if (left != -1 && right != -1) question = quizData.substring(left, right).strip();
		else question = null;
		return question;
	}
	
	
	private String parseAnswer(int index) {
		 String answerBullet = createAnswerBullet(index);
		 int left = quizData.indexOf(answerBullet);
		 int right = quizData.indexOf(createQuestionBullet(index + 1));
		 int adjustedLeft = left + answerBullet.length();
		return determineAnswer(left, right, adjustedLeft);
	}
	
	/*
	 * if both are OK = adjusted left to right
	 * we know at least one is bad. if left is OK = adjusted left to end
	 * we know left is bad. null
	 */
	private String determineAnswer(int left, int right, int adjustedLeft) {
		String answer;
		if (left != -1 && right != -1) answer = quizData.substring(adjustedLeft, right).strip();
		else if (left != -1) answer = quizData.substring(adjustedLeft, quizData.length()).strip();
		else answer = null;
		return answer;
	}
}