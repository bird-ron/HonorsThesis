package HonorsThesis;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public class Client {
	private static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			String quizData = Files.readString(Path.of("quiz.txt"));
			Quiz quiz = QuizParser.parseQuiz(quizData);
			quiz.takeQuiz(userInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}