package org.asl.socketserver.games;

public class Board {
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