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
		String instructions = "Welcome to Hangman -- try to reveal the hidden word by guessing letters.\n";
		instructions+="If the letter appears in the word, I will show you where.\n";
		instructions+="If the letter does not appear, I will assess a strike.\n";
		instructions+="You may have up to "+MAX_INCORRECT+" strikes before you lose.\n";
		instructions+="The word I am considering is " + hidden.length() + " letters long.\nGuess a letter:";
		out.println(instructions);
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
				break;
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
		return "Sorry, there is no "+guess+" in the word. -"+numIncorrect;
	}

	private String buildCongratulationsMessage() {
		return "You guessed it!  The word was " + hidden;
	}

	private void checkWord(String guess) {
		if (guess.equals(hidden)) {
			solutionSoFar = hidden;
		} else {
			numIncorrect = MAX_INCORRECT+1;
		}

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
