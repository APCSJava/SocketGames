import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Commemorating the 1983 film of the same name.
 * 
 * @author kentcollins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "WarGames", description = "1983 film fiction.")
public class WarGames implements Servable {
	private String[] games = { "FALKEN'S MAZE", "BLACK JACK",
			"GIN RUMMY", "HEARTS", "BRIDGE", "CHECKERS", "CHESS",
			"POKER", "FIGHTER COMBAT", "GUERRILLA ENGAGEMENT",
			"DESERT WARFARE", "AIR-TO-GROUND ACTIONS",
			"THEATERWIDE TACTICAL WARFARE",
			"THEATERWIDE BIOTOXIC AND CHEMICAL WARFARE",
			"\nGLOBAL THERMONUCLEAR WAR\n" };

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		output.println(
				"IMSAI 8080\nHELLO");
		if (!input.readLine().trim().toLowerCase()
				.equals("joshua")) {
			output.println("PASSWORD NOT RECOGNIZED.  GOODBYE.  POINTS: -50");
			return;
		}
		output.println("GREETINGS PROFESSOR FALKEN\n");
		if (!input.readLine().trim().toLowerCase()
				.contains("game")) {
			output.println("I CAN DO MANY THINGS.  THAT IS NOT AMONG THEM.  GOODBYE.  POINTS: -30");
			return;
		}
		output.println("\nGame List\n");
		for (String s : games) {
			output.println(s);
		}
		output.println("\nSHALL WE PLAY A GAME?\n");
		if (!input.readLine().trim().toLowerCase()
				.contains("global thermonuclear war")) {
			output.println("HOW EXCITING <YAWN>.  GOODBYE.  POINTS: -17");
			return;
		}
		output.println("\nHOW ABOUT A NICE GAME OF CHESS?\n");
		if (!input.readLine().trim().toLowerCase()
				.contains("global thermonuclear war")) {
			output.println("TRAGEDY AVERTED.  GOODBYE.  POINTS: -5.3");
			return;
		}
		output.println(
				"\nA STRANGE GAME.\nTHE ONLY WINNING MOVE\nIS NOT TO PLAY.\n");
		int points = Math.abs((int) (Math.random()*Integer.MAX_VALUE));
		output.println("CONGRATULATIONS.  YOU WIN. POINTS: "+points);
	}
}
