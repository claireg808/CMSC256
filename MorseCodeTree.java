/**
 * MorseCodeTree
 * Claire Gillaspie
 * Project 6
 * CMSC256-901
 * Spring 2023
 * This class builds a MorseCodeTree object using BSTElements from BRIDGES
 * Computes level-, post-, in-, and pre- order traversals
 */

package cmsc256;

import bridges.base.*;
import bridges.connect.*;
import java.io.*;
import java.util.*;
import bridges.validation.RateLimitException;


import java.io.IOException;

public class MorseCodeTree {
    public static void main(String[] args) throws IOException, RateLimitException {
        //tree built in main
        BSTElement<String, MorseCharacter> root2 = new BSTElement<>("root", "", new MorseCharacter());
        BSTElement<String, MorseCharacter> root3 = new BSTElement<>("root", "", new MorseCharacter('A',".." ));
        BSTElement<String, MorseCharacter> root4 = new BSTElement<>("root", "", new MorseCharacter('B',".-." ));
        BSTElement<String, MorseCharacter> root5 = new BSTElement<>("root", "", new MorseCharacter('C',".--." ));
        MorseCodeTree tree2 = new MorseCodeTree();
//        tree2.add(root3);
//        tree2.add(root4);
//        tree2.add(root5);
        //System.out.println(tree2.getNumberOfNodes());

        //tree built with codefile
        BSTElement<String, MorseCharacter> root = new BSTElement<>("root", "", new MorseCharacter());
        BSTElement<String, MorseCharacter> curr = new BSTElement<>();
        MorseCodeTree tree1 = new MorseCodeTree(root);
        try {
            File inputFile = new File("codefile.dat");
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineParsed = line.split(" ");
                char currLetter = lineParsed[0].charAt(0);
                MorseCharacter morse = new MorseCharacter(currLetter, lineParsed[1]);
                //call add method using this as input
                curr.setValue(morse);
                //tree.add(input)
                tree1.add(curr);
            }
        } catch (FileNotFoundException E) {
            System.out.println("Error: File not found");
        }
//        System.out.println(tree1.isEmpty());
//        System.out.println("Inorder: " + tree1.inOrderTraversal(root));
//        System.out.println("levelOrder: " + tree1.levelOrderTraversal(root));
//        System.out.println("postOrder: " + tree1.postOrderTraversal(root));
//        System.out.println("preOrder: " + tree1.preOrderTraversal(root));
//        System.out.println(tree1.decode(".-"));
//        System.out.println(tree1.encode('A'));

        Bridges bridges = new Bridges(3, "gillaspiecl", "304035823200");
        bridges.setDataStructure(root);
        bridges.setTitle("Morse code binary search tree.");
        bridges.setDescription("By Claire Gillaspie");
        bridges.visualize();
    }

    private BSTElement<String, MorseCharacter> root;

    /**
     * constructors
     */
    public MorseCodeTree() {
        root = null;
    }

    public MorseCodeTree(BSTElement<String, MorseCharacter> tree) {
        this.root = tree;
    }

    public int getHeight() {
        int height = 0;
        if (getNumberOfNodes() == 0 || getNumberOfNodes() == 1) {
            return 0;
        }
        Queue<BSTElement> elements = new LinkedList<>();
        elements.add(root);
        while (!elements.isEmpty()) {
            int size = elements.size();
            for (int i = 0; i < size; i++) {
                BSTElement temp = elements.poll();
                if (temp.getLeft() != null) {
                    elements.add(temp.getLeft());
                }
                if (temp.getRight() != null) {
                    elements.add(temp.getRight());
                }
            }
            height++;
        }
        return height - 1;
    }

    public int getNumberOfNodes(){
        if (root == null || root.getKey().equals("")) { return 0; }
        Queue<BSTElement> queue = new LinkedList<>();
        queue.add(root);
        int count = 0;
        while (!queue.isEmpty()){
            BSTElement temp = queue.poll();
            if (temp != null) {
                count++;
                if (temp.getLeft() != null) {
                    queue.add(temp.getLeft());
                }
                if (temp.getRight() != null) {
                    queue.add(temp.getRight());
                }
            }
        }
        return count;
    }

    public BSTElement<String, MorseCharacter> getRoot() {
        return root;
    }

    public void add(BSTElement<String, MorseCharacter> input) {
        BSTElement<String, MorseCharacter> curr = root;
        for (int i = 0; i < input.getValue().getCode().length(); i++) {
            if (input.getValue().getCode().charAt(i) == '.') {
                if (curr.getLeft() == null) {
                    curr.setLeft(new BSTElement<>("", "", new MorseCharacter()));
                }
                curr = curr.getLeft();
            }
            else if (input.getValue().getCode().charAt(i) == '-') {
                if (curr.getRight() == null) {
                    curr.setRight(new BSTElement<>("", "", new MorseCharacter()));
                }
                curr = curr.getRight();
            }
        }
            String label = String.valueOf(input.getValue().getLetter());
            MorseCharacter letterVal = new MorseCharacter(input.getValue().getLetter(), input.getValue().getCode());
            BSTElement<String, MorseCharacter> finalNode = new BSTElement<>(label, "", letterVal);
            curr.setValue(letterVal);
            curr.setKey(label);
    }

    /**
     * @return true if the BST is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * resets BST size to 0
     */
    public void clear() {
        root = null;
    }

    /**
     * takes in a letter and translates into morse code
     */
    public String encode(char input) {
        if (input == '\0'){ throw new IllegalArgumentException(); }
        return encode(getRoot(), input);
    }
    public String encode(BSTElement<String, MorseCharacter> node, char letter){
        if (node == null) {
            return "";
        }
        else if (node.getValue().getLetter() == letter){
            return node.getValue().getCode();
        }
        return encode(node.getLeft(), letter) + encode(node.getRight(), letter);
    }
    public String encode(String x) {
        if (x == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < x.length(); i++){
            result.append(encode(x.charAt(i)));
        }
        return result.toString();
    }

    /**
     * takes in a letter and translates into morse code
     */

    /**
     * takes in a string of morse code and translates into a letter
     */
    public char decode(String x) {
        if (x == null) { throw new IllegalArgumentException(); }
        BSTElement<String, MorseCharacter> curr = root;
        for (int i = 0; i < x.length(); i++){
            if (x.charAt(i) == '.'){
                curr = curr.getLeft();
            }
            else if (x.charAt(i) == '-'){
                curr = curr.getRight();
            }
        }
        return curr.getValue().getLetter();
    }

    /**
     * takes in a BST root node and performs an inorder traversal of the tree
     */
    public String inOrderTraversal(BSTElement<String, MorseCharacter> root) {
        if (root == null) {
            return "";
        } else if (root.getValue().getLetter() == '\0'){
            return inOrderTraversal(root.getLeft()) + inOrderTraversal(root.getRight());
        }
        return inOrderTraversal(root.getLeft()) + root.getValue() + inOrderTraversal(root.getRight());
    }

    /**
     * takes in a BST root node and performs a level order traversal of the tree
     */
    public String levelOrderTraversal(BSTElement<String, MorseCharacter> x) {
        Queue<BSTElement> queue = new LinkedList<>();
        if(x == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            BSTElement tmpNode = queue.remove();
            if(tmpNode.getLeft() != null) { queue.add(tmpNode.getLeft()); }
            if(tmpNode.getRight() != null) { queue.add(tmpNode.getRight()); }
            if (tmpNode.getValue() != null) {
                output.append(tmpNode.getKey());
            }
        }
        return output.toString();
    }

    /**
     * takes in a BST root node and performs a postorder traversal of the tree
     */
    public String postOrderTraversal(BSTElement<String, MorseCharacter> x) {
        if (x == null) {
            return "";
        } else if (x.getValue().getLetter() == '\0'){
            return postOrderTraversal(x.getLeft()) + postOrderTraversal(x.getRight());
        }
        return postOrderTraversal(x.getLeft()) + postOrderTraversal(x.getRight()) + x.getValue();
    }

    /**
     * takes in a BST root node and performs a preorder traversal of the tree
     */
    public String preOrderTraversal(BSTElement<String, MorseCharacter> x) {
        if (x == null) {
            return "";
        } else if (x.getValue().getLetter() == '\0'){
            return preOrderTraversal(x.getLeft()) + preOrderTraversal(x.getRight());
        }
        return x.getValue() + preOrderTraversal(x.getLeft()) + preOrderTraversal(x.getRight());
    }
}