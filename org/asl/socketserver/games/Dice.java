package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.MenuInfo;

//get number of dice you roll
//generate two random numbers from 1-6 to get roll
//add dice
//keep track of how many times each number is rolled
//show the number of times

import org.asl.socketserver.Servable; 

@MenuInfo(authors = {
"Tomas Belinky, Grace Gerwe" }, version = "Winter, 2017", title = "Dice", description = "Dice simulation")


public class Dice implements Servable{
	
	public Dice() {}
	
	public  String run(String statement){
		
		int diceNum = Integer.parseInt(statement);
		int[] results = new int[11];

		int diceSum;
		
		for (int i = 0; i<diceNum; i++){
			int roll1 = getRandomNum(1,6);
			int roll2 = getRandomNum(1,6);
			diceSum = roll1 + roll2;
			
			results[diceSum-2]++;
		}

		String totalString = "";
		
		for(int i2 = 0; i2 < 11; i2++) {
			totalString += "The dice rolled a " + (i2+2) + "," + results[i2] + " times\n";
		}
		return totalString;
		
	}
	private static int getRandomNum(int min, int max){
		return (int)(Math.random() * max + min);
	}
	@Override
	public void serve(BufferedReader in, PrintWriter out) throws IOException {
		out.println("Enter number of times to roll 2 dice:\nOr type \"quit\" to exit Game:");
		
		String statement;
		
		while(true) {
			statement = in.readLine().trim();
			
			boolean gate = false;
			
			for(int i3 = 0; i3<statement.length(); i3++) {
				char c = statement.charAt(i3);
				if (!Character.isDigit(c)) {
					gate = true;
				}
			}
			
			if(statement.equals("quit")) {
				break;
			}else if(gate) {
				out.println("Invalid input, enter a number");
			}else {
				break;
			}
		}
		
		out.println(run(statement));	
		
	}
}