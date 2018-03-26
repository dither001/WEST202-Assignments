
/**
 * Plays Greedy Coin game such that the computer never loses.
 * 
 * @ Nick Foster
 * @ September 26th
 */

import java.io.*;
import java.util.Scanner;

public class GreedyCoinGame<T> {
	private static LinkedList<Integer> coins = new LinkedList<Integer>();
	private static int playerScore = 0;
	private static int compScore = 0;

	private static Scanner input = new Scanner(System.in);

	public GreedyCoinGame(String file) throws FileNotFoundException {
		Scanner inFile = new Scanner(new File(file));

		while (inFile.hasNext()) {
			// System.out.println(inFile.nextInt());
			coins.add(Integer.valueOf(inFile.nextInt()));
		}

		inFile.close();
	}

	public void preventNonInteger() {
		while (!input.hasNextInt()) {
			System.out.printf("INVALID SELECTION. TRY AGAIN.");
			input.next();
		}
	}

	public int preventNonOption(int index) {
		while (index != 0 && index != (coins.getLength() - 1)) {
			System.out.printf("INVALID SELECTION. TRY AGAIN.");
			preventNonInteger();
			index = input.nextInt();
		}

		return index;
	}

	// prints the coins and their position
	public void printCoins() {
		System.out.printf("%n+++++++++++");

		// TO-DO print out each element and its position in the linked list
		System.out.printf("%n%-16s", "Coins: ");
		for (int i = 0; i < coins.getLength(); ++i) {
			System.out.printf("%-8d ", coins.get(i));
		}

		System.out.printf("%n%-16s", "Position: ");
		for (int i = 0; i < coins.getLength(); ++i) {
			System.out.printf("%-8d ", i);
		}

		System.out.printf("%n%nHuman: %2d Computer: %2d", playerScore, compScore);

		System.out.printf("%n+++++++++++");

	}

	public void playGame() {
		printCoins();

		int compIndex = (chooseHead()) ? 0 : coins.getLength() - 1;
		int compChoice = coins.get(compIndex);
		compScore += compChoice;
		coins.remove(compIndex);
		System.out.printf("%nI CHOOSE THE COIN AT POSITION %d.%n", compIndex);

		printCoins();

		if (coins.getLength() > 1)
			System.out.printf("%nYOU MAY CHOOSE THE COIN AT POSITION 0 OR %d: ", coins.getLength() - 1);
		else 
			System.out.printf("%nCLAIM THE FINAL COIN, HUMAN.");

		int playerIndex = -1;
		preventNonInteger();
		playerIndex = preventNonOption(input.nextInt());

		int playerChoice = coins.get(playerIndex);
		playerScore += playerChoice;
		coins.remove(playerIndex);

		System.out.printf("%nYOU HAVE CHOSEN THE COIN AT POSITION %d.%n", playerIndex);
	}

	public boolean chooseHead() {
		int redScore = 0;
		int blueScore = 0;

		for (int i = 0; i < coins.getLength(); ++i) {
			if (i % 2 == 0)
				redScore += coins.get(i);
			else
				blueScore += coins.get(i);
		}

		if (redScore >= blueScore)
			return true;
		else
			return false;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Pass a file on the command line");
			System.exit(0);
		}
		System.out.printf("TIME TO PLAY THE COIN GAME, HUMAN.");
		System.out.printf("%nEACH OF US SHALL TAKE A SINGLE COIN FROM EITHER END OF THE ROW.");
		System.out.printf("%nYOU CHOSE THIS GAME, SO I SHALL MAKE THE FIRST MOVE.");

		GreedyCoinGame game = new GreedyCoinGame(args[0]);

		while (!coins.isEmpty()) {
			game.playGame();
		}

		System.out.printf("%nTHE GAME IS OVER.");
		if (playerScore >= compScore)
			System.out.printf("%nYOU WIN THIS TIME, HUMAN.");
		else
			System.out.printf("%nYOU ARE BEATEN.");
		System.out.printf("%nTHE FINAL SCORE WAS YOUR %2d POINTS TO MY %2d.", playerScore, compScore);

		input.close();

	}

}
