/*
    Tira2017 coursework
    Joni Pesonen
*/

import datastructures.*;

// These were given imports to use
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.Formatter;
import java.util.Scanner;

public class Tira2017 {
    // Used for formatting in and, or, xor functions
    private int longestValue;
    private int mostLines;
    
    // Reads given file
    private void readInput(HashTable h, String fileName) {
        String line;
        String fileA = "setA.txt";
        String fileB = "setB.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));            
            // this follows the line number we are currently reading
            int i = 1;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] values = line.split("\n");
                
                // if the length of the read value is longer than of the current longest, write it up
                int lengthOfValue = values[0].length();
                if (lengthOfValue > longestValue)
                    longestValue = lengthOfValue;
                
                // creates new ListNode
                int convertedValue = Integer.parseInt(values[0]);
                int hashedValue = h.hashFunction(convertedValue, h.hashArray());
                ListNode ln = new ListNode(convertedValue, i);
                
                // additional flags to help us by following from which file the value came from
                if (fileName.equals(fileA)) {
                    ln.foundFromA(true);
                }
                else if (fileName.equals(fileB)) {
                    ln.foundFromB(true);
                }
                
                h.insertItem(hashedValue, ln, h.hashArray());
                i++;
            }
            // we use the max line number for formatting, there cannot be more values than there are lines
            if (String.valueOf(i).length() > mostLines)
                mostLines = String.valueOf(i).length();
            br.close();
        }
        catch(IOException e) {
            System.out.println("File not found.");
        }
    }
    
    // Writes output of given ListNode array
    private void writeOutput(Tira2017 t, ListNode[] hashArrA) {
        try {
            // Gives reference to writer object as well
            BufferedWriter bw = new BufferedWriter(new FileWriter("and.txt"));            
            t.andFunction(hashArrA, bw, t);
            bw.flush();
            
            bw = new BufferedWriter(new FileWriter("or.txt"));
            t.orFunction(hashArrA, bw, t);
            bw.flush();
            
            bw = new BufferedWriter(new FileWriter("xor.txt"));
            t.xorFunction(hashArrA, bw, t);
            bw.close();
        }
        catch(IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("Writing file...");
    }
    
    // Writes to and.txt the values that are both in A and B with given writer object
    private void andFunction(ListNode[] a, BufferedWriter b, Tira2017 h) throws IOException {
        int j = 0;
        for(int i = 0; i < a.length; i++) {
            if (a[i] != null) {
                ListNode current = a[i];
                if (current.foundFromA() == true && current.foundFromB() == true) {
                    String s = String.format("%"+h.longestValue+"d  %"+h.mostLines+"d", a[i].value(), a[i].foundFromLine());
                    b.write(s);
                    b.newLine();
                    j++;
                }
            }
        }
        System.out.println("and: " + j);
    }
    
    // Writes to or.txt the values that are at least in A or B with given writer object
    private void orFunction(ListNode[] a, BufferedWriter b, Tira2017 h) throws IOException {
        int j = 0;
        for(int i = 0; i < a.length; i++) {
            if (a[i] != null) {
                ListNode current = a[i];
                String s = String.format("%"+h.longestValue+"d  %"+h.mostLines+"d", a[i].value(), a[i].counter());
                b.write(s);
                b.newLine();
                j++;                
            }
        }
        System.out.println("or: " + j);
    }
    
    // Writes to xor.txt the values that are either in A or B with given writer object
    private void xorFunction(ListNode[] a, BufferedWriter b, Tira2017 h) throws IOException {
        int j = 0;
        for(int i = 0; i < a.length; i++) {
            if (a[i] != null) {
                ListNode current = a[i];
                if (current.foundFromA() ^ current.foundFromB()) {
                    if (current.foundFromA()) {
                        String s = String.format("%"+h.longestValue+"d  1", a[i].value());
                        b.write(s);
                        b.newLine();
                        j++;
                    }
                    else {
                        String s = String.format("%"+h.longestValue+"d  2", a[i].value());
                        b.write(s);
                        b.newLine();
                        j++;
                    }
                }
            }
        }
        System.out.println("xor: " + j);
    }
    
    // This function unpacks the nodes and returns an array in which one index will correspond to one value only
    private ListNode[] createArrayForSorting(ListNode[] a, HashTable h) {
        ListNode[] assistantArray = new ListNode[h.capacity()];
        int j = 0;
        for(int i = 0; i < a.length; i++) {
            if (a[i] != null) {
                ListNode current = new ListNode(a[i]);
                assistantArray[j] = current;
                j++;
                while(current.next() != null) {
                    current = new ListNode(current.next());
                    assistantArray[j] = current;
                    j++;
                }
            }
        }
        return assistantArray;
    }
    
    // Bubblesort to sort given array in place, smallest value first
    private void sortFunction(ListNode[] a) {
        boolean swapped = true;
        int j = 0;
        ListNode temp = null;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < a.length - j; i++) {
                if (a[i + 1] != null) {
                    if (a[i].value() > a[i + 1].value()) {
                        temp = a[i];
                        a[i] = a[i + 1];
                        a[i + 1] = temp;
                        swapped = true;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Tira2017 ht = new Tira2017();
        
        // Own HashTable objects for both files
        HashTable hashtableA = new HashTable();
        HashTable hashtableB = new HashTable();        

        ht.readInput(hashtableA, "setA.txt");
        ht.readInput(hashtableB, "setB.txt");
        
        // Retrieving both hashtables
        ListNode[] arrA = hashtableA.hashArray();
        ListNode[] arrB = hashtableB.hashArray();
        
        // Reading user input for any removable values
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter removable value (empty for none):");
        String userInput = sc.nextLine();
        while (!userInput.equals("")) {
            // Removing the value given if we find it
            int removable = Integer.parseInt(userInput);
            boolean didWeFindIt = hashtableA.remove(removable);
            boolean didWeFindItB = hashtableB.remove(removable);
            if (didWeFindIt || didWeFindItB)
                System.out.println("Removed " + removable + ".");
            else
                System.out.println("Did not find " + removable + " to remove.");
            System.out.println("Enter removable value (empty for none):");
            userInput = sc.nextLine();
        }
        
        ListNode[] sortThis = ht.createArrayForSorting(arrA, hashtableA);        
        ListNode[] sortThisB = ht.createArrayForSorting(arrB, hashtableB);
        
        // This tracks the first free index in our combined array
        int firstFreeIndex = 0;
        
        // Has exactly the number of indexes as there are items in A and B arrays
        ListNode[] combinedArray = new ListNode[sortThis.length + sortThisB.length];
        
        // Unpack array A into our new array
        for(int i = 0; i < sortThis.length; i++) {
            combinedArray[i] = sortThis[i];
            firstFreeIndex++;
        }
        
        // Unpack array B into our new array
        for(int i = 0; i < sortThisB.length; i++) {
            boolean alreadyThere = false;
            for(int j = 0; j < firstFreeIndex; j++) {
                if (combinedArray[j] != null) {
                    // If values match, instead of adding duplicate we increase the existing value
                    // and update the foundFromB flag
                    if (combinedArray[j].value() == sortThisB[i].value()) {
                        combinedArray[j].incrementCounter();
                        combinedArray[j].foundFromB(true);
                        alreadyThere = true;
                    }
                }
            }
            // Special case for last index
            if (alreadyThere == false) {
                combinedArray[firstFreeIndex] = sortThisB[i];
                firstFreeIndex++;
            }
        }
        
        // Sort combined array and write it to files
        ht.sortFunction(combinedArray);
        ht.writeOutput(ht, combinedArray);
    }
}