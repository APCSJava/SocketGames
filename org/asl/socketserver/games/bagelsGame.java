package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Vikram Chowdhary" }, version = "Jan 2018", title = "Bagels", description = "Guess a number.")

public class bagelsGame extends AbstractGame implements Servable {
	
	public bagelsGame() {
		//empty constructor
	}
	
	private static String generateAnswer() {
		/*generate 3 numbers between 0 and 9 
		generated separately so that all 3 digits are present (000, 001, 012, 111)
		*/
		String dig1 = Integer.toString((int)(Math.random() * 9 + 0));
		String dig2 = Integer.toString((int)(Math.random() * 9 + 0));
		String dig3 = Integer.toString((int)(Math.random() * 9 + 0));
		//separate digits added together
		String ansStr = dig1 + dig2 + dig3;
		//convert ansStr to int
		return ansStr;
	}
	
	@Override
	public void serve(BufferedReader input, PrintWriter out) throws IOException {
		
		String guess = "";
		//int guessInt = 0;
		int count = 0;
		String message = "";
		
		//greeting + rules
		out.println("Welcome to the Bagels Game."
						+ "\nI am thinking of a 3-digit number."
						+ "\nTry to guess my number and I will give you clues as follows:"
						+ "\n 	PICO:   one digit correct but in the wrong position"
						+ "\n 	FERMI:  one digit correct and in the right position"
						+ "\n 	BAGELS: no digits correct");
		//pt 2 of greeting
		out.println("OK, I have a number in mind.");

		
		// generate answer
		String ANSWER = generateAnswer();
		//System.out.println(ANSWER);
		//convert to array
		char[] ANSWERarray = ANSWER.toCharArray();


		
		/* Start guessing
		will loop until correct guess
		needs to be redone with args??
		*/
		while (!guess.equals(ANSWER)) {
			count++;
			//gets guess
			out.println("Enter guess #" + count + ": ");
			//guessInt = input.nextInt();
			guess = input.readLine();
			if (guess.equals(ANSWER)) {
				message = "You got it!";
				break;
			}
			
			//make array w/ 3 slots
			char[] guessArray = guess.toCharArray();
			
			
			//PICO/FERMI/BAGEl
			if (guessArray[0] == ANSWERarray[0]) {
				message += "FERMI ";
			} else if (guessArray[0] == ANSWERarray[1] || guessArray[0] == ANSWERarray[2]) {
				message += "PICO ";
			} else {
				message += "BAGEL ";
			}
			if (guessArray[1] == ANSWERarray[1]) {
				message += "FERMI ";
			} else if (guessArray[1] == ANSWERarray[0] || guessArray[1] == ANSWERarray[2]) {
				message += "PICO ";
			} else {
				message += "BAGEL ";
			}
			if (guessArray[2] == ANSWERarray[2]) {
				message += "FERMI ";
			} else if (guessArray[2] == ANSWERarray[0] || guessArray[2] == ANSWERarray[1]) {
				message += "PICO ";
			} else {
				message += "BAGEL ";
			}

			out.println(message);
			message = "";
		}
		
		//congratulations!
		out.println("You got it!!");
		/*String goAgain = readLine("Do you want to play again? (y/n) ");
		if ()
		*/
	}
}