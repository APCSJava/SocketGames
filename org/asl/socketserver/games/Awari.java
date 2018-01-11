package org.asl.socketserver.games;

/*
A version of the arcade game Awari
By Graham and Kippy
*/

// Importing classes
import java.util.Random;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.Servable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Awari extends AbstractGame implements Servable {

	// Setting up the random instance
	private static Random ranNumGen = new Random();

	// Making a board
	private static Board myBoard = new Board();

	
	private static BufferedReader in;
	private static PrintWriter out;
	
	int myScore = 0;

	public Awari() {

	}


	public  void serve(BufferedReader in, PrintWriter out) throws IOException {
		this.in = in;
		this.out = out;
		

		// Initialising the playAgain variable
		boolean playAgain = true;

		// A loop to make the game keep playing
		while (playAgain) {
			// Initialising the variable that determines when a particular bout has ended
			boolean end = false;

			// Credits
			out.println("\nAWARI\nCREATIVE COMPUTING MORRISTOWN, NEW JERSEY\n\n" + myBoard + "\n");

			// Loop for moves
			while (!end) {

				// Player turn
				if (myBoard.getSum(8 , 13) != 0) {
					playerMove();
					if (myBoard.doubleMove("Player") && (myBoard.getSum(8 , 13) != 0)) {
						out.println("YOU GET TO GO AGAIN");
						playerMove();
					}
				}

				// Computer turn
				if (myBoard.getSum(1 , 6) != 0) {
					computerMove();
					if (myBoard.doubleMove("Computer") && (myBoard.getSum(1 , 6) != 0)) {
						out.println("I GET TO GO AGAIN");
						computerMove();
					}
				}

				// Checking if the game is over based upon there not being any more beans on either side
				if (myBoard.getSum(1 , 6) == 0 || myBoard.getSum(8 , 13) == 0) {
					// Ending the game
					end = true;
					out.println("GAME OVER");
				}
			}

			// Getting the ending scores
			int playerScore = myBoard.getScore("Player");
			int computerScore = myBoard.getScore("Computer");

			// Determining who wins and displaying an appropriate message
			if (playerScore > computerScore) {
				out.println("YOU WIN BY " + (playerScore - computerScore) + " POINTS!");
				myScore++;
			} else if (playerScore < computerScore) {
				out.println("YOU LOSE");
				myScore--;
			} else {
				out.println("YOU TIED");
			}

			// Asking if the player wants to play again
			out.println("\nPLAY AGAIN? (Y/N) ");
			String again = (in.readLine()).toLowerCase();

			// Implementing the player request to play again or not
			if (again.equals("y")) {
				playAgain = true;
				myBoard.reset();
			} else {
				playAgain = false;
				checkBestScore(myScore);
			}
		}
	}

	// Method for the player move
	public static void playerMove() throws IOException {
		int yourMove = 0;
		while (true) {
			// Getting player move
			out.println("YOUR MOVE?");
			yourMove = Integer.parseInt(in.readLine());
			if (myBoard.getValue(yourMove + 7) != 0) {
				break;
			} else {
				out.println("ERROR NO BEANS\nCHOOSE AGAIN");
			}
		}
		// Implementing the player move
		myBoard.move(yourMove , "Player");
		myBoard.steal("Player");

		// Displaying the post-move board
		out.println("\n" + myBoard + "\n");
	}

	// Method for the computer move
	public static void computerMove() {
		int numComp = 0;
		// If statements for stealing
		if (myBoard.getValue(2) == 0 && myBoard.getValue(14 - 2) != 0 && (myBoard.getValue(1) % 14 == 1)) {
			numComp = 1;
		} else if (myBoard.getValue(3) == 0 && myBoard.getValue(14 - 3) != 0 && (myBoard.getValue(1) % 14 == 2 || myBoard.getValue(2) % 14 == 1)) {
			if (myBoard.getValue(1) % 14 == 2) {
				numComp = 1;
			} else {
				numComp = 2;
			}
		} else if (myBoard.getValue(4) == 0 && myBoard.getValue(14 - 4) != 0 && (myBoard.getValue(1) % 14 == 3 || myBoard.getValue(2) % 14 == 2 ||
				myBoard.getValue(3) % 14 == 1)) {
			if (myBoard.getValue(1) % 14 == 3) {
				numComp = 1;
			} else if (myBoard.getValue(2) % 14 == 2) {
				numComp = 2;
			} else {
				numComp = 3;
			}
		} else if (myBoard.getValue(5) == 0 && myBoard.getValue(14 - 5) != 0 && (myBoard.getValue(1) % 14 == 4 || myBoard.getValue(2) % 14 == 3 ||
				myBoard.getValue(3) % 14 == 2 || myBoard.getValue(4) % 14 == 1)) {
			if (myBoard.getValue(1) % 14 == 4) {
				numComp = 1;
			} else if (myBoard.getValue(2) % 14 == 3) {
				numComp = 2;
			} else if (myBoard.getValue(3) % 14 == 2) {
				numComp = 3;
			} else {
				numComp = 4;
			}
		} else if (myBoard.getValue(6) == 0 && myBoard.getValue(14 - 6) != 0 && (myBoard.getValue(1) % 14 == 5 || myBoard.getValue(2) % 14 == 4 ||
				myBoard.getValue(3) % 14 == 3 || myBoard.getValue(4) % 14 == 2 || myBoard.getValue(5) % 14 == 1)) {
			if (myBoard.getValue(1) % 14 == 5) {
				numComp = 1;
			} else if (myBoard.getValue(2) % 14 == 4) {
				numComp = 2;
			} else if (myBoard.getValue(3) % 14 == 3) {
				numComp = 3;
			} else if (myBoard.getValue(4) % 14 == 2) {
				numComp = 4;
			} else {
				numComp = 5;
			}

		// If statements for turn again
		} else if (myBoard.getValue(6) == 1) {
			numComp = 6;
		} else if (myBoard.getValue(5) == 2) {
			numComp = 5;
		} else if (myBoard.getValue(4) == 3) {
			numComp = 4;
		} else if (myBoard.getValue(3) == 4) {
			numComp = 3;
		} else if (myBoard.getValue(2) == 5) {
			numComp = 2;
		} else if (myBoard.getValue(1) == 6) {
			numComp = 1;

		// If statements for getting a point
		} else if (myBoard.getValue(6) >= 1) {
			numComp = 6;
		} else if (myBoard.getValue(5) >= 2) {
			numComp = 5;
		} else if (myBoard.getValue(4) >= 3) {
			numComp = 4;
		} else if (myBoard.getValue(3) >= 4) {
			numComp = 3;
		} else if (myBoard.getValue(2) >= 5) {
			numComp = 2;
		} else if (myBoard.getValue(1) >= 6) {
			numComp = 1;

		// Random fallback choice
		} else {
			while (true) {
				numComp = ranNumGen.nextInt(6) + 1;
				if (myBoard.getValue(numComp) != 0) {
					break;
				}
			}
		}

		// Implementing the computer move
		myBoard.move(numComp , "Computer");
		myBoard.steal("Computer");

		// Saying the computer's move
		out.println("MY MOVE IS " + numComp);

		// Displaying the post-move board
		out.println("\n" + myBoard + "\n");
	}

	private void checkBestScore(int score) throws IOException {
		if (getBestScore() == null || score > getBestScore().getScore()) {
			out.println("That's a new high score -- please enter your initials...");
			String initials = in.readLine().trim();
			setBestScore(score, initials);
		} else {
			out.println("Your score was " + myScore + ". Goodbye.");
		}
	}
}
