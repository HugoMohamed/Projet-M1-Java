package main.graph;
import java.util.ArrayList;

import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import main.tweet.Tweet;
import main.tweet.TweetBase;
import main.tweet.User;

public class TweetGraph {
	
	private Graph graph;
	private ArrayList<User> users;
	private ArrayList<User> filtredUsers;
	private GraphStats graphStats;
	private View view;
	private double mediumCentrality;
	
	private void newGraph(String name)
	{
		graph = new SingleGraph(name);
		graph.addAttribute("ui.stylesheet", "node {	"
				+ "text-mode: hidden;"
				+ "fill-mode: dyn-plain;" 
				+ "fill-color: green, red;"
				+ "size: 7px;}");
	}
	public TweetGraph(String name)
	{
		newGraph(name);
		
		users = TweetBase.getInstance().getUsers();
	}
	
	public void setUsers(Boolean filtred)
	{
		String name = graph.getId();
		newGraph(name);
		
		users = new ArrayList<User>();

		if(filtred)
			users = TweetBase.getInstance().getFiltredUsers();
		else
			users = TweetBase.getInstance().getUsers();
	}
	
	public View getView()
	{
		return view;
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
	
	public void displayGraph(int nb)
	{
		setNodes();
		setEdges();
		for(Node n : graph)
			if(n.getDegree() < nb)
			{
				for(Edge e : n.getEachEdge())
					graph.removeEdge(e);
				graph.removeNode(n);
			}
		
		for(Node n : graph)
			if(n.getDegree() < 1)
				n.addAttribute( "ui.hide" );
		
		setStats();
		setCentrality();
		
		for(Node n : graph)
		{
			double cb = n.getAttribute("Cb");
			if(cb > mediumCentrality*20)
				n.setAttribute("ui.color", 1);
			else
				if(cb > mediumCentrality*10)
					n.setAttribute("ui.color", 0.5);
				else
					n.setAttribute("ui.color", 0);
		}
		Viewer viewer = graph.display();
		view = viewer.getDefaultView();
		
		
	}
	public void setCentrality()
	{
		double totalCent = 0;
		int i = 0;
		BetweennessCentrality bcb = new BetweennessCentrality();
		bcb.setWeightAttributeName("weight");
		bcb.init(graph);
		bcb.compute();
		
		for(User u : users)
		{
			try
			{
				Node n = graph.getNode(u.getName());
				double cb = n.getAttribute("Cb");
				totalCent += cb;
				i++;
				u.setCentrality(cb);
			}
			catch(Exception e)
			{
			}
			
		}
		if(i != 0)
			mediumCentrality = totalCent/i;
	}
	
	public void setNodes()
	{
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
		int i = 0;
		for(User u : filtredUsers)
		{
			for(Tweet t : u.getTweets())
			{
				Node n = graph.getNode(t.getUser());
				Node m = graph.getNode(t.getRetweet());
				
				if(n == null)
				{
					graph.addNode(t.getUser());
					n = graph.getNode(t.getUser());
					n.addAttribute("ui.label", t.getUser());
				}

				if(n != null && m != null && !n.hasEdgeBetween(m) && !t.getUser().equals(t.getRetweet()))
				{
					graph.addEdge(t.getUser()+t.getRetweet()+Integer.toString(++i), t.getUser(), t.getRetweet()).addAttribute("layout.weight", 5);
				}
			}
		}
	}
	
	public GraphStats getGraphStats()
	{
		return graphStats;
	}
	
	public void setStats()
	{
		graphStats = new GraphStats(graph);
	}
}
