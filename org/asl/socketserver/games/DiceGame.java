package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.Servable;
import org.asl.socketserver.MenuInfo;
import java.util.Scanner;

@MenuInfo(authors = {
"Lindsay" }, version = "Winter, 2018", title = "DICE", description = "Mastermind meets Dice.")

public class DiceGame implements Servable {
	private String answer;
	private int roll1;
	//Scanner input = new Scanner(System.in);
	private int[] array;
	
	public DiceGame(int roll1, int[] array) {
		this.roll1 = roll1;

		this.array = array;
	}

	public DiceGame() {
	
	}
	
		
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		//output.println("HELLOOOOOOO");
		 int[] array = {0,0,0,0,0,0,0,0,0,0,0};

		answer = "yes";
		while(answer.equals( "yes")){
			output.println("How many times do you want to roll the dice?");
			int roll = Integer.parseInt(input.readLine());
			int min = 1;
			int max = 6;
			int totalRoll;
			
			for (int i = 0; i < roll; i++){
				int roll1 = (int) (Math.random() * max + min);
				int rollTwo = (int) (Math.random() * max + min);
				totalRoll = rollTwo + roll1;
				array[totalRoll-2] += 1;
		
				
			}

			output.println("Total spots:	Number of Times:");	
			for(int i1 = 0; i1 < array.length; i1++){
					
					output.println((i1+2) + "		" + array[i1]);
				}
			}
			output.println("If you want to play again, type yes");
			answer = input.readLine();
		}
	}

		