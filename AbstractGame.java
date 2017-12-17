/***
 * Base class for games.
 * 
 * @author kentcollins
 * @version Fall, 2017
 *
 */
public abstract class AbstractGame {

	/**
	 * Returns the highest score associated with this class, if present in the
	 * GameTracker.
	 * 
	 * @return the current high score, if present; Integer.MIN_VALUE, otherwise
	 */
	public final int getHighScoreValue() {
		return GameTracker.getHighScoreValue(this.getClass());
	}

	/**
	 * Returns the initials of the person who set the highest score, if set.
	 * 
	 * @return a three-letter, uppercase string; null, if no high score set
	 */
	public final String getHighScoreInitials() {
		return GameTracker.getHighScoreInitials(this.getClass());
	}

	/**
	 * Sets the high score in the GameTracker to the specified value.
	 * @param value an integer score
	 * @param initials the record holder
	 */
	public final void setHighScore(int value, String initials) {
		if (initials == null || initials.equals(""))
			initials = "---";
		else if (initials.length() > 3)
			initials = initials.substring(0, 3).toUpperCase();
		else
			initials = initials.toUpperCase();
		GameTracker.setHighScore(this.getClass(), value, initials);
	}
}
