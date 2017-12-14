import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Example implementation of a Servable class.
 * 
 * @author kentcollins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "The Secret Word", description = "Guess the secret word and win!")
public class SecretWord implements Servable {

	private String secret;
	private final String prompt;
	private final String[] kudos = {
			"Yay -- you won!  Way to go, you!!",
			"Amazing.  You're a clever one, you are.",
			"Did you figure that out by yourself or did you have help?  You WON!",
			"Brilliant -- but shhhhhh.  Don't give away the secret. ;-)" };
	private final String[] goodbyes = {
			"Oh well, better luck next time.",
			"Sorry, but you didn't win.  Have a go again, later.",
			"Think about it for a while then try again.  You can get this.",
			"Hint: If you follow my instructions exactly, you can win ;-)" };

	public SecretWord() {
		secret = "the secret word";
		prompt = "Guess the secret word or enter 'q' to quit";
	}

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		output.println("Welcome to the Secret Word game.");
		output.println(prompt);
		String userInput = input.readLine().trim();
		while (!userInput.equals("q")) {
			if (userInput.equals(secret)) {
				output.println(getRandomMessage(kudos));
				return; // end the serve method, thus ending the game
			} else {
				output.println("You said '" + userInput
						+ "' but that is not the secret word.");
				output.println(prompt);
				userInput = input.readLine().trim();
			}
		}
		output.println(getRandomMessage(goodbyes));
	}
	
	private String getRandomMessage(String[] array) {
		int randomChoice = (int) (Math.random()*array.length);
		String s = array[randomChoice];
		return s;
	}
}
