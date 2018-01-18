package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.MenuInfo;
import org.asl.socketserver.Servable;

@MenuInfo(authors = {
"Dom Alberts" }, version = "January, 2018", title = "HiLo", description = "How HiLo can you go?")


public class HiLo extends AbstractGame implements Servable{
	 
	 private int target;
	 private int bank;

	 
	 public HiLo() {
		 target = 0;
	 }
	 
	 
	 public void deposit(int addition) {
		 bank += addition;
	 }
	 
	 public void setTarget(int num) {
		 target = num;
	 }
	 
	 
	 
	 
	 public void serve(BufferedReader input, PrintWriter output) throws IOException {
		 output.println("This is a game of Hi-Lo. You have six tries to correctly guess the value of the pot (1-100 dollars).");

		 boolean play = true;
		  while(play){
		    
		    boolean noWin = true;

		    int randNum = (int) (Math.random()*100+1);
		    
		    setTarget(randNum);

		    for(int i = 0; i < 6; i++){
		      output.println("Your guess: ");
		      String name = input.readLine();
		      int guess = Integer.parseInt(name);
		      
		      if(noWin==true){
		        if(guess==target){
		          noWin = false;
		          deposit(randNum);
		          output.println("Got it! You win " + target + " dollars.");
		          output.println("Your total winnings are now " + bank + " dollars");
		          break;
		        } else if(guess<target){
		          output.println("Your guess is too low.");
		        } else if(guess>target){
		          output.println("Your guess is too high.");
		        }
		      }
		    }

		    if(noWin==true){
		      output.println("You blew it... too bad. The number was " + target);
		      output.println("You earned " + bank + " dollars total.");
		      play = false;
		    } else {
		      output.println("Play again? y/n");
		      String keepPlaying = input.readLine();
		      if(keepPlaying.equals("y")){
		        play = true;
		      } else if(keepPlaying.equals("n")){
		        play = false;
		      }
		    }

		  }
		
		  
		  if (this.getBestScore() == null || bank > this.getBestScore().getScore()) {
				output.println(bank
						+ " is a new high score.  Please enter your initials...");
				String inits = input.readLine().trim();
				setBestScore(bank, inits);
			}
		  
	 }
 }