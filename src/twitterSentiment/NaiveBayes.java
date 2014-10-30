package twitterSentiment;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

public class NaiveBayes {
	public static String text = "";
	private static String[] words;
	
	
	public NaiveBayes(String newText) {
		TextPreprocessing word = new TextPreprocessing(newText);
		this.text = word.start();
		this.words = text.split("\\s+");
		
		}

	// This method gives the prior probability of a text document being positive
	// of negative
	private static double priorProbability(String positiveOrNegative)
			throws Exception {
		int positiveCount = 27781;
		int negativeCount = 28035;
		double total = positiveCount + negativeCount;

		if (positiveOrNegative.matches("positive")) {

			return Math.log(positiveCount / total);
		} else if (positiveOrNegative.matches("negative")) {
			return Math.log(negativeCount / total);
		}

		else
			return -1;
	}

	/*
	 * This determines the probability of a word being in one of the sentiment
	 * tables. It does this by dividing the frequency of the word occurring by
	 * the total number of words in a table and then returning the natural log
	 * of that number
	 */
	private static double probabilityOfSentence(String positiveOrNegative, String word)
			throws Exception {
	
		return Sql.countOfWordTotal(word, positiveOrNegative);
	}
	

	
	public static int start() throws Exception{
		
		double positiveProbability=priorProbability("positive");
		double negativeProbability=priorProbability("negative");
		

		positiveProbability+=probabilityOfSentence("positive", text);
		negativeProbability+=probabilityOfSentence("negative", text);
		
	
		
			
		
	
		if((Math.max(positiveProbability, negativeProbability)-Math.min(positiveProbability, negativeProbability)>(0.105*words.length))){
			if ((Math.max(positiveProbability, negativeProbability) == positiveProbability)) {
				return 0;
			}
				else if((Math.max(positiveProbability, negativeProbability) == negativeProbability)){
				return 1;
			}
			
			
			}
		else return 2;
		
		
						
		return -1;
	}


	
	
}
