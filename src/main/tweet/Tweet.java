package main.tweet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tweet {

	private String id;
	private String user;
	private Date date;
	private String content;
	private String retweet;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Tweet(String id, String user, Date date, String content, String retweet) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.content = content;
		this.retweet = retweet;
	}

	public String getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getContent() {
		return content;
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return id + "	" + user + "	" + date + "	" + content + "	" + retweet;
	}
}
