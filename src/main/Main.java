package main;
import main.reader.CSVReader;

public class Main {
	private static CSVReader reader;
	
	public static void main(String[] args) {
		
		reader = new CSVReader("data/climat.txt", "\t");
		
		reader.readCSV();
	}

}
