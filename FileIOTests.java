package honorsThesis;

import java.util.List;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import static honorsThesis.QuizClient.quizFolder;
import static honorsThesis.QuizClient.quizFileExt;
import static honorsThesis.FileIO.findFilePaths;
import static honorsThesis.FileIO.findFilenames;
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
		List<String> filenames = findFilenames(quizFolder, quizFileExt);
		for (String filename : filenames)
		{
			print(filename);
		}
	}
	
	@SuppressWarnings("unused")
	private static void checkQuizPaths() throws IOException
	{
		List<String> filepaths = findFilePaths(quizFolder, quizFileExt);
		for (String filepath : filepaths)
		{
			print(filepath);
		}
	}
}
