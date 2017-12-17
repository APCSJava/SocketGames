import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Interface for games that read and write across streams.
 * 
 * @author kentcollins
 * @version Fall, 2017
 */
public interface Servable {

	void serve(BufferedReader input, PrintWriter output)
			throws IOException;

}
