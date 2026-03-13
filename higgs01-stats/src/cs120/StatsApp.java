package cs120;

import java.io.PrintStream;

/**
 * The launching application for the Statistics Calculator.
 * <p>
 * Greets the operator, prompts for a series of real numbers using
 * a NumPrompter, then builds a Reporter to display the statistical
 * metrics derived from those numbers.
 * </p>
 * <p>
 * Follows the dependency-injection pattern established by NumPrompter:
 * a default constructor wired for production (System.out), and an
 * alternate constructor plus setters that allow test code to inject
 * stunt-double dependencies.
 * </p>
 */
public class StatsApp {

	/** where application-level messages go */
	protected PrintStream ostrm;

	/** the object responsible for prompting the user for numbers */
	protected NumPrompter prompter;

	/**
	 * Production constructor. Wires the output to System.out and
	 * creates a default NumPrompter (which reads from System.in).
	 */
	public StatsApp() {
		ostrm = System.out;
		prompter = new NumPrompter();
	}

	/**
	 * Testing constructor. Allows injection of a pre-configured
	 * NumPrompter and an alternate output stream so that automated
	 * tests can run without a human operator.
	 *
	 * @param prompter a NumPrompter (possibly configured with a fake input stream)
	 * @param ostrm    the output stream for application messages
	 */
	public StatsApp(NumPrompter prompter, PrintStream ostrm) {
		this.prompter = prompter;
		this.ostrm = ostrm;
	}

	/**
	 * Runs the application workflow:
	 * <ol>
	 *   <li>Greets the operator</li>
	 *   <li>Prompts for a series of real numbers</li>
	 *   <li>Reports all statistical metrics for the collected numbers</li>
	 *   <li>Prints a farewell message</li>
	 * </ol>
	 */
	public void run() {
		// Greet the operator
		outStream().println("\nWelcome to the Statistics Calculator!");

		// Prompt for numbers
		double[] nums = prompter.getReals();

		// Build the report and display it
		Reporter reporter = new Reporter(nums);
		outStream().println(reporter.reportStatistics());

		// Farewell
		outStream().println("Thank you for using the Statistics Calculator!\n");
	}

	/**
	 * Use this in your code whenever you need a handle on the output stream.
	 * Do not assume System.out every time. It might be something else during testing.
	 *
	 * @return the current output stream
	 */
	protected PrintStream outStream() {
		return ostrm;
	}

	/**
	 * Setter for the output stream. Useful for testing.
	 *
	 * @param ostrm the output stream to use
	 */
	public void setOutputStream(PrintStream ostrm) {
		this.ostrm = ostrm;
	}

	/**
	 * Setter for the NumPrompter. Useful for testing.
	 *
	 * @param prompter the NumPrompter to use
	 */
	public void setPrompter(NumPrompter prompter) {
		this.prompter = prompter;
	}

	/**
	 * The launching main method. Creates and runs a StatsApp with
	 * default (production) configuration.
	 *
	 * @param args command-line arguments (unused)
	 */
	public static void main(String[] args) {
		StatsApp app = new StatsApp();
		app.run();
	}

}
