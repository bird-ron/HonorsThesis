package honorsThesis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileIO 
{
	public static ArrayList<String> findFilePaths(String folder, String extension)
	{
		return findFileNames(folder, extension, folder + "\\");
	}
	
	public static ArrayList<String> findFileNames(String folder, String extension)
	{
		return findFileNames(folder, extension, "");
	}
	
	public static ArrayList<String> findFileNames(String folder, String extension, String prefix)
	{
		File fileFolder = new File(folder);
		File[] files = fileFolder.listFiles();
		ArrayList<String> filenames = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) 
		{
			File file = files[i];
			String filename = prefix + file.getName();
			if (filename.endsWith(extension))
			{
				filenames.add(filename);
			}
		}
		return filenames;
	}
	
	public static String findFileContents(String pathString)
	{
		String contents;
		try 
		{
			Path path = Path.of(pathString);
			contents = Files.readString(path);
		}
		catch (IOException ioException)
		{
			contents = null;
		}
		return contents;
	}
}
