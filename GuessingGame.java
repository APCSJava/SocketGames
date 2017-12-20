import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@GameInfo(authors = {
"Kent Collins" }, version = "Fall, 2017", gameTitle = "GuessingGame", description = "Completed version of HiddenWord FRQ.")

public class GuessingGame extends AbstractGame implements Servable {
	private String word;

	/** PRECONDITION: parameter is upper case */
	public GuessingGame(String word) {
		this.word = word;
	}
	
	public GuessingGame() {
		word = Dictionary.randomBySize(5, 8).toUpperCase();
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
	public void serve(BufferedReader in, PrintWriter out)
			throws IOException {
		out.println("The word is "+word.length()+" letters long.  Make a guess and I'll show you where you have the correct letters.  Letters that belong in the word but not in the given location will be marked by a '+'");
		String guess ="";
		while (!word.equals(guess)) {
			while(guess == null || guess.equals("") || guess.length()!=word.length()) {
				out.println("The word must be "+word.length()+ " letters long.");
				guess = in.readLine().trim().toUpperCase();
			}
			out.println(getHint(guess));
			guess = in.readLine().trim().toUpperCase();
		}
	}
}
