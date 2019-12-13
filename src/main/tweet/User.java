package main.tweet;

import java.util.ArrayList;

public class User {
	
	private String name;
	private double centrality;
	
	public double getCentrality() {
		return centrality;
	}

	public void setCentrality(double centrality) {
		this.centrality = centrality;
	}

	public String getName() {
		return name;
	}

	private ArrayList<Tweet> retweets;
	
	public User(String name) {
		this.name = name;
		retweets = new ArrayList<Tweet>();
	}

	public ArrayList<Tweet> getTweets() {
		return retweets;
	}

	public void setTweets(ArrayList<Tweet> tweets) {
		this.retweets = tweets;
	}
	
}
