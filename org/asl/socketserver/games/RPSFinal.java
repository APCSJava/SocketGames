package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Naz Ozturk and lorenzo" }, version = "Winter, 2018", title = "Rock Paper Scissors", description = "A game of Rock Paper Scissors between you and the computer.")

public class RPSFinal implements Servable{
//final code for rock paper scissors project
	//public static void main(String[] args)  {
	
	@Override
	public void serve(BufferedReader input, PrintWriter out) throws IOException {
		//Scanner input = new Scanner(System.in);
		out.println("How many games?");
		int guess1 = Integer.parseInt(input.readLine().trim());
		String message = "";
		int numberGames = 0;
		if(guess1 > 10){
			message = "Sorry, but we aren't allowed to play that many";
		}else{
			numberGames = guess1;
		}
		out.println(message);

		
		int ties  = 0;
		int win = 0;
		int humanWin  = 0;
		String message2 = "";
		String  message3  = "";
		int guess2 = 0;
		int choice = 0;
		int choice2 = 0;
		int number = 0;
		String choice3 = "";
		String gameNum = "";
		
		for(int i = 0; i < numberGames; i++){
			choice2 = (int) (Math.random() * 3 + 1);
			choice = choice2;
			if(choice == 1) {
				choice3 = "Paper"; 
			}else if(choice == 2) {
				choice3 = "Scissors"; 
			}else if(choice == 3) {
				choice3 = "Rock"; 
			}
			number ++;
			gameNum = "This is game number " + number;
			out.println(gameNum);
			message2 = "3 = rock, 2 = scissors, 1 = paper. What's your choice?";
			out.println(message2);
			guess2 = Integer.parseInt(input.readLine().trim());
			message3 = "This my choice: " + choice3;
			out.println(message3);
			String end = "";
			if(choice == guess2){
				//tie
				ties ++;
				end = "Tie game, no winner";
			}else if(choice == 2 && guess2 == 3){
				//guess wins
				humanWin ++;
				//rock trumps scissors
				end = "You win!!";
			}else if(choice == 3 && guess2 == 1){
				//guess wins
				humanWin ++;
				//paper trumps rock
				end = "You win!!";
			}else if(choice == 2 && guess2 == 1){
				//choice wins
				win ++;
				//scissors trump paper
				end = "Wow. I win!!";
			}else if(choice == 3 && guess2 == 2){
				//choice wins
				win ++;
				//rock trumps scissors
				end = "Wow. I win!!";
			}else if(choice == 1 && guess2 == 2){
				//guess wins
				humanWin ++;
				//scissors trump paper
				end = "You win!!";
			}else if(choice == 1 && guess2 == 3){
				//choice wins
				win ++;
				//paper trumps rock
				end = "Wow. I win!!";
			}
			out.println(end);
		}
		String finalMessage = "Here's the final game score: I have won " + win + " game(s). You have won " + humanWin + " game(s). And " + ties + " game(s) ended in a tie.";
		out.println(finalMessage);
	}
}