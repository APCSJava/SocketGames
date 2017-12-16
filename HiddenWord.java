public class HiddenWord {
	private String word;

	/** PRECONDITION: parameter is upper case */
	public HiddenWord(String word) {
		this.word = word;
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
}
