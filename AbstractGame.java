/***
 * Base class for games. 
 * 
 * @author kentcollins
 * @version Fall, 2017
 *
 */
public abstract class AbstractGame {

	public final int getHighScoreValue() {
		return GameTracker.getHighScoreValue(this.getClass());
	}
	
	public final String getHighScoreInitials() {
		return GameTracker.getHighScoreInitials(this.getClass());
	}

	public final void setHighScore(int value, String initials) {
		if (initials== null || initials.equals("")) initials = "---";
		else if (initials.length()>3) initials = initials.substring(0, 3).toUpperCase();
		else initials = initials.toUpperCase();
		GameTracker.setHighScore(this.getClass(), value, initials);
	}
}
