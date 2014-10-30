package twitterSentiment;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

//This is the Training Data that is processed and stored in two databases, positive and negative
public class TrainingData {

	public static void main(String[] args) throws Exception {

		TwitterInitializer test = new TwitterInitializer();

		for (int j = 0; j < 50; j++) {
			Query query = new Query(":)");
			QueryResult result;
			do {
				result = test.twitter.search(query);
				List<Status> tweets = result.getTweets();

				for(Status tweet: tweets) {
					if (!tweet.getLang().contains("en")
							|| tweet.getText().contains("RT")
							|| tweet.getText().contains(":(")) {
						continue;
					}
					String tweetText = tweet.getText();
					System.out.println(tweetText);
					TextPreprocessing test3 = new TextPreprocessing(tweetText);
					tweetText=test3.start();
					String[] words = tweetText.split("\\s+");
					for (int i = 0; i < words.length; i++) {
						Sql.insertData("positive", words[i]);

					}

				}

			} while ((query = result.nextQuery()) != null); // System.exit(0);
			continue;
		}

	}
}
