package honorsThesis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import static honorsThesis.Util.backslash;
import static honorsThesis.Util.emptyString;

public class FileIO 
{
	public static List<String> findFilePaths(String folder, String extension)
	{
		return findFilenames(folder, extension, folder + backslash);
	}
	
	public static List<String> findFilenames(String folder, String extension)
	{
		return findFilenames(folder, extension, emptyString);
	}
	
	public static List<String> findFilenames(String folder, String extension, String prefix)
	{
		File fileFolder = new File(folder);
		File[] files = fileFolder.listFiles();
		List<String> filenames = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) 
		{
			String filename = prefix + files[i].getName();
			if (filename.endsWith(extension))
			{
				filenames.add(filename);
			}
		}
		return unmodifiableList(filenames);
	}
	
	public static List<String> findLines(String pathString)
	{
		List<String> lines;
		Path path = Path.of(pathString);
		try 
		{
			lines = Files.readAllLines(path);
		}
		catch (IOException ioException)
		{
			lines = null;
		}
		return unmodifiableList(lines);
	}
}
