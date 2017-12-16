/***
 * Base class for games. 
 * 
 * @author kentcollins
 * @version Fall, 2017
 *
 */
public abstract class AbstractGame implements Servable {

	private static int highScore;
	private static String highScoreHolder;
	
	public static int getHighScore() {
		return highScore;
	}
	
	public static void setHighScore(int score) {
		highScore = score;
	}

	public static String getHighScoreHolder() {
		return highScoreHolder;
	}

	public static void setHighScoreHolder(String highScoreHolder) {
		AbstractGame.highScoreHolder = highScoreHolder;
	}
}
