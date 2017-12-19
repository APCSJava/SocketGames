/***
 * Plain old Java object for associating a score with a player's initials.
 * 
 * Changed from "high score" to "record score" because sometimes a lower score
 * is the target.  
 * 
 * @author K. Collins
 * @version Fall, 2017
 */

public class Record {
	private int score;
	private String holder;

	public Record(int score, String holder) {
		this.score = score;
		this.holder = holder;
	}

	public int getScore() {
		return score;
	}

	public String getHolder() {
		return holder;
	}

}