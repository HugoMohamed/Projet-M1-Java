package main;
import main.graph.TweetGraph;
import main.reader.CSVReader;


public class Main {
	private static CSVReader reader;
	
	public static void main(String[] args) {
		
		reader = new CSVReader("data/FootShort.txt", "\t");
		
		reader.readCSV();
		
		
		TweetGraph tweetGraph = new TweetGraph("climat");
		
		tweetGraph.displayGraph();
		
		
	}

}
