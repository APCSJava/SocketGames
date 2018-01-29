package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Naz Ozturk" }, version = "Winter, 2018", title = "Guessing Game", description = "A guessing game for a number between 1  and a limit of your choice.")


public class GuessGame implements Servable {
	// Final code for the Guess project

	@Override
	public void serve(BufferedReader input, PrintWriter out) throws IOException {
		// TODO Auto-generated method stub
		int choice = 0;
		out.println("What limit do you want?");
		int limit = Integer.parseInt(input.readLine().trim());
		int choice2 = (int) (Math.random() * limit + 1);
		choice = choice2;
		// get number of tries, num of tries should be equal to the hidden number except
		// for the first digit
		// so if the hidden number is 180 then you should guess that number in 80 tries
		String num = choice + "";
		String tries1 = num.substring(1);
		int tries = Integer.parseInt(tries1);
		int numTries = 0;
		String end = "";
		String message1 = "";
		int guess1 = 0;

		for (int i = 0; i < choice; i++) {
			message1 = "Im thinking of a number between 1 and " + limit + ". Now you try to guess what it is.";
			out.println(message1);
			guess1 =  Integer.parseInt(input.readLine().trim());
			if (guess1 < choice) {
				numTries++;
				end = "Too low. Try a bigger number.";
				out.println(end);
			} else if (guess1 > choice) {
				numTries++;
				end = "Too high. Try a smaller number.";
				out.println(end);
			} else if (guess1 == choice) {
				numTries++;
				end = "That's it! You got it in " + numTries + " tries.";
				out.println(end);
				break;
			}
			// out.println(end);
		}

		String finalMessage = "";
		if (guess1 == choice && numTries <= tries) {
			finalMessage = "Very good.";
		} else if (guess1 == choice && numTries > tries) {
			finalMessage = "You should have been able to get it in " + tries + " tries.";
		}
		out.println(finalMessage);

	}
}