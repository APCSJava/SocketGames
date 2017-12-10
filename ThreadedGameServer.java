import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * Spins connection requests into parallel game threads.
 * 
 * K Collins, Fall 2017
 */
public class ThreadedGameServer {

	/**
	 * Opens a port to listen for incoming requests. When a request is received,
	 * wraps the resulting socket connection in a new thread and starts the process.
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
			System.out.println(
					"Server running on port " + desiredPort);
			GameTracker.initializeGameList();
			while (true) {
				// the following call blocks until a connection is made
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
			System.out.println(
					"Error.  No active socket connection exists.");
			return;
		}
		InetAddress remoteMachine = socket.getInetAddress();
		String remoteHost = remoteMachine.getHostName();
		System.out
				.println("New socket connection with " + remoteHost);
	}
}
