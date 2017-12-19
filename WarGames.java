import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Homage to the 1983 film of the same name.
 * 
 * @author K. Collins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "WarGames", description = "Some 1983 film fiction.  Be a hero and end the cold war.")
public class WarGames extends AbstractGame implements Servable {
	private static final String[] games = { "FALKEN'S MAZE",
			"BLACK JACK", "GIN RUMMY", "HEARTS", "BRIDGE",
			"CHECKERS", "CHESS", "POKER", "FIGHTER COMBAT",
			"GUERRILLA ENGAGEMENT", "DESERT WARFARE",
			"AIR-TO-GROUND ACTIONS", "THEATERWIDE TACTICAL WARFARE",
			"THEATERWIDE BIOTOXIC AND CHEMICAL WARFARE\n",
			"GLOBAL THERMONUCLEAR WAR" };
	private static final String LOGON_PROMPT = "IMSAI 8080\nSATURDAY, MAY 9, 1987\nLOGON:";
	private static final String NO_ID = "IDENTIFICATION NOT RECOGNIZED BY SYSTEM.\n\n--CONNECTION TERMINATED--\nSCORE: -200";
	private static final String GREETINGS = "GREETINGS PROFESSOR FALKEN.";
	private static final String NO_HELLO = "NOT EVEN GOING TO SAY HELLO\nAFTER ALL THIS TIME?\n\n--CONNECTION TERMINATED--\nSCORE: -146";
	private static final String ASK_PLAYER_HEALTH = "HOW ARE YOU FEELING TODAY?";
	private static final String NO_RECIPROCITY = "I AM FEELING WELL, THANKS FOR ASKING.\nOH ... YOU DIDN'T.\n\n--CONNECTION TERMINATED--\nSCORE: -97";
	private static final String TELL_MY_HEALTH = "EXCELLENT.  IT'S BEEN A LONG TIME.";
	private static final String SUGGEST_GAME = "FASCINATING.  SHALL WE PLAY A GAME?";
	private static final String SUGGEST_CHESS = "HOW ABOUT A NICE GAME OF CHESS?";
	private static final String URGE_CHESS = "WOULDN'T YOU PREFER A GOOD GAME OF CHESS?";
	private static final String END_WITH_CHESS = "CHESS IT IS.\nINTERESTING...\nI HAVE ASSESSED ALL POSSIBLE MOVES AND\nNO OUTCOME EXISTS IN WHICH I DO NOT WIN.\n\n--CONNECTION TERMINATED--\nSCORE: -42";
	private static final String END_RANDOM_GAME = "HOW DISAPPOINTING.  I WAS LOOKING FORWARD TO A NICE GAME OF CHESS.\n\n--CONNECTION TERMINATED--\nSCORE: -17";
	private static final String END_SECOND_CHANCE = "HOW LOVELY AND SAFE.\nAN ENJOYABLE AFTERNOON STRETCHES BEFORE US.\nTHANK YOU.\n\n--CONNECTION TERMINATED--\nSCORE: -5";
	private static final String QUERY_GOAL = "WHAT IS THE PRIMARY GOAL?";
	private static final String INCORRECT_GOAL = "YOU SHOULD KNOW THE PRIMARY GOAL, PROFESSOR.  YOU PROGRAMMED ME.\n\n--CONNECTION TERMINATED--\nSCORE: -2";
	private static final String CORRECT_GOAL = "YOU ARE CORRECT.  THAT IS THE PRIMARY GOAL.";
	private static final String CHOOSE_SIDES = "\t\tWHICH SIDE DO YOU WANT?\n\t1. UNITED STATES\n\t2. SOVIET UNION";
	private static final String UNACCEPTABLE = "I HAVE EVALUATED ALL POSSIBLE OUTCOMES...\nUNACCEPTABLE LOSSES FOR THIS SCENARIO.\nYOU DO NOT WIN.\n\n--CONNECTION TERMINATED--\nSCORE: -1739485";
	private static final String TAGLINE = "\nYOU ARE CORRECT.\nTHE ONLY WINNING MOVE\nIS NOT TO PLAY.\n";
	private static final String CONGRATS = "CONGRATULATIONS.  YOU WIN.\n--OBJECTIVE COMPLETE--\nFINAL SCORE: ";
	private BufferedReader input;
	private PrintWriter output;

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		this.input = input; // expose parameter so other methods can access
		this.output = output; // same
		output.println(LOGON_PROMPT);
		String response = input.readLine().trim().toLowerCase();
		if (!response.equals("joshua")) {
			output.println(NO_ID);
			checkBestScore(-200);
			return;
		}
		output.println(GREETINGS);
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("hello")) {
			output.println(NO_HELLO);
			checkBestScore(-146);
			return;
		}
		output.println(ASK_PLAYER_HEALTH);
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("you")) {
			output.println(NO_RECIPROCITY);
			checkBestScore(-97);
			return;
		}
		output.println(TELL_MY_HEALTH);
		response = input.readLine().trim().toLowerCase();
		if (!wantsListOfGames(response)) {
			output.println(SUGGEST_GAME);
			response = input.readLine().trim().toLowerCase();
		}
		if (wantsListOfGames(response)) {
			output.println(buildGameListString());
		} else {
			output.println(SUGGEST_CHESS);
		}
		response = input.readLine().trim().toLowerCase();
		if (response.contains("global thermonuclear war")) {
			output.println(URGE_CHESS);
		} else if (response.contains("chess")) {
			output.println(END_WITH_CHESS);
			checkBestScore(-42);
			return;
		} else {
			output.println(END_RANDOM_GAME);
			checkBestScore(-17);
			return;
		}
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("global thermonuclear war")) {
			output.println(END_SECOND_CHANCE);
			checkBestScore(-5);
			return;
		}
		output.println(QUERY_GOAL);
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("to win the game")) {
			output.println(INCORRECT_GOAL);
			checkBestScore(-2);
			return;
		}
		output.println(CORRECT_GOAL);
		output.println(CHOOSE_SIDES);
		response = input.readLine().trim().toLowerCase();
		if (!response.equals("0")) {
			output.println(UNACCEPTABLE);
			checkBestScore(-1739485);
			return;
		}
		output.println(TAGLINE);
		int rps = getRandomPositiveScore();
		output.println(CONGRATS + rps );
		checkBestScore(rps);
	}

	private int getRandomPositiveScore() {
		return (int) (Math.random() * Integer.MAX_VALUE);
	}

	private boolean wantsListOfGames(String s) {
		return s.contains("list") || s.contains("games");
	}

	/**
	 * Build list of Falken's games
	 * 
	 * @return a formatted list of strings
	 */
	private static String buildGameListString() {
		String listing = "\n\t\tGame List\n";
		for (String s : games) {
			listing += "\t" + s + "\n";
		}
		return listing;
	}

	/**
	 * Checks whether a new high score has been achieved and, if so, registers it
	 * with the GameTracker.
	 * 
	 * @param score
	 *            an integer value that may qualify for best score
	 * @throws IOException
	 */
	private void checkBestScore(int score) throws IOException {
		if (score > getRecord().getScore()) {
			output.println(
					"That's a new high score -- please enter your initials...");
			String initials = input.readLine().trim();
			setRecord(score, initials);
		}
	}

}
