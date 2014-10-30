package twitterSentiment;

public class TextPreprocessing {
	static String text = "";
	static String[] words;
	
	public TextPreprocessing(String word){
		this.text = word;
		this.words= text.split("\\s+");
	}
	
	public static void allLowerCase(){
		
		for(int i =0; i<words.length;i++){
			words[i]=words[i].toLowerCase();
			
			
		}
		
	}
	
	
	/**
	 * Removes stopwords based on norm.al's list of stopwords. This also removes
	 * usernames,websites and any words less than 3 letters long from a tweet
	 */
	public static void removeStopWords() {
		int numberOfBlankArray=0;

		String[] stopwords = { "a", "able", "about", "across", "after", "all",
				"almost", "also", "am", "among", "an", "and", "any", "are",
				"as", "at", "be", "because", "been", "but", "by", "can",
				"cannot", "could", "dear", "did", "do", "does", "either",
				"else", "ever", "every", "for", "from", "get", "got", "had",
				"has", "have", "he", "her", "hers", "him", "his", "how",
				"however", "i", "if", "in", "into", "is", "it", "its", "just",
				"least", "let", "likely", "may", "me", "might", "most",
				"must", "my", "neither", "no", "not", "nor", "of", "off", "often",
				"on", "only", "or", "other", "our", "own", "rather","rt","said",
				"say", "says", "she", "should", "since", "so", "some", "than",
				"that", "the", "their", "them", "then", "there", "these",
				"they", "this", "tis", "to", "too", "twas", "us", "wants",
				"was", "we", "were", "what", "when", "where", "which", "while",
				"who", "whom", "why", "will", "with", "would", "yet", "you",
		"your" };

		//Checks to see if the word in question is a stop word
		for (int i = 0; i < words.length; i++) {
			boolean isStopWord = false;

			for (int j = 0; j < stopwords.length; j++) {
				
				//This condition checks for the "not_" prefix to make sure there is no stop words escaping through the method
				if(words[i].startsWith("not_")){
					String newWord = words[i].substring(4);
					if(newWord.matches(stopwords[j])){
						isStopWord = true;
						break;
					}
				}

				if (words[i].matches(stopwords[j])) {

					isStopWord = true;
					break;
				}

			}
			//Makes sure "not_it" or similar words get removed
			if(words[i].length()<=2){
				words[i]="";
				numberOfBlankArray++;
			}
			
			else if (words[i].length()>3&&words[i].length()<6){
				if(words[i].charAt(3)=='_'){
					words[i]="";
					numberOfBlankArray++;
				}
				
				
			}

			//If it isn't a stop word, and doesn't start with @, or contain www or http,  or have more than 2 letter, then it is added to the string
			else if (!isStopWord && !words[i].startsWith("@") && !words[i].contains("www") && !words[i].contains("http") && words[i].length()>2) {
				words[i] = words[i];
			}
			else{
				words[i]="";
				numberOfBlankArray++;
				}

		}
		
		
		String[] newArray = new String[words.length-numberOfBlankArray];
		int j=0;
		
		
		for(int i=0; i<words.length;i++){
			if(words[i].matches("")){
				continue;
			}
			else{
				newArray[j]=words[i];
				j++;
			}
		}
		
		words = newArray;
		
		
		
		
		

		
	}

	/**
	 * Fixes words that have been cut too much by adding an "e" to certain words
	 * E.G. words ending in "at" become "ate"
	 * @return 
	 */
	public static String fixWords(String text) {
		if (text.endsWith("at") || text.endsWith("bl") || text.endsWith("iz")) {
			text = text.concat("e");
		}
		return text;
	}

	/**
	 * This method removes any words that start with an alphanumeric character,
	 * while also removing punctuation
	 */
	public static void noAlphaNumeric() {
		

		for (int i = 0; i < words.length; i++) {
			boolean hasAlphaNumeric;
			Character t = null;
			try {
				hasAlphaNumeric = t.isLetterOrDigit(words[i].charAt(0));
			} catch (StringIndexOutOfBoundsException e) {
				continue;
			}

			if (hasAlphaNumeric&&words[i].startsWith("not_")) {
				words[i] = "not_"+words[i].substring(3).replaceAll("[^a-zA-Z ]", "");
			} 
			else words[i] = words[i].replaceAll("[^a-zA-Z ]", "");
				
		}

		
	}

	/**
	 * Fixes words that leave a double consonant at the end of the word by
	 * removing the last letter
	 * @return 
	 */
	public static String fixWords(String text, boolean doubleConsonant) {
		if (doubleConsonant) {

			return text.substring(0, text.length() - 1);
		} 
		return text;
	}

	/** Checks to see if the word ends in a double consonant or not */
	public static boolean endsWithDoubleConsonant(String text) {
		if (text.charAt(text.length() - 1) == 'a'
				|| text.charAt(text.length() - 1) == 'e'
				|| text.charAt(text.length() - 1) == 'i'
				|| text.charAt(text.length() - 1) == 'o'
				|| text.charAt(text.length() - 1) == 'u') {
			return false;
		}
		if (text.charAt(text.length() - 2) == 'a'
				|| text.charAt(text.length() - 2) == 'e'
				|| text.charAt(text.length() - 2) == 'i'
				|| text.charAt(text.length() - 2) == 'o'
				|| text.charAt(text.length() - 2) == 'u') {
			return false;
		}

		if (text.charAt(text.length() - 1) == text.charAt(text.length() - 2)) {
			return true;
		}

		return false;
	}

	/** Checks to see if the letter is a vowel or not */
	public static boolean isVowel(char c) {
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
			return true;

		} else
			return false;
	}

	/**
	 * Checks to see the number of "measures", i.e. the number of
	 * vowel/consonant combinations combos there are in a word, ignoring the
	 * first and last letter
	 */
	public static int numberOfMeasures(String text) {
		int firstVowel = -1;
		int measures = 0;

		/* Checks for the first occurrence of a vowel */
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == 'a' || text.charAt(i) == 'e'
					|| text.charAt(i) == 'i' || text.charAt(i) == 'o'
					|| text.charAt(i) == 'u') {
				firstVowel = i;
				break;
			}

		}

		if (firstVowel > -1) {
			for (int i = firstVowel; i < text.length() - 2; i = i + 2) {
				if (isVowel(text.charAt(i))) {
					measures++;
				}
			}

		}

		return measures;
	}

	/** This method removes the first set of suffixes from a word */
	public static void removeSuffixes1() {
		
		for (int i = 0; i < words.length; i++) {

			if(numberOfMeasures(words[i])>0){
				switch(words[i].charAt(words[i].length()-2)){

				case('a'): 
					if (words[i].endsWith("ational")) {

						words[i] = words[i].substring(0, words[i].length() - 7)+"ate";
					}
				// Removes "tional" and changes it to "tion"
					else if (words[i].endsWith("tional")) {
						words[i] = words[i].substring(0, words[i].length() - 2);

					}
				break;
				//removes "enci", "anci"
				case('c'):
					if (words[i].endsWith("enci") || words[i].endsWith("anci")){
						words[i] = words[i].substring(0, words[i].length() - 1)+"e";
					}
				break;

				//removes "izer"	
				case('e'):
					if (words[i].endsWith("izer")) {
						words[i] = words[i].substring(0, words[i].length() - 1);

					}
				break;
				//removes "abli", "alli", "entli", "eli", "ousli"	
				case('l'):
					if (words[i].endsWith("abli")) {
						words[i] = words[i].substring(0, words[i].length() - 1)+"e";
					}
					else if (words[i].endsWith("alli")
							|| words[i].endsWith("entli")
							|| words[i].endsWith("eli")
							|| words[i].endsWith("ousli")) {
						words[i] = words[i].substring(0, words[i].length() - 2);
					}
				break;

				case('o'):
					if (words[i].endsWith("ization")
							|| words[i].endsWith("isation")) {
						words[i] = words[i].substring(0, words[i].length() - 5)+"e";
					}
					else if (words[i].endsWith("ation")) {
						words[i] = words[i].substring(0, words[i].length() - 3)+"e";
					}
					else if (words[i].endsWith("ator")) {
						words[i] = words[i].substring(0, words[i].length() - 2)+"e";

					}
				break;

				case('s'):
					if (words[i].endsWith("alism")) {
						words[i] = words[i].substring(0, words[i].length() - 3);

					}
					else if (words[i].endsWith("iveness")
							|| words[i].endsWith("fulness")) {
						words[i] = words[i].substring(0, words[i].length() - 4);
					}
				break;

				case('t'):
					if (words[i].endsWith("iviti")) {
						words[i] = words[i].substring(0, words[i].length() - 3)+"e";
					}
					else if (words[i].endsWith("aliti")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
					else if (words[i].endsWith("biliti")) {
						words[i] = words[i].substring(0, words[i].length() - 6)+"ble";
					}
				break;
				default: break;
				}	

			}

		}

		
	}

	/** This method removes the second set of suffixes from a word */
	public static void removeSuffixes2() {
		
		for (int i = 0; i < words.length; i++) {

			if(numberOfMeasures(words[i])>0){
				switch(words[i].charAt(words[i].length()-2)){

				//removes "al" from words ending in "ical"
				case('a'):
					if (words[i].endsWith("ical")) {
						words[i].substring(0, words[i].length() - 2);
					}
				break;


				case('s'):
					if ( words[i].endsWith("alise")) {
						words[i].substring(0, words[i].length() - 3);
					}else if (words[i].endsWith("ness")) {
						words[i].substring(0, words[i].length() - 4);
					}


				break;
				//removes the last three letters from icate and iciti
				case('t'):
					if (words[i].endsWith("icate")
							|| words[i].endsWith("iciti")) {
						words[i].substring(0, words[i].length() - 3);
					}
					else if (words[i].endsWith("ative")) {
						words[i].substring(0, words[i].length() - 5);
					}
				break;

				case('u'):
					if (words[i].endsWith("ful")) {
						words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('z'):
					if (words[i].endsWith("alize")) {
						words[i].substring(0, words[i].length() - 3);
					}
				break;

				default:break;
				}

			
			}
		}
		
	}

	/** This method implements step 4 of Porter's Stemming algorithm */
	public static void removeSuffixes3() {
		
		for (int i = 0; i < words.length; i++) {
			if (numberOfMeasures(words[i]) > 1) {
				switch(words[i].charAt(words[i].length()-2)){
				case('a'):
					if (words[i].endsWith("al")) {
						words[i] = words[i].substring(0, words[i].length() - 2);
					}
				break;

				case('c'):
					if (words[i].endsWith("ance")
							|| words[i].endsWith("ence")) {
						words[i] = words[i].substring(0, words[i].length() - 4);
					} 
				break;

				case('e'):
					if (words[i].endsWith("er")) {
						words[i] = words[i].substring(0, words[i].length() - 2);
					}
				break;

				case('i'):
					if (words[i].endsWith("ic") || words[i].endsWith("ou")) {
						words[i] = words[i].substring(0, words[i].length() - 2);
					}
				break;

				case('l'):
					if (words[i].endsWith("able")
							|| words[i].endsWith("ible")) {
						words[i] = words[i].substring(0, words[i].length() - 4);
					} 
				break;

				case('n'):
					if (words[i].endsWith("ment")) {
						words[i] = words[i].substring(0, words[i].length() - 4);
					} else if (words[i].endsWith("ement")) {
						words[i] = words[i].substring(0, words[i].length() - 5);
					}
					else if (words[i].endsWith("ant") || words[i].endsWith("ent")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('o'):
					if (words[i].endsWith("ou")) {
						words[i] = words[i].substring(0, words[i].length() - 2);
					}
					else if (((words[i].charAt((words[i].length() - 4)) == 's' || (words[i]
							.charAt(words[i].length() - 4) == 't')) && words[i]
									.endsWith("ion"))) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('s'):
					if (words[i].endsWith("ism") || words[i].endsWith("ise")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('t'):
					if (words[i].endsWith("ate")
							|| words[i].endsWith("iti")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('u'):
					if (words[i].endsWith("ous")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('v'):
					if (words[i].endsWith("ive")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				case('z'):
					if (words[i].endsWith("ize")) {
						words[i] = words[i].substring(0, words[i].length() - 3);
					}
				break;

				default:break;
				}

			}

			
		}

		

	}

	/**
	 * This method implements the final step of Porter's Stemming algorithm,
	 * which fixes up some words that have been edited too much.
	 */
	public static void finalFixUp() {
		

		for (int i = 0; i < words.length; i++) {
			if ((numberOfMeasures(words[i]) > 1)) {
				if (words[i].endsWith("e") || words[i].endsWith("ll")) {
					words[i] = words[i].substring(0, words[i].length() - 1);
				}
			}

			
		}

	}

	/** This method removes plurals and past participle stems from words */
	public static void removePluralsAndPastParticiples() {
				

		// This checks each word to see if it can be shortened.
		for (int i = 0; i < words.length; i++) {
			// Makes sure it does not remove an entire word
			// Changes all "sses" to "ss", e.g. "caresses" -> "caress"
			if (words[i].endsWith("sses")) {
				
				words[i] = words[i].substring(0,words[i].length()-2);
				

			}

			// Changes all "ies" to "i" , so "ties" becomes "ti"
			else if (words[i].endsWith("ies")) {
				words[i] = words[i].substring(0,words[i].length()-2);
				
				

			}

			// Stops the method from affecting words ending with ss
			else if (words[i].endsWith("ss")) {

				continue;
			}

			// Removes "s" from the end of words
			else if (words[i].endsWith("s")) {
				words[i] = words[i].substring(0, words[i].length() - 1);
				

			}
			// Replaces "eed" with "ee", e.g. "agreed" to "agree"
			else if (words[i].endsWith("eed") && words[i].length() > 4) {
				words[i] = words[i].substring(0, words[i].length() - 1);
				
			}
			// Removes "ed" from the end of words
			else if (words[i].endsWith("ed") && words[i].length() > 4) {
				words[i] = words[i].substring(0, words[i].length() - 2);
				words[i] = fixWords(words[i]);
				if (!words[i].endsWith("l") || words[i].endsWith("s")
						|| words[i].endsWith("z")) {
					words[i] = fixWords(words[i],
							endsWithDoubleConsonant(words[i]));
				}

				

			}

			else if (words[i].endsWith("ing") && words[i].length() > 4) {
				words[i] = words[i].substring(0, words[i].length() - 3);
				words[i] = fixWords(words[i]);
				if (!words[i].endsWith("l") || words[i].endsWith("s")
						|| words[i].endsWith("z")) {
					words[i] = fixWords(words[i],
							endsWithDoubleConsonant(words[i]));
				}

				

			}

			else if (words[i].endsWith("y")) {
				words[i] = words[i].substring(0, words[i].length() - 1)+"i";
			}

			
			
		}
		
		
	}

	/**
	 * This method removes the occurrences of 3 or more of the same consecutive
	 * letter in a word, and replaces it with just 3 occurrences DOES NOT WORK
	 * YET
	 */
	public static String removeSameOccurences(String text) {
		String[] words = text.split("\\s+");
		text = "";

		for (int i = 0; i < words.length; i++) {

			char letterCheck;

			int count = 0;// counts the number of consecutive occurrences of a
			// letter in a word
			String newWord = "";

			// Loop to check for the number of occurrences of a letter
			for (int j = 0; j < words[i].length() - 1; j++) {
				letterCheck = words[i].charAt(j);
				if (letterCheck == words[i].charAt(j + 1)) {
					count++;
					if (count > 1) {
						continue;
					}
				} else {
					count = 0;
				}

				newWord += letterCheck;

			}
			newWord += words[i].charAt(words[i].length() - 1);

			text += newWord + " ";

		}

		return text;
	}

	/**
	 * This method deals with negative switching words such as "not", "cannot"
	 * or any word with "n't" in it
	 */
	public static void negationHandler() {
		
		String negationWord = "not_";
		int number = 0;
		boolean isNegative = false;

		for (int i = 0; i < words.length; i++) {
			if (number % 2 == 1) {
				isNegative = true;
				negationWord = "not_";
			}
			if (number % 2 == 0) {
				isNegative = false;
				negationWord = "";
			}

			if (words[i].contains("n't")
					|| words[i].matches("cannot")
					|| words[i].matches("not")
					) {
				number++;

			}

			if(i< words.length-1 && ((words[i].endsWith(",") && (words[i + 1]
					.matches("although")))
					|| ((words[i].endsWith(",") && (words[i + 1].matches("but")))))){

				number++;
			}

			if (isNegative) {
				if (words[i].endsWith(".") || words[i].contains("?")
						|| words[i].endsWith("!")) {
					number = 0;

				}

			}

			words[i] = negationWord + words[i];
		}

		
	}


	/**This method fixes up words so that they won't cause an error in the SQL insertion*/
	public static void sqlSyntaxHandler(){
		

		for(int i =0; i<words.length; i++){
			if(words[i].contains("'")||words[i].contains("\"")){
				String newWord = "";
				for(int j =0; j<words[i].length();j++){ //Loops through each character of the word and ignores any 's and "s
					if(words[i].charAt(j)=='\'' || words[i].charAt(j)=='\"'){
						continue;
					}
					else{
						newWord+=words[i].charAt(j);
					}

				}
				words[i]=newWord;
			}
			

		}



	}


	// Initiates all text preprocessing methods to the String
	public static String start() {
		String result="";
		allLowerCase();   /* Puts all words to lowercase*/
		negationHandler();/*  Adds not_ to the words as specified in
		 *  the negation handling method*/
		removeStopWords();/* Removes all the stop words*/
		noAlphaNumeric(); /* Removes all words not beginning with a
		 * letter*/
		removePluralsAndPastParticiples();	// Removes all plural words
											// and past particples
											// of words. Step 1 of
											// Porter's Stemming
											// Algorithm
		removeSuffixes1();// Step 2 of Porter's Stemming Algorithm
		removeSuffixes2();// Step 3
		removeSuffixes3();// Step 4
		finalFixUp();// Step 5
		sqlSyntaxHandler();

		for(int i =0; i<words.length;i++){
			result+=words[i]+" ";				
		}

		return result;
	}

	public static void main(String[] args) {
		String test = "@CharlieBearsLtd Did he sell out? Can't spot him on the website so think I'm too''s''s'ss??$$$%%% late :(";
		TextPreprocessing test2 = new TextPreprocessing(test);
		test = test2.start();
		System.out.println(test);
		
		
					
	}




}
