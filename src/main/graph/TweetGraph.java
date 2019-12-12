package main.graph;
import java.util.ArrayList;

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
	
	private void newGraph(String name)
	{
		graph = new SingleGraph(name);
		graph.addAttribute("ui.stylesheet", "node {"
				+ "size: 7px;"
				+ "fill-color: #777;"
				+ "text-mode: hidden;}");
	}
	public TweetGraph(String name)
	{
		newGraph(name);
		
		users = TweetBase.getInstance().getUsers();

		setNodes();
		setEdges();
	}
	
	public void setUsers(Boolean filtred)
	{
		newGraph("search");
		
		users = new ArrayList<User>();

		if(filtred)
			users = TweetBase.getInstance().getFiltredUsers();
		else
			users = TweetBase.getInstance().getUsers();
		setNodes();
		setEdges();
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
		
		Viewer viewer = graph.display();
		view = viewer.getDefaultView();
		
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
