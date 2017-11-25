import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Represents a game.
 * 
 * @author kentcollins
 *
 */
public interface Servable {

	void serve(BufferedReader input, PrintWriter output) throws IOException;

}
