package honorsThesis;

import java.util.List;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class ModeParser 
{
	public static final List<String> modeFirst = generateModeFirst();
	public static final List<String> modes = generateModes();
	private final TokenStream tokenStream;
	
	private static List<String> generateModeFirst()
	{
		String[] modeFirst = {"<mode>", "<m>"};
		return unmodifiableList(asList(modeFirst));
	}
	
	private static List<String> generateModes()
	{
		String[] modes = {"immediate", "delayed"};
		return unmodifiableList(asList(modes));
	}
	
	public static boolean parseMode(TokenStream tokenStream) throws UnexpectedTokenException
	{
		ModeParser modeParser = new ModeParser(tokenStream);
		return modeParser.parse();
	}
	
	private ModeParser(TokenStream tokenStream)
	{
		this.tokenStream = tokenStream;
	}
	
	private boolean parse() throws UnexpectedTokenException
	{
		tokenStream.expect(modeFirst);
		Token feedback = tokenStream.expect(modes);
		String immediate = modes.get(0);
		return feedback.lexeme.equals(immediate);
	}
}
