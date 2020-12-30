//package test;

import java.util.ArrayList;
import java.util.Random;

public class Knapsack {
	
	public static void main(String[] args) {

		final int Capacity = 100;
		
		for (int n = 10; n <= 100 ; n = n + 10) {
		
		long timeElapsed = 0;
		long timeElapsedDP = 0;
		long startTime = 0;
		long endTime = 0;
		long startTimeD = 0;
		long endTimeD = 0;
		
		int maxR = 0;
		int maxDP = 0;

		int[] w = new int[n];
		int[] v = new int[n];
		
		w = generateRandomNo(n);
		v = generateRandomNo(n);
		
		startTime = System.currentTimeMillis();

		// final KnapsackR knapR = new KnapsackR();
		maxR = KnapsackRecursive(w, v, n, Capacity);
		endTime = System.currentTimeMillis();

		startTimeD = System.currentTimeMillis();
		maxDP = knapsackDynamicProg(w, v, n, Capacity);
		endTimeD = System.currentTimeMillis();

		timeElapsed = endTime - startTime;
		timeElapsedDP = endTimeD - startTimeD;

		// Printing the runtime value in Milliseconds
		System.out.print("N=" + n + " W=" + Capacity + " Rec time= " + timeElapsed + " DP time= " + timeElapsedDP+ " max Rec=" + maxR+ " max DP=" + maxDP);
		// System.out.println(" RunTime "+ k +" : "+runTime);

		System.out.println(" ");
		}
	}

	public static int KnapsackRecursive(int[] w, int[] v, int n, int Capacity) {
		if (n <= 0) {
			return 0;
		} else if (w[n - 1] > Capacity) {
			return KnapsackRecursive(w, v, n - 1, Capacity);
		} else {
			return Math.max(KnapsackRecursive(w, v, n - 1, Capacity),
					v[n - 1] + KnapsackRecursive(w, v, n - 1, Capacity - w[n - 1]));
		}
	}
	
	static int knapsackDynamicProg(int w[],int val[], int n,int capacity)  
	{ 
		int i, totW, p =0; 
		ArrayList<Integer> item = new ArrayList<Integer>(); 
		int M[][] = new int[n + 1][capacity + 1]; 

		//building the table and storing it in the 2D array M
		for (i = 0; i <= n; i++) { 
			
			for (totW = 0; totW <= capacity; totW++) { 
				
				if (i == 0 || totW == 0) 
				{
					M[i][totW] = 0; 
				}
				else if (w[i - 1] <= totW) {
					
					M[i][totW] = Math.max(val[i - 1] +  M[i - 1][totW - w[i - 1]], M[i - 1][totW]); 
					
				}
				else {
					M[i][totW] = M[i - 1][totW]; }
			} 
		} 

		int result = M[n][capacity]; 
		return result;
	
	}
	
	private static int[] generateRandomNo(int n) {
		Random r = new Random();
		int[] randomNo = r.ints(n, 0, 100).toArray();
		// int randomNumber = r.ints(1, 0, 11).findFirst().getAsInt();
		// System.out.println("Random No generated - " + Arrays.toString(randomNo));
				
		return randomNo;
	}
	
}
