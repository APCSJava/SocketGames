public class RPS{
	private String choice;

	public RPS(String choice){
		this.choice = choice;
	}
	public RPS(){
		int choice2 = (int) Math.random() * 3 + 1;
		//will the code above make the random number a range from 1-3?
		choice = choice2;
	}
	public int getNumGames(int guess1){
		System.out.println("How many games?");
		String message = "";
		int numberGames = 0;
		if(guess1 > 10){
			message = "Sorry, but we aren't allowed to play that many";
		}else if{
			numberGames = guess1;
		}
		System.out.println(message);
		return numberGames;
	}
	public String getHint(String guess2, int numberGames){
		String gameNum = "This is game number " + numberGames;
		System.out.println(gameNum);
		for(int i = 0; i < numberGames; i++){
			String message = "3 = rock, 2 = scissors, 1 = paper. What's your choice?"
			String message2 = "This my choice " + choice;
			String end = "";
			int ties = 0;
			int win = 0;
			int humanWin = 0;
			if(choice = guess2){
				//tie
				ties++;
				end = "Tie game, no winner";
			}else if(choice = 2 && guess2 = 3{
				//guess wins
				humanWin++
				//rock trumps scissors
				end = "You win!!";
			}else if(choice = 3 && guess2 = 1{
				//guess wins
				humanWin++
				//paper trumps rock
				end = "You win!!";
			}else if(choice = 2 && guess2 = 1{
				//choice wins
				win++
				//scissors trump paper
				end = "Wow. I win!!";
			}else if(choice = 3 && guess2 = 2{
				//choice wins
				win++
				//rock trumps scissors
				end = "Wow. I win!!";
			}else if(choice = 1 && guess2 = 2{
				//guess wins
				humanWin++
				//scissors trump paper
				end = "You win!!";
			}else if(choice = 1 && guess2 = 3{
				//choice wins
				win++
				//paper trumps rock
				end = "Wow. I win!!";
			}
			System.out.println(end);
		}
		String finalMessage = "Here's the final game score: I have won " + win " game(s). You have won " + humanWin " game(s). And " + ties + " game(s) ended in a tie.";
		return finalMessage;
	}