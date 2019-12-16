package main.tweet;

import java.util.ArrayList;

public class User {
	
	private String name;
	private int CommunityValue;
	private ArrayList<Tweet> retweets;
	
	public String getName() {
		return name;
	}
	
	public User(String name) {
		this.name = name;
		retweets = new ArrayList<Tweet>();
	}

	public ArrayList<Tweet> getRetweets() {
		return retweets;
	}

	public void setRetweets(ArrayList<Tweet> retweets) {
		this.retweets = retweets;
	}
	
	public int getCommunityValue() {
		return CommunityValue;
	}
}
