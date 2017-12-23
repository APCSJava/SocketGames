import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Think of a number between 1 and 100.  Tell me some facts and I'll guess it.
 * 
 * @author K. Collins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "Nicomachus", description = "I can guess your number!")
public class Nicomachus implements Servable {

	private int mod3;
	private int mod5;
	private int mod7;

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		output.println("Think of a number between 1 and 100 but don't tell me.  Press Enter to continue.");
		input.readLine();
		output.println("What is the remainder when you divide your number by 3?");
		mod3 = Integer.parseInt(input.readLine().trim());
		output.println("What is the remainder when you divide your number by 5?");
		mod5 = Integer.parseInt(input.readLine().trim());
		output.println("What is the remainder when you divide your number by 7?");
		mod7 = Integer.parseInt(input.readLine().trim());
		

		output.println("Aha!  The number you selected was "+calc());
	}
	
	private int calc() {
		return (70*mod3+21*mod5+15*mod7)%105;
	}

	
}
