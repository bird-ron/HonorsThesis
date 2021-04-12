package honorsThesis;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import static honorsThesis.FileIO.findLines;
import static honorsThesis.Util.whitespaceRegex;
import static honorsThesis.Util.listContains;
import static honorsThesis.Token.nullToken;

public class TokenStream 
{
	private static final String expectedDouble = "number";
	private final List<Token> tokens;
	private int tokenIndex;
	
	public TokenStream(String path)
	{
		this.tokens = tokensFromPath(path);
		this.tokenIndex = 0;
	}
	
	@Override
	public String toString()
	{
		return tokens.toString();
	}
	
	private static List<Token> tokensFromPath(String path)
	{
		List<Token> tokens = new ArrayList<Token>();
		List<String> lines = findLines(path);
		int lineCount = lines.size();
		for (int i = 0; i < lineCount; i++)
		{
			String line = lines.get(i);
			if (line.length() > 0) 
			{
				List<Token> lineTokens = tokensFromLine(lines.get(i), i + 1);
				tokens.addAll(lineTokens);
			}
		}
		tokens.add(nullToken);
		return unmodifiableList(tokens);
	}
	
	private static List<Token> tokensFromLine(String line, int lineNumber) 
	{
		List<Token> tokens = new ArrayList<Token>();
		String[] lexemes = line.split(whitespaceRegex);
		for (int i = 0; i < lexemes.length; i++)
		{
			Token token = new Token(lexemes[i], i + 1, lineNumber);
			tokens.add(token);
		}
		return unmodifiableList(tokens);
	}
	
	public Token getToken() 
	{
		Token token = tokens.get(tokenIndex);
		if (tokenIndex < tokens.size())
		{
			tokenIndex++;
		}
		return token;
	}
	
	public void ungetToken()
	{
		if (tokenIndex > 0)
		{
			tokenIndex--;
		}
	}
	
	public Token peek()
	{
		Token token = getToken();
		ungetToken();
		return token;
	}
	
	public Token expect(List<String> expected) throws UnexpectedTokenException
	{
		Token token = getToken();
		if (!listContains(expected, token.lexeme))
		{
			throw new UnexpectedTokenException(expected, token);
		}
		return token;
	}
	
	public Token expect(String expected) throws UnexpectedTokenException
	{
		Token token = getToken();
		if (!expected.equals(token.lexeme))
		{
			throw new UnexpectedTokenException(expected, token);
		}
		return token;
	}
	
	public Double expectDouble() throws UnexpectedTokenException
	{
		Token token = getToken();
		Double myDouble;
		try
		{
			myDouble = Double.parseDouble(token.lexeme);
		}
		catch (NumberFormatException numberFormatException)
		{
			myDouble = null;
			throw new UnexpectedTokenException(expectedDouble, token);
		}	
		return myDouble;
	}
	
	public Double expectOperand(HashMap<String, Double> variables) throws UnexpectedTokenException
	{
		Token token = getToken();
		Double myDouble;
		if (variables != null && variables.containsKey(token.lexeme))
		{
			myDouble = variables.get(token.lexeme);
		}
		else
		{
			ungetToken();
			myDouble = expectDouble();
		}
		return myDouble;
	}
	
	public Token forbid(List<String> expected, List<String> forbidden) throws UnexpectedTokenException
	{
		Token token = getToken();
		if (listContains(forbidden, token.lexeme))
		{
			throw new UnexpectedTokenException(expected, token);
		}
		return token;
	}
	
	public Token forbid(String expected, List<String> forbidden) throws UnexpectedTokenException
	{
		Token token = getToken();
		if (listContains(forbidden, token.lexeme)) 
		{
			throw new UnexpectedTokenException(expected, token);
		}
		return token;
	}
}
