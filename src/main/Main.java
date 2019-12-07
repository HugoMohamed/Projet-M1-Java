package main;
import main.reader.CSVReader;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Main {
	private static CSVReader reader;
	
	public static void main(String[] args) {
		
		reader = new CSVReader("data/climat.txt", "\t");
		
		reader.readCSV();
		
		Graph graph = new SingleGraph("Test");
		
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.display();
	}

}
