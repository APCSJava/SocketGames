package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Dom Alberts" }, version = "January, 2018", title = "Dice Roll", description = "Roll a biggie")

public class DiceRoll extends AbstractGame implements Servable{
	
	private int rollCount;


	public DiceRoll() {
	}
	
	
	public static String message() {
		String str = "This program simulates the rolling of a pair of dice.\n";
		str += "You enter the number of times you want the computer to roll the dice.\n";
		str += "Number of rolls: ";
		return str;
	}
	
	public double frequency(int rolledCount) {
		double rCount = (double) rolledCount;
		return rCount/rollCount*100;
	}
	
	public void setRollCount(int totalRolls) {
		rollCount = totalRolls;
	}
	
	public boolean isInteger(String str) {
		
		for(int i = 0; i<str.length(); i++) {
			if(!(str.charAt(i)>=48&&str.charAt(i)<=57)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		
		output.println(message());
	    
	    String rollsStr = input.readLine();
	    if(isInteger(rollsStr)) {
	    		int numRolls = Integer.parseInt(rollsStr);
	    		output.println("Dice");
	    
	    		setRollCount(numRolls);
	    
	    		int[] outcomes = new int[11];

	    		for(int a = 0; a < rollCount; a++){
	    			int die1 = (int) (Math.random()*6+1);
	    			int die2 = (int) (Math.random()*6+1);
	    			int total = die1+die2;
	    			for(int b = 0; b < 11; b++) {
	    				if(total==b+2) {
	    	  			outcomes[b]++;
	    				}
	    			}
	    		}

	    		output.println("Total: Times Total Rolled, Frequency");
	    
	    		
	    		for(int i = 0; i < 10; i++) {
	    			double frequencyNum = frequency(outcomes[i]);
	    			output.print((i+2) + ": " + outcomes[i] + ", " + frequencyNum + "\n");
	    		}
	    		output.println(12 + ": " + outcomes[10] + ", " + frequency(outcomes[10]));
	    } else {
	    		output.println("You must enter an integer. Thanks for trying!");
	    }
	}
		
}
	