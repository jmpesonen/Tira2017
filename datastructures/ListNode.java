/*
    Object for the nodes used in this program
    Joni Pesonen
*/

package datastructures;

public class ListNode {
    private int value;
    private ListNode next;
    
    private boolean foundFromA = false;
    private boolean foundFromB = false;
    private int fromAorB;
    private int counter;
    private int foundFromLine;
    
    // Constructors
    
    public ListNode() {
        next = null;
    }
    
    public ListNode(int v, int f) {
        value = v;
        next = null;
        foundFromLine = f;
        counter = 1;
    }
    
    // Deep copy constructor
    
    public ListNode(ListNode l) {
        if (l instanceof ListNode) {
            value(l.value());
            next(l.next());
            foundFromA(l.foundFromA());
            foundFromB(l.foundFromB());
            counter(l.counter());
            foundFromLine(l.foundFromLine());
        }
    }
    
    // setters
    
    public void value(int va) {
        value = va;
    }
    
    public void next(ListNode n) {
        next = n;
    }
    
    public void foundFromA(boolean foundA) {
        foundFromA = foundA;
    }
    
    public void foundFromB(boolean foundB) {
        foundFromB = foundB;
    }
    
    public void counter(int c) {
        counter = c;
    }
    
    public void foundFromLine(int fo) {
        foundFromLine = fo;
    }
    
    // getters
    
    public int value() {
        return value;
    }
    
    public boolean foundFromA() {
        return foundFromA;
    }
    
    public boolean foundFromB() {
        return foundFromB;
    }
    
    public int counter() {
        return counter;
    }
    
    public ListNode next() {
        return next;
    }
    
    public int foundFromLine() {
        return foundFromLine;
    }
    
    // other functions
    
    public void incrementCounter() {
        counter++;
    }
}