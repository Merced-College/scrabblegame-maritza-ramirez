//Name: Maritza Ramirez
//Date: July 1, 2025
//This program is a simple Scrabble game that gives the user random letters to form a word. 

//*************************************IMPORTS*************************************
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

//*************************************DEFINING METHODS************************************
public class ScrabbleGame {

// Checks if any word in the dictionary can be formed from the given letters
private static boolean canFormAnyWord(char[] randomLetters, ArrayList<Word> words) {
    int[] letterCount = new int[26];
    for (char c : randomLetters) letterCount[c - 'A']++;
    for (Word w : words) {
        String word = w.toString().toUpperCase();
        int[] tempCount = letterCount.clone();
        boolean canForm = true;
        for (char c : word.toCharArray()) {
            if (c < 'A' || c > 'Z' || tempCount[c - 'A'] == 0) {
                canForm = false;
                break;
            }
            tempCount[c - 'A']--;
        }
        if (canForm) return true;
    }
    return false;
    
}

// Checks if the user word is valid using only the letters provided
private static boolean validLetters(char[] randomLetters, String userWord) {
        int[] letterCount = new int[26];
        for (char c : randomLetters) letterCount[c - 'A']++;
        for (char c : userWord.toCharArray()) {
            if (c < 'A' || c > 'Z' || letterCount[c - 'A'] == 0) {
                return false;
            }
            letterCount[c - 'A']--;
        }
        return true;
    }
    
    // Prompts the user to enter a word using the random letters
    // Pass scanner as parameter
    private static String gitUSER(char[] randomLetters, Scanner input){
        while(true) {
            System.out.print("Your letters are: ");
            for (char c : randomLetters) System.out.print(c + " ");
            System.out.println();

            System.out.print("Enter a word using these letters: ");
            String userWord = input.nextLine().trim().toUpperCase();

            if (userWord.isEmpty()) {
                System.out.println("Invalid word: cannot be empty.");
                continue;
            }

            if (!validLetters(randomLetters, userWord)) {
                System.out.println("Invalid word: uses letters not in the set.");
                continue;
            } else {
                return userWord;
            }
        }
    }
        
    //*************************************START OF PROGRAM*************************************
    // Main method to run the Scrabble game
    // This method initializes the game, reads the word list, generates random letters,
    // prompts the user for input, and checks if the word is valid using binary search.

    public static void main(String[] args) {
        //Introductory message
            System.out.println("*************************************START OF SCRABBLE*************************************");
            System.out.println("Welcome to the Scrabble Game!");

        //*************************************WORD LIST*************************************
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

        //*************************************ENTER ROUND LOOP*************************************
        // This loop will run until the user decides to exit

        Scanner roundScan = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean continueGame = true;
        while (continueGame) {
            //*************************************RANDOM LETTERS*************************************
            // Pick 4 random letters from the alphabet
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random rand = new Random();
            char[] randomLetters = new char[4];
            do {
                for (int i = 0; i < 4; i++) {
                    randomLetters[i] = alphabet.charAt(rand.nextInt(alphabet.length()));
                }
            } while (!canFormAnyWord(randomLetters, words));

            //************************************* CALL USER INPUT*************************************
            String userWord = ScrabbleGame.gitUSER(randomLetters, input);

            //*************************************BINARY SEARCH*************************************
            // Binary search for the word
            int idx = Collections.binarySearch(words, new Word(userWord));
            if (idx >= 0) {
                System.out.println("Valid word: " + userWord);
            } else {
                System.out.println("Not a valid word.");
            }

            // Ask to continue at the end of the round
            System.out.println("Do you wish to continue playing? (yes/no)");
            String round = input.nextLine().trim().toLowerCase();
            if (!round.equals("yes")) {
                continueGame = false;
            }
        }
        // End of the game
        System.out.println("Thank you for playing Scrabble!");
        System.out.println("*************************************END OF SCRABBLE*************************************");
}
}
