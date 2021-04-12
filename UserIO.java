package honorsThesis;

import java.util.Scanner;
import honorsThesis.Util.BoundingFunction;

public class UserIO 
{
	private static Scanner userInput = new Scanner(System.in);
	
	public static void print(Object object)
	{
		System.out.println(object);
	}
	
	public static void print()
	{
		System.out.println();
	}
	
	public static Integer promptUserForIntegerIncl(int min, int max, String message) 
	{
		print(message);
		return promptUserForIntegerIncl(min, max);
	}
	
	private static Integer promptUserForIntegerIncl(int min, int max) 
	{
		return promptUserForInteger(min, max, Util::isInclusivelyBounded);
	}
	
	public static Integer promptUserForIntegerExcl(int min, int max, String message) 
	{
		print(message);
		return promptUserForIntegerExcl(min, max);
	}
	
	private static Integer promptUserForIntegerExcl(int min, int max) 
	{
		return promptUserForInteger(min, max, Util::isExclusivelyBounded);
	}
	
	private static Integer promptUserForInteger(int min, int max, BoundingFunction boundingFunction)
	{
		Integer integer = null;
		try 
		{
			integer = Integer.parseInt(userInput.nextLine());
			if (!boundingFunction.call(integer, min, max))
			{
				integer = null;
			}
		} 
		catch (NumberFormatException e) 
		{
			integer = null;
		}
		return integer;
	}
	
	public static Double promptUserForDouble(String message)
	{
		print(message);
		Double myDouble = null;
		try 
		{
			myDouble = Double.parseDouble(userInput.nextLine());
		} 
		catch (NumberFormatException e) 
		{
			myDouble = null;
		}
		return myDouble;
	}
}
