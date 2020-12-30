package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TspDP1 {
	private static int V = 50;
	

	private static ArrayList<Integer> outputArray = new ArrayList<Integer>();
	private static int g[][];
	private static int p[][];
	private static int npow;
	private static int N;
	private static int d[][];

	public static long time;

	public TspDP1() {

	}

	
	public static void main(String[] args) {

		
		long start = System.nanoTime();
		ArrayList<Integer> n = new ArrayList<Integer>();
    									
	
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
			long timeElapsed = 0;
			long startTime = 0;
			long endTime = 0;

			reader = new BufferedReader(new FileReader(inFile));
			System.out.println("read lines");

			// Reading the first line
			// aLine = readLine(reader);
			// V = aLine[0];

			System.out.println("Total no of Vertex - " + V);
			// int n=4;
			int e[] = new int[V];
			int graph1[][] = new int[V][V];
			int[] v = new int[V];
			int[] x = new int[V];
			int[] y = new int[V];

			// rm----------------------------
			/*
			 * aLine = readLine(reader); v[0] = aLine[0]; x[0] = aLine[1]; y[0] = aLine[2];
			 * 
			 * System.out.println("Array values: v-"+ v[0]+" x-"+x[0]+" y-"+y[0]);
			 */

			for (int i = 0; i < V; i++) {

				aLine = readLine(reader);
				v[i] = aLine[0];
				x[i] = aLine[1];
				y[i] = aLine[2];

			}
			startTime = System.currentTimeMillis();
			
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					e[i] = (int) Math.round(Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j])));
					System.out.println("Distance btw :" + "(" + x[i] + "," + y[i] + ") and " + "(" + x[j] + "," + y[j]
							+ ") " + e[i]);

					graph1[i][j] = e[i];
				}
			}

			System.out.println("Edge Calculation : ");
			System.out.println(Arrays.deepToString(graph1));
			
			n = computeTSP(graph1,V);
			
			long end = System.nanoTime();
			time = (end - start) / 1000000000;
			System.out.println("TSP = "+ n);
			System.out.println("Running time for finding the TSP = "+time);
			
	
			reader.close();
		
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		static ArrayList<Integer> computeTSP(int[][] inputArray, int nO) {
			

			N = nO;
			npow = (int) Math.pow(2, nO);
			g = new int[nO][npow];
			p = new int[nO][npow];
			d = inputArray;
			
			int i, j, k, l, m, s;

			for (i = 0; i < nO; i++) {
				for (j = 0; j < npow; j++) {
					g[i][j] = -1;
					p[i][j] = -1;
				}
			}

			// initialize based on distance matrix

			for (i = 0; i < nO; i++) {

				g[i][0] = inputArray[i][0];
			}

			int result = tsp(0, npow - 2);
			outputArray.add(0);
			getPath(0, npow - 2);
			outputArray.add(result);

		
			return outputArray;
		}

		private static int tsp(int start, int set) {

			int masked, mask, result = -1, temp;

			if (g[start][set] != -1) {
				return g[start][set];
			}

			else {

				for (int x = 0; x < N; x++) {

					mask = npow - 1 - (int) Math.pow(2, x);
					masked = set & mask;
					if (masked != set) {
						temp = d[start][x] + tsp(x, masked);
						if (result == -1 || result > temp) {
							result = temp;
							p[start][set] = x;
						}
					}
				}

				g[start][set] = result;
				return result;
			}
		}

		private static void getPath(int start, int set) {
			if (p[start][set] == -1) {
				return;
			}

			int x = p[start][set];
			int mask = npow - 1 - (int) Math.pow(2, x);
			int masked = set & mask;
			outputArray.add(x);
			getPath(x, masked);
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
			System.out.println("Splitted - " + Arrays.toString(a));
			return a;
		}

		return null;
	}
}
