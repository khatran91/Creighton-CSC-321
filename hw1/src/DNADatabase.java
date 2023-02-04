import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DNADatabase {
	static Scanner sc = new Scanner(System.in);
	private static List<DNAData> dnaData;
	private static String dnaSequence = "";

	/**
	 * Creates a database of DNA profiles. The profiles are stored as the users name
	 * and their counts of the various short tandem repeats (STRs) that are
	 * included.
	 * 
	 * The file will be organized such that the first line is a header row that will
	 * start with name and then list the STR that the database includes separated by
	 * commas (e.g. name,AGAT,AATG,TATC). Each line after that will have the data
	 * for a single profile (e.g. Alice,28,42,14 which indicates that the profile
	 * stored is for Alice and she has 28 AGATs in a row, 42 AATGs in a row and 14
	 * TATCs in a row).
	 * 
	 * @param filename the file that stores the database
	 */

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// read file and store data to array list (dnaData)
		getFile();

		// Loop program until user enter Q to quit
		while (!dnaSequence.equalsIgnoreCase("Q")) {
			// Ask for DNA string to match
			getSequence();

			// Match sequence entered
			if (!findExactMatch()) {
				findClosestMatches();
			}
		}
	}

	/**
	 * This method is used to load data from the file into a list of DNAdata objects
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void getFile() throws FileNotFoundException, IOException {
		boolean fileNotFound = true;
		while (fileNotFound) {
			System.out.println("What file has the DNA database?");
			String filename = sc.nextLine();
			String line = "";
			List<List<String>> dnaFileData = new ArrayList<>();
			List<String> dnaFileHeaderData = new ArrayList<>();

			try {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				while ((line = br.readLine()) != null) {
					String[] values = line.split(",");
					dnaFileData.add(Arrays.asList(values));
				}
				// Create list of DNA Match Data objects
				dnaData = new ArrayList<>();
				dnaFileHeaderData = dnaFileData.get(0); // gets list of first row

				for (int i = 0; i < dnaFileData.size(); i++) {
					if (i > 0) {
						DNAData dna = new DNAData(); // create new object, set name to name
						dna.name = dnaFileData.get(i).get(0);

						// Get list of STRs from file header
						for (int j = 1; j < dnaFileHeaderData.size(); j++) { // goes through DNA strings
							DNAStrData dnaStrData = new DNAStrData();
							dnaStrData.str = dnaFileHeaderData.get(j);
							dnaStrData.strCount = Integer.parseInt(dnaFileData.get(i).get(j));
							dnaStrData.strString = buildDNAString(dnaStrData.str, dnaStrData.strCount); // calls method
							dna.dnaStrData.add(dnaStrData); // add STR and loop through
						}

						dnaData.add(dna); // add person and all information to list
					}
				}
				fileNotFound = false;
				br.close();
			} catch (FileNotFoundException e) {
				fileNotFound = true;
				System.out.println("File not found. Try again.");
			} catch (IOException e) {
				System.out.println("Unable to read file. Try again.");
			}
		}
	}

	/**
	 * This method asks the user to enter the dna sequence
	 */
	public static void getSequence() {
		System.out.println("Enter the DNA sequence or Q to quit: ");
		dnaSequence = sc.nextLine();
		// Quit program is the user enters Q
		if (dnaSequence.equalsIgnoreCase("Q")) {
			System.out.println("Program ended.");
			System.exit(0);
		}
	}

	/**
	 * Finds the profile in the database that is an exact match for the counts of
	 * the STRs in the provided DNA sequence
	 * 
	 * @param dnaSequence the sequence of the DNA as a string of As, Gs, Ts, and Cs
	 * @return the name of the profile that matches all the counts of STRs or the
	 *         text "No Match" if no exact match is found
	 */
	public static boolean findExactMatch() {

		// Find an exact match
		for (int i = 0; i < dnaData.size(); i++) {
			DNAData dna = dnaData.get(i);
			int totalCount = dna.dnaStrData.size();
			int matchCount = matchCount(dna);

			// Get exact matches by comparing the total number of STRs to number of matches
			if (totalCount == matchCount) {
				System.out.println("The sequence matches " + dna.name + ".");
				return true;
			}

		}
		System.out.println("The sequence matches NO MATCH.");
		return false;
	}

	/**
	 * Finds the profile(s) in the database that are the closest match for the DNA
	 * sequence. The closest match is determine by counting the number of STR counts
	 * that are the same and selecting the profile that match the largest number of
	 * STR matches.
	 */
	public static void findClosestMatches() {
		int highestMatchCount = 0;

		for (int i = 0; i < dnaData.size(); i++) {
			DNAData dna = dnaData.get(i);
			int matchCount = matchCount(dna);

			if (matchCount > highestMatchCount) {
				highestMatchCount = matchCount;
			}
		}
		
		if (highestMatchCount > 0) {
			System.out.println("Closest Matches are: ");
			for (int i = 0; i < dnaData.size(); i++) {
				DNAData dna = dnaData.get(i);
				if (matchCount(dna) == highestMatchCount) {

					System.out.println(dna.name);
				}
			}

			System.out.println("With " + highestMatchCount + " STRs in common");
		}
		
		return;
	}

	/**
	 * This method build the entire DNA string for a given STR and count
	 *
	 * @param str      This is the first parameter that is a String for this method
	 * @param strCount This is the second parameter that is an integer for this
	 *                 method
	 * @return String This returns a concatenated string that is built by looping
	 *         through the count passed in
	 */
	public static String buildDNAString(String str, int strCount) {
		String dnaString = "";

		for (int i = 0; i < strCount; i++) {
			dnaString += str;
		}

		return dnaString;
	}

	/**
	 * This method is used to get a count of STR matches for a given DNAData object
	 * 
	 * @param dna This is the only parameter for the matchCount Method
	 * @return int This returns the number of matches from the entered dnaSequence
	 */
	public static int matchCount(DNAData dna) {
		int cnt = 0;

		for (DNAStrData strData : dna.dnaStrData) {
			if (isStrMatch(strData.strString)) {
				cnt += 1;
			}
		}

		return cnt;
	}

	/**
	 * This method is used to match a given DNA string to the DNA sequence entered
	 * by the user This method uses the String.indexOf() method to fin the index of
	 * the DNA to see if there is an exact match We first tried the
	 * String.Contains() method but it returned mixed results for finding an exact
	 * match
	 * 
	 * @param str This is the only parameter for the isStrMatch method
	 * @return boolean This returns true if there is a DNA match and false if not
	 */
	public static boolean isStrMatch(String str) {

		int num = dnaSequence.indexOf(str);
		int cnt = 0;
		while (num >= 0) {
			num = dnaSequence.indexOf(str, num + 1);
			cnt += 1;
		}

		return (cnt == 1);
	}

	/**
	 * This class represents a DNAdata object that has a person's name and a list of
	 * DNAStrData objects Used to map the file data to a class object
	 */
	static class DNAData {

		String name;
		List<DNAStrData> dnaStrData;

		DNAData() {
			name = "";
			dnaStrData = new ArrayList<>();
		}
	}

	/**
	 * This class represents a DNAStrData object that has a single DNA STR, number
	 * of occurrences, and the actual DNA string Used to map the file data to a
	 * class object
	 */
	static class DNAStrData {

		String str;
		int strCount;
		String strString;
	}
}
