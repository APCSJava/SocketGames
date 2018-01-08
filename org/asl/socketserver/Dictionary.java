package org.asl.socketserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * Library that provides methods for retrieving words from a word source. The
 * default source is Google's 10000 most frequently used words in USA English
 * (w/o swear words) and is taken from
 * https://github.com/first20hours/google-10000-english
 * 
 * @author K. Collins
 * @version Fall, 2017
 *
 */
public final class Dictionary {

	private static Map<Integer, List<String>> mapBySize;
	private static Map<Character, List<String>> mapByFirstCharacter;
	private static List<String> immutableList;
	private static final String DEFAULT_DICT = "google-10000-english-usa-no-swears.txt";

	static {
		List<String> words = new ArrayList<String>();
		words.add("no dictionary loaded");
		try {
			String filename = DEFAULT_DICT;
			words = Files.readAllLines(Paths.get(filename));
		} catch (IOException e) {
			// the phrase 'no dictionary loaded' will populate the dictionary
		}
		immutableList = Collections.unmodifiableList(words);
		mapBySize = words.stream()
				.collect(Collectors.groupingBy(s -> s.length()));
		mapByFirstCharacter = words.stream()
				.collect(Collectors.groupingBy(s -> s.charAt(0)));
	}

	private Dictionary() {
		// Nope. Use the static methods.
	}

	/**
	 * Returns an immutable version of the word source. Immutability is needed so
	 * that a single copy of the dictionary may be stored in memory and used as a
	 * common resource by all threads. While every thread can therefore read freely
	 * from the list, no thread may write to, delete from, or otherwise modify the
	 * list.
	 * 
	 * @return a reference to an immutable word list
	 */
	public static List<String> getImmutableList() {
		return immutableList;
	}

	/**
	 * Return a random word beginning with the specified letter
	 * 
	 * @param c
	 *            the initial character
	 * @return a random word starting with c
	 */
	public static String randomByFirstCharacter(Character c) {
		List<String> words = mapByFirstCharacter.get(c);
		if (words == null)
			return null;
		int size = words.size();
		int randomIndex = (int) (Math.random() * size);
		return words.get(randomIndex);
	}

	/**
	 * Returns a random word beginning with the specified letter
	 * 
	 * @param s
	 *            the initial letter
	 * @return a random word beginning with s
	 */
	public static String randomByFirstLetter(String s) {
		return randomByFirstCharacter(s.charAt(0));
	}

	/**
	 * Return a random word of the specified size.
	 * 
	 * @param i
	 *            the size
	 * @return a random word of size i
	 */
	public static String randomBySize(int i) {
		List<String> words = mapBySize.get(i);
		if (words == null)
			return null;
		int size = words.size();
		int randomIndex = (int) (Math.random() * size);
		return words.get(randomIndex);
	}

	/**
	 * Return a random word between min and max characters long, inclusive.
	 * 
	 * @param min
	 *            the minimum length of a qualifying word
	 * @param max
	 *            the maximum length of a qualifying word
	 * @return a word meeting the length criteria
	 */
	public static String randomBySize(int min, int max) {
		List<String> words = new ArrayList<String>();
		for (int i = min; i <= max; i++) {
			words.addAll(mapBySize.get(i));
		}
		int randomIndex = (int) (Math.random() * words.size());
		return words.get(randomIndex);
	}

	/**
	 * Returns a random word from the word source
	 * 
	 * @return a random word from the entire dictionary
	 */
	public static String random() {
		int randomIndex = (int) (Math.random()
				* immutableList.size());
		return immutableList.get(randomIndex);
	}
}
