package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;
@MenuInfo(authors = {
"Alex Dowd" }, version = "Winter, 2018", title = "Guess", description = "Guess the number.")

public class Guess implements Servable {
	public static void main(String[] args) {
	}

	private static int pickRandomNumber(int limit) {
		
		return (int) (Math.random() * limit);
	}
	
	private static boolean lowOrHigh(int random, int theGuess) {
		if (theGuess > random) {
			return true;
		} else {
			return false;
	}
	}

	private static boolean isCorrect(int random, int theGuess) {

		if (random == theGuess) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void serve(BufferedReader input, PrintWriter out) throws IOException {
		// TODO Auto-generated method stub

		out.println(
				"This is a guessing game. I'll think of a number between 0 and any number you want. Then you have to guess what it is.");

		out.println("Enter a number: ");
		int l = Integer.parseInt(input.readLine());
		int rand = pickRandomNumber(l);
		int count = 0;
		
		System.out.println("I'm thinking of a number between 0 and " + l + ". Now make a guess:");
		int g = Integer.parseInt(input.readLine());
		//input.close();
			
		while (true) {
		if (isCorrect(rand, g) == true) {
			count++;
			out.println("You got it in " + count + " guesses!");
			break;
		} else {
			if (lowOrHigh(rand, g) == true) {
			out.println("Too high. Try a smaller answer.");
			g = Integer.parseInt(input.readLine());
			count++;
			} else {
				out.println("Too low. Try a bigger answer.");
				g = Integer.parseInt(input.readLine());
				count++;
			}
		}
		}
	}

}

// find out how to get it to loop until the user gets it right