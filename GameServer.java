import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * A single connection socket game server. Identifies games that can be served,
 * presents a list to remote users, and serves a requested game until its
 * completion. Listens for additions to the working directory folder and updates
 * the list, as appropriate.
 * 
 * K Collins, Fall 2017
 */
public class GameServer {

	/**
	 * Opens a port to listen for incoming requests. When a request is received,
	 * opens a socket connection and delivers a service.
	 * 
	 * args[0] the port to use when listening for connections
	 * 
	 */
	public static void main(String[] args) throws IOException {
		// if no port number provided, use as default the value 9090
		int desiredPort = args.length > 0 ? Integer.parseInt(args[0])
				: 9090;
		try (ServerSocket socketRequestListener = new ServerSocket(
				desiredPort)) {
			while (true) {
				// the following call blocks until a connection is made
				System.out.println(
						"Game server listening for connections on port "
								+ socketRequestListener
										.getLocalPort());
				Socket socket = socketRequestListener.accept();

				// verify the socket connection has been opened
				printConnectionInfo(socket);
				new Thread(new GameThread(socket)).start();
			}
		}
	}

	/**
	 * Prints diagnostic information to the local machine (server)
	 * 
	 * Note the ordering of the OR arguments
	 */
	private static void printConnectionInfo(Socket socket) {
		if (socket == null || socket.isClosed()) {
			System.out
					.println("No active socket connection exists.");
			return;
		}
		InetAddress remoteMachine = socket.getInetAddress();
		String remoteHost = remoteMachine.getHostName();
		System.out.println(
				"Socket connection established with " + remoteHost);
	}
}
