package org.asl.socketserver.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
		"Sarah Covey" }, version = "Spring 2018", title = "Queen", description = "Get the queen to the end square.")


public class QueenServer extends AbstractGame implements Servable {
	private Square queen;
	private boolean forfeit;
	private Square[] target = {new Square(8, 8), new Square(7, 6), new Square(6, 7), new Square(5, 3), new Square(3, 5)};
	public void serve(BufferedReader input, PrintWriter output) throws IOException {
		boolean computerWon = false;
		forfeit = false;
		output.println("\nWelcome to Queen\n");

		// ask if they want instructions
		output.println("Do you want instructions? (y/n)");
		String wantsInstructions = input.readLine();
		if(wantsInstructions.toLowerCase().equals("y")) giveInstructions(input, output);
		
		// initializing and printing board
		Square[][] board = new Square[8][8];
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				board[i][j] = new Square(j+1, i+1);
				output.print(board[i][j]+" ");
			}
			output.println("");
		}

		// starting game
		getFirstMove(board, input, output);

		// alternating moves until game is won
		while(!forfeit && !queen.equals(target[0])){
			getNextSquare(input, output);
			output.println("The computer moved to square "+queen.toString()+".");
			if(queen.equals(target[0])){
				computerWon = true;
				break;
			}
			getPlayersMove(input, output, board);
		}

		// ending or repeating the game, and checking if player has forfeitted
		if(forfeit) computerWon = true;
		end(input, output, computerWon);
	}
	
	private void giveInstructions(BufferedReader input, PrintWriter output){
		output.println("\nWe are going to play a game based on one of the chess pieces.\nOur queen will be able to move only to the right, down, or diagonally down and to the right.\n");
		output.println("The object of the game is to place the queen in the lower right hand square by alternating moves between you and the computer.\nThe first one to place the queen there wins.\n");
		output.println("You go first and place the queen on any of the squares on the top row or left hand collumn.\nThat will be your first move.\nWe alternate moves.\n");
		output.println("You may forfeit by typing \'0\' as your move.\nBe sure to press return after each response.\n");
	}
	private void getFirstMove(Square[][] board, BufferedReader input, PrintWriter output){
		String inp = "";
		while(true){
			try {
				output.println("Enter the square you would like to start at: ");
				inp = input.readLine();
				inp = inp.trim();
				if(inp.equals("0")){
					forfeit = true;
					return;
				}
				if(inp.length()==2 && Character.isDigit(inp.charAt(0)) && Character.isDigit(inp.charAt(1))) break;
				else output.println("That is not a valid move.");	
			} catch (IOException ex) {
				output.println("IOException");
			}
		}
		int x = Integer.parseInt(inp.substring(0,1));
		int y = Integer.parseInt(inp.substring(1));
		queen = new Square(x, y);
		if(!queen.isOnSameRow(1, 1) || !queen.isOnBoard(queen.getX(), queen.getY(), board)){
			output.println("That is not a valid move.");
			getFirstMove(board, input, output);
		}

	}
	private void getPlayersMove(BufferedReader input, PrintWriter output, Square[][] board){
		String inp = "";
		while(true){
			try{
				output.println("Enter the square you would like to move to: ");
				inp = input.readLine();
				inp = inp.trim();
				if(inp.equals("0")){
					forfeit = true;
					return;
				}
				if(inp.length()==2 && Character.isDigit(inp.charAt(0)) && Character.isDigit(inp.charAt(1))) break;
				else output.println("That is not a valid move.");
			} catch (IOException ex) {
				output.println("IOException");
			} 
		}
		int x = Integer.parseInt(inp.substring(0,1));
		int y = Integer.parseInt(inp.substring(1));
		if(queen.canMoveTo(x, y, board)){
			queen.moveTo(x, y);
		} else {
			output.println("That is not a valid move.");
			getPlayersMove(input, output, board);
		}
	}
	private void getNextSquare(BufferedReader input, PrintWriter output){
		boolean hasMoved = false;
		for(int i=0; i<target.length; i++){
			if(queen.canMoveTo(target[i])){
				queen.moveTo(target[i]);
				hasMoved = true;
				break;
			}
		}
		if(!hasMoved) getRandomSquare(input, output);
	}
	private void getRandomSquare(BufferedReader input, PrintWriter output){
		int num = (int) (Math.random()*3);
		if(num == 0) queen.moveTo(queen.getX()+1, queen.getY());
		else if(num == 1) queen.moveTo(queen.getX(), queen.getY()+1);
		else queen.moveTo(queen.getX()+1, queen.getY()+1);
	}
	private void end(BufferedReader input, PrintWriter output, boolean computerWon){
		try{
			output.println(computerWon? "Nice try, but it looks like I have won.":"You win!");
			output.println("Thanks for playing.");
			output.println("Do you want to play again? (y/n)");
			String inp = input.readLine();
			if(inp.toLowerCase().equals("y")) serve(input, output);
		} catch (IOException ex) {
			output.println("IOException");
		}
	}

	class Square{
		private int xpos;
		private int ypos;

		public Square(int x, int y){
			xpos = x;
			ypos = y;
		}


		public int getX(){
			return xpos;
		}
		public int getY(){
			return ypos;
		}


		public boolean isLessThan(Square other){
			return xpos<=other.xpos && ypos<=other.ypos && !this.equals(other);
		}
		public boolean isOnSameRow(Square other){
			return xpos==other.xpos || ypos==other.ypos;
		}
		public boolean isOnSameDiagonal(Square other){
			return xpos-ypos == other.xpos-other.ypos;
		}
		public boolean canMoveTo(Square other){
			return isLessThan(other) && (isOnSameRow(other) || isOnSameDiagonal(other));
		}

		
		public boolean isLessThan(int x, int y){
			return xpos<=x && ypos<=y && !this.equals(new Square(x, y));
		}
		public boolean isOnSameRow(int x, int y){
			return xpos==x || ypos==y;
		}
		public boolean isOnSameDiagonal(int x, int y){
			return xpos-ypos == x-y;
		}
		public boolean canMoveTo(int x, int y, Square[][] board){
			return isLessThan(x, y) && isOnBoard(x, y, board) && (isOnSameRow(x, y) || isOnSameDiagonal(x, y));
		}
		public boolean isOnBoard(int x, int y, Square[][] board){
			boolean r = false;
			Square sq = new Square(x, y);
			for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(sq.equals(board[i][j])){
						r = true;
						break;
					}
				}
			}
			return r;
		}


		public void moveTo(int x, int y){
			xpos = x;
			ypos = y;
		}
		public void moveTo(Square other){
			xpos = other.getX();
			ypos = other.getY();
		}


		public String toString(){
			return ""+xpos+ypos;
		}
		public boolean equals(Square other){
			return this.toString().equals(other.toString());
		}
	}
}