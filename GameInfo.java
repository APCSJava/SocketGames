import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * A class annotation holding meta information about a game. Providing
 * information with this class annotation allows it to be accessed by the
 * GameTracker and used as an information source when preparing menus.
 * 
 * @author K. Collins
 * @version Fall, 2017
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface GameInfo {
	String gameTitle() default "Untitled";

	String[] authors() default "No author information";

	String version() default "No version information";

	String description() default "No description provided";
}
