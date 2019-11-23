package main.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/*
 * Reader for CSV file
 */
public class CSVReader {
	
	private ArrayList<String> data;
	private String filepath;
	private char sep;
	
	/*
	 * Constructor
	 * filepath : path for csv file
	 * sep : character for separation
	 */
	public CSVReader(String filepath, char sep){
		data = new ArrayList<String>();
		this.filepath = filepath;
		this.sep = sep;
	}
	
	public ArrayList<String> getData() {
		return data;
	}

	/*
	 * Read data from CSV file
	 * filepath : path for CSV file
	 */
	public void readCSV() {
		
		BufferedReader csvReader = null;
		String line = "";
		
		try {
			csvReader = new BufferedReader(new FileReader(filepath));
			while((line = csvReader.readLine()) != null) {
				data.add(line);
			}
			csvReader.close();
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
