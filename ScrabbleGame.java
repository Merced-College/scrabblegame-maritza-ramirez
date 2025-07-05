//Name: Maritza Ramirez
//Date: July 1, 2025
//This program is a simple Scrabble game that gives the user random letters to form a word. 
//First I added a looping system to allow the user to play multiple rounds until they choose to exit.
//Second I added another level of verification to ensure the user does not get letters that cannot form any word in the dictionary.
//Finally I implemented a point system where the user can gain points for valid words and lose points for invalid words or letters used.
//There are different levels of scoring: Novice (10 points), Intermediate (25 points), and Expert (50 points).


//*************************************IMPORTS*************************************
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

//*************************************DEFINING METHODS************************************
public class ScrabbleGame {

    // Helper class for user result
    private static class UserResult {
        String word;
        int scoreChange;
        UserResult(String word, int scoreChange) {
            this.word = word;
            this.scoreChange = scoreChange;
        }
    }

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
    private static UserResult gitUSER(char[] randomLetters, Scanner input, ArrayList<Word> words) {
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
                System.out.println("Points Deducted: 2");
                return new UserResult(null, -2); // Deduct points for invalid letters
            }

            // Binary search for the word in the dictionary
            int idx = Collections.binarySearch(words, new Word(userWord));
            if (idx >= 0) {
                System.out.println("Valid word: " + userWord);
                System.out.println("Points Added: 5");
                return new UserResult(userWord, 5); // Add points for valid word
            } else {
                System.out.println("Not a valid word.");
                System.out.println("Points Deducted: 2");
                return new UserResult(null, -2); // Deduct points for invalid word
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
        System.out.println("There is a point system in place, so be careful with your words!");
        System.out.println("Novice: 10 points. Intermediate: 25 points. Expert: 50 points.");
        System.out.println("Points will be deducted for invalid words. Good luck!");
        System.out.println(" ");

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

    Scanner input = new Scanner(System.in);
    boolean continueGame = true;
    int score = 0;
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
        UserResult result = gitUSER(randomLetters, input, words);
        score += result.scoreChange;
        System.out.println("Your Score: " + score);

        // Ask to continue at the end of the round
        System.out.println("Do you wish to continue playing? (yes/no)");
        String round = input.nextLine().trim().toLowerCase();
        if (!round.equals("yes")) {
            continueGame = false;
        }
    }

    //*************************************END OF SCRABBLE*************************************
    // End of the game SCORING
    if (score >= 50) {
        System.out.println("Congratulations! You are an Expert Scrabble player!");
    } else if (score >= 25) {
        System.out.println("Good job! You are an Intermediate Scrabble player!");
    } else if (score >= 10) {
        System.out.println("Nice try! You are a Novice Scrabble player!");
    } else {
        System.out.println("Better luck next time!");
    }
    System.out.println("Your final score is: " + score);
    if (score < 0) {
        System.out.println("You ended with a negative score. Please try again!");
    }
    // End of the game
    System.out.println("Thank you for playing Scrabble!");
    System.out.println("*************************************END OF SCRABBLE*************************************");
        input.close();
}
}
