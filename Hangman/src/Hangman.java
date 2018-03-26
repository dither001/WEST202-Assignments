/**
 * Add comments at the heading describing what the program does
 * as well as who authored it.
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
	List<String> wordList = new ArrayList<String>();
	
	private static final int MAX_GUESSES = 6; 

	/**
	 * Read in the list of words
	 */
	public void readWords(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));

		while (s.hasNext())
			wordList.add(s.next());

		s.close();
	}

	/**
	 * Selects a random word from the list
	 * and returns it.
	 */
	public String getWord() {
		// returns a random word from wordList

		Random r = new Random();

		return wordList.get(r.nextInt(wordList.size()));
	}

	public void playGame(String word) {
		// instantiate Scanner first
		Scanner reader = new Scanner(System.in);
		
		// create & initialize guessedRight array to false (just in case)
		boolean[] guessedRight = new boolean[word.length()];
		for (int i = 0; i < guessedRight.length; ++i) {guessedRight[i] = false;}
		
		char nextChoice;
        int remain = MAX_GUESSES;
        String guesses = "";
        
        boolean victory = false;
        
        // TIME TO PLAY
		System.out.printf("%n%s", drawTheLines(word, guessedRight));

		while (remain > 0) {
			if (victory) {
				System.out.printf("%n%nYou dodged the noose this time!");
				break;
			}
			
			// testing only: prints word
//			System.out.printf("%n%n%s", word);
			
			System.out.printf("%n%nYour choice: ");
			nextChoice = reader.next().charAt(0);
			
			// input validation
			if (isLetter(nextChoice)) {
				// decrement remaining guesses
				remain -= (isGuessUsed(word, guesses, nextChoice)) ? 1 : 0;
				if (! (word.contains(String.valueOf(nextChoice)))) {
					System.out.printf("%n%n%c does not appear.", nextChoice);
				}
				else {
					System.out.printf("%nYou guessed %c", nextChoice);
				}
				
				// compare guess to undiscovered letters
				if (isWordComplete(word, guessedRight, nextChoice)) {victory = true;}
				
				
				// add guess to the list
				guesses += (! (guesses.contains(String.valueOf(nextChoice)))) ? nextChoice : "";
				
				// testing only: print list of guessed letters
//				System.out.printf("%n");
//				for (int i = 0; i < guesses.length(); ++i) {
//					System.out.printf(" %c ", guesses.charAt(i));
//				}
			}
			
			if (remain == 1) {
				System.out.printf("%n%nYou have %d more guess.", remain);
			}
			else if (remain <= 0) {
				System.out.printf("%n%nYou have no more guesses.");
			}
			else {
				System.out.printf("%n%nYou have %d more guesses.", remain);
			}
			
			System.out.printf("%n%n%s", drawTheLines(word, guessedRight));
        }
		
		System.out.printf("%n%nGame over.");
		reader.close();
	}
	
	public static String drawTheLines(String word, boolean[] correct) {
		String s = "";
		
		for (int i = 0; i < word.length(); ++i) {
			if (correct[i]) {
				s += String.format(" %c ", word.charAt(i));
			}
			else {
				s += String.format(" _ ");
			}
		}
		
		return s;
	}
	
	public static boolean isGuessUsed(String word, String guesses, char c) {
		if (word.contains(String.valueOf(c)))		{return false;}
		if (guesses.contains(String.valueOf(c)))	{return false;}
		
		return true;
	}
	
	public static boolean isLetter(char c) {
		// TO DO
		if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122))
			return true;
		else
			return false;
	}
	
	public static boolean isWordComplete(String word, boolean[] correct, char c) {
		for (int i = 0; i < word.length(); ++i) {
			if (word.charAt(i) == c) correct[i] = true;
		}
		
		for (boolean el : correct) {
			if (!el) return false;
		}
		
		return true;
	}

	public static void main(String[] args) {
		Hangman game = new Hangman();

		try {
			game.readWords("words.txt");
			game.playGame(game.getWord());
		} catch (FileNotFoundException fnf) {
			System.err.println("words.txt file Not Found");
		}

		
	}

}
