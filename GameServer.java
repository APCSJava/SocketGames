import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/***
 * Spins connection requests into parallel game threads.
 * 
 * K Collins, Fall 2017
 */
public class GameServer {

	final static Logger LOGGER = Logger
			.getLogger(GameServer.class.getName());

	/**
	 * Opens a port to listen for incoming requests. When a request is received,
	 * wraps the resulting socket connection in a new thread and starts the process.
	 * 
	 * @param args[0]
	 *            the port to use when listening for connections
	 * @param args[1]
	 *            the max number of concurrent connections to accept
	 * 
	 */
	public static void main(String[] args) throws IOException {
		int desiredPort = Integer.parseInt(args[0]);
		int maxConnections = Integer.parseInt(args[1]);
		String refuseMessage = "Server limit of " + maxConnections
				+ ((maxConnections == 1) ? " connection"
						: " connections")
				+ " has been reached.  Please try again, later.";

		try (ServerSocket socketRequestListener = new ServerSocket(
				desiredPort)) {
			LOGGER.info("Server started.  Port: " + desiredPort
					+ ".  Capacity: " + maxConnections);
			GameTracker.initialize();
			while (true) {
				// the following call blocks until a connection is made
				Socket socket = socketRequestListener.accept();
				InetAddress remoteMachine = socket.getInetAddress();
				// String remoteHost = remoteMachine.getHostName();
				LOGGER.info("Incoming request: " + remoteMachine);

				int numActiveSockets = Thread.activeCount() - 1;
				if (numActiveSockets < maxConnections) {
					new Thread(new GameThread(socket)).start();
					numActiveSockets++;
					LOGGER.info("Accepted " + remoteMachine
							+ ".  Current connections: "
							+ numActiveSockets);
				} else {
					PrintWriter out = new PrintWriter(
							socket.getOutputStream());
					out.println(refuseMessage);
					out.close();
					socket.close();
					LOGGER.warning("Refused " + remoteMachine
							+ ".  Current connections: "
							+ numActiveSockets);
				}
			}
		}
	}

}
