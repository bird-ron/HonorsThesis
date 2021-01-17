package HonorsThesis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {
	public static String fileRead(Path path) {
		try {
			return Files.readString(path);
		} catch (IOException ioException) {
			return null;
		}	
	}
	
	public static boolean fileWrite(Path path, String data) {
		try {
			Files.writeString(path, data);
			return true;
		} catch (IOException ioException) {
			return false;
		}
	}
}