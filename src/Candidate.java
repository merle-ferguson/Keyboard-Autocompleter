/** @file Candidate.java
 *  @brief Blueprint of a Candidate
 *
 *  This class is a blueprint of a Candidate, which is a possible 
 *  completion of an offered word fragment. This stores not only the
 *  word which completes the fragment, but a confidence integer signifying
 *  the certainty that this is the correct candidate, relative to the other
 *  candidates.
 *
 *  @author Merle Ferguson
 */

public class Candidate implements Comparable<Candidate>{

	private String word;		// The candidate word
	private int confidence;		// The confidence that the candidate word is correct
	
	/** @name Candidate
	 *	@brief Constructor for creating a Candidate for fragment completion
	 *
	 *  @param word 		The candidate word for completing the fragment
	 *  @param confidence 	The integer representing the confidence
	 *  				  	that this is the correct completion of the fragment.
	 *  @return Candidate
	 */
	public Candidate(String word, int confidence)
	{
		this.word = word;
		this.confidence = confidence;
	}
	
	/** @name getWord
	 *	@brief Returns the word that this object stores
	 *
	 *  @return String
	 */
	public String getWord()
	{
		return word;
	}
	
	/** @name 	getConfidence
	 *	@brief 	Returns the confidence that the candidate word
	 *			is correct.
	 *
	 *  @return int
	 */
	public int getConfidence()
	{
		return confidence;
	}
	
	/** @name 	getConfidence
	 *	@brief 	Returns a string form of this object, which
	 *			includes the candidate word and it's confidence
	 *			number
	 *
	 *  @return String
	 */
	public String toString()
	{
		return "\""+word+"\" ("+confidence+")";
	}
	
	/** @name 	compareTo
	 *	@brief 	Used to determine which candidate comes first in an ordering.
	 *			A candidate is considered greater if it has a higher confidence.
	 *			This function returns a positive number if the one stored in this
	 *			object is greater, a negative number if it is less, and zero if
	 *			they are equal (have the same confidence)
	 *
	 *	@param c The candidate to which this one is being compared
	 *
	 *  @return int
	 */
	public int compareTo(Candidate c)
	{
		return confidence - c.getConfidence();
	}
}
