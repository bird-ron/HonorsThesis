package honorsThesis;

import java.util.List;
import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class Util 
{
	public static final String space = " ";
	public static final String newline = "\n";
	public static final String leftParen = "(";
	public static final String rightParen = ")";
	public static final String emptyString = "";
	public static final String backslash = "\\";
	public static final String commaSpace = ", ";
	public static final String textItemDelimiter = " ";
	public static final String whitespaceRegex = "\\s+";	
	
	public static <T> boolean listContains(List<T> list, T item)
	{
		return list.indexOf(item) != -1;
	}
	
	public static String removeAllWhitespace(String string)
	{
		return string.replaceAll(whitespaceRegex, emptyString);
	}
	
	public static double truncateToTwoPlaces(double myDouble)
	{
		return floor(myDouble * 100) / 100;
	}
	
	public static boolean closeEnough(Double left, Double right)
	{
		return left != null && right != null && abs(left - right) <= 0.1;
	}
	
	public interface BoundingFunction
	{
		boolean call(int operand, int min, int max);
	}
	
	public static boolean isInclusivelyBounded(int operand, int min, int max) 
	{
		return min <= operand && operand <= max; 
	}
	
	public static boolean isExclusivelyBounded(int operand, int min, int max)
	{
		return min < operand && operand < max;
	}
	
	public static boolean isInclusivelyBounded(double operand, double min, double max) 
	{
		return min <= operand && operand <= max; 
	}
	
	public static boolean isExclusivelyBounded(double operand, double min, double max) 
	{
		return min < operand && operand < max; 
	}
}
