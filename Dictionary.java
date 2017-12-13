import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * Library of methods for retrieving words from an English dictionary.
 * 
 * @author kentcollins
 *
 */
public final class Dictionary {

	private static Map<Integer, List<String>> mapBySize;
	private static Map<Character, List<String>> mapByFirstCharacter;
	private static List<String> immutableList;

	static {
		List<String> words = new ArrayList<String>();
		words.add("no dictionary loaded");
		try {
			String filename = "google-10000-english-usa-no-swears.txt";
			words = Files.readAllLines(Paths.get(filename));
		} catch (IOException e) {
			// use the phrase 'no dictionary loaded' to populate the dictionary
		}
		immutableList = Collections.unmodifiableList(words);
		mapBySize = words.stream()
				.collect(Collectors.groupingBy(s -> s.length()));
		mapByFirstCharacter = words.stream()
				.collect(Collectors.groupingBy(s -> s.charAt(0)));
	}

	private Dictionary() {
		// no, you do NOT get to make an instance. Use the static methods.
	}

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
	 * Change the single letter string into a Character and delegate
	 * 
	 * @param s
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
	 * Return a random word of at least a minimum size and up to a maximum size.
	 * 
	 * @param min
	 *            the minimum length of a qualifying word
	 * @param max
	 *            the maximum length of a qualifying word
	 * @return
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
	 * Returns a random word, no constraints
	 * 
	 * @return a random word from the entire dictionary
	 */
	public static String random() {
		int randomIndex = (int) (Math.random() * immutableList.size());
		return immutableList.get(randomIndex);
	}

	/**
	 * For testing.
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		System.out.println("Random word: " + random());
		System.out.println("Random word 4: " + randomBySize(4));
		System.out
				.println("Random word 2 - 4: " + randomBySize(2, 4));
		System.out.println(
				"Random word z: " + randomByFirstCharacter('z'));
		System.out.println(
				"Random word z: " + randomByFirstLetter("z"));
		System.out.println(getImmutableList().get(9));
	}

}
