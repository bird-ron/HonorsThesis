package HonorsThesis;

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
	private String quizSelectionUserPrompt;
	
	public static void main(String[] args) {
		String quizDirectory = "Quizzes";
		String quizExtension = ".txt";
		String quizSelectionUserPrompt = "Select a quiz, or type -1 to quit.";
		QuizClient quizClient = new QuizClient(quizDirectory, quizExtension, quizSelectionUserPrompt);
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
		} 
		catch (NumberFormatException e) {
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
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return filenames;
	}
	
	private QuizClient(String quizDirectory, String quizExtension, String quizSelectionUserPrompt) {
		this.quizDirectory = quizDirectory;
		this.quizExtension = quizExtension;
		this.quizSelectionUserPrompt = quizSelectionUserPrompt;
	}
	
	private boolean promptUserToTakeQuiz() {
		Boolean quizTakenSuccessfully = null;
		try {
			String quizLabel = promptUserForQuizLabel();
			if (quizLabel != null) {
				String quizData = Files.readString(Path.of(String.format("%s/%s%s", quizDirectory, quizLabel, quizExtension)));
				Quiz quiz = QuizParser.parseQuiz(quizData);
				quiz.takeQuiz(userInput);
				quizTakenSuccessfully = true;
			}
			else quizTakenSuccessfully = false;
		} catch (IOException e) {
			e.printStackTrace();
			quizTakenSuccessfully = false;
		}
		return quizTakenSuccessfully;
	}
	
	private String promptUserForQuizLabel() {
		String quizLabel = null;
		ArrayList<String> quizLabels = getFilenames(Path.of(quizDirectory));
		System.out.println(quizSelectionUserPrompt);
		for (int i = 0; i < quizLabels.size(); i++) System.out.println(String.format("%d. %s", i, quizLabels.get(i)));
		Integer quizLabelIndex = promptUserForInteger(0, quizLabels.size() - 1);
		if (quizLabelIndex != null) quizLabel = quizLabels.get(quizLabelIndex);
		return quizLabel;
	}
}