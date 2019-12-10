package main;
import main.reader.CSVReader;
import main.tweet.Tweet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Main {
	private static CSVReader reader;
	
	public static void main(String[] args) throws ParseException {
		
		reader = new CSVReader("data/climat.txt", "\t");
		
		reader.readCSV();
		
		Graph graph = new SingleGraph("Test");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tweet t1 = new Tweet("1","Joe", dateFormat.parse("2019-09-02 09:40:46.497980"), "Salut", "Jack");
		Tweet t2 = new Tweet("2","Jack", dateFormat.parse("2019-09-02 09:40:46.497980"), "Wesh", "Joffrey");
		Tweet t3 = new Tweet("3","Joffrey", dateFormat.parse("2019-09-02 09:40:46.497980"), "Hey", "vide");
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(t1);
		tweets.add(t2);
		tweets.add(t3);
		
		for(Tweet t : tweets)
		{
			// Ajoute un noeud au graphe, avec l'id correspondant à l'id du tweet
			graph.addNode(t.getId());
			// Récupère un noeud grâce à son id
			Node n = graph.getNode(t.getId());
			// Ajoute un attribut "tweet" contenant le tweet
			n.setAttribute("tweet", t);
			// Set le nom du noeud affiché dans le graphe
			n.addAttribute("ui.label", t.getUser());
		}

		for(Node n : graph)
		{
			Tweet t = n.getAttribute("tweet");
			for(Node m : graph)
			{
				Tweet rt = m.getAttribute("tweet");
				if(t.getUser() == rt.getRetweet())
					graph.addEdge(t.getId()+rt.getId(),rt.getId(), t.getId(), true);
			}
		}
		graph.display();
	}

}
