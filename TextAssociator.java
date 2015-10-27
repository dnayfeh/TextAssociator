import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 Starter Code
 * @Author Kevin Quinn
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;

	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;

		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}

		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
		}

		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
		}

		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}

		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}

		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
	}

	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
		this.table = new WordInfoSeparateChain[10];
		this.size = 10;
	}

	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		int hash = Math.abs(word.hashCode() % this.size);
		if ((this.size + 1) >= 0.70 * this.table.length) {
			WordInfoSeparateChain[] newTable = new WordInfoSeparateChain[this.table.length * 2];
			for (int i = 0; i < this.size; i++) {
				newTable[i] = this.table[i];
			}
			this.table = newTable;
		}
		if (this.table[hash] == null) {
			this.table[hash] = new WordInfoSeparateChain();
		} else {
			for (WordInfo wiWord : this.table[hash].getElements()) {
				if (wiWord.getWord().equals(word)) {
					return false;
				}
			}
		}
		WordInfo newWord = new WordInfo(word);
		return this.table[hash].add(newWord);
	}

	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the SpellChecker or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		int hash = Math.abs(word.hashCode() % this.size);
		for (WordInfo wiWord : this.table[hash].getElements()) {
			if (word.equals(wiWord.getWord())) {
				return wiWord.addAssociation(association);
			}
		}
		return false;		
	}

	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		int hash = Math.abs(word.hashCode() % this.size);
		for (WordInfo wiWord : this.table[hash].getElements()) {
			if (wiWord.getWord().equals(word)) {
				return this.table[hash].remove(wiWord);
			}
		}
		return false;
	}

	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int hash = Math.abs(word.hashCode() % this.size);
		for (WordInfo wiWord : this.table[hash].getElements()) {
			if (word.equals(wiWord.getWord())) {
				return wiWord.getAssociations();
			}
		}
		return null;
	}

	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + this.size);
		System.out.println("Current table size: " + this.table.length);

		//Walk through every possible index in the table
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i] != null) {
				WordInfoSeparateChain bucket = this.table[i];

				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
}