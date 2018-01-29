package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;


@MenuInfo(authors = {
		"Christina Leonard & Lauren Brantley" }, version = "Winter, 2018", title = "Craps", description = "Woohoo!")

public class Craps implements Servable {
	
	@Override
	public void serve(BufferedReader input, PrintWriter out) throws IOException {
		// TODO Auto-generated method stub
		
		out.println("GAME OF CRAPS." + "\n" + "7 or 11 will win you a point. 2, 3, or 12 will lose you a point." + "\n" + "Every other number won't do anything to your score." + "\n" + "PRESS Y TO KEEP PLAYING AND Q TO QUIT.");
		
		String yourPlay = input.readLine().toUpperCase();
		int yourPoints = 0;
		
		while(!yourPlay.equals("Q")) {
			int MIN = 1;
			int MAX = 6;
			
			int firstDice = (int) (Math.floor(MAX - MIN + 1) * Math.random() + MIN);
			int secondDice = (int) (Math.floor(MAX - MIN + 1) * Math.random() + MIN);
			int sum = firstDice + secondDice;
			
			int win1 = 7;
			int win2 = 11;
			
			int lose1 = 2;
			int lose2 = 3;
			int lose3 = 12;

			if(yourPlay.equals("Q")) {
				break;
			} else if (sum == win1 || sum == win2) {
				yourPoints += 1;
				out.println("You rolled a " + firstDice + " and a " + secondDice + ".\n" + "YAY! You recieve 1 point.");
			} else if (sum == lose1 || sum == lose2 || sum == lose3) {
				yourPoints -= 1;
				out.println("You rolled a " + firstDice + " and a " + secondDice + ".\n" + "You lost 1 point. :(");
			} else { 
				yourPoints += 0;
				out.println("You rolled a " + firstDice + " and a " + secondDice + ".\n" + "This roll doesn't do anything.");
			}
			out.println("Press Y to keep playing. Press Q to quit.");
			yourPlay = input.readLine().toUpperCase();
		}
		out.println("Your total score is: " + yourPoints + ".\n" + "Goodbye!");
		
		
		}
		
	}

