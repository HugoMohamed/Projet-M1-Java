package main.graph;
import java.util.ArrayList;

import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import main.tweet.Tweet;
import main.tweet.TweetBase;
import main.tweet.User;

public class TweetGraph {
	
	private Graph graph;
	private ArrayList<User> users;
	private GraphStats graphStats;
	
	private void newGraph(String name)
	{
		graph = new SingleGraph(name);
		graph.addAttribute("ui.stylesheet", "node {"
				+ "size-mode: dyn-size;	"
				+ "text-mode: hidden;"
				+ "fill-mode: dyn-plain;" 
				+ "fill-color: green,yellow,orange,red,purple;"
				+ "size: 8px;}");
	}
	public TweetGraph()
	{	
		users = new ArrayList<User>();
		users = TweetBase.getInstance().getUsers();
	}
	
	public void setUsers(Boolean filtred)
	{		
		users = new ArrayList<User>();

		if(filtred)
			users = TweetBase.getInstance().getFiltredUsers();
		else
			users = TweetBase.getInstance().getUsers();
	}
	
	public Graph getGraph()
	{
		return graph;
	}
	
	public void filterNodes(int nb)
	{
		int nbNodes = graph.getNodeCount();
		int i= 0;
		while(i < nbNodes)
		{
			Node n = graph.getNode(i);
			if(n.getDegree() < nb)
			{
				graph.removeNode(i);
				nbNodes--;
			}
			else
				i++;
		}
	}
	
	public void computeGraph(int nb, String name, boolean centrality)
	{
		newGraph(name);
		setNodes();
		setEdges();
		filterNodes(nb);
		setStats();
		if(centrality) 
		{
			setCentrality();
			setColorSize();
		}
	}
	
	public void displayGraph(boolean hide)
	{
		if(hide)
			for(Node n : graph)
				if(n.getDegree() < 1)
					n.addAttribute("ui.hide");
		graph.display();
	}
	
	private void setColorSize() 
	{
		for(Node n : graph)
		{
			double cb = n.getAttribute("Cb");
			int log = 0;
			double color = 0;
			if(cb >= 1)
			{
				log = (int) Math.log10(cb);
				switch(log)
				{
				case 0: color = 0;break;
				case 1: color = 0.25;break;
				case 2: color = 0.5;break;
				case 3: color = 0.75;break;
				case 4: color = 1;break;
				default: color = 1;break;
				}
			}
			
			n.setAttribute("ui.color",color);
			n.setAttribute("ui.size", (8*log)+8);
		}
		
	}
	public void setCentrality()
	{
		BetweennessCentrality bcb = new BetweennessCentrality();
		bcb.init(graph);
		bcb.compute();
	}
	
	public void setNodes()
	{
		for(User u : users)
		{
			// Ajoute un noeud au graphe, avec l'id correspondant à l'id du tweet
			graph.addNode(u.getName());
			// Récupère un noeud grâce à son id
			Node n = graph.getNode(u.getName());
			// Ajoute un attribut "tweet" contenant le tweet
			n.setAttribute("tweets", u.getRetweets());
			// Set le nom du noeud affiché dans le graphe
			n.addAttribute("ui.label", u.getName());
		}
	}
	
	public void setEdges() 
	{
		int i = 0;
		for(User u : users)
		{
			for(Tweet t : u.getRetweets())
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
