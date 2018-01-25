package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Vikram Chowdhary" }, version = "Jan 2018", title = "Dice", description = "Roll some dice.")


public class DiceGame extends AbstractGame implements Servable {
	
	public DiceGame() {
		//empty constructor
	}
	
	private static int roll() {
		int result1 = (int) (Math.random() * 6 + 1);
		int result2 = (int) (Math.random() * 6 + 1);
		int result = result1 + result2;
		return result;
	}

	@Override
	public void serve(BufferedReader input, PrintWriter out) throws IOException {

		int ROLLS = 0;

		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		int seven = 0;
		int eight = 0;
		int nine = 0;
		int ten = 0;
		int eleven = 0;
		int twelve = 0;

		// greeting
		out.println("This program simulates the rolling of a pair of dice."
				+ "\nYou enter the number of times you want the computer to 'roll' the dice."
				+ "\nWatch out, very large numbers take a long time. In particular, numbers over 5000." + "\n"
				+ "\nHow many rolls? ");
		ROLLS = input.read();

		// rolling + counting
		for (int i = 0; i < ROLLS; i++) {
			int roll = roll();
			if (roll == 2) {
				two++;
			} else if (roll == 3) {
				three++;
			} else if (roll == 4) {
				four++;
			} else if (roll == 5) {
				five++;
			} else if (roll == 6) {
				six++;
			} else if (roll == 7) {
				seven++;
			} else if (roll == 8) {
				eight++;
			} else if (roll == 9) {
				nine++;
			} else if (roll == 10) {
				ten++;
			} else if (roll == 11) {
				eleven++;
			} else if (roll == 12) {
				twelve++;
			}
		}

		// calculating
		double twoProp = ((double) two / ROLLS) * 100;
		double threeProp = ((double) three / ROLLS) * 100;
		double fourProp = ((double) four / ROLLS) * 100;
		double fiveProp = ((double) five / ROLLS) * 100;
		double sixProp = ((double) six / ROLLS) * 100;
		double sevenProp = ((double) seven / ROLLS) * 100;
		double eightProp = ((double) eight / ROLLS) * 100;
		double nineProp = ((double) nine / ROLLS) * 100;
		double tenProp = ((double) ten / ROLLS) * 100;
		double elevenProp = ((double) eleven / ROLLS) * 100;
		double twelveProp = ((double) twelve / ROLLS) * 100;

		// construct message
		out.println("2 -- " + two + "/" + ROLLS + " -- " + twoProp + "%" + "\n3 -- " + three + "/" + ROLLS + " -- "
				+ threeProp + "%" + "\n4 -- " + four + "/" + ROLLS + " -- " + fourProp + "%" + "\n5 -- " + five + "/"
				+ ROLLS + " -- " + fiveProp + "%" + "\n6 -- " + six + "/" + ROLLS + " -- " + sixProp + "%" + "\n7 -- "
				+ seven + "/" + ROLLS + " -- " + sevenProp + "%" + "\n8 -- " + eight + "/" + ROLLS + " -- " + eightProp
				+ "%" + "\n9 -- " + nine + "/" + ROLLS + " -- " + nineProp + "%" + "\n10 -- " + ten + "/" + ROLLS
				+ " -- " + tenProp + "%" + "\n11 -- " + eleven + "/" + ROLLS + " -- " + elevenProp + "%" + "\n12 -- "
				+ twelve + "/" + ROLLS + " -- " + twelveProp + "%");

	}

}