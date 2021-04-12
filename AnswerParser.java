package honorsThesis;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static honorsThesis.TextParser.descriptionFirst;
import static honorsThesis.TextParser.feedbackFirst;
import static honorsThesis.Token.nullToken;
import static honorsThesis.Util.listContains;

public class AnswerParser 
{	
	public static final List<String> answerFirst = generateAnswerFirst();
	public static final List<String> answerFollows = generateAnswerFollows();
	
	public static final List<String> operatorTierZero = generateOperatorTierZero();
	public static final List<String> operatorTierOne = generateOperatorTierOne();
	public static final List<String> operators = generateOperators();
	
	private final List<String> lexemes;

	private static List<String> generateAnswerFirst()
	{
		String[] first = {"<answer>", "<ans>", "<a>"};
		return unmodifiableList(asList(first));
	}
	
	private static List<String> generateAnswerFollows()
	{
		List<String> follows = new ArrayList<String>();
		follows.addAll(feedbackFirst);
		follows.addAll(descriptionFirst);
		follows.add(nullToken.lexeme);
		return unmodifiableList(follows);
	}
	
	private static List<String> generateOperatorTierZero()
	{
		String[] operatorTierZero = {"*", "/"};
		return unmodifiableList(asList(operatorTierZero));
	}
	
	private static List<String> generateOperatorTierOne()
	{
		String[] operatorTierOne = {"+", "-"};
		return unmodifiableList(asList(operatorTierOne));
	}
	
	private static List<String> generateOperators()
	{
		List<String> operators = new ArrayList<String>();
		operators.addAll(operatorTierZero);
		operators.addAll(operatorTierOne);
		return unmodifiableList(operators);
	}
	
	public static double parseAnswer(TokenStream tokenStream, HashMap<String, Double> variables) throws UnexpectedTokenException
	{
		AnswerParser answerParser = new AnswerParser(tokenStream, variables);
		return answerParser.parse();
	}
	
	private AnswerParser(TokenStream tokenStream, HashMap<String, Double> variables) throws UnexpectedTokenException
	{
		this.lexemes = deriveLexemes(tokenStream, variables);
	}
	
	private List<String> deriveLexemes(TokenStream tokenStream, HashMap<String, Double> variables) throws UnexpectedTokenException
	{
		tokenStream.expect(answerFirst);
		List<String> lexemes = new ArrayList<String>();
		Double firstOperand = tokenStream.expectOperand(variables);
		lexemes.add(firstOperand.toString());
		while (!listContains(answerFollows, tokenStream.peek().lexeme))
		{
			String operator = tokenStream.expect(operators).lexeme;
			Double operand = tokenStream.expectOperand(variables);
			lexemes.add(operator);
			lexemes.add(operand.toString());
		}
		return lexemes;
	}
	
	private Double parse()
	{
		performOperations(operatorTierZero);
		performOperations(operatorTierOne);
		return Double.parseDouble(lexemes.get(0));
	}
	
	private void performOperations(List<String> symbolTier)
	{
		int index = findFirstOperatorIndex(symbolTier);
		while (index != - 1)
		{		
			Double result = computeResult(index);
			insertResult(result, index);
			index = findFirstOperatorIndex(symbolTier);
		}
	}
	
	private void insertResult(Double result, int index)
	{
		lexemes.remove(index + 1);
		lexemes.remove(index);
		lexemes.add(index, result.toString());
		lexemes.remove(index - 1);
	}
	
	private double computeResult(int index)
	{
		double left = Double.parseDouble(lexemes.get(index - 1));
		String operator = lexemes.get(index);
		double right = Double.parseDouble(lexemes.get(index + 1));
		Double result = 0.0;
		switch (operator)
		{
		case "*":
			result = left * right;
			break;
		case "/":
			result = left / right;
			break;
		case "+":
			result = left + right;
			break;
		case "-":
			result = left - right;
			break;
		}
		return result;
	}
	
	private int findFirstOperatorIndex(List<String> operatorTier)
	{	
		int operatorIndex = -1;
		for (String operator : operatorTier)
		{
			int symbolIndex = lexemes.indexOf(operator);
			if (symbolIndex != -1 && (operatorIndex == -1 || symbolIndex < operatorIndex))
			{
				operatorIndex = symbolIndex;
			}
		}
		return operatorIndex;
	}
}
