package main.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.tweet.Tweet;
import main.tweet.TweetBase;


/*
 * Reader for CSV file
 */
public class CSVReader {
	private String filepath;
	private String sep;
	
	/*
	 * Constructor
	 * filepath : path for csv file
	 * sep : character for separation
	 */
	public CSVReader(String filepath, String sep){
		this.filepath = filepath;
		this.sep = sep;
	}

	/*
	 * Read data from CSV file
	 * filepath : path for CSV file
	 */
	public void readCSV() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BufferedReader csvReader = null;
		String line = "";
		
		try {
			csvReader = new BufferedReader(new FileReader(filepath));
			while((line = csvReader.readLine()) != null) {
				String[] tweetInfo = line.split(sep);
				Date date = null;
				date = dateFormat.parse(tweetInfo[2]);
				Tweet t;
				if(tweetInfo.length == 5)
					t = new Tweet(tweetInfo[0], tweetInfo[1], date, tweetInfo[3], tweetInfo[4]);
				else
					t = new Tweet(tweetInfo[0], tweetInfo[1], date, tweetInfo[3], "null");
				
				TweetBase.getInstance().getTweets().add(t);
			}
			csvReader.close();
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
