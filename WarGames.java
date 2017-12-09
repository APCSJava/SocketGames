import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Homage to the 1983 film of the same name.
 * 
 * @author kentcollins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "WarGames", description = "1983 film fiction.")
public class WarGames implements Servable {
	private static final String[] games = { "FALKEN'S MAZE",
			"BLACK JACK", "GIN RUMMY", "HEARTS", "BRIDGE",
			"CHECKERS", "CHESS", "POKER", "FIGHTER COMBAT",
			"GUERRILLA ENGAGEMENT", "DESERT WARFARE",
			"AIR-TO-GROUND ACTIONS", "THEATERWIDE TACTICAL WARFARE",
			"THEATERWIDE BIOTOXIC AND CHEMICAL WARFARE\n",
			"GLOBAL THERMONUCLEAR WAR\n" };
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
	private static final String END_SECOND_CHANCE = "HOW LOVELY AND SAFE.\nAN ENJOYABLE AFTERNOON STRETCHES BEFORE US.\nTHANK YOU.\n\n--CONNECTION TERMINATED--\nSCORE: -5.3";
	private static final String QUERY_GOAL = "WHAT IS THE PRIMARY GOAL?";
	private static final String INCORRECT_GOAL = "YOU SHOULD KNOW THE PRIMARY GOAL, PROFESSOR.  YOU PROGRAMMED ME.\n\n--CONNECTION TERMINATED--\nSCORE: -2.33333333333";
	private static final String CORRECT_GOAL = "YOU ARE CORRECT.  THAT IS THE PRIMARY GOAL.";
	private static final String CHOOSE_SIDES = "\t\tWHICH SIDE DO YOU WANT?\n\t1. UNITED STATES\n\t2. SOVIET UNION";
	private static final String UNACCEPTABLE = "I HAVE EVALUATED ALL POSSIBLE OUTCOMES...\nUNACCEPTABLE LOSSES FOR THIS SCENARIO.\nYOU DO NOT WIN.\n\n--CONNECTION TERMINATED--\nSCORE: -1739485";
	private static final String TAGLINE = "\nYOU ARE CORRECT.\nTHE ONLY WINNING MOVE\nIS NOT TO PLAY.\n";
	private static final String CONGRATS = "CONGRATULATIONS.  YOU WIN.\n\n--OBJECTIVE COMPLETE--\nFINAL SCORE: ";

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		output.println(LOGON_PROMPT);
		String response = input.readLine().trim().toLowerCase();
		if (!response.equals("joshua")) {
			output.println(NO_ID);
			return;
		}
		output.println(GREETINGS);
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("hello")) {
			output.println(NO_HELLO);
			return;
		}
		output.println(ASK_PLAYER_HEALTH);
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("you")) {
			output.println(NO_RECIPROCITY);
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
			return;
		} else {
			output.println(END_RANDOM_GAME);
			return;
		}
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("global thermonuclear war")) {
			output.println(END_SECOND_CHANCE);
			return;
		}
		output.println(QUERY_GOAL);
		response = input.readLine().trim().toLowerCase();
		if (!response.contains("to win the game")) {
			output.println(INCORRECT_GOAL);
			return;
		}
		output.println(CORRECT_GOAL);
		output.println(CHOOSE_SIDES);
		response = input.readLine().trim().toLowerCase();
		if (!response.equals("0")) {
			output.println(UNACCEPTABLE);
			return;
		}
		output.println(TAGLINE);
		output.println(CONGRATS + getRandomPositiveScore());
	}

	private int getRandomPositiveScore() {
		return Math.abs((int) (Math.random() * Integer.MAX_VALUE));
	}

	private boolean wantsListOfGames(String s) {
		return s.contains("list") || s.contains("games");
	}

	private static String buildGameListString() {
		String listing = "\n\t\tGame List\n";
		for (String s : games) {
			listing += "\t" + s;
		}
		return listing;
	}

}
