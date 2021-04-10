package honorsThesis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static honorsThesis.Util.backslash;
import static honorsThesis.Util.emptyString;

public class FileIO 
{
	public static String[] findFilePaths(String folder, String extension)
	{
		return findFilenames(folder, extension, folder + backslash);
	}
	
	public static String[] findFilenames(String folder, String extension)
	{
		return findFilenames(folder, extension, emptyString);
	}
	
	public static String[] findFilenames(String folder, String extension, String prefix)
	{
		File fileFolder = new File(folder);
		File[] files = fileFolder.listFiles();
		ArrayList<String> filenameList = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) 
		{
			File file = files[i];
			String filename = prefix + file.getName();
			if (filename.endsWith(extension))
			{
				filenameList.add(filename);
			}
		}
		int filenameCount = filenameList.size();
		String[] filenames = filenameList.toArray(new String[filenameCount]);
		return filenames;
	}
	
	public static String[] findLines(String pathString)
	{
		String[] lines;
		Path path = Path.of(pathString);
		try 
		{
			List<String> linesList = Files.readAllLines(path);
			int lineCount = linesList.size();
			lines = linesList.toArray(new String[lineCount]);
		}
		catch (IOException ioException)
		{
			lines = null;
		}
		return lines;
	}
}
