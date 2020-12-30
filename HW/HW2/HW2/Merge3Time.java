package test;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Merge3Time {
	public static void main(String[] args) {

		for (int n = 0; n < 100000; n = n + 10000) {
			
			for (int k = 1; k<=4; k++) {
				long timeElapsed = 0;
				long startTime = 0;
				long endTime = 0;
				
				startTime = System.currentTimeMillis();

				//Date date = new Date(); // Working------------------

				int[] aList = generateRandomNo(n);

				Integer[] b = new Integer[aList.length];
				int i = 0;
				for (int value : aList) {
					b[i++] = Integer.valueOf(value);
				}

				mergeSort3Way(b);

				// System.out.println("Sorted - " +Arrays.toString(b));

				 endTime = System.currentTimeMillis();
				//Date date2 = new Date(); // Working------------------
			     timeElapsed = endTime - startTime;
				// System.out.println(n+" "+timeElapsed);
				//runTime = date2.getTime() - date.getTime();
				
				System.out.print(n); // Printing the value of n
				//System.out.print(" " + timeElapsed); // Printing the runtime value in Milliseconds
				System.out.print(" Run "+ k +" : "+timeElapsed);
				//System.out.println(" RunTime "+ k +" : "+runTime);
			
				//total = total + timeElapsed;
			}
			//average = total / 3;
			//System.out.println(" average " + average);
			System.out.println(" ");
		}
	}
	

	// Method to generate Random Numbers
	private static int[] generateRandomNo(int n) {
		Random r = new Random();
		int[] randomNo = r.ints(n, 0, 10000).toArray();
		// int randomNumber = r.ints(1, 0, 11).findFirst().getAsInt();
		// System.out.println("Random No generated - " + Arrays.toString(randomNo));
				
		return randomNo;
	}

	// Helper function

	public static void mergeSort3Way(Integer[] a)

	{

		// Checking if the array is empty

		if (a == null)

			return;

		// Storing the array in another array b

		Integer[] b = new Integer[a.length];

		for (int i = 0; i < b.length; i++)

			b[i] = a[i];

		// Calling the 3 way merge sort function

		mergeSort3(b, 0, a.length, a);
		// Copying to the original array

		for (int i = 0; i < b.length; i++)

			a[i] = b[i];

	}

	/*
	 * Function to divide the array into 3 and sort it recursively
	 */

	public static void mergeSort3(Integer[] a, int low, int high, Integer[] f)

	{

		// If array size is 1 then do nothing

		if (high - low < 2)

			return;

		// Splitting array into 3 parts

		int mid1 = low + ((high - low) / 3);

		int mid2 = low + 2 * ((high - low) / 3) + 1;

		// Sorting 3 arrays recursively

		mergeSort3(f, low, mid1, a);

		mergeSort3(f, mid1, mid2, a);

		mergeSort3(f, mid2, high, a);

		// Merging the sorted arrays

		merge(f, low, mid1, mid2, high, a);

	}

	/*
	 * Merging the splitted arrays together low to mid1 mid1 to mid2 mid2 to high
	 */
	public static void merge(Integer[] a, int low,

			int m1, int m2, int high,

			Integer[] f)

	{

		int i = low, j = m1, p = m2, l = low;

		while ((i < m1) && (j < m2) && (p < high))

		{

			if (a[i] < (a[j])) {

				if (a[i] < a[p])

					f[l++] = a[i++];

				else

					f[l++] = a[p++];

			}

			else

			{

				if (a[j].compareTo(a[p]) < 0)

					f[l++] = a[j++];

				else

					f[l++] = a[p++];

			}

		}

		// comparing first and second ranges

		while ((i < m1) && (j < m2))

		{

			if (a[i].compareTo(a[j]) < 0)

				f[l++] = a[i++];

			else

				f[l++] = a[j++];

		}

		// comparing second and third ranges

		while ((j < m2) && (p < high))

		{

			if (a[j].compareTo(a[p]) < 0)

				f[l++] = a[j++];

			else

				f[l++] = a[p++];

		}

		// comparing first and third range

		while ((i < m1) && (p < high))

		{

			if (a[i].compareTo(a[p]) < 0)

				f[l++] = a[i++];

			else

				f[l++] = a[p++];

		}

		// copying from first range

		while (i < m1)

			f[l++] = a[i++];

		// copying from second range

		while (j < m2)

			f[l++] = a[j++];

		// copying from the third range

		while (p < high)

			f[l++] = a[p++];

	}

}
