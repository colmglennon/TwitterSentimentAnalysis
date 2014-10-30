package twitterSentiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;

public class Sql {
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	public Sql() throws Exception {

		connect = DriverManager.getConnection("jdbc:mysql://localhost/twitter?"
				+ "user=root&password=");

		statement = connect.createStatement();

	}

	public static Connection getConnection() {
		return connect;

	}

	public static void insertData(String user, String text,
			GeoLocation geoLocation, HashtagEntity[] hashtag) throws Exception {

		try {
			// Hopefully will load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost?"
							+ "user=root&password=");

			// Statements allow the issue SQL queries to the database
			statement = connect.createStatement();

			preparedStatement = connect
					.prepareStatement("Insert into twitter.tweets (user,text,location,hashtag) values(\""
							+ user
							+ "\", \""
							+ text
							+ "\", \""
							+ geoLocation
							+ "\", \"" + hashtag + "\")");
			preparedStatement.executeUpdate();

			/*
			 * preparedStatement.setString(1, user);
			 * preparedStatement.setString(2, text);
			 * preparedStatement.setString(3, hashtag);
			 * preparedStatement.executeUpdate();
			 * 
			 * preparedStatement =
			 * connect.prepareStatement("SELECT * FROM twitter.tweets");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 */

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		}

	}

	/**
	 * This method checks to see if the word is in the database, and either
	 * increments the words by one, or inserts it into the database
	 */
	public static void insertData(String positiveOrNegative, String text)
			throws Exception {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost?"
							+ "user=root&password=");

			// Statements allow the issue SQL queries to the database
			statement = connect.createStatement();

			preparedStatement = connect
					.prepareStatement("select case when count(cx.text) > 0 then 1 else 0 end FROM twitter.twitter_"
							+ positiveOrNegative
							+ " as cx WHERE cx.text='"
							+ text + "'");
			resultSet = preparedStatement.executeQuery();

			resultSet.first();
			int isDuplicate = resultSet.getInt(1);

			if (isDuplicate == 1) {
				preparedStatement = connect
						.prepareStatement("update twitter.twitter_"
								+ positiveOrNegative
								+ " SET count = count+1 where text = \"" + text
								+ "\"");
				preparedStatement.executeUpdate();
			} else {
				preparedStatement = connect
						.prepareStatement("insert into twitter.twitter_"
								+ positiveOrNegative
								+ " (text,count) VALUES(\"" + text + "\" , 1)");
				try {
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					System.out.println("Error inserting into SQL");
				}
			}

			/*
			 * preparedStatement.setString(1, user);
			 * preparedStatement.setString(2, text);
			 * preparedStatement.setString(3, hashtag);
			 * preparedStatement.executeUpdate();
			 * 
			 * preparedStatement =
			 * connect.prepareStatement("SELECT * FROM twitter.tweets");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 */

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connect != null) {
				connect.close();
			}
		}

	}

	public static int count(String positiveOrNegative) throws Exception {
		int count = -1;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup the connection with the DB
		try {
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost?user=rootn&password=");

			statement = connect.createStatement();

			preparedStatement = connect
					.prepareStatement("Select MAX(id) FROM twitter.twitter_"
							+ positiveOrNegative);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			count = resultSet.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connect != null) {
				connect.close();
			}
		}

		return count;

	}

	public static double countOfWordTotal(String text, String positiveOrNegative)
			throws Exception {
		double probability=0.0;
		int total = -1;
		int count=0;

		if (positiveOrNegative.matches("positive")) {
			total = 27781;

		} else if (positiveOrNegative.matches("negative")) {
			total = 28035;
			
		}

		String[] words = text.split("\\s+");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup the connection with the DB
		try {
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/?user=root&password=");
			statement = connect.createStatement();

			String queryString = "Select count FROM twitter.twitter_"
					+ positiveOrNegative + " WHERE text = '" + words[0] + "'";
			for (int i = 1; i < words.length; i++) {
				queryString += (" OR text = '" + words[i] + "'");
			}
			
			
			

			
			preparedStatement = connect.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();

			while (!resultSet.isLast()) {
				resultSet.next();
				int result = resultSet.getInt(1);
				count++;
				probability += Math.log((result + 1.0) / (total + 1.0));

			}
			
			while(count<words.length){
				
				probability+=Math.log((1.0) / (total + 1.0));
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (connect != null) {
				connect.close();
			}
		}

		return probability;

	}

	public static void main(String[] args) throws Exception {

		System.out.println(countOfWordTotal(
				"@TJ_Chelsea yeah of course I feel damn bad man physics haih",
				"positive"));
		System.out.println(countOfWordTotal(
				"@TJ_Chelsea yeah of course I feel damn bad man physics haih",
				"negative"));

	}

}
