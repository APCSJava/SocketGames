import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class GameThread implements Runnable {

	private Socket socket;

	public GameThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		List<Class<Servable>> games;
		try {
			games = ClassFinder.findServableClasses();
			PrintWriter out = new PrintWriter(
					socket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			out.println("The following games are available: ");
			if (games.size() == 0) {
				out.println("None");
				socket.close();
				return;
			} else {
				for (int i = 0; i < games.size(); i++) {
					out.println(i + "\t" + games.get(i).getName());
				}
				out.println("Enter game number or 'q' to exit ... ");
			}
			String input = br.readLine().trim().toLowerCase();
			while (!checkValidInteger(input) && !input.equals("q")) {
				out.println(input
						+ " is not acceptable.  Let's try again.");
				out.println(
						"Please enter the game number or 'q' to exit ... ");
				input = br.readLine().trim().toLowerCase();
			}
			if ("q".equals(input)) {
				out.println("Okay.  Thanks for playing!");
				socket.close();
				return;
			}
			int requestedGame = Integer.parseInt(input);
			try {
				Servable game1 = games.get(requestedGame)
						.newInstance();
				System.out.println("Started something " + game1);
				game1.serve(br, out);
				System.out.println("Concluded the game.");
			} catch (IndexOutOfBoundsException
					| InstantiationException | IllegalAccessException e) {
				out.println("Game not found.  Please try again.");
				socket.close();
				return;
			}

		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			// must tidy up
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// verify the socket connection has been closed
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

}
