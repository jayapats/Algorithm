#include <sstream>
#include <fstream>
#include <ostream>
#include <string>
#include <iostream>
#include <iomanip>
#include <vector>

using namespace std;

//raw city data ie cityNum, x coordinate, and y coordinate
struct city {
	int cityNum;
	int xCoord;
	int YCoord;
	int key = INFINITY;
	bool visited;
	vector<int> neighbors;
};

//Takes two inputs, The original city struct and an empty struct to generate the MST with
vector<city> primCity(vector<city> &citiesParam, vector<city> &primMST) {
	vector<city>::iterator itCities = citiesParam.begin();
	vector<city>::iterator itPrim = primMST.begin();
	

	return primMST;
 }

// make the program with g++ main.cpp -o main
// The program accept command line argument as such
// main.exe file_input.txt
// It will then output a file of file_input.txt.tour
int main(int argc, char* argv[]) {

	/* used for testing what the arguments passed where.
	cout << "args: " << argc << endl;
	for (int i = 0; i < argc; i++) {
		cout << argv[i] << endl;
	}
	*/

	//verifying there was a file passed in. 
	ifstream infile(argv[1]);
	if (!infile) {
		cout << "Error opening file." << endl;
		return 1;
	}

	//Creating the output file by adding the .tours at the end
	string outputFile = argv[1];
	outputFile += ".tour";
	//cout << outputFile << endl;
	ofstream outData(outputFile);
	if (!outData) {
		cout << "Error opening file." << endl;
		return 1;
	}

	//vector of city in order to manage the data
	vector<city> cities;

	//Works through the file and adds all the cities to a vector
	//Which will be used for the TSP
	while (!infile.eof()) {
		city temp;
		string line;
		getline(infile, line);
		istringstream intstr(line);
		
		intstr >> temp.cityNum;
		intstr >> temp.xCoord;
		intstr >> temp.YCoord;
		cities.push_back(temp);
	}


	//Implementation should begin here
	vector<city> cityMST;
	primCity(cities, cityMST);


	//Implementation should end here


	//Format this oncewe finish implementation
	//Can be used to print data back out to the new tour file. 
	if (outData.is_open()) {
		
		//temp placeholder for the eventual best tour number
		outData << "Temp solution: ###" << endl;

		//This loop can be used on the resulting optimal tour that we generate
		for (vector<city>::iterator it = cities.begin(); it != cities.end(); it++) {
			outData << (*it).cityNum << " " << (*it).xCoord << " " << (*it).YCoord << endl;
		}
	}

	return 0;
}