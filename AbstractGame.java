/***
 * Base class for games. Provides methods for saving/retrieving memorable
 * scores.
 * 
 * @author K. Collins
 * @version Fall, 2017
 *
 */
public abstract class AbstractGame {

	/**
	 * Retrieves the recorded score object associated with this class, if one is
	 * available.
	 * 
	 * @return the current record, if present; null, otherwise
	 */
	public final BestScore getBestScore() {
		return GameTracker.getRecord(this.getClass());
	}

	/**
	 * Registers a game record with the GameTracker.
	 * 
	 * @param value
	 *            an integer score
	 * @param initials
	 *            the record holder
	 */
	public final void setBestScore(int value, String initials) {
		if (initials == null || initials.equals(""))
			initials = "---";
		else if (initials.length() > 3)
			initials = initials.substring(0, 3).toUpperCase();
		else
			initials = initials.toUpperCase();
		GameTracker.setBestScore(this.getClass(),
				new BestScore(value, initials));
	}
}
