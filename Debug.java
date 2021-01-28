package honorsThesis;

public class Debug {
	public static void printIntSequence(String string) {
		System.out.print(String.format("%s = ", string));
		for (int i = 0; i < string.length(); i++) System.out.print(String.format("%d ", (int) string.charAt(i)));
		System.out.println();
	}
}