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
@GameInfo(authors = { "Kent Collins" }, version = "Fall, 2017", gameTitle = "An Echo Game")
public class Echo implements Servable {

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		output.println("Started the Echo Game.  Instructions to follow");
		output.println("Enter something. 'q' to quit");
		String userInput = input.readLine().trim();
		while (!userInput.equals("q")) {
			output.println(
					"you said " + userInput + ".  Say 'q' to exit");
			userInput = input.readLine().trim();
		}
		output.println("Exiting the " + this.getClass().getName()
				+ " class");
	}
}
