package honorsThesis;

import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Quiz 
{
	public final boolean feedbackIsImmediate;
	public final List<Question> questions;
	
	public Quiz(boolean feedbackIsImmediate, List<Question> questions)
	{
		this.feedbackIsImmediate = feedbackIsImmediate;
		this.questions = unmodifiableList(questions);
	}
}
