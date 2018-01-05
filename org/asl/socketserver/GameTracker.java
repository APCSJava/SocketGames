package org.asl.socketserver;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * Locates servable classes within the project directory structure. Maintains a
 * list of servables, as well as information collected from class type GameInfo
 * annotations and high scores from a local flat file.
 * 
 * @author K. Collins
 * @version Fall, 2017
 *
 */
public class GameTracker {

	private static List<Class<? extends Servable>> gameList;
	private static Map<Class<? extends Servable>, String> gameInfo;
	private static Map<Class<? extends AbstractGame>, BestScore> bestScores;

	private static final String HIGH_SCORE_FILE = "best-scores.csv";

	/**
	 * Looks inside the current working directory and collects all file names having
	 * the extension .class
	 * 
	 * @return an array of files
	 */
	private static File[] findClassFilesInPackage() {
		// File directory = new File(".");
		// preceding line does not work when files split into different package folders
		File directory = new File(GameTracker.class.getResource("/org/asl/socketserver/games/").getFile());
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
		String s = "=====\tGAME LIST\t\tRecord Score\tInitials\n";
		int i = 0;
		for (Class<? extends Servable> c : gameList) {
			boolean hasHighScoreEntry = bestScores.containsKey(c);
			s += (i++) + "\t" + c.getSimpleName();
			if (hasHighScoreEntry) {
				BestScore r = bestScores.get(c);
				s += "\t\t" + r.getScore() + "\t\t" + r.getHolder();
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
	 *            string representing the user's choice
	 * @return an object representing a menu string, a game instance or null
	 */
	public static Object handleUserSelection(String userSelection) {
		Object o = null;
		if (GameTracker.checkValidInteger(userSelection)) {
			try {
				o = gameList.get(Integer.parseInt(userSelection)).newInstance();
			} catch (NumberFormatException | InstantiationException | IllegalAccessException e) {
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
	 *             if the requested class is not found
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class<? extends Servable>> findServableClasses() throws ClassNotFoundException {
		List<Class<? extends Servable>> servableClasses = new ArrayList<Class<? extends Servable>>();
		for (File f : findClassFilesInPackage()) {
			System.out.println(f);
			String nameWithExtension = f.getName();
			System.out.println(nameWithExtension);
			int idx = nameWithExtension.lastIndexOf(".class");
			String searchString = nameWithExtension.substring(0, idx);
			Class classObj = Class.forName("org.asl.socketserver.games."+searchString);
			Class[] interfaces = classObj.getInterfaces();
			if (interfaces.length > 0) {
				for (Class c : interfaces) {
					if (Servable.class.isAssignableFrom(c) && !Modifier.isAbstract(classObj.getModifiers())) {
						servableClasses.add(classObj);
						break;
					}
				}
			}
		}
		GameServer.LOGGER.info("GameTracker detected the following servable classes " + servableClasses);
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
			bestScores = new HashMap<Class<? extends AbstractGame>, BestScore>();
			loadBestScores();

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
	 * For the indicated game, get the current best score.
	 * 
	 * @param someClass
	 *            a class reference
	 * @return the current best score; null, if not found
	 */
	public static BestScore getBestScore(Class<? extends AbstractGame> someClass) {
		if (bestScores.containsKey(someClass)) {
			return bestScores.get(someClass);
		}
		return null;
	}

	/**
	 * Associates a BestScore with the indicated game class.
	 * 
	 * @param someClass
	 *            the class for which a new high score has been achieved
	 * @param record
	 *            a record indicating the best score and initials of who set
	 */
	public static void setBestScore(Class<? extends AbstractGame> someClass, BestScore record) {
		bestScores.put(someClass, record);
		writeBestScores();
	}

	/**
	 * Write out the best scores to a flat-file which can be loaded later
	 */
	private static void writeBestScores() {
		Set<String> scoreInfo = new HashSet<String>();
		Path p = Paths.get(HIGH_SCORE_FILE);
		for (Class<? extends AbstractGame> c : bestScores.keySet()) {
			String classname = c.getName();
			BestScore r = bestScores.get(c);
			int i = r.getScore();
			String initials = r.getHolder();
			String info = classname + "," + i + "," + initials;
			scoreInfo.add(info);
		}
		try {
			Files.write(p, scoreInfo, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Retrieve and load the best scores from a csv file. If the file is not
	 * present, then the best score table is effectively reset.
	 */
	@SuppressWarnings("unchecked")
	private static void loadBestScores() {
		List<String> words = new ArrayList<String>();
		try {
			String filename = HIGH_SCORE_FILE;
			words = Files.readAllLines(Paths.get(filename));
			for (String s : words) {
				String[] data = s.trim().split(",");
				if (data.length == 3) {
					Class<? extends AbstractGame> c = (Class<? extends AbstractGame>) Class.forName(data[0]);
					int i = Integer.parseInt(data[1]);
					String initials = data[2];
					bestScores.put(c, new BestScore(i, initials));
				}
			}
		} catch (NoSuchFileException e) {
			// oh, well -- no high scores, I guess...
			GameServer.LOGGER.warning("Best scores file not found.");
		} catch (ClassNotFoundException e) {
			GameServer.LOGGER.warning("Unable to open the best score for a class.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
