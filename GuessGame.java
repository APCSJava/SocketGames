public class GuessGame{
//Final code for the Guess project
	

	// public GuessGame(String choice){
	// 	this.choice = choice;
	// }
	// public GuessGame(int limit){
		
	// }

	//public int getHint(int guess1){
	public static void main(String[] args)  {
		String choice;
		System.out.println("What limit do you want?");
		int choice2 = (int) Math.random() * limit + 1;
		choice = choice2;
		//get number of tries, num of tries should be equal to the hidden number except for the fir  st digit 
		//so if the hidden number is 180 then you should guess that number in 80 tries
		String num = Integer.toString(choice);
		String tries1 = num.substring(1);
		int tries = (int)tries1;
		int numTries = 0;
		String end = "";
		String message1 = "Im thinking of a number between 1 and " + limit + ". Now you try to guess what it is.";
		System.out.println(message1);
		for(int i = 0; i < choice + 100; i++){
			if(guess1 < choice){
				numTries++;
				end = "Too low. Try a bigger number.";	
			}else if(guess1 > choice){
				numTries++;
				end = "Too high. Try a smaller number.";
			}else if(guess1 = choice){
				numTries++;
				end = "That's it! You got it in " + numTries + " tries.";
			}
		System.out.println(end);
		}
		
		String finalMessage = "";
		if(guess1 = choice && numTries <= tries){
			finalMessage = "Very good.";
		}else if(guess1 = choice && numTries > tries){
			finalMessage = "You should have been able to get it in " + tries + " tries.";
		}
		return finalMessage;
	}
}