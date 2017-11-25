import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
								+ socketRequestListener.getLocalPort());
				Socket socket = socketRequestListener.accept();

				// verify the socket connection has been opened
				printConnectionInfo(socket);
				List<Class<Servable>> games = ClassFinder
						.findServableClasses();
				try {
					PrintWriter out = new PrintWriter(
							socket.getOutputStream(), true);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()));
					out.println(
							"The following games are available: ");
					if (games.size() == 0) {
						out.println("None");
						break;
					} else {
						for (int i = 0; i < games.size(); i++) {
							out.println(i + "\t"
									+ games.get(i).getName());
						}
						out.println(
								"Enter game number or 'q' to exit ... ");
					}
					String input = br.readLine().trim()
							.toLowerCase();
					while (!checkValidInteger(input)
							&& !input.equals("q")) {
						out.println(input
								+ " is not acceptable.  Let's try again.");
						out.println(
								"Please enter the game number or 'q' to exit ... ");
						input = br.readLine().trim().toLowerCase();
					}
					if ("q".equals(input)) {
						out.println("Okay.  Thanks for playing!");
						socket.close();
						continue;
					}
					int requestedGame = Integer.parseInt(input);
					try {
						Servable game1 = games.get(requestedGame)
								.newInstance();
						System.out.println(
								"Started something " + game1);
						game1.serve(br, out);
						System.out.println("Concluded the game.");
					} catch (IndexOutOfBoundsException e) {
						out.println(
								"Game not found.  Please try again.");
						socket.close();
						continue;
					}

				} finally {
					// must tidy up
					socket.close();
				}
				// verify the socket connection has been closed
				printConnectionInfo(socket);
			}
		} catch (IOException | ClassNotFoundException
				| InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static boolean checkValidInteger(String s) {
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
				"Socket connection established: " + remoteHost);
	}
}
