/** @file AutocompleteProvider.java
 *  @brief Class for storing, training, and using the keyboard autocompleter
 *
 *  This class stores the data structure, training functions, and retrieval functions
 *  for keyboard autocompletion.
 *
 *  @author Merle Ferguson
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutocompleteProvider {
	
	private HashMap<Character, SubstringNode> trie;		// Data structure which we will store training input in
	
	/** @name AutocompleteProvider
	 *	@brief Constructor for AutocompleteProvider
	 *  
	 *  @return AutocompleteProvider.
	 */
	public AutocompleteProvider ()
	{
		trie = new HashMap<Character, SubstringNode>(); 
	}  
	 
	/** @name 	getWords
	 * 	@brief 	Given a word fragment, this will return a list of
	 *  		candidates for completing that word.
	 *
	 *  @param fragment The word to complete.
	 *  @return ArrayList<Candidate>.
	 */
	ArrayList<Candidate> getWords(String fragment)
	{
		// If the fragment is a blank string, just return an empty candidate list
		if(fragment.length() == 0)
		{
			return new ArrayList<Candidate>();
		}
		
		// Convert to lower case so we don't need to worry about casing
		fragment = fragment.toLowerCase();
		
		boolean notInTrie = false;
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		
		// Get the trie node for the first character in the fragment
		Character c;
		SubstringNode n = null;
		
		// Loop through the character one fragment at a time and see if this
		// walks through a path in the trie
		for(int x = 0; x < fragment.length(); x++)
		{
			c = new Character(fragment.charAt(x));
			
			if(x == 0)	// If it is the first character, get the node from the trie data structure.
			{
				n = trie.get(c);
				if(n == null)
				{
					notInTrie = true;
					break;
				}
			}
			else if(n.getChild(c) == null)	// If this is not the first character, check if the current node has a child.
			{								// If it does not, then the fragment does not exist in the trie
				notInTrie = true;
				break;
			}
			else
			{
				n = n.getChild(c);			// If the current node has the next character as a child, get it and make that the current node
			}
		}
		
		// If the fragment exists in the trie then get the previously entered words which are down the tree from
		// the node which contains the fragment.
		if(!notInTrie)
		{
			ArrayList<SubstringNode> candidateNodes = new ArrayList<SubstringNode>();
			getChildrenWithOccurrences(n, candidateNodes);
			
			// Concatenate the list of possible answers
			for(SubstringNode canNode : candidateNodes)
			{
				candidates.add(new Candidate(canNode+"", canNode.getOccurrences()));
			}
		}
		else
		{
			// If the fragment was not found, do nothing. Nice trie though! :D
			// candidates.add(new Candidate(fragment+"-supercalifragilisticexpialidocious", 100)); 
		}
		
		// Order the candidates by the number of times they each occurred in training.
		Collections.sort(candidates);
		
		// Organize the results from highest to lowest number of occurrences.
		Collections.reverse(candidates);
		return candidates;
	}
	
	/** @name 	train
	 * 	@brief 	Updates the trie with new training data so it
	 *  		can more accurately predict word completion.
	 *  
	 *  @param passage The words which will be used to update the trie
	 *  @return void
	 */
	void train(String passage)
	{
		// Convert to lower case so we don't need to worry about casing.
		passage = passage.toLowerCase();
		
		// Retrieve the plain words from the passage
		ArrayList<String> words = parsePassage(passage);
		
		// For each word in the passage, train the trie with it
		for(int x = 0; x < words.size(); x++)
		{
			putWordInTrie(words.get(x));
		}
	}
	
	/**	@name 	parsePassage 
	 * 	@brief 	Helper function to extract the plain words from a string that
	 *  		my have whitespace or other characters.
	 *  
	 *  @param passage The string containing words and possibly
	 *  			   whitespace or other characters.
	 *  @return ArrayList<String>
	 */
	ArrayList<String> parsePassage(String passage)
	{
		ArrayList<String> matches = new ArrayList<String>();
		Matcher matcher = Pattern.compile("[a-z]+").matcher(passage);
		while (matcher.find()) 
		{
			matches.add(matcher.group());
		}
		return matches;
	}
	
	/** @name 	putWordInTrie
	 * @brief 	Provided a word, this function will update the trie
	 *  		with the occurrence of this word. This function is
	 *  		used during the training of the keyboard autocompleter.
	 *
	 *  @param word The word that is being trained with
	 *  @return void
	 */
	void putWordInTrie(String word)
	{
		Character c;
		SubstringNode n = null;
		
		// Navigate through the trie with each letter. Insert
		// new substring nodes as necessary so the entire word 
		// is represented in the trie
		for(int x = 0; x < word.length(); x++)
		{
			// All hail the Java garbage collector!
			c = new Character(word.charAt(x));
			
			if(x == 0) 	// If it is the first character, get the node from the trie data structure.
			{
				n = trie.get(c);
				if(n == null)
				{
					n = new SubstringNode(c+"");
					trie.put(c, n);
				}
			}
			else if(n.getChild(c) == null)	// If this is not the first character, check if the current node has a child.
			{								// If it does not, then the fragment does not exist in the trie. Add it!
				n.addChild(c);
				n = n.getChild(c);
			}
			else						
			{
				n = n.getChild(c);			// If the current node has the next character as a child, get it and make that the current node
			}
		}
		
		// We have reached the substring node which represents this word. Increment the number of times
		// this node has occurred as a full word in training.
		n.addOccurrence();
	}
	
	/** @name 	getChildrenWithOccurrences
	 * 	@brief 	Recursive function for searching the tree starting at a given node.
	 *  		This will return all of the substring nodes which have an occurrence value
	 *  		of greater than 0.
	 *
	 *  @param node The node at which the search is beginning
	 *  @param returnList The list of nodes which are reachable starting from i_Node in the trie.
	 *  				  And which also have an occurrence value of greater than 0.
	 *  				  This represents all of the possible completions of a given fragment.
	 *  @return void
	 */
	void getChildrenWithOccurrences(SubstringNode node, ArrayList<SubstringNode> returnList)
	{
		// If this node has appeared as a full word in training, add it to the list.
		if(node.getOccurrences() > 0)
		{
			returnList.add(node);
		}
		
		// Loop through each child of this node and recursively call this function.
		// This allows us to search the entire tree starting at the given node.
		for(SubstringNode child : node.getChildren())
		{
			getChildrenWithOccurrences(child, returnList);
		}
	}
	
}
