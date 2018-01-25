package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Lauren Brantley and Christina Leonard" }, version = "Winter, 2018", title = "Rock, Paper, Scissors", description = "The classic wonderful game of Rock, Paper, Scissors")

public class RPS implements Servable {

	@Override
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		// TODO Auto-generated method stub

		output.println("GAME OF ROCK, SCISSORS, PAPERS." + "\n" + "Enter your guess: (Type R for Rock, P for Paper, S for Scissors or Q to quit)");
		String yourPlay = input.readLine().toUpperCase();

			//if( howMany < 11)
			//System.output.println("Sorry, but we aren't allowed to play that many.");

		int yourPoints = 0;

		while(yourPlay != "Q") {
			int MIN = 0;
			int MAX = 2;
			String message = "";
			int play = (int) (Math.floor(MAX-MIN+1) * Math.random() + MIN);
			String play1 = "";

			if(play == 0) {
				play1 = "Rock";
			} else if (play == 0) {
				play1 = "Paper";
			} else {
				play1 = "Scissors";
			}
			
			String tie = "Computer plays " + play1 + "\n" + "It's a tie!";
			String rockWin = "Computer plays " + play1 + "\n" + "Rock crushes scissors, you win!.";
			String rockComputerWin = "Computer plays " + play1 + "\n" + "Rock crushes scissors, computer wins.";
			String paperWin = "Computer plays " + play1 + "\n" + "Paper covers rock, you win!";
			String paperComputerWin = "Computer plays " + play1 + "\n" + "Paper covers rock, computer wins.";
			String scissorsWin = "Computer plays " + play1 + "\n" + "Scissors cut paper, you win!";
			String scissorsComputerWin = "Computer plays " + play1 + "\n" + "Scissors cut paper, computer wins.";
			
			
					if(yourPlay.equals("R") && play1.equals("Rock")) {
						output.println(tie);
					//scissor and rock
					} else if (yourPlay.equals("S") && play1.equals("Rock")) {
						output.println(rockComputerWin);
						yourPoints = yourPoints - 1;
					//paper and rock
					} else if (yourPlay.equals("P") && play1.equals("Rock")) {
						output.println(paperWin);
						yourPoints = yourPoints + 1;
					//rock and paper
					} else if (yourPlay.equals("R") && play1.equals("Paper")) {
						output.println(paperComputerWin);
						yourPoints = yourPoints - 1;
					//paper and paper
					} else if (yourPlay.equals("P") && play1.equals("Paper")) {
						output.println(tie);
					//scissor and paper
					} else if (yourPlay.equals("S") && play1.equals("Paper")) {
						output.println(scissorsWin);
						yourPoints = yourPoints + 1;
					//rock and scissor
					} else if (yourPlay.equals("R") && play1.equals("Scissors")) {
						output.println(rockWin);
						yourPoints = yourPoints + 1;
					//paper and scissors
					} else if (yourPlay.equals("P") && play1.equals("Scissors")) {
						output.println(scissorsComputerWin);
						yourPoints = yourPoints - 1;
					//scissors and scissors
					} else if (yourPlay.equals("S") && play1.equals("Scissors")) {
						output.println(tie);
					//creates outcome for what would happen if person wanted to quit the game
					} else if (yourPlay.equals("Q")) {
						break;
						//creating the messages that will be displayed
						
					} else {
						//create alert for any invalid responses
						output.println("Invalid Response.");
					}
					output.println(message);
					output.println("GAME OF ROCK, SCISSORS, PAPERS." + "\n" + "Enter your guess: (Type R for Rock, P for Paper, S for Scissors or Q to quit)");
					yourPlay = input.readLine().toUpperCase();
				}
		output.println("Your score is " + yourPoints + "." + "\n" + "Goodbye.");
			}

		
		}
	
