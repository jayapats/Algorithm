//package test;

public class Edge {

	public double cost;
    public Vertex source;
    public Vertex target;

    public Edge(Vertex v1, Vertex v2, double weight) {
        source = v1;
        target = v2;
        this.cost = weight;
    }

    public String toString() {
        return source + " - " + target;
    }
}
