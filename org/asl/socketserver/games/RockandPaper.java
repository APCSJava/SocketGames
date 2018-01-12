package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.BestScore;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

/***
 * Rock Paper Scissors -- The real deal
 * 
 * @author Jonathan
 * @version Winter 2018
 *
 */
@MenuInfo(authors = {
		"Jonthan" }, version = "Winter, 2018", title = "Rock Paper Scissors -- The real deal", description = "A classic made digital")

public class RockandPaper extends AbstractGame implements Servable {
	private int consecutiveRuns;
	private int curRuns;

	public RockandPaper() {
		consecutiveRuns = 0;
		curRuns = 0;
	}

	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		output.println("Welcome to Rock, Paper, Scissors - "
				+ "Extreme Text Edition! You will be playing against a genius computer who is the"
				+ "world champion of Rock, Paper, Scissors. Choose 1 for Rock, 2 for Paper, or 3 for Scissors for your play. Type Q to quit.");
		while (true) {
			output.println("");
			output.println("");
			output.println("");
			output.println("1...2...3... What's your choice:");
			String original = input.readLine();
			String humanMove = uniformer(original);
			if (humanMove.equals("not valid")) {
				output.println("not a valid input: please put in rock, paper or scissors, we have assigned you paper");

			}
			if (original.equals("Q") || (original.equals("q")))
				break;

			String computerMove = RPS();
			output.println("This is my choice: " + computerMove);
			String winner = getWinner(humanMove, computerMove);
			if (winner.equals("User wins!")) {
				curRuns++;
				checkMaxRuns();
			}
			else curRuns = 0;
			output.println(winner);

		}

		BestScore current = this.getBestScore();
		output.println("Nicely done. Your largest consecutive winning streak is: " + consecutiveRuns);

		if (current == null || consecutiveRuns > current.getScore()) {
			output.println(consecutiveRuns + " is a new high score.  Please enter your initials...");
			String inits = input.readLine().trim();
			setBestScore(consecutiveRuns, inits);
		}
	}

	private void checkMaxRuns() {
		if (curRuns >= consecutiveRuns)
			consecutiveRuns = curRuns;
	}

	private static String RPS() {

		int rpsFinder = (int) Math.ceil((Math.random() * 3));
		if (rpsFinder == 1) {
			return "rock";
		}
		if (rpsFinder == 2) {
			return "paper";
		}
		return "scissors";
	}

	private static String uniformer(String original) {
		if (original.equals("1"))
			return "rock";
		if (original.equals("2"))
			return "paper";
		if (original.equals("3"))
			return "scissors";
		return "not valid";
	}

	private static String getWinner(String user, String computer) {
		// Return the proper string for the result.
		String user_player = "User wins!";
		String computer_player = "Computer wins!";
		String tie = "tie";

		if (user.equals("rock")) {
			if (computer.equals("rock"))
				return tie;
			else if (computer.equals("scissors"))
				return user_player;
			return computer_player;
		} else if (user.equals("scissors")) {
			if (computer.equals("rock"))
				return computer_player;
			else if (computer.equals("scissors"))
				return tie;
			return user_player;
		} else if (user.equals("paper")) {
			if (computer.equals("rock"))
				return user_player;
			return computer_player;
		}
		return tie;
	}

}
