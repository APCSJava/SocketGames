import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Provides a sample of a Servable class.
 * 
 * @author kentcollins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "High Low", description = "Can you guess the secret number if I tell you High or Low?")
public class HighLow implements Servable {

	private int target;
	private int lowBound;
	private int highBound;
	private int numGuesses;
	private boolean gameWon;
	private int MAX_TARGET = 1_000_000; // one in a million

	public HighLow() {
		target = (int) (Math.random() * MAX_TARGET) + 1;
		lowBound = 0;
		highBound = MAX_TARGET + 1;
		numGuesses = 0;
		gameWon = false;
	}

	@Override
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		@SuppressWarnings("unused")
		HighLow game = new HighLow();

		output.println(buildInitialPrompt());
		String userInput = input.readLine().trim();

		while (!gameWon) {
			int theirGuess = evaluateInput(userInput);
			if (theirGuess == -1) {
				output.println(buildQuitMessage());
				break;
			}
			else if (theirGuess == 0) {
				output.println(buildErrorMessage(userInput));
				userInput = input.readLine().trim();
			}
			numGuesses++;
			if (theirGuess == target) {
				output.println(buildCongratulationsString());
				break;
			} else {
				output.println(buildSuggestion());

			}
			userInput = input.readLine().trim();

			
		}
	}

	private String buildCongratulationsString() {
		// TODO Auto-generated method stub
		return null;
	}

	private void handleGuess(int theirGuess) {
		if (theirGuess < target) {
			output.println("I'm sorry but " + theirGuess + " is too low.  Try guessing higher.");
			if (theirGuess > lowBound)
				lowBound = theirGuess;
		} else if (theirGuess > target) {
			output.println("I'm sorry but " + theirGuess + " is too high.  Try guessing lower.");
			if (theirGuess < highBound)
				highBound = theirGuess;
		} else {
			gameWon = true;
			output.println("You guessed it exactly!  You used " + numGuesses + " guesses.");
			if (numGuesses < 20)
				output.println("You're pretty lucky, dontcha know?");
			else if (numGuesses < 25)
				output.println("You are pretty good at this!");
			else
				output.println("Finally -- I can't believe you needed " + numGuesses + " guesses!");
			break;
		}
		output.println(buildSuggestion());		
	}

	private String buildErrorMessage(String errorGuess) {
		String s = "I'm sorry, but "+errorGuess+" is not a valid guess";
		s += "Guess a number between 1 and " + MAX_TARGET + " or press 'q' to quit";
		return s;
	}

	private String buildQuitMessage() {
		String s = "Thanks for playing High-Low.  ";
		s += "You used "+numGuesses+(numGuesses>1?" guesses.":" guess.");
		s += "Try again, if you think you can do better.";
		return s;
	}

	private String buildInitialPrompt() {
		return "Guess a number between 1 and " + MAX_TARGET + " or press 'q' to quit";
	}

	private String buildSuggestion() {
		String s1 = "The answer lies between " + (lowBound + 1) + " and " + (highBound - 1) + ".";
		s1 += "  You have used " + numGuesses + (numGuesses == 1 ? " guess" : " guesses");
		return s1;
	}

	/**
	 * Checks the user input to see if it is a valid sequence. If a valid number,
	 * returns that number. If an invalid number, returns a 0. If the user has
	 * chosen 'q', returns a -1.
	 * 
	 * @param s
	 *            the user's input
	 * @return a valid number; 0 for an invalid number; -1 for the quit signal
	 */
	private int evaluateInput(String s) {
		if (s.toLowerCase().equals("q"))
			return -1;
		try {
			int guess = Integer.parseInt(s);
			return guess;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
