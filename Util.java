package honorsThesis;

import java.util.List;
import java.util.Objects;

public class Util 
{
	public static final String newline = "\n";
	public static final String leftParen = "(";
	public static final String rightParen = ")";
	public static final String emptyString = "";
	public static final String backslash = "\\";
	public static final String commaSpace = ", ";
	public static final String whitespaceRegex = "\\s+";
	
	
	public static <T> boolean arrayContains(T[] array, T searchItem)
	{
		boolean found = false;
		for (T arrayItem : array)
		{
			found = Objects.equals(arrayItem, searchItem);
			if (found)
			{
				break;
			}
		}
		return found;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] addArrays(T[] left, T[] right, Class<T> type)
	{
		int sumLength = left.length + right.length;
		T[] sum = (T[]) java.lang.reflect.Array.newInstance(type, sumLength);
		for (int i = 0; i < left.length; i++)
		{
			sum[i] = left[i];
		}
		for (int i = left.length; i < sum.length; i++)
		{
			sum[i] = right[i - left.length];
		}
		return sum;
	}
	
	public static <T> void addArrayToList(List<T> list, T[] array)
	{
		for (T item : array)
		{
			list.add(item);
		}
	}
	
	public static <T> String arrayToTupleString(T[] array)
	{
		String string = leftParen;
		for (T item : array)
		{
			string += item.toString() + commaSpace;
		}
		int truncateIndex = string.lastIndexOf(commaSpace);
		string = string.substring(0, truncateIndex) + rightParen;
		return string;
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
