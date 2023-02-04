# DNA Analysis
By: Kha Tran & Nolan Sailors
 at Creighton University CSC 321 (Spring 2023).

# Purpose
This project read a txt file database of STRs DNA and names with specific number of DNA type in a row. It will then ask user for their input of a DNA Sequence string to compare against the DNA Database. If user's input has the exact match with 1 or more people in the Database it will then inform who has the exact match. Or else, it will inform who has the closest match the user's input DNA Sequence.

# Usage
To be able to run the code. Type src/ + txt file that would like to read.
In this case after Scanner run "What file has the DNA database?".
Type in ```src/smallSample.txt```

# Example of Exact Match and Closest Match
```
What file has the DNA database?
src/smallSample.txt
Enter the DNA sequence or Q to quit:
AGATAGATAGATAGATAGATAATGAATGAATGAATGAATGAATGAATGTATCTATCTATCTATCTATCTATCTATC
The sequence matches NO MATCH.
Closest Matches are: 
Alice
Bob
With 1 STRs in common
```
```
Enter the DNA sequence or Q to quit: 
AGACGGGTTACCATGACTATCTATCTATCTATCTATCTATCTATCTATCACGTACGTACGTATCGAGATAGATAGATAGATAGATCCTCGACTTCGATCGCAATGAATGCCAATAGACAAAA
The sequence matches Alice.
```
