package cs120;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * An instance of this class provides an object expert in prompting the user
 * for integers and real numbers. 
 * <p>
 * We isolate the current input stream and output stream so that during testing
 * we don't have to use the default System.in and System.out.   This allows us
 * to automate the source of the information and avoid the human operator during
 * testing. 
 * </p>
 * <p>
 * NOTE:  During testing we can "inject" the istrm and ostrm in a number of ways:
 * <ul>
 * <li>direct access of the instance variables using testing subclass</li>
 * <li>overwriting the access method using a testing subclass</li>
 * <li>adding and using additional setter methods on a testing subclass</li>
 * </ul>
 * This concept is known as "dependency injection".
 * </p>
 */
public class NumPrompter {
	
	/** where messages to the user go */
	protected PrintStream ostrm;   
	
	/** helps us read numbers from user input */
	protected Scanner scan;
	
	/**
	 * As the primary constructor, this algorithm initializes the handles
	 * on our helper objects.   This represents our runtime configuration
	 * for our finished running app.
	 */
	public NumPrompter() {
		ostrm = System.out;
		scan = new Scanner(System.in);
	}
	
	
	/**
	 * A alternate constructor (for testing) allows us to also provide a
	 * testing stunt double (a fake user) from which we can pull numbers
	 * as if a person typed them in. This allows us to automate the testing
	 * without the need of human intervention while we run the tests.
	 *  
	 * @param s a handle on the scanner configured on a alternate input stream
	 */
	public NumPrompter(Scanner s) {
		this();
		scan = s;
	}
	
	/**
	 * Prompts the user for zero or more real numbers (doubles) and collects
	 * them into an array of doubles.   Negative values are ignored.   The user
	 * will continue to be prompted until the required numbers are provided 
	 * by the operator.  Can return an empty array if the user wants not to 
	 * provide any real numbers.
	 * 
	 * @return full array of n double values 
	 * 
	 */
	public double[] getReals() {
				
		outStream().print("\nHow many real numbers do you intend to provide? ");
		int n = scan.nextInt();
		
		double[] theNums = new double[n];   // holds the numbers from user
		
		/*
		 * repeatedly prompt user for the data. use the scanner 
		 */
		for (int i=0; i<n; ) {
			outStream().print("\nPlease enter your real numbers (negatives are ignored): ");
			double dval = scan.nextDouble(); 
			if (dval < 0) continue;
			theNums[i++] = dval;
		}
		
		outStream().println();
		
		return theNums;
	}
	
	
	
	/**
	 * Use this in your code whenever you need a handle on the output stream.  Do
	 * not assume System.out every time.  It might be something else during testing.
	 * @return
	 */
	protected PrintStream outStream() {
		return ostrm;
	}
	


	/**
	 * Another way to inject a specific output stream.  Might
	 * be useful in testing purposes if we override to provide a "mock" output. 
	 * 
	 * @param ostrm
	 */
	public void setOutputStream(PrintStream ostrm) {
		this.ostrm = ostrm;
	}


	/**
	 * Another way to inject a scanner.  Might
	 * be useful in testing purposes.  WARNING:  the programmer
	 * is responsible for making sure the scanner is appropriate
	 * for the input stream.
	 * 
	 * @param ostrm
	 */
	public void setScanner(Scanner scan) {
		this.scan = scan;
	}

}
