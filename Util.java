package honorsThesis;

public class Util 
{
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
}
