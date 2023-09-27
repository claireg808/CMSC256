/**
 * Claire Gillaspie
 * Project 5
 * Project5
 * CMSC256-901
 * Spring 2023
 * This class utilizes three methods to build a parse tree, evaluate a parse tree, and return the
 * equation stored in a parse tree. Uses the BRIDGES object BinTreeElement
 */
package cmsc256;

import bridges.base.BinTreeElement;
import java.util.*;
import java.util.regex.*;
import java.lang.*;
import bridges.connect.*;
import bridges.validation.RateLimitException;

import java.io.IOException;

public class Project5 {
    public static void main(String[] args){
//        String ex1 = "( ( 7 + 3 ) * ( 5 - 2 ) )";
//        BinTreeElement<String> parseTree1 = buildParseTree(ex1);
//        System.out.println("Num leaves should be: 4. Actually was: " + getNumberOfLeaves(parseTree1));
//        double answer1 = evaluate(parseTree1);
//        System.out.println(answer1);
//        System.out.println(getEquation(parseTree1));

//        String ex2 = "( ( 10 + 5 ) / 0 )";
//        BinTreeElement<String> parseTree2 = buildParseTree(ex2);
//        System.out.println("Num leaves should be: 3. Actually was: " + getNumberOfLeaves(parseTree2));
//        System.out.println("Answer should be 45. Actually was: " + evaluate(parseTree2));
//        System.out.println("Equation should be: ( ( 10 + 5 ) * 3 ). Actually was: " + getEquation(parseTree2));


        //visualize on bridges
        Bridges bridges = new Bridges(3, "gillaspiecl", "304035823200");
        bridges.setTitle("tree");
        String ourExpression = "( ( 10 + 5 ) * 3 )";
        bridges.base.BinTreeElement<String> ourRoot = buildParseTree(ourExpression);
        bridges.setDataStructure(ourRoot);
        try {
            bridges.visualize();
        } catch (IOException E){
            System.out.println("Error");
        } catch (RateLimitException E){
            System.out.println("Rate error");
        }
    }

    /**
     * check method, counts number of roots
     * @param root
     * @return
     */
    private static int getNumberOfLeaves(BinTreeElement<String> root){
        if (root == null) { return 0; }
        if (root.getLeft() == null && root.getRight() == null) { return 1; }
        return getNumberOfLeaves(root.getLeft()) + getNumberOfLeaves(root.getRight());
    }
    /**
     * The method accepts a mathematical expression as a string parameter and returns the root of the parse tree.
     * Each token in the mathematical expression is separated by a white-space character
     * for example, “( ( 7 + 3 ) * ( 5 – 2 ) )”
     */
    public static bridges.base.BinTreeElement<String> buildParseTree(String expression){
        //breakup input string expression into list of tokens
        String[] parsedLine = expression.split(" ");
        //create the root of the expression tree that will be returned by this method
        bridges.base.BinTreeElement<String> root = new BinTreeElement<>("root", "");
        //reference to the current node
        bridges.base.BinTreeElement<String> current = root;
        //create stack
        Stack<BinTreeElement<String>> treeStack = new Stack<BinTreeElement<String>>();
        for (String token : parsedLine) {
            //if (current token . equals ("(")
            if (token.equals("(")) {
                //add a new node as the left child of the current node
                current.setLeft(new BinTreeElement<>());
                //push current onto stack so we can back track
                treeStack.push(current);
                //move to the new node
                current = current.getLeft();
            }
            //if (current token . equals (")") - making a subtree
            else if (token.equals(")")){
                if (!treeStack.isEmpty()) {
                    //go to the parent of the current node
                    current = treeStack.pop();
                    root = current;
                }
            }
            //if (current token is in operator list {'+', '-', '/', '*'}
            else if (isOperator(token)){
                //set the root value of the current node to the operator represented by the current token
                current.setValue(token);
                current.setRight(new BinTreeElement<>(""));
                treeStack.push(current);
                current = current.getRight();
            }
            //else, the current token is a number
            else if (isNumeric(token)){
                current.setValue(token);
                current = treeStack.pop();
            }
            else{ //if the token is anything else
                throw new IllegalArgumentException();
            }
        }
        return root;
    }

    /**
     * checks if input is an operator +, -, /, *
     * @param x token to be checked
     * @return true if it is an operator
     */
    private static boolean isOperator(String x){
        if (x.equals("+")) { return true; }
        else if (x.equals("-")) { return true; }
        else if (x.equals("/")) { return true; }
        else return x.equals("*");
    }

    /**
     * checks if input is a number
     * @param strNum token to be checked
     * @return true if the input matches a number
     */
    private static boolean isNumeric(String strNum){
        if (strNum == null){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(strNum);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    /**
     * @param tree BinTreeElement to be converted
     * @return BinTreeElement to double
     */
    private static double toDouble(bridges.base.BinTreeElement<String> tree){
        String value = tree.getValue();
        return Double.parseDouble(value);
    }

    /**
     * This method evaluates a parse tree by recursively evaluating each subtree
     */
    public static double evaluate(bridges.base.BinTreeElement<String> tree){
        if (tree == null) { return 0; }
        //recursively evaluate each subtree
        //define the base case:
        if (tree.getRight() == null && tree.getLeft() == null) { //if the current node is a leaf
            return toDouble(tree);
        }//recursive step:
        //store value: call evaluate on the left children of current node
        double leftNum = 0;
        double rightNum = 0;
            leftNum = evaluate(tree.getLeft());
            //store value: call evaluate on the right children of the current node
            rightNum = evaluate(tree.getRight());
        //put the results of both recursive calls together:
        //ID the operator stored in the parent node & perform appropriate calculation
        String operator = tree.getValue();
        if (operator.equals("+")) {
            return leftNum + rightNum;
        } else if (operator.equals("-")) {
            return leftNum - rightNum;
        } else if (operator.equals("*")) {
            return leftNum * rightNum;
        } else if (operator.equals("/")) {
            if (rightNum != 0){ //cannot divide by zero
                return leftNum / rightNum;
            } else {
                throw new ArithmeticException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * The method accepts the root of the parse tree parameter and returns a mathematical expression as a string.
     */
    public static String getEquation(bridges.base.BinTreeElement<String> tree){
        //inorder traversal:
        if (tree == null) { return ""; }
        else if (tree.getLeft() == null && tree.getRight() == null){
            return tree.getValue();
        }
        else if (isNumeric(tree.getLeft().toString()) && isNumeric(tree.getRight().toString())) {
            return "( " + getEquation(tree.getLeft()) + " " + tree.getValue() + " " + getEquation(tree.getRight()) + " )";
        }
        //add opening and closing parenthesis to retain the meaning of the equation
        return getEquation(tree.getLeft()) + " " + tree.getValue() + " " + getEquation(tree.getRight());
    }
}
