import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/***
 * Sends menu options and reads the user's selection. If the selection is for a
 * specific game, serves the game. This thread loops until the selection "q" is
 * received, at which point the socket is closed and the thread terminates.
 * 
 * @author kentcollins
 *
 */

public class GameThread implements Runnable {

	private Socket socket;
	private final long SCROLL_DELAY = 500L; // half second delay

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
			String gameMenu = GameTracker.buildGameListMenu();
			// TODO show author info, if available
			out.println(); // provide some white space before menu
			while (true) {
				Thread.sleep(SCROLL_DELAY);
				out.print(gameMenu);  // accept input on same line?
				out.flush();
				String choice = br.readLine().trim().toLowerCase();
				if ("q".equals(choice))
					break;
				Object o = GameTracker.handleUserSelection(choice);
				if (o instanceof Servable) {
					out.println("Game selection: "+choice+"\n"); // whitespace
					((Servable) o).serve(br, out);
					Thread.sleep(SCROLL_DELAY);
					out.println(
							"\nThe game is over.  Press Enter to continue.");
					br.readLine(); // provide opportunity for them to see msg
					continue;
				} else if (o instanceof Exception) {
					out.println(
							"An error occurred while attempting to start the game.");
				} else {
					out.println(choice + " is not a valid option.");
				}
				out.println("Press Enter/Return to continue.");
				br.readLine(); // user is ready to continue
			}
			GameServer.LOGGER.info("Connection with "
					+ socket.getInetAddress() + " closed normally.");

		} catch (IOException |

				NullPointerException e1) {
			GameServer.LOGGER.warning(socket.getInetAddress()
					+ " ended connection abruptly.");
		} catch (InterruptedException e) {
			// Likely, because thread was interrupted while sleeping
			GameServer.LOGGER.warning("Thread interrupted while sleeping."+e);
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
