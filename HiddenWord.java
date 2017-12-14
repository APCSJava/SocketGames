import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@GameInfo(authors = {
"Kent Collins" }, version = "Fall, 2017", gameTitle = "Hidden Word", description = "A simple demonstration of the FRQ.")
public class HiddenWord implements Servable {
	private String word;

	public HiddenWord(String word) {
		this.word = word;
	}
	
	public HiddenWord() {
		this.word = Dictionary.randomBySize(5).toUpperCase();
	}

	public String getHint(String guess) {
		// must decide how to handle incorrect precondition

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
		out.println("The word is "+word.length()+" letters long. Take a guess");
		while (true) {
			String guess = in.readLine().trim().toUpperCase();
			if (guess.length()!=word.length()) {
				out.println("The guess must be "+word.length()+" long");
				continue;
			}
			String hint = getHint(guess);
			if (hint.equals(word)) break;
			out.println("Your hint is "+hint);
		}
		out.println("You guessed it!");
	}
}