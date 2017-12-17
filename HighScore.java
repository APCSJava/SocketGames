/***
 * Plain old Java object for associating a score with a player's initials.
 * 
 * @author kentcollins
 * @version Fall, 2017
 */

public class HighScore {
	private int score;
	private String holder;

	public HighScore(int score, String holder) {
		this.score = score;
		this.holder = holder;
	}

	public int getScore() {
		return score;
	}

	public String getInitials() {
		return holder;
	}

}