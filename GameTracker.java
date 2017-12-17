import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Locates playable games within the project directory structure. Maintains a
 * list of playable games, along with game information collected from class
 * annotations and high scores stored in a flat file.
 * 
 * @author K. Collins
 * @version Fall, 2017
 *
 */
public class GameTracker {

	private static List<Class<? extends Servable>> gameList;
	private static Map<Class<? extends Servable>, String> gameInfo;
	private static Map<Class<? extends AbstractGame>, HighScore> highScores;

	/**
	 * Looks inside the current working directory and collects all file names having
	 * the extension .class
	 * 
	 * @return an array of files
	 */
	private static File[] findClassFilesInWorkingDirectory() {
		File directory = new File(".");
		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("class");
			}
		});
		return files;
	}

	/**
	 * Prepares a game menu string displaying the game index, game name, current
	 * high score, and high score holder's initials.
	 * 
	 * @return a menu string displaying information on available games
	 */
	public static String buildGameListMenu() {
		String s = "=====\tGAME LIST\t\tHigh Score\tInitials\n";
		int i = 0;
		for (Class<? extends Servable> c : gameList) {
			boolean hasHighScoreEntry = highScores.containsKey(c);
			s += (i++) + "\t" + c.getName();
			if (hasHighScoreEntry) {
				HighScore h = highScores.get(c);
				s += "\t\t" + h.getScore() + "\t\t"
						+ h.getInitials();
			}
			s += "\n";
		}
		s += "\nEnter the number of the game to play or 'q' to exit.\n";
		return s;
	}

	/**
	 * Checks that a submitted string can be safely converted to an integer.
	 * 
	 * @param s
	 *            the string to be tested
	 * @return true if s can be parsed to integer; false, otherwise
	 */
	public static boolean checkValidInteger(String s) {
		try {
			int i = Integer.parseInt(s);
			if (i < 0)
				return false;
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Examines user selection and returns either a String holding a menu, a new
	 * instance of the requested game, or null if neither of the above makes sense.
	 * 
	 * @param userSelection
	 * @return an object representing a menu string, a game instance or null
	 */
	public static Object handleUserSelection(String userSelection) {
		Object o = null;
		if (GameTracker.checkValidInteger(userSelection)) {
			try {
				o = gameList.get(Integer.parseInt(userSelection))
						.newInstance();
			} catch (NumberFormatException | InstantiationException
					| IllegalAccessException e) {
				// game not instantiated -- leave object null
				GameServer.LOGGER.warning(e.toString());
			} catch (IndexOutOfBoundsException e) {
				// chose a game not on the list
				GameServer.LOGGER.warning(e.toString());
			}
		} else {
			o = buildGameListMenu();
		}
		return o;
	}

	/**
	 * Returns members of the authors array in a well formed String
	 * 
	 * @param authors
	 * @return comma separated list of authors
	 */
	private static String formatAuthorString(String[] authors) {
		String authorString = "";
		for (int i = 0; i < authors.length - 1; i++) {
			authorString += authors[i];
			if (i < authors.length - 2)
				authorString += ", ";
			else
				authorString += " and "; // no Oxford commas
		}
		authorString += authors[authors.length - 1];
		return authorString;
	}

	/**
	 * Collects from the working directory all files that implement the Servable
	 * interface
	 * 
	 * @return a list holding all classes that implement Servable
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class<? extends Servable>> findServableClasses()
			throws ClassNotFoundException {
		List<Class<? extends Servable>> servableClasses = new ArrayList<Class<? extends Servable>>();
		for (File f : findClassFilesInWorkingDirectory()) {
			String nameWithExtension = f.getName();
			int idx = nameWithExtension.lastIndexOf(".class");
			Class classObj = Class
					.forName(nameWithExtension.substring(0, idx));
			Class[] interfaces = classObj.getInterfaces();
			if (interfaces.length > 0) {
				for (Class c : interfaces) {
					if (Servable.class.isAssignableFrom(c)
							&& !Modifier.isAbstract(
									classObj.getModifiers())) {
						servableClasses.add(classObj);
						break;
					}
				}
			}
		}
		GameServer.LOGGER.info(
				"GameTracker detected the following servable classes "
						+ servableClasses);
		return servableClasses;
	}

	/**
	 * Initializes the game database. Should be called on the class when starting
	 * the application. The GameServer class does this when starting the service.
	 * 
	 */
	public static void initialize() {
		try {
			gameInfo = new HashMap<Class<? extends Servable>, String>();
			gameList = findServableClasses();
			for (Class<? extends Servable> c : gameList) {
				Annotation[] annotations = c.getAnnotations();
				for (Annotation a : annotations) {
					if (a instanceof GameInfo) {
						GameInfo info = (GameInfo) a;
						gameInfo.put(c, formatGameInfoString(info));
					} else {
						gameInfo.put(c, null);
					}
				}
			}
			highScores = new HashMap<Class<? extends AbstractGame>, HighScore>();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Takes the information in a GameInfo annotation and prepares it for display.
	 * For uniformity, games may and should call this method when starting or
	 * restarting a game.
	 * 
	 * @param g
	 *            a GameInfo reference; a class level annotation on games
	 * @return a string appropriate for text display of game information
	 */
	public static String formatGameInfoString(GameInfo g) {
		String s = "=====";
		s += "\t" + g.gameTitle() + "\n";
		s += "\t" + g.description() + "\n";
		s += "\t" + formatAuthorString(g.authors());
		s += "\t" + g.version();
		return s;
	}

	/**
	 * Retrieves the GameInfo object associated with this class. The returned object
	 * is drawn from the game data map collected when the server was initialized.
	 * 
	 * @param c
	 *            the class on which to search for game information
	 * @return the GameInfo object loaded for the indicated class
	 */
	public static String getGameInfo(Class<Servable> c) {
		if (gameInfo.containsKey(c))
			return gameInfo.get(c);
		return c + " -- no game information available";
	}

	/**
	 * For the indicated game, get the current high score.
	 * 
	 * @param someClass
	 * @return the current recorded int; Integer.MIN_VALUE, if none found
	 */
	public static int getHighScoreValue(
			Class<? extends AbstractGame> someClass) {
		if (highScores.containsKey(someClass)) {
			return highScores.get(someClass).getScore();
		}
		return Integer.MIN_VALUE;
	}

	/**
	 * For the indicated game, get the initials of the high score holder.
	 * 
	 * @param someClass
	 * @return an initials string; null, if no string is found
	 */
	public static String getHighScoreInitials(
			Class<? extends AbstractGame> someClass) {
		if (highScores.containsKey(someClass)) {
			return highScores.get(someClass).getInitials();
		}
		return null;
	}

	/**
	 * Associates a HighScore object with the indicated game class.
	 * 
	 * @param someClass
	 *            the class for which a new high score has been achieved
	 * @param value
	 *            the integer value of the high score
	 * @param initials
	 *            the initials of the new record holder
	 */
	public static void setHighScore(
			Class<? extends AbstractGame> someClass, int value,
			String initials) {
		highScores.put(someClass, new HighScore(value, initials));
		writeHighScores();
	}

	/**
	 * Write out the high scores to a flat-file so can be loaded later
	 */
	private static void writeHighScores() {
		// TODO Auto-generated method stub

	}

}
