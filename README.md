# Tira2017
Performs logical operations on given text files with the help of a custom hashtable and node. The results of each required logical operation (AND, OR, XOR) are saved into correspondingly named text file.

This was the coursework of the "Data structures" course at University of Tampere.

Result text files have two columns in them. The contents of those columns are as follows:
  
* and.txt
  * The first column has each unique number from the input files
  * The second column tells the line in which that number first appeared in the first input file

* or.txt
  * The first column has each unique number from the input files
  * The second column tells how many times that number appeared in the input files
  
* xor.txt
  * The first column has each unique number from the input files
  * The second column tells in which file that number occurred (1 for the first one, 2 for the second one)
