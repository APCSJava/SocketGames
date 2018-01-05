package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.BestScore;
import org.asl.socketserver.Dictionary;
import org.asl.socketserver.GameInfo;
import org.asl.socketserver.Servable;

@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "GuessingGame", description = "Completed version of HiddenWord FRQ.")

public class GuessingGame extends AbstractGame implements Servable {
	private String word;
	private int numGuesses;

	/** PRECONDITION: parameter is upper case
	 * 
	 * @param word - an uppercase word to guess
	 */
	public GuessingGame(String word) {
		this.word = word;
	}

	public GuessingGame() {
		word = Dictionary.randomBySize(5, 8).toUpperCase();
		numGuesses = 0;
	}

	/** PRECONDITION: guess has same length as word, is upper case 
	 * 
	 * @param guess a word to compare against the hidden word
	 * @return a hint string 
	 */
	public String getHint(String guess) {
		String hint = "";
		for (int i = 0; i < word.length(); i++) {
			String guessLetter = guess.substring(i, i + 1);
			if (word.substring(i, i + 1).equals(guessLetter))
				hint += guessLetter;
			else if (word.indexOf(guessLetter) != -1)
				hint += "+";
			else
				hint += "*";
		}
		return hint;
	}

	@Override
	public void serve(BufferedReader in, PrintWriter out)
			throws IOException {
		out.println(
				"Make a guess and I'll show you where you have the correct letters.  Letters that belong in the word but not in the given location will be marked by a '+'");
		String guess = "";
		while (!word.equals(guess)) {
			while (guess == null || guess.equals("")
					|| guess.length() != word.length()) {
				out.println("Your guess should be " + word.length()
						+ " letters long.");
				guess = in.readLine().trim().toUpperCase();
			}
			numGuesses++;
			out.println(getHint(guess));
			guess = in.readLine().trim().toUpperCase();
		}
		int score = word.length() * numGuesses;
		BestScore current = this.getBestScore();
		out.println("Nicely done. "+numGuesses+" guesses earns a score of "+score);

		if (current == null || score < current.getScore()) {
			out.println(score
					+ " is a new high score.  Please enter your initials...");
			String inits = in.readLine().trim();
			setBestScore(score, inits);
		}
	}
}
