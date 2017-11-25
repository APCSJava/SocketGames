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
	int MAX_TARGET = 1_000_000_000; // one in a million

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
		output.println("Guess a number between 1 and "+MAX_TARGET+". 'q' to quit");
		String userInput = input.readLine().trim();
		while (!userInput.equals("q")) {
			output.println(
					"Not implemented yet" + userInput + ".  Say 'q' to exit");
			userInput = input.readLine().trim();
		}
		output.println("Exiting the " + this.getClass().getName()
				+ " class");
	}
}
