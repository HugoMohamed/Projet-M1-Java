package main.tweet;

import java.util.ArrayList;

public class TweetBase {

	private static TweetBase tweetBase = new TweetBase();
	private static ArrayList<Tweet> tweets;
	
	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	private TweetBase() {
		tweets = new ArrayList<Tweet>();
	}
	
	public static TweetBase getInstance()
	{
		return tweetBase;
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
