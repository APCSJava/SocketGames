package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Tomas Belinky and Grace Gerwe" }, version = "January, 2018", title = "Hangman", description = "Classic Hangman Game.")

public class Hangman implements Servable{
	
	public Hangman() {}

	@Override
	public void serve(BufferedReader in, PrintWriter out) throws IOException {
		Word word = new Word();
		
		int turns = 0;
		
		while(true) {
			out.println(word.display());
			
			String letter;
			
			while (true) {
				out.println("What letter would you like to guess?\nType quit to exit.");
				letter = in.readLine();
				letter = letter.trim().toLowerCase();

				//check response
				if (letter.equals("quit")) {
					break;
				//if is more than one charachter
				} else if (letter.length() > 1) {
					out.println("Invalid letter.");
				//if is not a letter	
				} else if (!Character.isLetter(letter.charAt(0))){
					out.println("Invalid letter.");
				} else {
					break;
				}
			}
			
			turns++;
			
			
			if (letter.equals("quit")) {
				out.println("You lose\nYou took " + turns + " turns\nThe word was \"" + word.getWord() + "\"");
				break;
			}
			char letterChar = letter.charAt(0);

			if (word.hasUsedLetter(letterChar)) out.println("You've already guessed \"" + letterChar + "\" before.\nTry again.\n");
			else word.checkLetter(letterChar);

			if (!(word.isAlive())) {
				out.println("You lose\nYou took " + turns + " turns\nThe word was \"" + word.getWord() + "\"");
				break;
			}

			if (word.isAlive() && word.isCorrect()) {
				out.println(word.display());
				out.println("You win!\nIt took you " + turns + " turns to guess the word \"" + word.getWord() + "\"");
				break;
			}

			out.println("You have " + word.getLives() + " lives left\nThere are " + word.unkownLetters() + " unknown letters left\n");
		}
		
	}
}