

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.BestScore;
import org.asl.socketserver.Servable;
import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;

@MenuInfo(authors = {
"Nick Mannhardt and Natalie Vann" }, version = "Winter, 2018", title = "Chemist Game", description = "Either save the world, or explode.")


public class Chemist extends AbstractGame implements Servable {

	public static int randomNum() {
		int MIN = 1;
		int MAX = 50;
		int output = (int)((MAX - MIN + 1) * Math.random() + MIN);
		return output;
	}

	
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		
		int lives = 3;
		int rounds = 0;
		//input directions
		output.println("Do you want instructions? Type Yes or y if wanted or press anything (or type anything) to continue");
		String instructions = input.readLine().trim().toUpperCase();
		if(instructions.equals("YES") || instructions.equals("Y")) {
			output.println("The fictitious chemical, kryptocyanic acid, can only be diluted by the ratio of 7 parts water to 3 parts acid."); 		
			output.println("Any other ration causes an unstable compound which soon explodes."); 
			output.println("Given an amount of acid, you must determine how much water to add for dilution.");
			output.println("If you're more than 5% off, you lose one of your three lives!");
			
			
			
		} else {
			
		}
		
		output.println("If you would like to quit (I don't know why you would WANT to) type q or quit. \n");
		
		while(lives != 0) {
			int acid = randomNum();
			String message = acid + " liters of Kryptocynanic. How much water?";
			output.println(message);
			String stringAnswer = input.readLine().trim().toLowerCase();
			int answer = 0;
			if(stringAnswer.equals("q") || stringAnswer.equals("quit")) {
				break;
			}
			try {
				answer = Integer.parseInt(stringAnswer);
			} catch (NumberFormatException e) {
				output.println("hahaha that's not a number");
			}
			
			double limitDouble = (acid/3.0) * 7.0;
			int upperlimit = (int)limitDouble + 1;
			int lowerlimit = (int)limitDouble;

			if(answer<=upperlimit && answer>= lowerlimit){
				output.println("Good job, you may breathe now, but don't inhale the fumes!");
				rounds++;
			} else {
				lives--;
				output.println("Sizzle! You have just been desalinated into a blob of quivering protoplasm! You have " + lives + " lives left!");
			}
		}
		
		if(lives==0) {
			output.println("Aw you lost. Wah wah, you will get over it! \nYou completed " + rounds + "!");
			BestScore current = this.getBestScore();
			output.println("Nicely done. " + rounds + " is your score!");
			if (rounds > current.getScore()) {
				output.println(rounds + " is a new high score.  Please enter your initials...");
				String inits = input.readLine().trim();
				setBestScore(rounds, inits);
			}
		} else {
			output.println("Why did you stop? You had " + lives + " lives left!");
			BestScore current = this.getBestScore();
			output.println("Nicely done. " + rounds + " is your score!");
			if (rounds < current.getScore()) {
				output.println(rounds + " is a new high score.  Please enter your initials...");
				String inits = input.readLine().trim();
				setBestScore(rounds, inits);
			}
		}
	}
}

