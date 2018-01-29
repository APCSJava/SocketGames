package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.Servable;
import org.asl.socketserver.MenuInfo;

@MenuInfo(authors = {
		"Anisa Cooper" }, version = "Winter, 2018", title = "Bagles", description = "A game where you guess the number and compete against the computer.")

public class Bagles implements Servable {
	// instance & static variables

	private int number1;
	private int number2;
	private int number3;
	private int gameCount;
	private String guess;
	String finalNum;
	String hint; 

	// Constructor
	public Bagles() { 
		guess = "";
		number1 = (int) ((Math.random()) * 9);
		number2 = (int) ((Math.random()) * 9);
		number3 = (int) ((Math.random()) * 9);
		gameCount = 0;
		hint = "";
	}

	public void serve(BufferedReader input, PrintWriter out) throws IOException {

		String begNum = number1 + "" + number2 + "" + number3 + "";
		int num = Integer.parseInt(begNum);
		 finalNum = num + "";
		// methods
		// String method

		out.println("BAGELS");
		out.println("CREATIVE COMPUTING MORRISTOWN, NEW JERSEY");
		out.println("WOULD YOU LIKE TO LEARN THE RULES (YES OR NO) ?");
		String userInput = input.readLine();
		if (userInput.equals("YES")) {
			out.println(
					"I AM THINKING OF A THREE DIGIT NUMBER. TRY TO GUESS MY NUMBER AND I WILL GIVE YOU CLUES AS FOLLOWS: \nPICO- ONE DIGIT CORRECT BUT IN THE WRONG POSITION \nFERMI- ONE DIGIT CORRECT AND IN THE RIGHT POSITION \nBAGELS- NO DIGITS CORRECT");

		}
		out.println("O.K. I HAVE A NUMBER IN MIND");
		guess = input.readLine().trim();

		// method call it after play again
		while (gameCount <= 20) {
			if(guess.equals(finalNum)) break;
			guessNumber(finalNum);
			System.out.println(hint);
			System.out.println("Guess again:");
			guess = input.readLine().trim();
			gameCount++;
		}
		out.println("PLAY AGAIN (YES OR NO) ?");
		String userInput_2 = input.readLine();
		if (userInput_2.equals("YES")) {
			guessNumber(finalNum);
		}

	}

private String guessNumber(String finalNum) {
			String N = "";
			String G = "";
			String hint = "";
		//for(int i = 0; i < 20; i++) {
			System.out.println("NUMBER "+ finalNum);
			for(int g = 0; g < guess.length(); g++) {
				G = guess.substring(g,g+1);
				N = finalNum.substring(g,g+1);
					if (N.equals(G)) {
					hint += "FERMI ";
					} else if (finalNum.indexOf(G) != -1) {
					hint += "PICO ";
					} else {
					hint+= "BAGLES ";
					} 
			}
			return hint; 
}	
} 
 
