/*
    Object for the hashtable
    Joni Pesonen
*/

package datastructures;

import java.lang.Integer;

public class HashTable {
    private ListNode[] hashArray = new ListNode[0];
    private int capacity;
    
    // Constructor
    
    public HashTable() {
        hashArray = new ListNode[8];
    }
    
    // Setters and getters
    
    public ListNode[] hashArray() {
        return hashArray;
    }
    
    public void hashArray(ListNode[] aa) {
        hashArray = aa;
    }
    
    public int capacity() {
        return capacity;
    }
    
    // Returns the hash
    public int hashFunction(int valueToHash, ListNode[] ar) {
        return valueToHash % ar.length;
    }
    
    public void insertItem(int key, ListNode n, ListNode[] a) {
        // Special case if the first one is free
        if (a[key] == null) {
            a[key] = n;
        }
        else {
            ListNode current = a[key];
            boolean alreadyInList = false;
            // Instead of adding duplicates, update the existing one's counter
            if (current.value() == n.value()) {
                alreadyInList = true;
                current.incrementCounter();
            }
            else {
                while((current.next() != null) && (alreadyInList == false)) {
                    current = current.next();
                    if (current.value() == n.value()) {
                        // Instead of adding duplicates, update the existing one's counter
                        alreadyInList = true;
                        current.incrementCounter();
                        // Update file flags if needed
                        if (n.foundFromA() == true)
                            current.foundFromA(true);
                        if (n.foundFromB() == true)
                            current.foundFromB(true);
                    }
                }
            }
            // Last spot special case
            if (alreadyInList == false)
                current.next(n);
        }
        capacity++;
        double loadFactor = (double)capacity / (double)a.length;
        // Rehash if load factor over 0.75
        if (loadFactor > 0.75) {
            capacity = 0;
            hashArray = rehash();
        }
    }
    
    public boolean remove(int v) {
        // Find the exact index instantly with hash
        int whereToFindIt = hashFunction(v, hashArray);
        if (hashArray[whereToFindIt] != null) {
            ListNode current = hashArray[whereToFindIt];
            // special case for the first one at the index
            if (current.value() == v) {
                ListNode assistant = current.next();
                current.next(null);
                hashArray[whereToFindIt] = assistant;
                capacity--;
                return true;
            }
            // otherwise we start looping through the list
            else {
                while (current.next() != null) {
                    if (current.next().value() == v) {
                        ListNode assist = current.next().next();
                        current.next(assist);
                        capacity--;
                        return true;
                    }
                    current = current.next();
                }
            }
        }
        return false;
    }
    
    public ListNode[] rehash() {
        int size = hashArray.length * 2;
        ListNode[] largerArray = new ListNode[size];
        for(int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                // Deep copies the existing node, since next references etc might change
                ListNode helper = new ListNode(hashArray[i]);
                helper.next(null);
                int valueForHashing = helper.value();
                int hashed = hashFunction(valueForHashing, largerArray);
                insertItem(hashed, helper, largerArray);
                
                ListNode curr = hashArray[i];
                // Looping through attached nodes at given index
                while(curr.next() != null) {
                    helper = new ListNode(curr.next());
                    helper.next(null);
                    int valueForHashing2 = helper.value();
                    int hashed2 = hashFunction(valueForHashing2, largerArray);
                    insertItem(hashed2, helper, largerArray);
                    curr = curr.next();
                }
            }
        }
        return largerArray;
    }
}