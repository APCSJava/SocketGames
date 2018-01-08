package org.asl.socketserver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * A class annotation for storing meta information about a class. Using this
 * annotation allows key informative data about the game/service to be accessed
 * by the GameTracker and used to prepare descriptive menus.
 * 
 * @author K. Collins
 * @version Fall, 2017
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface MenuInfo {
	String title() default "Untitled";

	String[] authors() default "No author information";

	String version() default "No version information";

	String description() default "No description provided";
}
