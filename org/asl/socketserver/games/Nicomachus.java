package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

/***
 * Think of a number between 1 and 100.  Tell me some facts and I'll guess it.
 * 
 * @author K. Collins
 * @version Fall 2017
 *
 */
@MenuInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", title = "Nicomachus - Mystic Math", description = "Pick a secret number and I will divine your selection!")
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
		mod3 = protectedParse(input.readLine().trim());
		if (mod3<0) {
			output.println("Hmmm....I don't understand that input.  Try again later.");
			return;
		}
		output.println("What is the remainder when you divide your number by 5?");
		mod5 = protectedParse(input.readLine().trim());
		if (mod5<0) {
			output.println("Hmmm....I don't understand that input.  Try again later.");
			return;
		}
		output.println("What is the remainder when you divide your number by 7?");
		mod7 = protectedParse(input.readLine().trim());
		if (mod7<0) {
			output.println("Hmmm....I don't understand that input.  Try again later.");
			return;
		}
		output.println("Aha!  The number you selected was "+calc()+".  How can I know that??!");
	}
	
	private int calc() {
		return (70*mod3+21*mod5+15*mod7)%105;
	}
	
	private int protectedParse(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	
}
