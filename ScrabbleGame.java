//Name: Maritza Ramirez
//Date: July 1, 2025
// This program is a simple Scrabble game that gives the user random letters to form a word. 

//*************************************IMPORTS*************************************
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

//*************************************START OF PROGRAM*************************************
//Word class used to represent each word in arrayList

public class ScrabbleGame {
    public static void main(String[] args) {
        ArrayList<Word> words = new ArrayList<>();
        try {
            Scanner collinsScanner = new Scanner(new File("CollinsScrabbleWords_2019.txt"));
            while (collinsScanner.hasNextLine()) {
                String line = collinsScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    words.add(new Word(line));
                }
            }
            collinsScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Word list file not found.");
            return;
        }
        Collections.sort(words);

        //*************************************RANDOM LETTERS*************************************
        // Pick 4 random letters from the alphabet
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        char[] randomLetters = new char[4];
        for (int i = 0; i < 4; i++) {
            randomLetters[i] = alphabet.charAt(rand.nextInt(alphabet.length()));
        }
        
        //*************************************USER INPUT*************************************
        //Introductory message
        System.out.println("*************************************START OF SCRABBLE*************************************");
        System.out.println("Welcome to the Scrabble Game!");

        //Display the random letters
        System.out.print("Your letters are: ");
        for (char c : randomLetters) System.out.print(c + " ");
        System.out.println();


        //*************************************VALID WORD CHECK*************************************
        // Ask user for a word
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a word using these letters: ");
        String userWord = input.nextLine().trim().toUpperCase();

        // Check if userWord uses only the given letters
        boolean validLetters = true;
        int[] letterCount = new int[26];
        for (char c : randomLetters) letterCount[c - 'A']++;
        for (char c : userWord.toCharArray()) {
            if (c < 'A' || c > 'Z' || letterCount[c - 'A'] == 0) {
                validLetters = false;
                break;
            }
            letterCount[c - 'A']--;
        }

        if (!validLetters) {
            System.out.println("Invalid word: uses letters not in the set.");
            return;
        }

        //*************************************BINARY SEARCH*************************************
        // Binary search for the word
        int idx = Collections.binarySearch(words, new Word(userWord));
        if (idx >= 0) {
            System.out.println("Valid word: " + userWord);
        } else {
            System.out.println("Not a valid word.");
        }

        // Close the input scanner
        input.close();
    }
}
