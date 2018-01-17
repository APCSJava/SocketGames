package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
A version of the arcade game Awari
By Graham and Kippy
*/

// Importing classes
import java.util.Random;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Graham Brantley and Kippy" }, version = "Spring, 2018", title = "Awari", description = "Bean Game.")


public class Awari extends AbstractGame implements Servable {

	// Setting up the random instance
	private Random ranNumGen = new Random();

	// Making a board
	private Board myBoard = new Board();

	
	private BufferedReader in;
	private  PrintWriter out;
	
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
	public void playerMove() throws IOException {
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
	public  void computerMove() {
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


	class Board {
		// The array of values that makes up the board
		// The array goes anticlockwise, with your home being 0 and the opponent's being 7
		private int[] values = new int[14];
	
		// Storing the last part of the last move
		private int lastPartLastMove = 1;
	
		// The required constructor class (might as well initialise the values array first time through)
		public Board() {
			reset();
		}
	
		// The method to get a value
		public int getValue(int element) {
			return values[element];
		}
	
		// The method for making a move on a side of the board
		public void move(int number, String side) {
			// Calculating which element of the array the number and side refer to
			int place = number;
			if (side.equals("Player")) {
				place += 7;
			}
	
			// Taking the beans from that element and storing their value
			int value = values[place];
			values[place] = 0;
	
			// Spreading the beans across the next few elements around the board
			for (int i = 0 ; i < value ; i++) {
				values[(place + 1 + i) % 14] += 1;
			}
	
			// Storing the last part of the last move
			lastPartLastMove = (place + value) % 14;
		}
	
		// The getter method for lastPartLastMove
		public int getLastMove() {
			return lastPartLastMove;
		}
	
		// The method to get the sum of the beans in a certain number of consecutive cells
		public int getSum(int initialV , int finalV) {
			// Initialising the sum
			int sum = 0;
	
			// Summing the elements
			for (int i = initialV  ; i <= finalV ; i++) {
				sum += values[i];
			}
	
			// Returning the sum
			return sum;
		}
	
		// The method to get the score of a player
		public int getScore(String player) {
			// Pretty self explanatory, but basically just returning the relevant home value
			if (player.equals("Player")) {
				return values[0];
			} else if (player.equals("Computer")) {
				return values[7];
			} else {
				return 999999999;
			}
		}
	
		// The method to check if the player gets to go again
		public boolean doubleMove(String player) {
			// Getting the last part of the last move
			int lastElement = getLastMove();
	
			// Checking if it is a home of the player
			if (player.equals("Player")) {
				return (lastElement == 0);
			} else {
				return (lastElement == 7);
			}
		}
	
		// The method to check if the player gets to steal
		public void steal(String player) {
			// Getting the last part of the last move
			int lastElement = getLastMove();
	
			// Checking the conditions for a steal
			if (values[lastElement] == 1 && lastElement != 0 && lastElement != 7 && values[14 - lastElement] != 0) {
				// Choosing where to put the stolen beans
				if (player.equals("Player")) {
					values[0] += 1 + values[14 - lastElement];
				} else {
					values[7] += 1 + values[14 - lastElement];
				}
	
				// Emptying the places the beans were stolen from
				values[lastElement] = 0;
				values[14 - lastElement] = 0;
			}
		}
	
		// The method to reset the board
		public void reset() {
			// Giving each home a value of 0 beans and each other place a value of 3 beans
			for (int i = 0 ; i < values.length ; i++) {
				if (i == 0 || i == 7) {
					values[i] = 0;
				} else {
					values[i] = 3;
				}
			}
		}
	
		// The toString method for the board (allows us to just write System.out.println(myBoard))
		public String toString() {
			// Initilising the message and storing the correct amount of spacing
			String spacing = "   ";
			String message = "";
	
			// Top line
			for (int i = 0 ; i < 6 ; i++) {
				message += spacing + values[6 - i];
			}
	
			// New line
			message += "\n";
	
			// Middle line
			message += values[7] + spacing + spacing + spacing + spacing + spacing + spacing + spacing + spacing + " " + values[0];
	
			// New line
			message += "\n";
	
			// Bottom line
			for (int i = 0 ; i < 6 ; i++) {
				message += spacing + values[i + 8];
			}
	
			// Returning the message
			return message;
		}
	}
}
