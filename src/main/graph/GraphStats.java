package main.graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class GraphStats {

	private int volume;
	private int ordre;
	private int diametre;
	private int degre;
	
	public GraphStats(Graph graph)
	{
		int volume = 0;
		int ordre = 0;
		for(Node n : graph)
		{
			volume += n.getDegree();
			ordre++;
		}
		if(ordre != 0)
			degre = volume / ordre;
		this.volume = volume;
		this.ordre = ordre;
		
		diametre = 0;
	}

	public int getVolume() {
		return volume;
	}

	public int getOrdre() {
		return ordre;
	}

	public int getDiametre() {
		return diametre;
	}

	public int getDegre() {
		return degre;
	}

}
