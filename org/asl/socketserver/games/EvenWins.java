package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Jake Perelmuter" }, version = "Winter, 2018", title = "Even Wins", description = "Even wins.")

public class EvenWins implements Servable {

	public void serve(BufferedReader in, PrintWriter out) 
			throws IOException {

		int marbles = 27;
		int playerScore = 0;
		int compScore = 0;
		String startNum;
		int startInt;

		out.println("Type \'0\' if you want to go first or \'1\' if you want me to go first");

		startNum = in.readLine();
		startInt = Integer.parseInt(startNum);

		if (startInt == 1){
			out.println("Total: " + marbles);
			out.println("I remove 1 marble.");
			marbles -= 1;
			compScore += 1;
		}

		while(marbles > 0){
			out.println("Total: " + marbles);
			out.println("How many marbles will you take out: ");
			String inputStr = in.readLine();
			int input = Integer.parseInt(inputStr);
			marbles -= input;
			playerScore += input;
			out.println("Total: " + marbles);
			out.println("I remove 1 marble.");
			marbles -= 1;
			compScore += 1;
			out.println("Total: " + marbles);
		}

		if (marbles == 0){
			out.println("Game over.");
			out.println("Your score: " + playerScore);
			out.println("My score: " + compScore);
			if (playerScore % 2 == 0) out.println("You win!");
			else out.println("I win!"); 
		}



	}
}
