import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * A game for guessing one number out of a million -- can it be done in a
 * reasonable number of tries?
 * 
 * An example implementation of the Servable interface
 * 
 * @author kentcollins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "One in a Million", description = "How many guesses does it take you?")
public class OneInAMillion implements Servable {

	private int target;
	private int lowBound;
	private int highBound;
	private int numGuesses;
	private boolean gameWon;
	private String promptString;
	private int MAX_TARGET = 1_000_000; // one in a million

	public OneInAMillion() {
		target = (int) (Math.random() * MAX_TARGET) + 1;
		lowBound = 0;
		highBound = MAX_TARGET + 1;
		numGuesses = 0;
		gameWon = false;
	}

	@Override
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		setInitialPrompt();
		output.println(promptString);
		String userInput = input.readLine().trim();

		while (!gameWon) {
			int guessCode = evaluateUserInput(userInput);
			if (guessCode == -1) { // they chose to quit
				setQuitPrompt();
				output.println(promptString);
				break; // exit this loop
			} else if (guessCode == target) { // they have won the game
				numGuesses++;
				setCongratulationsPrompt();
				output.println(promptString);
				gameWon = true;
			} else if (guessCode == 0) { // their number isn't a valid choice
				setErrorPrompt(userInput);
				output.println(promptString);
				userInput = input.readLine().trim();
				continue; // begin next iteration
			} else {
				numGuesses++;
				updateBounds(guessCode);
				setSuggestionPrompt();
				output.println(promptString);
				userInput = input.readLine().trim();
			}
		}
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
	private int evaluateUserInput(String s) {
		if (s.toLowerCase().equals("q"))
			return -1;
		try {
			int guess = Integer.parseInt(s);
			if (0 < guess && guess <= MAX_TARGET) {
				return guess;
			} else
				return 0;
		} catch (NumberFormatException e) {
			// couldn't parse their input as a number
			return 0;
		}
	}

	private void setInitialPrompt() {
		promptString = "Guess a number between 1 and " + MAX_TARGET + " or press 'q' to quit";
	}

	private void setCongratulationsPrompt() {
		promptString = "You guessed it exactly!  You used " + numGuesses + " guesses.";
	}

	private void setQuitPrompt() {
		String s = "Thanks for playing High-Low.  ";
		s += "You tried " + numGuesses + (numGuesses > 1 ? " guesses" : " guess");
		s += " but did not win.  The target was " + target;
		s += "\nTry again, if you think you can do better.";
		promptString = s;
	}

	private void setErrorPrompt(String errorGuess) {
		String s = "I'm sorry, but " + errorGuess + " is not a valid guess";
		s += "\nGuess a number between 1 and " + MAX_TARGET + " or press 'q' to quit";
		promptString = s;
	}

	private void setSuggestionPrompt() {
		String s1 = "The answer lies between " + (lowBound) + " and " + (highBound) + ".";
		s1 += "  \nYou have used " + numGuesses + (numGuesses == 1 ? " guess" : " guesses");
		promptString = s1;
	}

	/**
	 * Evaluates a guess and updates the higher and lower bounds, as appropriate.
	 * This tracks the nearest guesses made above and below the target.
	 * 
	 * @param guessCode
	 */
	private void updateBounds(int guessCode) {
		if (guessCode < target && guessCode > lowBound) {
			lowBound = guessCode;
		} else if (guessCode > target && guessCode < highBound) {
			highBound = guessCode;
		}
	}
}
