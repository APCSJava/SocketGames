package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.Dictionary;
import org.asl.socketserver.GameInfo;
import org.asl.socketserver.Servable;

@GameInfo(authors = {
"Kent Collins" }, version = "Fall, 2017", gameTitle = "Hidden Word", description = "Bestest game ever!")
public class HiddenWord extends AbstractGame implements Servable{
	private String word;
	private int numGuesses;

	/** PRECONDITION: parameter is upper case */
	public HiddenWord(String word) {
		this.word = word;
		numGuesses = 0;
	}
	
	public HiddenWord() {
		this(Dictionary.randomBySize(5).toUpperCase());
	}

	/** PRECONDITION: guess has same length as word, is upper case */
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
	public void serve(BufferedReader br, PrintWriter pw) throws IOException {
		pw.println("Enter a word "+word.length()+" letters long.");
		String guess = br.readLine().trim().toUpperCase();
		numGuesses++;
		while(!guess.equals(word)) {
			pw.println(this.getHint(guess));
			guess = br.readLine().trim().toUpperCase();
			numGuesses++;
		}
		pw.println("Congrat -- you guessed it !  You used "+numGuesses+" guesses.");
		if (numGuesses<getBestScore().getScore()) {
			pw.println("Enter your initials, you wonderful thing");
			String inits = br.readLine().trim().toUpperCase();
			this.setBestScore(numGuesses, inits);
		}
	}
}
