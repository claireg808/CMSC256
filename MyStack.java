/**
 * Claire Gillaspie
 * Project 4
 * MyStack
 * CMSC256-901
 * Spring 2023
 * This class implements StackInterface and builds a Linked Stack
 * isBalanced() method checks if delimiter tags are balanced in a file, returns true if so
 */
package cmsc256;

import bridges.base.SLelement;

import java.io.*;
import java.util.*;

public class MyStack<E> implements StackInterface<E> {
    private SLelement<E> top;
    private int size;

    /**
     * default constructor
     */
    public MyStack() {
        top = null;
        size = 0;
    }

    /**
     * Adds a new entry to the top of this stack.
     *
     * @param newEntry an object to be added to the stack.
     */
    @Override
    public void push(E newEntry) {
        if (newEntry == null) {
            throw new IllegalArgumentException();
        }
        top = new SLelement<E>(newEntry, top); //set top to point to the new node
        size++;
    }

    /**
     * Removes and returns this stack's top entry.
     * @return the object at the top of the stack.
     * @throws java.util.EmptyStackException if the stack is empty.
     */
    @Override
    public E pop() {
        if (top == null) {
            throw new java.util.EmptyStackException();
        }
        E it = top.getValue();
        top = top.getNext();
        size--;
        return it;
    }

    /**
     * Retrieves this stack's top entry.
     * @return the object at the top of the stack.
     * @throws java.util.EmptyStackException if the stack is empty.
     */
    @Override
    public E peek() {
        if (top == null) {
            throw new java.util.EmptyStackException();
        }
        return top.getValue();
    }

    /**
     * Detects whether this stack is empty.
     * @return True if the stack is empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all entries from this stack.
     */
    @Override
    public void clear() {
        top = null;
        size = 0;
    }

    /**
     * Detects whether an html file is tag balanced
     * @param webpage the HTML webpage code to be analyzed
     * @return True if the tags are balanced
     */
    public static boolean isBalanced(File webpage) throws FileNotFoundException {
        //Create new scanner object of webpage input
        int openLines = 0;
        Scanner input = new Scanner(webpage);
        String line;
        MyStack<String> delimiters = new MyStack<>();
        //Check for starting bracket (<)
        while (input.hasNextLine()) {
            line = input.nextLine();
            String[] parsedLine = line.split("<");
            for (String bracket : parsedLine) {
                try {
                    //Find starting bracket
                    if (bracket.contains(">") && !bracket.contains("/")) {
                        openLines++;
                        //find end delimiter
                        String open = bracket.substring(0, bracket.indexOf(">"));
                        delimiters.push(open);
                    }
                    //When the first ending bracket (>) is encountered
                    else if (bracket.contains("/") && bracket.contains(">")) {
                        String close = bracket.substring(1, bracket.indexOf(">"));
                        //pop the top element off the stack
                        Object value = delimiters.pop();
                        //check if the string complements the top element
                        if (!(value.equals(close))) {
                            return false;
                        }
                    }
                } catch (Exception E){
                    return false;
                }
            }
        }
        //Continue to do this, and each element in the stack should consecutively match with its pair & pop off
        //If this is successful, the stack size is 0. Otherwise, there is an error in the order of the brackets
        return delimiters.isEmpty() && openLines != 0;
    }
}