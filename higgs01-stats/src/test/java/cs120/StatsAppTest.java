package cs120;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cs120.NumPrompter;
import cs120.StatsApp;

/**
 * Comprehensive JUnit4 tests for the StatsApp class.
 * Covers typical cases, exceptional/edge cases, and all code paths.
 * <p>
 * Strategy: inject a NumPrompter wired to a fake input stream and route
 * both the NumPrompter's output and the StatsApp's output to the same
 * captured ByteArrayOutputStream so we can verify the full console
 * experience the operator would see.
 * </p>
 */
public class StatsAppTest {

	/** Saved handles so we can restore System.in / System.out after tests that touch them */
	private InputStream originalIn;
	private PrintStream originalOut;

	@Before
	public void saveSystemStreams() {
		originalIn = System.in;
		originalOut = System.out;
	}

	@After
	public void restoreSystemStreams() {
		System.setIn(originalIn);
		System.setOut(originalOut);
	}

	// -------- helper: build a fully-injected StatsApp --------

	/**
	 * Creates a StatsApp whose NumPrompter reads from {@code input} and whose
	 * entire console output (prompts + app messages) goes to {@code captured}.
	 */
	private StatsApp buildTestApp(String input, ByteArrayOutputStream captured) {
		PrintStream ps = new PrintStream(captured);

		InputStream stream = new ByteArrayInputStream(input.getBytes());
		NumPrompter np = new NumPrompter(new Scanner(stream));
		np.setOutputStream(ps); // prompts go to the captured stream

		return new StatsApp(np, ps); // app messages also go to the captured stream
	}

	// ==================== run() — typical cases ====================

	/**
	 * Typical workflow: operator provides 5 positive numbers.  Verify the
	 * greeting, report content, and farewell all appear in the output.
	 */
	@Test
	public void testRun_typicalInput() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("5 1.0 2.0 3.0 4.0 5.0", out);

		app.run();
		String output = out.toString();

		// Greeting
		assertTrue(output.contains("Welcome to the Statistics Calculator!"));

		// Numbers echoed
		assertTrue(output.contains("Numbers: [1.0, 2.0, 3.0, 4.0, 5.0]"));

		// Statistical metrics
		assertTrue(output.contains("Min: 1.0"));
		assertTrue(output.contains("Max: 5.0"));
		assertTrue(output.contains("Mean: 3.0"));
		assertTrue(output.contains("Median: 3.0"));
		assertTrue(output.contains("Variance: 2.0"));
		assertTrue(output.contains("Q1: 1.5"));
		assertTrue(output.contains("Q3: 4.5"));
		assertTrue(output.contains("IQR: 3.0"));
		assertTrue(output.contains("Outliers: []"));

		// Farewell
		assertTrue(output.contains("Thank you for using the Statistics Calculator!"));
	}

	/**
	 * Verifies the known textbook dataset {2,4,4,4,5,5,7,9} flows
	 * correctly through the full app pipeline.
	 */
	@Test
	public void testRun_knownDataset() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("8 2.0 4.0 4.0 4.0 5.0 5.0 7.0 9.0", out);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Min: 2.0"));
		assertTrue(output.contains("Max: 9.0"));
		assertTrue(output.contains("Mean: 5.0"));
		assertTrue(output.contains("Median: 4.5"));
		assertTrue(output.contains("Variance: 4.0"));
		assertTrue(output.contains("Q1: 4.0"));
		assertTrue(output.contains("Q3: 6.0"));
		assertTrue(output.contains("IQR: 2.0"));
		assertTrue(output.contains("Outliers: []"));
	}

	// ==================== run() — edge cases ====================

	/**
	 * Operator requests zero numbers.  The report should show an empty
	 * dataset and all metrics should default to 0.
	 */
	@Test
	public void testRun_zeroNumbers() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("0", out);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Welcome to the Statistics Calculator!"));
		assertTrue(output.contains("Numbers: []"));
		assertTrue(output.contains("Min: 0.0"));
		assertTrue(output.contains("Mean: 0.0"));
		assertTrue(output.contains("Outliers: []"));
		assertTrue(output.contains("Thank you for using the Statistics Calculator!"));
	}

	/**
	 * Operator provides a single number.  Quartile and IQR should be 0,
	 * variance should be 0, no outliers.
	 */
	@Test
	public void testRun_singleValue() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("1 42.0", out);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Numbers: [42.0]"));
		assertTrue(output.contains("Min: 42.0"));
		assertTrue(output.contains("Max: 42.0"));
		assertTrue(output.contains("Mean: 42.0"));
		assertTrue(output.contains("Variance: 0.0"));
		assertTrue(output.contains("Outliers: []"));
	}

	/**
	 * Input includes negatives that the NumPrompter should reject.
	 * Only the valid numbers reach the Reporter.
	 */
	@Test
	public void testRun_withNegativesRejected() {
		// Request 2 numbers, supply -1.0 (rejected), 10.0, -5.0 (rejected), 20.0
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("2 -1.0 10.0 -5.0 20.0", out);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Numbers: [10.0, 20.0]"));
		assertTrue(output.contains("Min: 10.0"));
		assertTrue(output.contains("Max: 20.0"));
		assertTrue(output.contains("Mean: 15.0"));
	}

	/**
	 * Dataset that produces an outlier flows through the full pipeline.
	 */
	@Test
	public void testRun_withOutlier() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("6 1.0 2.0 3.0 4.0 5.0 100.0", out);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Numbers: [1.0, 2.0, 3.0, 4.0, 5.0, 100.0]"));
		assertTrue(output.contains("Outliers: [100.0]"));
	}

	/**
	 * Two numbers — the smallest useful dataset for quartile calculations.
	 */
	@Test
	public void testRun_twoValues() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("2 5.0 15.0", out);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Numbers: [5.0, 15.0]"));
		assertTrue(output.contains("Min: 5.0"));
		assertTrue(output.contains("Max: 15.0"));
		assertTrue(output.contains("Mean: 10.0"));
		assertTrue(output.contains("Median: 10.0"));
	}

	// ==================== run() — output ordering ====================

	/**
	 * Greeting must appear before the report, and the report must appear
	 * before the farewell.
	 */
	@Test
	public void testRun_outputOrder() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("3 1.0 2.0 3.0", out);

		app.run();
		String output = out.toString();

		int greetIdx    = output.indexOf("Welcome to the Statistics Calculator!");
		int numbersIdx  = output.indexOf("Numbers:");
		int minIdx      = output.indexOf("Min:");
		int outliersIdx = output.indexOf("Outliers:");
		int farewellIdx = output.indexOf("Thank you for using the Statistics Calculator!");

		assertTrue("Greeting should come first",         greetIdx >= 0);
		assertTrue("Numbers echo after greeting",        numbersIdx > greetIdx);
		assertTrue("Min after numbers",                  minIdx > numbersIdx);
		assertTrue("Outliers after min",                 outliersIdx > minIdx);
		assertTrue("Farewell after report",              farewellIdx > outliersIdx);
	}

	/**
	 * The NumPrompter's count prompt should appear between the greeting
	 * and the statistical report.
	 */
	@Test
	public void testRun_promptAppearsAfterGreeting() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StatsApp app = buildTestApp("2 3.0 7.0", out);

		app.run();
		String output = out.toString();

		int greetIdx  = output.indexOf("Welcome");
		int promptIdx = output.indexOf("How many real numbers");
		int reportIdx = output.indexOf("Numbers:");

		assertTrue(greetIdx >= 0);
		assertTrue("Prompt should appear after greeting", promptIdx > greetIdx);
		assertTrue("Report should appear after prompts",  reportIdx > promptIdx);
	}

	// ==================== Constructors ====================

	/**
	 * Default constructor creates a usable object (does not throw).
	 */
	@Test
	public void testDefaultConstructor() {
		StatsApp app = new StatsApp();
		assertNotNull(app);
	}

	/**
	 * Injection constructor stores the provided dependencies.
	 */
	@Test
	public void testInjectionConstructor() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);
		NumPrompter np = new NumPrompter(
				new Scanner(new ByteArrayInputStream("0".getBytes())));

		StatsApp app = new StatsApp(np, ps);
		assertNotNull(app);

		// Verify the injected output stream is used
		app.run();
		assertTrue(out.toString().contains("Welcome"));
	}

	// ==================== Setters ====================

	/**
	 * setOutputStream replaces the output stream after construction.
	 */
	@Test
	public void testSetOutputStream() {
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();

		StatsApp app = buildTestApp("0", out1);

		// Redirect to a new stream before running
		app.setOutputStream(new PrintStream(out2));
		app.run();

		// App messages should go to out2 (prompts still go to out1 via NumPrompter)
		assertTrue(out2.toString().contains("Welcome"));
	}

	/**
	 * setPrompter replaces the NumPrompter after construction.
	 */
	@Test
	public void testSetPrompter() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);

		// Build app with an initial prompter that would give 0 numbers
		StatsApp app = buildTestApp("0", out);

		// Replace with a prompter that provides 2 numbers
		InputStream stream2 = new ByteArrayInputStream("2 99.0 101.0".getBytes());
		NumPrompter np2 = new NumPrompter(new Scanner(stream2));
		np2.setOutputStream(ps);
		app.setPrompter(np2);

		app.run();
		String output = out.toString();

		assertTrue(output.contains("Numbers: [99.0, 101.0]"));
		assertTrue(output.contains("Mean: 100.0"));
	}

	// ==================== outStream() ====================

	/**
	 * outStream() returns the configured stream.
	 */
	@Test
	public void testOutStream_returnsConfiguredStream() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);

		NumPrompter np = new NumPrompter(
				new Scanner(new ByteArrayInputStream("0".getBytes())));

		StatsApp app = new StatsApp(np, ps);

		// outStream is protected, so we verify indirectly: run() uses it
		app.run();
		String output = out.toString();
		assertTrue(output.length() > 0);
	}

	// ==================== main() ====================

	/**
	 * Exercises the static main method by temporarily redirecting
	 * System.in and System.out so no human operator is required.
	 */
	@Test
	public void testMain_runsWithoutError() {
		System.setIn(new ByteArrayInputStream("3 5.0 10.0 15.0".getBytes()));
		ByteArrayOutputStream captured = new ByteArrayOutputStream();
		System.setOut(new PrintStream(captured));

		StatsApp.main(new String[] {});

		String output = captured.toString();
		assertTrue(output.contains("Welcome to the Statistics Calculator!"));
		assertTrue(output.contains("Numbers: [5.0, 10.0, 15.0]"));
		assertTrue(output.contains("Mean: 10.0"));
		assertTrue(output.contains("Thank you for using the Statistics Calculator!"));
	}

	/**
	 * main() with zero numbers also completes without error.
	 */
	@Test
	public void testMain_zeroNumbers() {
		System.setIn(new ByteArrayInputStream("0".getBytes()));
		ByteArrayOutputStream captured = new ByteArrayOutputStream();
		System.setOut(new PrintStream(captured));

		StatsApp.main(new String[] {});

		String output = captured.toString();
		assertTrue(output.contains("Numbers: []"));
	}

	// ==================== Manual / visual test ====================

	/**
	 * Manual test for visual validation only — reads from a real user.
	 * Remove the @Ignore annotation to run interactively.
	 */
	@Ignore
	@Test
	public void testRun_manualFromUser() {
		StatsApp app = new StatsApp();
		app.run();
	}

}
