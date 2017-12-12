import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Library of methods for retrieving words from an English dictionary.
 * 
 * @author kentcollins
 *
 */
public final class Dictionary {

	private static Map<Integer, List<String>> mapBySize;
	private static Map<Character, List<String>> mapByFirstCharacter;

	static {
		// intialize class fields
	}

	private Dictionary() {
		// no, you do NOT get to make an instance. Use the static methods.
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
	 * Return a random word of at least a minimum size and up to a
	 * maximum size.
	 * 
	 * @param min the minimum length of a qualifying word
	 * @param max the maximum length of a qualifying word
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

}
