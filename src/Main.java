import java.util.ArrayList;
import java.util.Scanner;
public class Main 
{
	static AutocompleteProvider acp;
	public static void main(String args[])
	{
		acp = new AutocompleteProvider();
		
		Scanner sc = new Scanner(System.in);
		String input = "";
		
		System.out.println("\nWelcome to the wonderful and amazing word autocompleter!\n");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("-T [comma delimited list of strings] to train.");
		System.out.println("! to quit.");
		System.out.println("!! to quit by dividing by zero.");
		System.out.println("Other entries are word fragments which I will attempt to complete.");
		System.out.println("-------------------------------------------------------------------\n");
		while((input = sc.nextLine()).compareTo("!") != 0)
		{
			// Trim whitespace
			input = input.trim();
			if(input.length() == 0)
			{
				// If they entered nothing of value, don't do anything
				continue;
			}
			else if(input.compareTo("!!") == 0)
			{
				// You asked for it...
				int largeNumber = 5/0;
			}
			else if(input.length() >= 2 && input.substring(0, 2).compareTo("-T") == 0)
			{
				// If they entered -T then get the training words.
				acp.train(input.substring(2, input.length()));
			}
			else
			{
				// Solve for the completion of the entered fragment
				inputAndAnswer(input);
			}
		}
	}
	
	/** @name 	inputAndAnswer
	 * 	@brief	This function will print the candidate list for a given
	 * 			word fragment. That is, it prints the possible word 
	 * 			autocompletions for the fragment, as well as the confidence
	 * 			of each.
	 *
	 *  @param fragment The substring which we would like to complete
	 *  @return void
	 */
	static void inputAndAnswer(String fragment)
	{
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates = acp.getWords(fragment);
		
		String answers = fragment+" --> ";
		for(int x = 0; x < candidates.size(); x++)
		{
			answers+=candidates.get(x);
			if(x != candidates.size() - 1)
			{
				answers+=",";
			}
			
			answers+=" ";
		}
		System.out.println(answers);
	}
}
