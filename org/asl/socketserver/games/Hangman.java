package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.Servable;
import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.Dictionary;
import org.asl.socketserver.MenuInfo;

@MenuInfo(authors = {
		"Kent Collins" }, version = "Spring, 2018", title = "Hangman", description = "Guess the letters to reveal the word.")

public class Hangman extends AbstractGame implements Servable {
	private String hidden;
	private String solutionSoFar;
	private int numIncorrect;
	private final int MAX_INCORRECT = 6;

	public Hangman() {
		this(Dictionary.randomBySize(5, 8).toUpperCase());
	}

	public Hangman(String s) {
		hidden = s;
		solutionSoFar = "";
		for (int i = 0; i < hidden.length(); i++) {
			solutionSoFar += "*";
		}
		numIncorrect = 0;
	}

	@Override
	public void serve(BufferedReader in, PrintWriter out) throws IOException {
		out.println("Welcome to Hangman -- try to reveal the hidden word by guessing letters.");
		out.println("After each guess, I will show you where the letter appears, if it appears,");
		out.println("or penalize you, if it does not appear.");
		out.println("The word I am considering is " + hidden.length() + " letters long.\nGuess a letter:");
		String guess = in.readLine().trim().toUpperCase();
		while (true) {
			if (guess.length() == 1) {
				checkLetter(guess);
			} else {
				checkWord(guess);
			}
			if (hidden.equals(solutionSoFar)) {
				out.println(buildCongratulationsMessage());
				break;
			} else if (numIncorrect > MAX_INCORRECT) {
				out.println(buildLoseMessage());
			} else {
				out.println(buildProgressMessage(guess));
			}
			
			out.println(solutionSoFar);
			guess = in.readLine().trim().toUpperCase();
		}
	}

	private String buildLoseMessage() {
		return "I'm sorry, but that is not correct.  The word was " + hidden;
	}

	private String buildProgressMessage(String guess) {
		if (hidden.contains(guess)) return "Yes, there is at least one " + guess + " in the word.";
		return "Sorry, there is no "+guess+" in the word.";
	}

	private String buildCongratulationsMessage() {
		// TODO Auto-generated method stub
		return "You guessed it!  The word was " + hidden;
	}

	private void checkWord(String guess) {
		// TODO Auto-generated method stub

	}

	private void checkLetter(String guess) {
		if (hidden.indexOf(guess) < 0) {
			numIncorrect++;
		} else {
			String updated = "";
			for (int i = 0; i < hidden.length(); i++) {
				String hiddenLetter = hidden.substring(i, i + 1);
				String knownLetter = solutionSoFar.substring(i, i + 1);
				if (hiddenLetter.equals(guess)) {
					updated += guess;
				} else
					updated += knownLetter;
			}
			solutionSoFar = updated;
		}

	}

}
