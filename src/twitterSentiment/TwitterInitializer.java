package twitterSentiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class TwitterInitializer extends HttpServlet{

	private final static String API_KEY = "QPVcvo4C7qOULfitnQwLL7kR5";
	private final static String API_SECRET = "dOn2dQlVt0VW00YbHj6MOvIEky5v6MRPgRYQN8SOT6EElYfwN3";
	Twitter twitter = new TwitterFactory().getInstance();
	private String accessToken = "22692552-qRI95OTYBvD4dBKkRZ8cqqPo4PbDBy9sktUToVIiR";
	private String accessTokenSecret = "svtsRfTf0MtLxrAgEkA9XZeaEQMILqB8j79J5qJxVOMki";
	private String text = "";

	private String getSavedAccessTokenSecret() {

		// consider this is method to get your previously saved Access Token

		// Secret

		return "svtsRfTf0MtLxrAgEkA9XZeaEQMILqB8j79J5qJxVOMki";

	}

	private String getSavedAccessToken() {

		// consider this is method to get your previously saved Access Token

		return "22692552-qRI95OTYBvD4dBKkRZ8cqqPo4PbDBy9sktUToVIiR";

	}
	
	public TwitterInitializer(){
		
				twitter.setOAuthConsumer(API_KEY, API_SECRET);

				AccessToken oathAccessToken = new AccessToken(accessToken,

				accessTokenSecret);

				twitter.setOAuthAccessToken(oathAccessToken);

				}


	public void doGet(HttpServletRequest request,
 HttpServletResponse response)
			throws ServletException, IOException{ 
		
		
		new TwitterInitializer();
		
		PrintWriter out = response.getWriter();
		
					   if (text.length() < 1) {
				            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
				            System.exit(-1);
				        }
				       
				        try {
				            Query query = new Query(text);
				            QueryResult result;
				            do {
				                result = twitter.search(query);
				                List<Status> tweets = result.getTweets();
				                          
				                for (Status tweet : tweets) {
				                	if(tweet.getText().contains("RT")){
				                		continue;
				                	}
				                    out.println("<p> @" + tweet.getUser().getScreenName() + " - " + tweet.getText()+"</p>");
				                    try {
										Sql.insertData(tweet.getUser().getScreenName(), tweet.getText(), tweet.getGeoLocation(), tweet.getHashtagEntities());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				                }
				            } while ((query = result.nextQuery()) != null);
				            System.exit(0);
				        } catch (TwitterException te) {
				            te.printStackTrace();
				            System.out.println("Failed to search tweets: " + te.getMessage());
				            
				        }
				    }
			

public static void main(String[] args) throws Exception {

		//String[] language = {"en"};
		String well = "Liverpool";
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(API_KEY, API_SECRET);

		String accessToken = "22692552-qRI95OTYBvD4dBKkRZ8cqqPo4PbDBy9sktUToVIiR";

		String accessTokenSecret = "svtsRfTf0MtLxrAgEkA9XZeaEQMILqB8j79J5qJxVOMki";

		AccessToken oathAccessToken = new AccessToken(accessToken,

		accessTokenSecret);

		twitter.setOAuthAccessToken(oathAccessToken);

		 if (well.length() < 1) {
	            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
	            System.exit(-1);
	        }
	       
	        try {
	            Query query = new Query(well);
	            QueryResult result;
	            do {
	                result = twitter.search(query);
	                List<Status> tweets = result.getTweets();
	                          
	                for (Status tweet : tweets) {
	                	if(!tweet.getLang().contains("en")){
	                		continue;
	                	}
	                	//System.out.println(TextPreprocessing.sqlSyntaxHandler(tweet.getText()));
	                	
	                    //System.out.println("<p> @" + tweet.getUser().getScreenName() + " - " + tweet.getText()+"</p>");
	                    try {
							//Sql.insertData(tweet.getUser().getScreenName(), tweet.getText(), tweet.getGeoLocation(), tweet.getHashtagEntities());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            } while ((query = result.nextQuery()) != null);
	            System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	        }
}
}
