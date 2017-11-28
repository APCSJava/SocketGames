import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Available interface for classes that read from and write to the indicated
 * network streams.
 * 
 * @author kentcollins
 *
 */
public interface Servable {

	void serve(BufferedReader input, PrintWriter output)
			throws IOException;

}
