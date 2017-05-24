/** @file SubstringNode.java
 *  @brief Class for the Substring Nodes which will populate the trie
 *
 *  This class is the blueprint for Substring Nodes which 
 *	will populate the trie
 *
 *  @author Merle Ferguson
 */
import java.util.HashMap;
import java.util.ArrayList;
public class SubstringNode 
{
	private String substring;	// The substring data which is stored in this node
	private int occurrences;	// The number of times this node has been hit
								// during the trie-training. Will only increment
								// if this is the last node hit by an input word
	private HashMap<Character, SubstringNode> nextLetter; // Storage for child nodes
								
	
	/** @name 	SubstringNode
	 * 	@brief	Constructor for SubstringNode
	 *
	 *  @param substring The substring which will be stored in 
	 *  				 this node of the trie
	 *  @return SubstringNode.
	 */
	public SubstringNode(String substring)
	{
		this.substring = substring;
		occurrences = 0;
		nextLetter = new HashMap<Character, SubstringNode>();
	}
	
	/** @name 	addChild
	 * 	@brief 	Adds child node to nextLetter map
	 *
	 *  @param letter The letter which the child node is concatenating to the
	 *  			  current node.
	 *  @return void
	 */
	public void addChild(Character letter)
	{
		SubstringNode child = new SubstringNode(substring + letter.charValue());
		nextLetter.put(letter, child);
	}
	
	/** @name	getChild 
	 * 	@brief 	Retrieves a child node with the given "next character"
	 *
	 *  @param c The key used to find the child in the nextLetter map
	 *  @return SubstringNode
	 */
	public SubstringNode getChild(Character c)
	{
		return nextLetter.get(c);
	}
	
	/** @name 	getChildren
	 * 	@brief 	Returns an ArrayList of all child nodes. This would include
	 *  		anything which has been added to the trie as a "next letter" 
	 *  		for this node's substring.
	 *
	 *  @return ArrayList<SubstringNode>
	 */
	public ArrayList<SubstringNode> getChildren()
	{
		return new ArrayList<SubstringNode>(nextLetter.values());
	}
	
	/** @name 	getOccurrences
	 *  @brief 	Returns the number of times this node has occurred in the trie's
	 *  		training. It is only considered an "occurrence" if this was the
	 *  		last node for a given training input.
	 *
	 *  @return int
	 */
	public int getOccurrences()
	{
		return occurrences;
	}
	
	/** @name 	addOccurrence
	 * 	@brief 	Increments the number of occurrences of this node by one.
	 *
	 *  @return void
	 */
	public void addOccurrence()
	{
		occurrences++;
	}
	
	/** @name 	toString
	 * @brief 	Returns the string version of this object. In this implementation
	 *  		it is just the substring that this node represents.
	 *
	 *  @return String
	 */
	public String toString()
	{
		return substring;
	}
}
