package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Merge3File {

	public static void main(String args[])

	{

		BufferedReader reader;
		int i = 0;

		try {

			// Using Buffered reader to read the content of the file - data.txt
			//reader = new BufferedReader(new FileReader("C:\\Users\\sweth\\workspace\\test\\src\\test\\data.txt"));
			 reader = new BufferedReader(new FileReader("/nfs/stak/users/jayapats/CS325/HW2/data.txt"));

			// Using Buffered writer to write the output into the file - merge.txt
			BufferedWriter bw = null;
			File file = new File("/nfs/stak/users/jayapats/CS325/HW2/merge3.txt");
			//File file = new File("C:\\Users\\sweth\\workspace\\test\\src\\test\\merge3.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			// Reading line by line
			String line = reader.readLine();

			while (line != null) {

				// Splitting the string into array of strings
				String[] splited = line.split(" ");

				Integer[] a = new Integer[splited.length];

				// Parsing the string into integers
				for (int m = 0; m < splited.length; m++) {
					a[m] = Integer.parseInt(splited[m]);
				}

				// Storing the first element as the array size
				int arr_size = a[0];

				Integer[] b = new Integer[arr_size];
				System.arraycopy(a, 1, b, 0, arr_size);

				mergeSort3Way(b);

				// Writing the sorted array into file - merge.txt
				// bw.write(Arrays.toString(b));

				for (int j = 0; j < arr_size; j++) {
					bw.write(Integer.toString(b[j]) + " ");
				}
				bw.newLine();

				i++;

				// Reading the next line
				line = reader.readLine();

			}
			reader.close();
			if (bw != null)
				bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

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
	 * Merging the splitted arrays together
	 * low to mid1
	 * mid1 to mid2 
	 * mid2 to high
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
