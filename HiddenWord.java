import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@GameInfo(authors = {
"Kent Collins" }, version = "Fall, 2017", gameTitle = "HiddenWord", description = "Sample demonstration of the FRQ.")
public class HiddenWord implements Servable {
	private String word;
	private String hint;

	public HiddenWord(String word) {
		this.word = word;
	}
	
	public HiddenWord() {
		this.word = Dictionary.randomBySize(5).toUpperCase();
		hint = "";
		for (int i = 0; i<word.length(); i++) {
			hint+="*";
		}
	}

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
		out.println("The word is "+word.length()+" letters long. Take a guess");
		while (hint.contains("*")) {
			String guess = in.readLine().trim().toUpperCase();
			hint = getHint(guess);
			out.println("Your hint is "+hint);
		}
		out.println("You guessed it!");
	}
}