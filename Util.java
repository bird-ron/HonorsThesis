package honorsThesis;

import java.util.List;

public class Util 
{
	public static final String whitespaceRegex = "\\s+";
	public static final String emptyString = "";
	public static final String backslash = "\\";
	public static final String newline = "\n";
	
	public static <T> void addArrayToList(List<T> list, T[] array)
	{
		for (T item : array)
		{
			list.add(item);
		}
	}
	
	public static <T> boolean arrayContains(T[] array, T searchItem)
	{
		boolean found = false;
		for (T arrayItem : array)
		{
			found = arrayItem.equals(searchItem);
			if (found)
			{
				break;
			}
		}
		return found;
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
