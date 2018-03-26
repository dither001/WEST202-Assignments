/**
 *  @author Nick Foster; December 2017
 *  
 *  @description: Reads in sample reviews, ranks words according to score, and rates new reviews.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MovieReview {
	private static String[] good = {
			"good", "great", "greatest", "better", "best", "brilliant", "super", "awesome",
			"fantastic", "wonderful", "exciting", "favorite"
	};
	
	private static String[] bad = {
			"bad", "worse", "worst", "least", "stupid", "boring", "awful", "terrible", "horrible",
			"lame", "wooden", "fail", "failure"
	};
	
	private static String[] duds = {
			"a", "an", "of", "the", "this", "that", "what", "is", "was", 
			"and", "but", "or", "though", "however", "for", "while", "when",
			"also", "some", "few", "many", "several",
			"i", "us", "you", "he", "his", "him", "she", "her", "hers", "they", "them",
			"film", "movie", "show", "story", "character", "series"
	};
	
	public static void main(String args[]) {
		if (args.length != 1) {
			System.err.println("Must pass name of movie reviews file");
			System.exit(0);
		}

		// creates Dictionary for storing WordEntry
		DictionaryInterface<String, WordEntry> words = new HashDictionaryChained<String, WordEntry>();

		// variables for keeping track of the input file
		String line;
		int score;
		List<String> fileLines;

		// the movie review entered by the user
		String review = " ";
		Scanner keyboard = new Scanner(System.in);

		try {
			fileLines = Files.readAllLines(Paths.get(args[0]));
		} catch(IOException e) {
			System.err.println("File " + args[0] + " not found.");
			return;
		}

		Iterator<String> itr = fileLines.iterator();
		while (itr.hasNext()) {
			line = itr.next();
			score = Integer.parseInt(line.substring(0, 1));
			line = line.substring(2).trim();
			line = line.replaceAll("[^a-zA-Z]+", " ");
			String tokens[] = line.split(" ");
			
			// iterates through tokens, updates dictionary
			for (String word : tokens) {
				if (words.contains(word)) {
					WordEntry previous = words.get(word);
					previous.update(score);
				} else {
					words.put(word, new WordEntry(word, score, 1));
					// Added this so I could track how many words had been entered.
					System.out.printf("Added %s to dictionary.%n", word);
				}
			}
		}

		System.out.printf("Finished reading %d reviews.%n", fileLines.size());

		while (review.length() > 0) {
			System.out.printf("Enter a review -- Press return to exit.%n");
			review = keyboard.nextLine();
			review = review.replaceAll("[^a-zA-Z]+", " ");
			String tokens[] = review.split(" ");

			// score the review
			int total = 0;
			for (String word : tokens) {
				if (isGood(word)) {
					total += 3.5;
				} else if (isBad(word)) {
					total += 0.5;
				} else if (words.contains(word) && isDud(word) != true) {
					total += words.get(word).getScore();
				} else {
					total += 2.0;
				}
			}
			double average = 0.5 + total / (1.0000 * tokens.length);

			if (average > 2.1) {
				System.out.printf("This is a mostly positive review, with a score of %.3f.%n", average);
			} else if (average < 1.9) {
				System.out.printf("This is a mostly negative review, with a score of %.3f.%n", average);
			} else {
				System.out.printf("This is a fairly neutral review, with a score of %.3f.%n", average);
			}
		}
	}
	
	private static boolean isGood(String word) {
		boolean isGood = false;
		for (String el : good) {
			if (word.compareTo(el) == 0) {
				isGood = true;
				break;
			}
		}
		return isGood;
	}
	
	private static boolean isBad(String word) {
		boolean isBad = false;
		for (String el : bad) {
			if (word.compareTo(el) == 0) {
				isBad = true;
				break;
			}
		}
		return isBad;
	}
	
	private static boolean isDud(String word) {
		boolean isDud = false;
		for (String el : duds) {
			if (word.compareTo(el) == 0) {
				isDud = true;
				break;
			}
		}
		return isDud;
	}
	
}
