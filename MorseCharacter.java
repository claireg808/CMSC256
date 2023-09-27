/**
 * MorseCharacter
 * Claire Gillaspie
 * Project 6
 * CMSC256-901
 * Spring 2023
 * This class builds a MorseCharacter object containing a letter & the corresponding morse code
 */

package cmsc256;

import java.util.*;
import java.lang.Comparable;
public class MorseCharacter implements Comparable<MorseCharacter> {
    private char letter;
    private String code;

    /**
     * constructors
     */
    public static void main(String[] args){
        MorseCharacter A = new MorseCharacter('A', "--...");
        MorseCharacter I = new MorseCharacter('A', "--.");
        System.out.println("compareTo: " + A.compareTo(I));
        System.out.println("equals: " + A.equals(I));
    }

    public MorseCharacter(char letter, String code){
        this.letter = letter;
        this.code = code;
    }

    public MorseCharacter(){
        code = "";
        letter = '\0';
    }

    /**
     * access & mutator
     * letter, morse
     */
    public char getLetter() {
        return letter;
    }
    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(MorseCharacter other){
        if (other == null){
            throw new IllegalArgumentException("Argument is null");
        }
        String stringInput = other + other.getCode(); //get letter & code for input
        String stringThis = this + this.getCode(); //get letter & code for this
        int inputLen = stringInput.length();
        int thisLen = stringThis.length();
        int result = 0;
        int result2 = 0;
        int index = 0;
        int index2 = 1;
        while (inputLen > 0 && thisLen > 0){ //while result hasn't changed & both inputs have characters
            String currInput = stringInput.substring(index,index2); //take substring of one character
            String currThis = stringThis.substring(index,index2); //take substring of one character
            if (!currThis.equals(currInput)){ //if the characters aren't equal
                if (currThis.equals("-")){
                    result = 1;
                    break;
                }
                if (currInput.equals("-")){
                    result2 = 1;
                    break;
                }
            }
            //iterate indices up to check the next character
            index++;
            index2++;
            //decrement length to stop while loop once one string has no more unchecked characters
            inputLen--;
            thisLen--;
        }
        if (result == result2){
            if (stringInput.length() > stringThis.length()){
                return -1;
            } else if (stringInput.length() < stringThis.length()){
                return 1;
            }
        }
        return Integer.compare(result, result2);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MorseCharacter that = (MorseCharacter) o;
        return letter == that.letter && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, code);
    }

    @Override
    public String toString() {
        return String.valueOf(letter);
    }
}
