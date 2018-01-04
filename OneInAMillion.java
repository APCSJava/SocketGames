import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * A game for guessing one number out of a million -- can it be done in a
 * reasonable number of tries?
 * 
 * @author K. Collins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "One in a Million", description = "How many guesses does it take you?")
public class OneInAMillion extends AbstractGame implements Servable {

	private int target;
	private int lowBound;
	private int highBound;
	private int numGuesses;
	private boolean gameWon;
	private int MAX_TARGET = 1_000_000; // one in a million
	public static final int QUIT_CODE = -1;
	public static final int ERROR_CODE = 0;

	public OneInAMillion() {
		target = (int) (Math.random() * MAX_TARGET) + 1;
		lowBound = 0;
		highBound = MAX_TARGET + 1;
		numGuesses = 0;
		gameWon = false;
	}

	@Override
	public void serve(BufferedReader in, PrintWriter out)
			throws IOException {
		out.println(buildInstructionsString());
		// GameServer.LOGGER.info("One in a million? " + target);
		String userInput = in.readLine().trim();

		while (!gameWon) {
			int guess = evaluateUserInput(userInput);
			if (guess == QUIT_CODE) {
				out.println(buildQuitString());
				return; // ends the serve method and, thus, the game
			} else if (guess == target) {
				numGuesses++;
				out.println(buildCongratulationsString());
				gameWon = true;
				continue; // bypasses rest of loop
			} else if (guess == ERROR_CODE) {
				out.println(buildErrorString(userInput));
			} else {
				numGuesses++;
				updateBounds(guess);
				String p = guess < target ? "Too low." : "Too high.";
				out.println(buildSuggestionString(p));
			}
			userInput = in.readLine().trim();
		}
		// user has won
		if (checkNewBestScore()) {
			out.println(
					"That's a new best score -- please enter your initials...");
			String initials = in.readLine().trim();
			setBestScore(numGuesses, initials);
		}
		out.println("Thanks for playing!");
	}

	private boolean checkNewBestScore() {
		return (getBestScore() == null
				|| numGuesses < getBestScore().getScore());
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
			return QUIT_CODE;
		try {
			int guess = Integer.parseInt(s);
			if (0 < guess && guess <= MAX_TARGET) {
				return guess;
			} else
				return ERROR_CODE;
		} catch (NumberFormatException e) {
			// couldn't parse their input as a number
			return ERROR_CODE;
		}
	}

	private String buildInstructionsString() {
		return "Guess a number between 1 and " + MAX_TARGET
				+ " or press 'q' to quit";
	}

	private String buildCongratulationsString() {
		String start;
		if (numGuesses <= 5)
			start = "Amazing!  ";
		else if (numGuesses <= 20)
			start = "Brilliant!  ";
		else if (numGuesses <= 25)
			start = "Good job!  ";
		else if (numGuesses <= 30)
			start = "Not too bad.  ";
		else
			start = "Finally!!  ";
		return start += "You guessed it using " + numGuesses
				+ " guesses.";
	}

	private String buildQuitString() {
		String s = "Thanks for playing High-Low.  ";
		s += "You tried " + numGuesses
				+ (numGuesses > 1 ? " guesses" : " guess");
		s += " but did not win.  The target was " + target;
		s += "\nTry again, if you think you can do better.";
		return s;
	}

	private String buildErrorString(String errorGuess) {
		String s = "I'm sorry, but " + errorGuess
				+ " is not a valid guess";
		s += "\nGuess a number between 1 and " + MAX_TARGET
				+ " or press 'q' to quit";
		return s;
	}

	private String buildSuggestionString(String cue) {
		String s = cue + "  The answer lies between " + (lowBound)
				+ " and " + (highBound) + ".";
		s += "  \nYou have used " + numGuesses
				+ (numGuesses == 1 ? " guess" : " guesses");
		return s;
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
