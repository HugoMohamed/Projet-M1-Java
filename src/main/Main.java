package main;
import main.graph.TweetGraph;
import main.reader.CSVReader;
import main.tweet.Tweet;
import main.tweet.TweetBase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Main {
	private static CSVReader reader;
	
	public static void main(String[] args) throws ParseException {
		
		reader = new CSVReader("data/foot.txt", "\t");
		
		reader.readCSV();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		TweetGraph tweetGraph = new TweetGraph("climat",TweetBase.getInstance().getTweets());
		
		tweetGraph.displayGraph();
		
	}

}
