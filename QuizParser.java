package HonorsThesis;

import java.util.ArrayList;

public class QuizParser {
	private String quizData;
	private String questionTextLabel = "(Q%d)";
	private String answerTextLabel = "(A%d)";
	private String feedbackTextLabel = "(F%d)";
	
	public static Quiz parseQuiz(String quizData) {
		return new QuizParser(quizData).parseQuiz();
	}
	
	private QuizParser(String quizData) {
		this.quizData = quizData;
	}
		
	private Quiz parseQuiz() {
		return new Quiz(parseQuestions());
	}
	
	private ArrayList<Question> parseQuestions() {
		int questionLabel = 1;
		ArrayList<Question> questions = new ArrayList<Question>();
		Question question = parseQuestion(questionLabel);
		while (question != null) { // parse questions until we can't find any more
			questions.add(question);
			questionLabel++;
			question = parseQuestion(questionLabel);
		}
		return questions;
	}
	
	private Question parseQuestion(int questionLabel) {
		Question question = null;
		if (quizData.contains(String.format(questionTextLabel, questionLabel)) ) { // check if the question exists
			ArrayList<String> textLabels = getTextLabels(questionLabel);
			ArrayList<String> texts = getTexts(textLabels);
			question = new Question(texts.get(0), texts.get(1), texts.get(2));
		}
		return question;
	}
	
	private ArrayList<String> getTextLabels(int questionLabel) {
		ArrayList<String> textLabels = new ArrayList<String>(); 
		textLabels.add(String.format(questionTextLabel, questionLabel)); // put the labels for the current question into a list
		textLabels.add(String.format(answerTextLabel, questionLabel));
		textLabels.add(String.format(feedbackTextLabel, questionLabel));
		textLabels.add(String.format(questionTextLabel, questionLabel + 1)); // put the first label of the next question at the end of the list
		return textLabels;
	}
	
	private ArrayList<String> getTexts(ArrayList<String> textLabels) {
		ArrayList<String> texts = new ArrayList<String>(); // extract text for each pair of labels
		for (int i = 0; i < textLabels.size() - 1; i++) {
			int textLabelIndex = quizData.indexOf(textLabels.get(i)) + textLabels.get(i).length(); // don't include the label
			int nextTextLabelIndex = quizData.indexOf(textLabels.get(i + 1));
			texts.add(determineText(textLabelIndex, nextTextLabelIndex));
		}
		return texts;
	}
	
	private String determineText(int textLabelIndex, int nextTextLabelIndex) {
		String text = null;
		if (nextTextLabelIndex != -1) text = quizData.substring(textLabelIndex, nextTextLabelIndex);
		else text = quizData.substring(textLabelIndex); // if the next label doesn't exist, assume we've reached the end
		text = text.trim(); // remove invisible characters
		return text;
	}
}