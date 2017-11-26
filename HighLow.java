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
@GameInfo(authors = {"Kent Collins" }, version = "Fall, 2017", gameTitle = "High Low", 
		description = "Can you guess the secret number if I tell you High or Low?")
public class HighLow implements Servable {

	int target;
	int lowestGuess;
	int highestGuess;
	int numGuesses;
	int MAX_TARGET = 1_000_000; // one in a million

	public HighLow() {
		target = (int) (Math.random() * MAX_TARGET) + 1;
		lowestGuess = 0;
		highestGuess = MAX_TARGET + 1;
		numGuesses = 0;
	}

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		HighLow game = new HighLow();
		output.println("Guess a number between 1 and "+MAX_TARGET+". Or press 'q' to quit");
		String userInput = input.readLine().trim();
		while (!userInput.equals("q")) {
			int theirGuess;
			try {
				theirGuess = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				output.println("Sorry, but that is not a valid number.  Guess again");
				userInput = input.readLine().trim();
				continue;
			}
			numGuesses++;
			if (theirGuess<target) {
				output.println("I'm sorry but "+theirGuess+" is too low.  Try guessing higher.");
			} else if (theirGuess>target) {
				output.println("I'm sorry but "+theirGuess+" is too high.  Try guessing lower.");
			} else {
				output.println("You guessed it exactly!  You used "+numGuesses+" guesses.");
				if (numGuesses<20) output.println("You're pretty lucky, dontcha know?");
				else if (numGuesses<25) output.println("You are pretty good at this!");
				else output.println("Finally -- I can't believe you needed "+numGuesses+" guesses!");
				break;
			}
			userInput = input.readLine().trim();
		}
		output.println("Thanks for playing High-Low.  Try again, if you think you can do better.");
	}
}
