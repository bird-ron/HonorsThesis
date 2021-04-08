package honorsThesis;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import static honorsThesis.QuizClient.quizFolder;
import static honorsThesis.QuizClient.quizFileExt;
import static honorsThesis.FileIO.findFilePaths;
import static honorsThesis.FileIO.findFileNames;
import static honorsThesis.FileIO.findFileContents;
import static honorsThesis.UserIO.print;

public class FileIOTests
{	
	private static String testFunctionPrefix = "check";
	
	public static void main(String[] args)
	{
		try
		{
			Method[] methods = FileIOTests.class.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++)
			{
				Method method = methods[i];
				String methodName = method.getName();
				if (methodName.startsWith(testFunctionPrefix))
				{
					print("%s:".formatted(methodName));
					method.invoke(null);
					print();
				}
			}
		}
		catch (IllegalAccessException illegalAccessException)
		{
			illegalAccessException.printStackTrace();
		}
		catch (IllegalArgumentException illegalArgumentException)
		{
			illegalArgumentException.printStackTrace();
		}
		catch (InvocationTargetException invocationTargetException)
		{
			invocationTargetException.printStackTrace();
		}
	} 
	
	@SuppressWarnings("unused")
	private static void checkQuizFileNames() throws IOException
	{
		ArrayList<String> filenames = findFileNames(quizFolder, quizFileExt);
		for (String filename : filenames)
		{
			print(filename);
		}
	}
	
	@SuppressWarnings("unused")
	private static void checkQuizPaths() throws IOException
	{
		ArrayList<String> filepaths = findFilePaths(quizFolder, quizFileExt);
		for (String filepath : filepaths)
		{
			print(filepath);
		}
	}
	
	@SuppressWarnings("unused")
	private static void checkQuizContents() throws IOException
	{
		ArrayList<String> filepaths = findFilePaths(quizFolder, quizFileExt);
		for (String filepath : filepaths)
		{
			print(findFileContents(filepath));
		}
	}
	
}
