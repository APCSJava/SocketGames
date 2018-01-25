package org.asl.socketserver.games;

import java.util.Scanner;

public class HangmanMenu {
	private static int turns = 0;;	

	/**
	* Gets user's character guess and adds a turn
	*
	* @return user's guess
	*/
	public static String getLetter() {
		String letter;

		while (true) {
			System.out.println("What letter would you like to guess?\nType quit to exit.");
			Scanner in = new Scanner (System.in);
			letter = in.nextLine();
			letter = letter.trim().toLowerCase();

			//check response
			if (letter.equals("quit")) {
				break;
			//if is more than one charachter
			} else if (letter.length() > 1) {
				System.out.println("Invalid letter.");
			//if is not a letter	
			} else if (!Character.isLetter(letter.charAt(0))){
				System.out.println("Invalid letter.");
			} else {
				break;
			}
		}
		System.out.println();
		turns++;
		return letter;
	}

	/**
	*/
	public static void usedLetter(char c) {
		System.out.println("You've already guessed \"" + c + "\" before.\nTry again.\n");
	}

	
	/**
	* Prints out number of lives left lives and number of letters to guess in word
	*
	* @param word
	* 	Word that user is trying to guess
	*/
	public static void stats(Word word) {
		System.out.println("You have " + word.getLives() + " lives left\nThere are " + word.unkownLetters() + " unknown letters left\n");
	}

	/**
	* Prints out winning message
	*
	* @param word
	* 	Word that user is trying to guess
	*/
	public static void win(Word word) {
		System.out.println("You win!\nIt took you " + turns + " turns to guess the word \"" + word.getWord() + "\"");
	}

	/**
	* Prints out losing message
	*
	* @param word
	* 	Word that user is trying to guess
	*/
	public static void lose(Word word) {
		System.out.println("You lose\nYou took " + turns + " turns\nThe word was \"" + word.getWord() + "\"");
	}
	
	public static void addTurn() {
		turns++;
	}
}