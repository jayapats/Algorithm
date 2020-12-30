//package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MinSpanningTree {
	
    private static int V = 5; 
  
    // Picking the minimum edge vertex from the set of vertices not yet included
    static int minEdge(int key[], Boolean mstTree[]) 
    { 
        int min = Integer.MAX_VALUE, minInd = -1; 
  
        for (int v = 0; v < V; v++) 
            if (mstTree[v] == false && key[v] < min) { 
                min = key[v]; 
                minInd = v; 
            } 
  
        return minInd; 
    } 

  
    static void findMST(int graph[][], int[] x, int[] y) 
    { 
     			
        int p[] = new int[V];         
        int key[] = new int[V];
        int val=0, totalDist=0;
  
        Boolean mstTree[] = new Boolean[V]; 
  
        try {
        
        for (int i = 0; i < V; i++) { 
            key[i] = Integer.MAX_VALUE; 
            mstTree[i] = false; 
        } 
  
        // including the first vertex in MST
        key[0] = 0; 
        
        // Parent of first node is set to -1, as it is the root node
        p[0] = -1; 
  
        for (int count = 0; count < V - 1; count++) { 
   
            int u = minEdge(key, mstTree); 
  
            // Adding the minimum edge
            mstTree[u] = true; 
  
            // Updating key value and parent index of the adjacent vertices which are not yet included
            for (int v = 0; v < V; v++) 

                if ((graph[u][v] != 0) && (mstTree[v] == false) && (graph[u][v] < key[v])) { 
                    p[v] = u; 
                    key[v] = graph[u][v]; 
                } 
        } 

        System.out.println("Edges in MST : ");
        System.out.println("Point(x,y) \t\tDistance"); 
        for(int i = 1; i<V; i++) { 
          for(int j = 0; j < V; i++) {
          
       /* for(int i=1;i<V;i++)
        {
        	for(int j=0;j<V;j++)
        	{*/
        		for(int m=0;m< V; m++) {

        			if(p[i]==m)	
        				{  
        				for(int k=0;k<V ; k++)
        				{
        				  if(i==k) {
        					  val = graph[i][p[i]];
        	        			totalDist = totalDist + val;
        				  System.out.println("("+x[m]+","+y[m]+")"+" - " + "(" + x[k]+","+y[k]+")"+ "\t\t" + val);
        				  }
        				}	  
        		} 
        			
        			}
           
        	}
        }  System.out.println("Total Distance \t\t"+totalDist);
    } 
        
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("");
            System.out.println("Total Distance \t\t"+totalDist);
             }
    }
    
    
  
    public static void main(String[] args) 
    {  							
	
		int[] aLine;
		BufferedReader reader;
		
		File inFile = null;
		if (0 < args.length) {
		   inFile = new File(args[0]);
		} else {
		   System.err.println("Invalid arguments count:" + args.length);
		   System.exit(0);
		}
		
		
		try {
		//reader = new BufferedReader(new FileReader("C:\\Users\\sweth\\workspace\\test\\src\\test\\graph.txt"));
		//reader = new BufferedReader(new FileReader("shopping.txt"));
		
		reader = new BufferedReader(new FileReader(inFile));
			
		// Reading the first line
		aLine = readLine(reader);
		V = aLine[0];
		
		System.out.println("Total no of Vertex - "+V);
		//int n=4;
		int e[] = new int[V];
		int graph1[][] = new int[V][V];
		int[] x = new int[V];
		int[] y = new int[V];
		
		for (int i = 0; i < V; i++) {

			aLine = readLine(reader);
			x[i] = aLine[0];
			y[i] = aLine[1];

		}
		
		for(int i=0;i<V;i++) {
			for(int j=0; j<V; j++)
			{
				e[i] = (int) Math.round(Math.sqrt((x[i]-x[j])*(x[i]-x[j]) + (y[i]-y[j])*(y[i]-y[j])));
				//System.out.println("Distance btw :" + "(" +x[i] + "," +y[i]+") and "+ "(" +x[j] + "," +y[j]+") " + e[i]);
				
				graph1[i][j]=e[i];
			}
		}
		
		//System.out.println("Edge Calculation : ");
		//System.out.println(Arrays.deepToString(graph1));
									
        findMST(graph1,x,y); 
        
        reader.close();
		}
		
		
		catch (IOException e) {
			e.printStackTrace();
		}
    } 
    
    private static int[] readLine(BufferedReader reader) throws IOException {
		// Reading line by line
		String line = reader.readLine();

		while (line != null) {
			String[] splited = line.split(" ");

			int[] a = new int[splited.length];

			// Parsing the string into integers
			for (int m = 0; m < splited.length; m++) {
				a[m] = Integer.parseInt(splited[m]);
			}
			return a;
		}

		return null;
	}

	
}
