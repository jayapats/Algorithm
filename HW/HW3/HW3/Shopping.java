//package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shopping {
	public static void main(String[] args) {
		BufferedReader reader;
		BufferedWriter bw = null;
		int testCase = 0;
		int n = 0, f = 0;

		try {

			int[] aLine;
			//reader = new BufferedReader(new FileReader("C:\\Users\\sweth\\workspace\\test\\src\\test\\shopping.txt"));
			reader = new BufferedReader(new FileReader("shopping.txt"));

			//File file = new File("C:\\Users\\sweth\\workspace\\test\\src\\test\\results.txt");
			File file = new File("results.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			// Reading the first line
			aLine = readLine(reader);
			// System.out.println("Read Line : " + Arrays.toString(aLine));
			testCase = aLine[0];
			System.out.println("Total No of test case = "+testCase);
			
			
			for (int m = 0; m < testCase; m++) {
				int totalPrice = 0;
				// Reading the second line - No of items
				aLine = readLine(reader);
				// System.out.println("Read Line : " + Arrays.toString(aLine));
				n = aLine[0];

				int[] w = new int[n];
				int[] v = new int[n];
				// Reading n lines for their weight and value
				for (int i = 0; i < n; i++) {

					aLine = readLine(reader);
					v[i] = aLine[0];
					w[i] = aLine[1];

				}

				// Reading the next line for no. family members
				aLine = readLine(reader);
				f = aLine[0];
				int[] W = new int[f];
				int[] maxDP = new int[f];

				for (int j = 0; j < f; j++) {

					aLine = readLine(reader);
					W[j] = aLine[0];
				}

				System.out.println("Test case = " + (m+1));
				bw.write("Test case "+(m+1));
				bw.newLine();
				
				System.out.println("No of items -" + n);
				System.out.println("Price -" + Arrays.toString(v));
				System.out.println("weight -" + Arrays.toString(w));
				System.out.println("No of family members -" + f);
				System.out.println("Total Capacity -" + Arrays.toString(W));

				bw.write("Member Items :");
				bw.newLine();
				for (int k = 0; k < f; k++) {
					bw.write((k+1) +":");
					//maxDP[k] = knapsackDynamicProg(w, v, n, W[k]);					
					//System.out.println("Optimal solution for member " + (k + 1) + " is " + maxDP[k]);
					maxDP[k] = knapsackDynamicProg(w, v, n, W[k],bw);
					System.out.println("");
				}

				for (int k = 0; k < f; k++) {
					totalPrice = totalPrice + maxDP[k];
				}
				System.out.println("Total Price = " + totalPrice);
				bw.write("Total Price "+ totalPrice);
				bw.newLine();
				
				
				// Code
				/*
				 * for (int j = 0; j < arr_size; j++) { bw.write(Integer.toString(b[j])+" "); }
				 * bw.newLine();
				 */
				System.out.println("");
				System.out.println("");
				bw.newLine();
				bw.newLine();
			}
			reader.close();
			if (bw != null)
				bw.close();

		} catch (IOException e) {
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

	
	static int knapsackDynamicProg(int w[],int val[], int n,int capacity,BufferedWriter bw) throws IOException 
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

		//Backtracking and getting the item position
		int result = M[n][capacity]; 
		int res1 = M[n][capacity]; 
		System.out.println("Optimal solution  = "+res1); 
		System.out.println("Items included : ");
		totW = capacity; 
		int x=0;
		for (i = n; i > 0 && res1 > 0; i--) { 
			
			if (res1 == M[i - 1][totW]) 
				continue; 
			else { 	
				p=i;
				//System.out.print(i + " "); 
				res1 = res1 - val[i - 1]; 
				totW = totW - w[i - 1]; 
			}
			
			item.add(p);
		}  
		
		item.sort(null);
		System.out.println(item + " ");
				
		//bw.write(item.toString().replace("[","").replace("]",""));
		bw.write(item.toString().replace("[", " ").replace("]", " ").replace(",", ""));
		bw.newLine();
		return result;
	
	}
	

}
