/**This is the Launcher Class, which uses all the other classes, and outputs it to a webpage*/

package twitterSentiment;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;


public class Launcher {
	 double positive = 0;//Number of positive tweets
	 double negative = 0;//Number of negative tweets
	 int neutral =0;//Number of neutral tweets
	 int count = 0;//This is used to count the number of tweets in the list
	 String email = "";
	 String word="";
	 
	 /*Below are the variables needed to store the top 3 retweeted tweets and the sentiment of the users
	  * based on the number of followers they have*/
	 Status highestRetweetCount=null;
	 Status secondRetweetCount=null;
	 Status thirdRetweetCount=null;
	 int[] retweetScores = new int[3];
	 int[][] followers=new int[6][2];
	

	
	public Launcher(){
		this.positive=positive;
		this.negative=negative;
		this.neutral=neutral;
		this.followers=followers;
		this.email=email;
		this.word=word;
	
	}
	
	/**Returns the number of positive tweets*/
	public double getPositive(){
		return positive;
	}
	
	/**Returns the number of negative tweets*/
	public double getNegative(){
		return negative;
	}
	
	public String getEmail(){
		return email;
	}
	
	
	/**This determines the tweets with the highest number of retweets*/
	public void topRetweets(Status tweet, int score){
		if(tweet.getRetweetCount()>=highestRetweetCount.getRetweetCount()){
			thirdRetweetCount=secondRetweetCount;
			
			
			retweetScores[2]=retweetScores[1];
			secondRetweetCount=highestRetweetCount;
			retweetScores[1]=retweetScores[0];
			highestRetweetCount=tweet;
			retweetScores[0]=score;
					
			
		}
		else if(tweet.getRetweetCount()>=secondRetweetCount.getRetweetCount()){
			thirdRetweetCount=secondRetweetCount;
			retweetScores[2]=retweetScores[1];
			secondRetweetCount=tweet;
			retweetScores[1]=score;
			
			
		}
		else if(tweet.getRetweetCount()>= thirdRetweetCount.getRetweetCount()){
			thirdRetweetCount=tweet;
			retweetScores[2]=score;
			
			
		}
				
	}
	
	
	/**This assigns a div class to each tweet based on its sentiment.*/
	public void printOutSentiment(PrintWriter out, Status tweet, int score){
		if(score==0){
			out.println("<div class=\"positivetweet\"> \n <div class = \"image\"> \n <img src=\""+tweet.getUser().getBiggerProfileImageURL()+"\"height=\"42\" width=\"42\" class=\"fixedsize\">\n</div>\n<div class=\"username\">\n<p>"+tweet.getUser().getScreenName()+"</p>\n</div>\n<div class=\"text\"><p>"+ tweet.getText() +"\n</p>\n</div>\n</div>\n<br /> \n\n");
			positive++;
		}
		else if(score ==1){
			negative++;
			out.println("<div class=\"negativetweet\">\n <div class = \"image\">\n <img src=\""+tweet.getUser().getBiggerProfileImageURL()+"\"height=\"42\" width=\"42\" class=\"fixedsize\">\n </div> \n<div class=\"username\"><p>"+tweet.getUser().getScreenName()+"</p></div><div class=\"text\">\n<p>"+ tweet.getText() +"\n</p></div></div><br /> \n\n");

		}
		else {neutral++;
		out.println("<div class=\"neutraltweet\"> \n <div class = \"image\"> \n <img src=\""+tweet.getUser().getBiggerProfileImageURL()+"\"height=\"42\" width=\"42\" class=\"fixedsize\">\n</div>\n<div class=\"username\"><p>"+tweet.getUser().getScreenName()+"\n</p>\n</div>\n<div class=\"text\">\n<p>"+ tweet.getText() +"\n</p>\n</div>\n</div><br /> \n \n");
		}
	}
	
	/**Finds the percentage of users that are positive and negative based on their follower count*/
	public void followerCount(Status tweet, int score){
		
		//0-200 Followers
		if(tweet.getUser().getFollowersCount()<=200){
			if(score==0){
				followers[0][0]++;
			}
			else if(score==1){
				followers[0][1]++;
			}
		}
		//201-500 Followers
		else if(tweet.getUser().getFollowersCount()>200&&tweet.getUser().getFollowersCount()<=500){
			if(score==0){
				followers[1][0]++;
			}
			else if(score==1){
				followers[1][1]++;
			}
		}
		//501-1000 Followers
		else if(tweet.getUser().getFollowersCount()>500&&tweet.getUser().getFollowersCount()<=1000){
			if(score==0){
				followers[2][0]++;
			}
			else if(score==1){
				followers[2][1]++;
			}
		}
		//1001-5000 Followers
		else if(tweet.getUser().getFollowersCount()>1000&&tweet.getUser().getFollowersCount()<=5000){
			if(score==0){
				followers[3][0]++;
			}
			else if(score==1){
				followers[3][1]++;
			}
		}
		else if(tweet.getUser().getFollowersCount()>5000&&tweet.getUser().getFollowersCount()<=10000){
			if(score==0){
				followers[4][0]++;
			}
			else if(score==1){
				followers[4][1]++;
			}
		}
		//10,000+ Followers
		else if(tweet.getUser().getFollowersCount()>10000){
			if(score==0){
				followers[5][0]++;
			}
			else if(score==1){
				followers[5][1]++;
			}
		}
		
	}
	
	
	public String getQuery(){
		
		
		return word;
	}
	
	//This retrieves the sentiment of users based on the number of followers
	public int getFollowers(int number, int positiveOrNegative){
		
		
		return followers[number][positiveOrNegative];
	}
	
	public String advancedQuery(String all, String exact, String any, String none, String hashtag, String from, String to, String mention) throws NullPointerException{
		String newQuery="";
		
		try{
		if(all.length()>1){
			newQuery+=all+" ";
		}
		if(exact.length()>1){
			newQuery+="\""+exact+"\" ";
			
		}
		}catch(NullPointerException e){
			
		}
		
		try{
		if(any.length()>1){
			
				String[] words = any.split("\\s+");
				for(int i=0;i<words.length-1;i++){
					newQuery+= "-"+words[i]+ " ";
				}
				newQuery+= "-"+ words[words.length-1]+" ";
					
		}}catch(NullPointerException e){
			
		}
		
		try{
		if(none.length()>1){
			String[] words = none.split("\\s+");
			for(int i=0;i<words.length-1;i++){
				newQuery+= "-"+words[i]+ " ";
			}
			newQuery+= "-"+ words[words.length-1]+" ";
		}}catch(NullPointerException e){
			
		}
		
		try{
		if(hashtag.length()>1){
			String[] words = hashtag.split("\\s+");
			for(int i=0;i<words.length-1;i++){
				newQuery+= "#"+words[i]+  " OR ";
			}
			newQuery+= "#"+ words[words.length-1]+" ";
					
		}}catch(NullPointerException e){
			
		}
		
		
		try{
		if(from.length()>1){
			String[] words = from.split("\\s+");
			for(int i=0;i<words.length-1;i++){
				newQuery+= "from:"+words[i]+  " OR ";
			}
			newQuery+= "from:"+ words[words.length-1]+" ";
					
		}}catch(NullPointerException e){
			
		}
		
		try{
		if(to.length()>1){
			String[] words = to.split("\\s+");
			for(int i=0;i<words.length-1;i++){
				newQuery+= "to:"+words[i]+  " OR ";
			}
			newQuery+= "to:"+ words[words.length-1]+" ";
					
		}}catch(NullPointerException e){
			
		}
		
		try{
		if(mention.length()>1){
			String[] words = mention.split("\\s+");
			for(int i=0;i<words.length-1;i++){
				newQuery+= "@"+words[i]+  " OR ";
			}
			newQuery+= "@"+ words[words.length-1]+" ";
					
		}}catch(NullPointerException e){
			
		}
		
		
		
		
		
		
		
		System.out.println(newQuery);
		
		return newQuery;
		
	}
	
	
	
	/**This method is the one that is called by the JSP file*/
	public void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		
		
		long firstTime=System.currentTimeMillis();//Checks the speed of the call
		PrintWriter out = response.getWriter();
				
				
		/**The code below prints out the HTML required to load the page*/
		out.println("<HTML>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
		out.println("<link rel =\"text/javascript\" src =\"script.js\">");
		out.println("<title>Results</title>");
		out.println("<script type=\"text/javascript\" src=\"http://www.amcharts.com/lib/3/amcharts.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"http://www.amcharts.com/lib/3/pie.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"http://www.amcharts.com/lib/3/serial.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"http://www.amcharts.com/lib/3/themes/none.js\"></script>");
		out.println("</head>");
		out.println("<BODY>");
		out.println("<div id =\"results\">");
		/**End HTML code*/
		
		
		
		int realUserInput = Integer.parseInt(request.getParameter("number"));//This retrieves the number that the user chose
		String word = request.getParameter("query");//This retrieves the query
		
		if(word==null){
			
			word=advancedQuery(request.getParameter("all"), request.getParameter("exact"), request.getParameter("or"), request.getParameter("none"), 
					request.getParameter("hashtag"), request.getParameter("from"), request.getParameter("to"), request.getParameter("mention"));
			
		}
		
		
		System.out.println(word);
		
		
		
				
		
		TwitterInitializer test = new TwitterInitializer();
		
			Query query = new Query(word);
			
			
			
			List<Status> finalTweets = new LinkedList();//This is the list that will have all the tweets
			QueryResult result;
		
			result = test.twitter.search(query);
			List<Status> tweets = result.getTweets();
			
			
			/*This loop runs until the finalTweets list = the user's request*/	
			while(finalTweets.size()<realUserInput&&((query = result.nextQuery()) != null)){
				
				
				
				
				result = test.twitter.search(query);
				tweets = result.getTweets();
				
				
				
				for(int i =0; i<tweets.size();i++){
					if(finalTweets.size()==realUserInput){
						break;
					}
					
					//This checks for non-retweeted tweets, english tweets and only
					if(!tweets.get(i).getLang().contains("en")||tweets.get(i).getText().contains("RT")||tweets.get(i).getText().charAt(0)==('\"')){
						continue;
						}
					else{
					
					finalTweets.add(tweets.get(i));
					}
				}
				//Queries the twitter API again
				query =result.nextQuery();
			}
			
			
			/*This fills each retweeted status with the last tweet, just to initialize it*/
			try{
			highestRetweetCount=finalTweets.get(finalTweets.size()-1);
			secondRetweetCount=finalTweets.get(finalTweets.size()-1);
			thirdRetweetCount=finalTweets.get(finalTweets.size()-1);
			}
			catch(IndexOutOfBoundsException e){
				out.println("Error in retweet collection");
				
			}
				
			
			/*Goes through each tweet and puts it through the Naive Bayes */
			for(Status tweet: finalTweets){
					NaiveBayes naiveBayes = new NaiveBayes(tweet.getText());
					int score =naiveBayes.start();
					
					printOutSentiment(out, tweet, score); //Prints out the Sentiment to a page
					topRetweets(tweet,score);//Adds it to the top retweets, or ignores
					followerCount(tweet, score);//Adds it into the correct follower range
				}

			/*More HTML required. Essentially it adds the retweets to the page*/
				out.println("</div>");
				
				out.println("<div id =\"topRetweets\">");
				try{
				out.println("<div id=\"title\"><h1>Top Retweets</h1></div><div class=\"grade\">1</div> <div class=\"retweet\"> Number of Retweets:" +highestRetweetCount.getRetweetCount()+"</div>");
				}catch(NullPointerException e){
					
				}
				printOutSentiment(out, highestRetweetCount, retweetScores[0]);
				out.println();
				out.println("<div class=\"grade\">2</div><div class=\"retweet\"> Number of Retweets:"+ secondRetweetCount.getRetweetCount()+" </div>");
				printOutSentiment(out, secondRetweetCount, retweetScores[1]);
				out.println();
				out.println("<div class=\"grade\">3</div><div class=\"retweet\"> Number of Retweets:" + thirdRetweetCount.getRetweetCount()+" </div>");
				printOutSentiment(out, thirdRetweetCount, retweetScores[2]);
				out.println("</div>");
				out.println();
			/*End HTML*/	
		
		long finalTime = System.currentTimeMillis();
		
		
		System.out.println(finalTime-firstTime);//Prints out the time taken

	}
		
					
		
		
		
		
		
		
	}
	

