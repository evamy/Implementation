package shortProject1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Vertex implements Iterable<Edge> {
	int name; // name of the vertex
	List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		adj = new LinkedList<Edge>();
		revAdj = new LinkedList<Edge>(); /* only for directed graphs */

	}

	public Iterator<Edge> iterator() {
		return adj.iterator();
	}

	/**
	 * Method to get vertex number. +1 is needed because [0] is vertex 1.
	 */
	public String toString() {
		return Integer.toString(name + 1);
	}
}
