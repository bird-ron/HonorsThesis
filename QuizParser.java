package HonorsThesis;

import java.util.ArrayList;

public class QuizParser {
	private String quizData;
	private String questionTextLabel = "(Q%d)";
	private String answerTextLabel = "(A%d)";
	private String feedbackTextLabel = "(F%d)";
	
	private QuizParser(String quizData) {
		this.quizData = quizData;
	}
	
	public static Quiz parseQuiz(String quizData) {
		return new QuizParser(quizData).parseQuiz();
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
			ArrayList<String> textLabels = new ArrayList<String>(); 
			textLabels.add(String.format(questionTextLabel, questionLabel)); // put the labels for the current question into a list
			textLabels.add(String.format(answerTextLabel, questionLabel));
			textLabels.add(String.format(feedbackTextLabel, questionLabel));
			textLabels.add(String.format(questionTextLabel, questionLabel + 1)); // put the first label of the next question at the end of the list
			
			ArrayList<String> texts = new ArrayList<String>(); // extract text for each pair of labels
			for (int i = 0; i < textLabels.size() - 1; i++) {
				int textLabelIndex = quizData.indexOf(textLabels.get(i)) + textLabels.get(i).length(); // don't include the label
				int nextTextLabelIndex = quizData.indexOf(textLabels.get(i + 1));
				
				String text = null;
				if (nextTextLabelIndex != -1) text = quizData.substring(textLabelIndex, nextTextLabelIndex);
				else text = quizData.substring(textLabelIndex); // if the next label doesn't exist, assume we've reached the end
				text = text.trim(); // remove invisible characters
				texts.add(text);
			}
			question =  new Question(texts.get(0), texts.get(1), texts.get(2));
		}
		return question;
	}
}