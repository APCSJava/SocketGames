package org.asl.socketserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

/***
 * Manages the connection with a remote machine for the lifetime of the thread.
 * Sends menu options and reads the user's selection. If the selection is for a
 * specific game, serves the game (see NOTE 1); terminates the thread and closes
 * the socket connection on receipt of a 'q'.
 * 
 * NOTE 1: This class creates a new thread by creating an instance of the
 * specified class and then invoking serve() on the instance. In practice, this
 * requires that candidate classes not only implement Servable but also provide
 * a default (no argument) constructor.
 * 
 * Extends PrintWriter and BufferedReader classes to echoed versions that
 * read/write a copy of the input/output streams to the logger, as well.
 * 
 * @author K. Collins
 * @version Fall, 2017
 */

public class GameThread implements Runnable {

	private Socket socket;
	private final long SCROLL_DELAY = 500L; // half second delay

	public GameThread(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try (PrintWriter out = new EchoWriter(
				socket.getOutputStream(), true);
				BufferedReader br = new EchoReader(
						new InputStreamReader(
								socket.getInputStream()))) {
			out.println(); // provide some white space before menu
			while (true) {
				Thread.sleep(SCROLL_DELAY);
				out.println(GameTracker.handleUserSelection("")); 
				String choice = br.readLine().trim().toLowerCase();
				if ("q".equals(choice))
					break;
				Object o = GameTracker.handleUserSelection(choice);
				if (o instanceof Servable) {
					out.println(GameTracker.getGameInfo(
							(Class<Servable>) o.getClass()));
					out.println(); // whitespace)
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
			GameServer.LOGGER.warning(
					"Thread interrupted while sleeping." + e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				GameServer.LOGGER.info(
						"GameThread attempting to close a closed socket");
			}
		}
	}

	/***
	 * Extends PrintWriter to capture and log outgoing data.
	 * 
	 * @author kentcollins
	 */
	class EchoWriter extends PrintWriter {

		public EchoWriter(OutputStream out, boolean autoFlush) {
			super(out, autoFlush);
		}

		@Override
		public void println(String s) {
			super.println(s);
			if (!s.trim().equals("")) {
				String first60 = "Sending --> "
						+ socket.getInetAddress() + " "
						+ s.replace("\n", " ").substring(0,
								Math.min(60, s.length()));
				GameServer.LOGGER.info(first60);
			}
		}

	}

	/***
	 * Extends BufferedReader to capture and log incoming data.
	 * 
	 * @author kentcollins
	 *
	 */
	class EchoReader extends BufferedReader {

		public EchoReader(Reader in) {
			super(in);
		}

		@Override
		public String readLine() {
			try {
				String s = super.readLine();
				if (!s.trim().equals("")) {
					String first60 = "Received <-- "
							+ socket.getInetAddress() + " "
							+ s.replace("\n", " ").substring(0,
									Math.min(60, s.length()));
					GameServer.LOGGER.info(first60);
				}
				return s;
			} catch (IOException e) {
				return "";
			}
		}

	}
}
