package main.graph;
import java.util.ArrayList;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import main.tweet.Tweet;
import main.tweet.TweetBase;
import main.tweet.User;

public class TweetGraph {
	
	private Graph graph;
	private ArrayList<Tweet> tweets;
	private ArrayList<User> users;
	private ArrayList<User> filtredUsers;
	public TweetGraph(String name)
	{
		graph = new SingleGraph(name);
		tweets = TweetBase.getInstance().getTweets();
		users = TweetBase.getInstance().getUsers();
		graph.addAttribute("ui.stylesheet", "node {"
				+ "size: 7px;"
				+ "fill-color: #777;"
				+ "text-mode: hidden;}");
		setNodes();
		setEdges();
	}
	
	public Graph getGraph()
	{
		return graph;
	}
	
	public void filterUsers(int nb)
	{
		filtredUsers = new ArrayList<User>();
		for(User u : users)
		{
			if(u.getTweets().size() >= nb)
			{
				filtredUsers.add(u);
			}
		}
	}
	
	public void displayGraph()
	{
		for(Node n : graph)
			if(n.getDegree() < 1)
				n.addAttribute("ui.hide");
		graph.display();
	}
	
	public void setTweets(ArrayList<Tweet> tweets)
	{
		this.tweets = tweets;
	}
	
	public void setNodes()
	{
		System.out.println("Set Nodes");
		filterUsers(1);
		for(User u : filtredUsers)
		{
			// Ajoute un noeud au graphe, avec l'id correspondant à l'id du tweet
			graph.addNode(u.getName());
			// Récupère un noeud grâce à son id
			Node n = graph.getNode(u.getName());
			// Ajoute un attribut "tweet" contenant le tweet
			n.setAttribute("tweets", u.getTweets());
			// Set le nom du noeud affiché dans le graphe
			n.addAttribute("ui.label", u.getName());
		}
	}
	
	public void setEdges() 
	{
		System.out.println("Set Edges");
		int i = 0;
		for(Tweet t : tweets)
		{
			Node n = graph.getNode(t.getUser());
			Node m = graph.getNode(t.getRetweet());
			if(n != null && m != null && !n.hasEdgeBetween(m.getId()) && !n.getId().equals(m.getId()))
				graph.addEdge(n.getId()+m.getId()+Integer.toString(++i), n.getId(), m.getId()).addAttribute("layout.weight", 5);;
		}
	}
}
