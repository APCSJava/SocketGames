import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/***
 * Sends menu options and reads the user's selection. If the selection is for a
 * different menu, displays the new menu. If the selection is for a specific
 * game, serves the game. This thread loops until the selection "q" is received,
 * at which point the socket is closed and the thread completes.
 * 
 * @author kentcollins
 *
 */

public class GameThread implements Runnable {

	private Socket socket;

	public GameThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (PrintWriter out = new PrintWriter(
				socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()))) {
			String activeMenu = null;
			String userSelection = "i";
			while (!userSelection.equals("q")) {
				Object o = GameTracker.handleInput(userSelection);
				if (o instanceof String) {
					activeMenu = (String) o;
				} else if (o instanceof Servable) {
					((Servable) o).serve(br, out);
				} else {
					out.println("Didn't understand the input "
							+ userSelection
							+ ".  Press Enter/Return to try again.");
					br.readLine(); // they acknowledge error
				}
				out.println(activeMenu);
				userSelection = br.readLine().trim().toLowerCase();
			}
			GameServer.LOGGER.info("Connection with "
					+ socket.getInetAddress() + " closed normally.");

		} catch (IOException | NullPointerException e1) {
			GameServer.LOGGER.warning(socket.getInetAddress()
					+ " ended connection abruptly.");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				GameServer.LOGGER.info(
						"GameThread attempting to close a closed socket");
			}
		}
	}
}
