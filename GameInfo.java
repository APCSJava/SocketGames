import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * An annotation to hold meta information about games.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface GameInfo {
	String gameTitle() default "Untitled";
	String[] authors() default "No author information";
	String version() default "No version information";
	String description() default "No description provided";
}
