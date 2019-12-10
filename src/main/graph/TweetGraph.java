package main.graph;
import java.util.ArrayList;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import main.tweet.Tweet;

public class TweetGraph {
	
	private Graph graph;
	private ArrayList<Tweet> tweets;
	
	public TweetGraph(String name,ArrayList<Tweet> tweets)
	{
		graph = new SingleGraph(name);
		this.tweets = tweets;
		setNodes();
		setEdges();
	}
	
	public Graph getGraph()
	{
		return graph;
	}
	
	public void displayGraph()
	{
		graph.display();
	}
	
	public void setTweets(ArrayList<Tweet> tweets)
	{
		this.tweets = tweets;
	}
	
	public void setNodes()
	{
		for(Tweet t : tweets)
		{
			// Ajoute un noeud au graphe, avec l'id correspondant � l'id du tweet
			graph.addNode(t.getId());
			// R�cup�re un noeud gr�ce � son id
			Node n = graph.getNode(t.getId());
			// Ajoute un attribut "tweet" contenant le tweet
			n.setAttribute("tweet", t);
			// Set le nom du noeud affich� dans le graphe
			n.addAttribute("ui.label", t.getUser());
		}
	}
	
	public void setEdges() 
	{
		// Parcours les noeuds du graphe
		for(Node n : graph)
		{
			// R�cup�re le tweet du noeud
			Tweet t = n.getAttribute("tweet");
			for(Node m : graph)
			{
				Tweet rt = m.getAttribute("tweet");
				// Si t a retweet� rt, on cr�e un arc de t vers rt
				if(rt.getUser() == t.getRetweet())
					graph.addEdge(t.getId()+rt.getId(),t.getId(), rt.getId(), true);
			}
		}
	}
}
