//package test;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TspNearestNeib {

	private static Map<Integer, Vertex> vertexNames;

	public TspNearestNeib() {
		vertexNames = new HashMap<>();
	}

	public static void main(String[] args) {

		long start = System.nanoTime();
		ArrayList<Integer> n = new ArrayList<Integer>();

		int V;
		TspNearestNeib g = new TspNearestNeib();
		int[] aLine;
		BufferedReader reader,reader1;
		vertexNames = new HashMap<>();
		// Random random = new Random();

		File inFile = null;
		if (0 < args.length) {
			inFile = new File(args[0]);
		} else {
			System.err.println("Invalid arguments count:" + args.length);
			System.exit(0);
		}

		try {
			long timeElapsed = 0;
			long startTime = 0;
			long endTime = 0;

			reader1 = new BufferedReader(new FileReader(inFile));
			
			int lines = 0;
			while (reader1.readLine() != null) lines++;
			reader1.close();
			V= lines;
			
			reader = new BufferedReader(new FileReader(inFile));
			System.out.println("read lines");
			
			vertexNames = new HashMap<>();
			List<Edge> path = new LinkedList<>();
			List<Edge> copyPath = new LinkedList<>();
			
			startTime = System.currentTimeMillis();

			
			
			//Generating random vertices
			for (int i = 0; i < V; i++) {
				aLine = readLine(reader);
				g.addVertex(new Vertex(aLine[0], aLine[1], aLine[2]));
			}
			
			// For each pair of vertices, add an undirected edge.
	        for(int i = 0; i < vertexNames.size(); i++) 
	            for(int j = i + 1; j < vertexNames.size(); j++) 
	                g.addUndirectedEdge(vertexNames.get(i).id, // Add edge
	                        vertexNames.get(j).id, 0);

	       g.calAllEDistances(); // compute distances
	        	
			path = g.NNTsp();
			//path = g.bruteForceTsp();
			
			copyPath = path;
			
			double distance = 0;
            for (Edge edge : path) {
            	System.out.println("Source - "+edge.source.id);
            	System.out.println("Target - "+edge.target.id);
            	System.out.println("Edge Distance - "+edge.cost);
                distance += edge.cost;
            }
            
          //Using Buffered writer to write the output into the file - merge.txt
			BufferedWriter bw = null;
			//File file = new File("/nfs/stak/users/jayapats/CS325/HW1/merge.txt");
			File file = new File("TSP_output.txt");
			//File file = new File("C:\\Users\\sweth\\workspace\\test\\src\\test\\TSP_output.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			int d =(int) Math.round(distance);
			
			bw.write(String.valueOf(d));
        	bw.newLine();
        	
            //for (int i=0; i<path.size();i++) {
			for (Edge edge : copyPath) {
				//System.out.println("Inside file write!!!!!!!!!!!!!!!!");
            	bw.write(String.valueOf(edge.source.id));
            	bw.newLine();
            }
            
        	System.out.println("Path - "+path);
			System.out.println("Distance = "+distance);
			
			
			long end = System.nanoTime();
			timeElapsed = (end - start) / 1000000;
			// System.out.println("TSP = "+ n);
			System.out.println("Running time for finding the TSP = " + timeElapsed);

			reader.close();
			if (bw != null)
				bw.close();

		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	  /**
     * To add vertex to the graph
     */
    public void addVertex(Vertex v) {
        if (vertexNames.containsKey(v.id))
            throw new IllegalArgumentException("Cannot create new vertex "
                    + "with existing name.");
        vertexNames.put(v.id, v);
    }

	// Gets a collection of all the vertices in the graph

	public static Collection<Vertex> getVertices() {
		return vertexNames.values();
	}

	/**
     * To get the vertex
     */
    public Vertex getVertex(String name) {
        return vertexNames.get(name);
    }

	/**
     * Adds an edge from u to v
     */
    public void addEdge(int nameU, int nameV, Double cost) {
        if (!vertexNames.containsKey(nameU))
            throw new IllegalArgumentException(nameU +
                    " does not exist. Cannot create edge.");
        if (!vertexNames.containsKey(nameV))
            throw new IllegalArgumentException(nameV +
                    " does not exist. Cannot create edge.");
        Vertex sourceVertex = vertexNames.get(nameU);
        Vertex targetVertex = vertexNames.get(nameV);
        Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
        sourceVertex.addEdge(newEdge);
    }

	
    public void addUndirectedEdge(int v1, int v2, double cost) {
        addEdge(v1, v2, cost);
        addEdge(v2, v1, cost);
    }



	 /**
     * Computes distance between two points
     */
    public double computeEDistPoints(
            double ux, double uy, double vx, double vy) {
        return Math.sqrt(Math.pow(ux - vx, 2) + Math.pow(uy - vy, 2));
    }

	/**
     * Computes Euclidean distance between two vertices 
     */
    public double calDistance(Vertex u, Vertex v) {
        return computeEDistPoints(u.x, u.y, v.x, v.y);
    }

    public void calAllEDistances() {
        for (Vertex u : getVertices())
            for (Edge uv : u.adjacentEdges) {
                Vertex v = uv.target;
                uv.cost = computeEDistPoints(u.x, u.y, v.x, v.y);
            }
    }


	public List<Edge> NNTsp() {
		
		double smallestDistance = Double.MAX_VALUE; // Store shortest d here
        List<Edge> shortestPath = null;   // Store the actual path here

        // For each vertex, perform nearest neighbor, check its length
        // against current smallest path. If smaller, replace.
        for(Vertex v : getVertices())
        {
            List<Edge> path = NNUtil(v); // NN path
            double length = PathLength(path); // Path's length

            // If smaller than current smallest, replace
            if(length < smallestDistance)
            {
                smallestDistance = length;
                shortestPath = path;
            }
        }

        // Return the shortest nearest neighbor path
        return shortestPath;
		
	}
	
	private List<Edge> NNUtil(Vertex v)
    {

        Vertex currentVertex = v;
        v.known = true;
        List<Edge> path = new LinkedList<>();
      

        while(path.size() < vertexNames.size() - 1)
        {
            double sLength = Double.MAX_VALUE; // Shortest edge length
            Edge sEdge = null;                  // Shortest edge

            // Cycle through all adjacent edges whose targets are unknown.
            // If distance is smaller than current smallest, replace length
            // and the edge
            for(Edge e : currentVertex.adjacentEdges)
            {
                if (!e.target.known && e.cost < sLength)
                {
                    sLength = e.cost;
                    sEdge = e;
                }
            }

            sEdge.target.known = true;
            path.add(sEdge);
            currentVertex = sEdge.target;
        }

        // Add last edge
        // the cycle
        for(Edge e : currentVertex.adjacentEdges)
        {
            if(e.target == v)
            {
                path.add(e);
                break;
            }
        }

        for(Vertex x : getVertices())
            x.known = false;
        return path;
    }

 
    private double PathLength(List<Edge> path)
    {
        double sum = 0.0;

        // Add distances of each edge to the sum and return the sum
        for(Edge e : path)
            sum += e.cost;
        return sum;
    }


	public void getNNeighbors() {

		for (Vertex vertex : getVertices()) {
			vertex.known = false;
		}

		Vertex start = vertexNames.get(0);
		start.known = true;
		Vertex current = start;

		while (true) {

			List<Edge> unknownEdges = current.adjacentEdges.stream().filter(x -> !x.target.known)
					.collect(Collectors.toList());

			if (unknownEdges.isEmpty()) {
				start.prev = current;
				return;
			}

			Edge shortestEdge = Collections.min(unknownEdges, Comparator.comparing(x -> x.cost));
			shortestEdge.target.prev = shortestEdge.source;
			current = shortestEdge.target;
			current.known = true;
		}
	}

	

	private static int[] readLine(BufferedReader reader) throws IOException {
		// Reading line by line
		String line = reader.readLine();

		while (line != null) {
			// String[] splited = line.split(" ");
			String[] splited = line.trim().split("\\s+");

			int[] a = new int[splited.length];

			// Parsing the string into integers
			for (int m = 0; m < splited.length; m++) {
				if (!splited[m].isEmpty()) {
					a[m] = Integer.parseInt(splited[m].trim());
				}

			}
			//System.out.println("Splitted - " + Arrays.toString(a));
			return a;
		}

		return null;
	}

	
}
