package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Lauren Brantley and Christina Leonard" }, version = "Winter, 2018", title = "Dice", description = "Throw those DICE!!!")

public class Dice implements Servable{
		

	@Override
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		// TODO Auto-generated method stub
		
		output.println("GAME OF DICE. Press N for next round or Q to quit.");
		String yourPlay = input.readLine().toUpperCase();
		int playerScore = 0;
		int computerScore = 0;
		String message = "";
		
		while (!yourPlay.equals("Q")) {
			int MAX = 6;
			int MIN = 1;
			int yourNumber = (int) (Math.floor((MAX-MIN + 1) * Math.random()) + MIN);
			int computerNumber = (int) (Math.floor((MAX-MIN + 1) * Math.random()) + MIN);
			
			if (yourNumber > computerNumber) {
				output.println("You rolled a " + yourNumber + " and the computer rolled a " + computerNumber + ".\nYou win! You get a point.");
				playerScore += 1;
			} else if (yourNumber < computerNumber) {
				output.println("You rolled a " + yourNumber + " and the computer rolled a " + computerNumber + ".\nYou lose :( Computer got the point. ");
				computerScore += 1;
			} else if (yourNumber == computerNumber) {
				output.println("You rolled a " + yourNumber + " and the computer rolled a " + computerNumber + ".\nYou guys tied. You both get a point.");
				computerScore += 1;
				playerScore += 1;
			}
			
			output.println("Press N for next round or Q to quit.");
			yourPlay = input.readLine().toUpperCase().trim();
			
		}
		
		if (playerScore > computerScore) {
			output.println("You had " + playerScore + " points. \nThe computer have " + computerScore + " points. You won! Goodbye.");
		} else if (playerScore < computerScore) {
			output.println("You had " + playerScore + " points. \nThe computer have " + computerScore + " points. You lost :( Goodbye.");
		} else if (playerScore == computerScore) {
			output.println("You had " + playerScore + " points. \nThe computer have " + computerScore + " points. You tied. Goodbye.");
		}
		
		
	}
		
}


