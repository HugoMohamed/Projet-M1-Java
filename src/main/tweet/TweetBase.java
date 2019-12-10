package main.tweet;

import java.util.ArrayList;

public class TweetBase {

	private static TweetBase tweetBase = new TweetBase();
	private static ArrayList<Tweet> tweets;
	private static ArrayList<User> users;
	
	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	private TweetBase() {
		tweets = new ArrayList<Tweet>();
		users = new ArrayList<User>();
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public static TweetBase getInstance()
	{
		return tweetBase;
	}
	
	public static void setRetweetFromUser(String name, Tweet t)
	{
		for(User u : users)
		{
			if(u.getName().equals(name))
			{
				u.getTweets().add(t);
			}
		}
	}
	
	public static ArrayList<Tweet> getTweetsFromUser(String user)
	{
		ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
		
		for(Tweet t : tweets)
		{
			if(t.getUser() == user)
			{
				tweetList.add(t);
			}
		}
		
		return tweetList;
	}

}
