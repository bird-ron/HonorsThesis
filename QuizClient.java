package honorsThesis;

import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.function.Consumer;

public class QuizClient {
	private static Scanner userInput = new Scanner(System.in);
	
	private String quizDirectory;
	private String quizExtension;
	private String quizNameSelectionPrompt;
	
	public static void main(String[] args) {
		QuizClient quizClient = new QuizClient();
		quizClient.quizDirectory = "Quizzes";
		quizClient.quizExtension = ".txt";
		quizClient.quizNameSelectionPrompt = "Select a quiz, or type -1 to quit.";
		Boolean quizTakenSuccessfully = null;
		do {
			quizTakenSuccessfully = quizClient.promptUserToTakeQuiz();
		} while (quizTakenSuccessfully);
	}
	
	private static boolean bounded(int min, int max, int value) {
		return min <= value && value <= max;
	}
	
	private static Integer promptUserForInteger(int min, int max) {
		Integer integer = null;
		try {
			integer = Integer.parseInt(userInput.next());
			if (!bounded(min, max, integer)) integer = null;
		} catch (NumberFormatException e) {
			integer = null;
		}
		return integer;
	}
	
	private static String getFilename(Path path) {
		String filename = path.toString();
		filename = filename.substring(filename.indexOf("\\") + 1, filename.indexOf("."));
		return filename;
	}
	
	private static ArrayList<String> getFilenames(Path path) {
		final ArrayList<String> filenames = new ArrayList<String>();
		try (Stream<Path> filepaths = Files.walk(path)) {
			Consumer<Path> addToSubpathFilenames = subpath -> filenames.add(getFilename(subpath));
			filepaths.filter(Files::isRegularFile).forEach(addToSubpathFilenames);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filenames;
	}
	
	private QuizClient() {
		quizDirectory = null;
		quizExtension = null;
		quizNameSelectionPrompt = null;
	}
	
	private boolean promptUserToTakeQuiz() {
		Boolean quizTakenSuccessfully = null;
		try {
			String quizName = promptUserForQuizName();
			if (quizName != null) {
				String quizData = Files.readString(Path.of(String.format("%s/%s%s", quizDirectory, quizName, quizExtension)));
				Quiz quiz = QuizParser.tryToParseQuiz(quizData);
				quiz.takeQuiz(userInput);
				quizTakenSuccessfully = true;
			} else quizTakenSuccessfully = false;
		} catch (IOException e) {
			e.printStackTrace();
			quizTakenSuccessfully = false;
		}
		return quizTakenSuccessfully;
	}
	
	private String promptUserForQuizName() {
		String selectedQuizName = null;
		ArrayList<String> quizNames = getFilenames(Path.of(quizDirectory));
		System.out.println(quizNameSelectionPrompt);
		for (int i = 0; i < quizNames.size(); i++) System.out.println(String.format("%d. %s", i, quizNames.get(i)));
		Integer quizIndex = promptUserForInteger(0, quizNames.size() - 1);
		if (quizIndex != null) selectedQuizName = quizNames.get(quizIndex);
		return selectedQuizName;
	}
}