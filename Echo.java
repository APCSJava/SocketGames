import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Provides an extremely sample of a Servable class.
 * 
 * @author kentcollins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "An Echo Game", description = "You say something and I'll repeat it back.")
public class Echo implements Servable {

	private String secret;
	private String prompt = "Guess the secret word or enter 'q' to quit";

	public Echo() {
		secret = "the secret word";
	}

	@Override
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		output.println("Started the Echo Game.  Instructions to follow");
		output.println(prompt);
		String userInput = input.readLine().trim();
		while (!userInput.equals("q")) {
			if (userInput.equals(secret)) {
				output.println("Yay -- you won!  Way to go, you!!");
				return; // end the serve method, thus ending the game
			} else {
				output.println("I'm sorry, but " + userInput + " isn't the secret word.");
				output.println(prompt);
				userInput = input.readLine().trim();
			}
		}
		output.println("Sorry.  You did not guess the secret word");
	}
}
