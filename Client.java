package HonorsThesis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import static HonorsThesis.Utility.println;
import static HonorsThesis.Parser.parseQuiz;

public class Client {
	private static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		final Quiz quiz = parseQuiz(FileIO.fileRead(Path.of("quiz.txt")));
		final ArrayList<String> userAnswers = getUserAnswers(quiz);
		println(quiz.score(userAnswers));
		for (String gradedQuestion : quiz.grade(userAnswers)) {
			println(gradedQuestion);
		}
	}
	
	private static ArrayList<String> getUserAnswers(Quiz quiz) {
		final ArrayList<String> userAnswers = new ArrayList<String>();
		for (String question : quiz.getQuestions()) {
			println(question);
			userAnswers.add(userInput.nextLine());
		}
		return userAnswers;
	}
}