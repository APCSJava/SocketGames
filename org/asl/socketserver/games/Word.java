package org.asl.socketserver.games;

public class Word {
	private boolean[] guess;
	private char[] word;
	private String usedLetters = "";
	private int length;
	private int lives = 10;

	//a
	
	public Word() {
		word = generateWord().toCharArray();
		length = word.length;
		guess = new boolean[length];
		setGuessesToFalse();
	}

	public Word(String word) {
		this.word = word.toCharArray();
		length = word.length();
		guess = new boolean[length];
		setGuessesToFalse();
	}

	/**
	* Returns a random word from an array of words
	*
	* @return a random word
	*/
	public String generateWord() {
		String[] words = {"hello","amazing","football", "backpack", "tennis", "shoe", "robot", "cloth", "apple", "telephone"};
		return words[randomNum(0, words.length)];
	}

	/**
	* Generates a random number
	*
	* @param max
	*	Maximum value of set
	*
	* @param min
	*	Minimum value of set
	*
	* @return random number inbetween min and max
	*/
	private static int randomNum(int min, int max) {
		int random = (int)(Math.random() * max + min);
		return random;
	}

	/**
	* Sets all values in guess boolean array to false
	*/
	public void setGuessesToFalse() {
		for (int i = 0; i < guess.length; i++) {
			guess[i] = false;
		}
	}

	/**
	* Displays letter if letter has been guessed
	* If letter has not been guessed "_" is printed
	*/
	public String display() {
		String totalString = "";
		
		for (int i = 0; i < length; i++) {
			if (guess[i]) {
				totalString += word[i] + " ";
			} else {
				totalString+= "_ ";
			}
		}

		return totalString;
	}

	/**
	* Checks if user's character is in target word
	* if it is not then subtract a life
	*
	* @param c
	* 	character that user has guessed
	*/
	public void checkLetter(char c) {
		boolean gate = false;

		for (int i = 0; i < length; i++) {
			if (word[i] == c) {
				guess[i] = true;
				gate = true;
			}
		}
		usedLetters += c + "";
		if (!gate) lives--;
	}

	/**
	* Checks if the user has already guessed a character before
	*
	* @param c
	* 	character that user has guessed
	*
	* @return true if the player has already used character c
	*/
	public boolean hasUsedLetter(char c) {
		boolean gate = false;

		for (int i = 0; i < usedLetters.length(); i++) {
			if (c == usedLetters.charAt(i)) gate = true;
		}

		return gate;
	}

	/**
	* Gets number of lives user has left
	*
	* @return number of lives user has left
	*/
	public int getLives() {
		return lives;
	}

	/**
	* Checks if all the letters have been guessed
	*
	* @return true if all the letters have been guessed
	*/
	public boolean isCorrect() {
		boolean gate = true;

		for (int i = 0; i < length; i++) {
			if (!guess[i]) {
				gate = false;
			}
		}

		return gate;
	}

	/**
	* Checks if user still has more than 0 lives
	*
	* @return true if user has more than 0 lives
	*/
	public boolean isAlive() {
		if (lives > 0) return true;
		return false;
	}

	/**
	* Gets number of letters to be guessed
	*
	* @return number of letters that havent been guessed
	*/
	public int unkownLetters() {
		int count = 0;
		for (int i = 0; i < length; i++) {
			if(!guess[i]) count++;
		}

		return count;
	}

	/**
	* Gets target word
	*
	* @return target word
	*/
	public String getWord() {
		String totalString = "";

		for (int i = 0; i < length; i++) {
			totalString += word[i] + "";
		}

		return totalString;
	}
}