package cs120;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This JUnit class tests our NumPrompter.  We can test the inputs, outputs, and the
 * values we get back from a working instance of the NumPrompter.
 * 
 */
public class NumPrompterTest {

	/**
	 * Using this test as a manual test, a visual validation only; not 
	 * intended for regression testing suite.  Remove the ignore annotation
	 * if you want to test manually.   Then its like a dedicated main algorithm
	 * for testing only the number prompter.
	 */
    @Ignore
	@Test
	public void test_getReals_whenNotEmpty_fromUser() {
		
		NumPrompter np = new NumPrompter();
		
		double[] vals = np.getReals();
		
		Arrays.stream(vals).forEach(System.out::println);  // print each valid value
	}
	
	
	/**
	 * Emulates the operator/user providing text as it might look like for a user
	 * that provides a negative value on the third desired number.  We respond with
	 * 3 for the number of items, then provide 3 valid values ignoring any invalid
	 * numbers. Injecting the scanner via setter method, but could also use
	 * the alternate constructor for this test.
	 */
	@Test
	public void test_getReals_whenNotEmpty_fromStringStream() {
		
		InputStream stream = new ByteArrayInputStream("3 10.7 5.2 -9.8 12.1".getBytes());

		/*
		 * prepares our test subject to use the input stream of numbers above
		 */
		NumPrompter np = new NumPrompter();
		np.setScanner(new Scanner(stream));  // inject/replace using setter method

		//NumPrompter np = new NumPrompter(new Scanner(stream));  // or like this
		
		/*
		 * now exercise our test subject, but not reading from a human operator
		 */
		double[] vals = np.getReals();
		
		/*
		 * Check we got the doubles expected, ignoring the negatives. 
		 */
		assertArrayEquals(new double[] {10.7, 5.2, 12.1}, vals, 0.0);
		
	}
	

	/**
	 * Ensures that the user will see the appropriate prompts when we exercise
	 * the NumPrompter instance.
	 */
	
	@Test
	public void test_getReals_output_whenNotEmpty_fromStringStream() {


		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);

		InputStream stream = new ByteArrayInputStream("3 10.7 5.2 -9.8 12.1".getBytes());


		/*
		 * For this test we read from the stream above (instead of a real user).  So
		 * we inject the helper scanner using the alternate constructor.
		 */
		NumPrompter np = new NumPrompter(new Scanner(stream));
		np.setOutputStream(new PrintStream(out));  // capture output in out object

		/*
		 * exercise our test subject, but not reading from a human operator
		 */
		double[] vals = np.getReals();

		/*
		 * Check we got the doubles expected, ignoring the negatives.
		 */
		assertArrayEquals(new double[] {10.7, 5.2, 12.1}, vals, 0.0);

		/*
		 * now check to make sure we prompted the user for number of items AND
		 * we surrounded the output by blank lines AND we did prompted again when
		 * the user provided a negative
		 */
		String expected = "\n"
				+ "How many real numbers do you intend to provide? \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n";

		assertEquals(expected, out.toString());   // check what the user would see

	}


	// ==================== Additional tests for coverage ====================

	/**
	 * When the user requests zero numbers, the loop body should never execute
	 * and an empty array is returned.  This covers the n=0 path where
	 * the for-loop condition (i < 0) is immediately false.
	 */
	@Test
	public void test_getReals_whenEmpty_zeroRequested() {

		InputStream stream = new ByteArrayInputStream("0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));

		double[] vals = np.getReals();

		assertArrayEquals(new double[] {}, vals, 0.0);
	}

	/**
	 * Verifies the output when zero numbers are requested: only the
	 * count prompt and a trailing newline, no value prompts at all.
	 */
	@Test
	public void test_getReals_output_whenEmpty_zeroRequested() {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream stream = new ByteArrayInputStream("0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));
		np.setOutputStream(new PrintStream(out));

		np.getReals();

		String expected = "\n"
				+ "How many real numbers do you intend to provide? \n";

		assertEquals(expected, out.toString());
	}

	/**
	 * All values are valid (no negatives).  The if-continue branch is
	 * never taken; every iteration increments i immediately.
	 */
	@Test
	public void test_getReals_allPositive_noNegatives() {

		InputStream stream = new ByteArrayInputStream("3 2.0 4.0 6.0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));

		double[] vals = np.getReals();

		assertArrayEquals(new double[] {2.0, 4.0, 6.0}, vals, 0.0);
	}

	/**
	 * Verifies output when all values are positive: exactly 3 value prompts
	 * (no extra prompt caused by a rejected negative).
	 */
	@Test
	public void test_getReals_output_allPositive_noNegatives() {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream stream = new ByteArrayInputStream("3 2.0 4.0 6.0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));
		np.setOutputStream(new PrintStream(out));

		np.getReals();

		String expected = "\n"
				+ "How many real numbers do you intend to provide? \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n";

		assertEquals(expected, out.toString());
	}

	/**
	 * A single valid value is requested and provided.  Covers n=1.
	 */
	@Test
	public void test_getReals_singleValue() {

		InputStream stream = new ByteArrayInputStream("1 7.5".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));

		double[] vals = np.getReals();

		assertArrayEquals(new double[] {7.5}, vals, 0.0);
	}

	/**
	 * Zero (0.0) is not negative, so it should be accepted into the array.
	 * Tests the boundary of the dval < 0 check.
	 */
	@Test
	public void test_getReals_zeroValueAccepted() {

		InputStream stream = new ByteArrayInputStream("2 0.0 3.0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));

		double[] vals = np.getReals();

		assertArrayEquals(new double[] {0.0, 3.0}, vals, 0.0);
	}

	/**
	 * Multiple consecutive negatives before a valid value.  The continue
	 * branch fires several times in a row without advancing i.
	 */
	@Test
	public void test_getReals_multipleConsecutiveNegatives() {

		InputStream stream = new ByteArrayInputStream("1 -1.0 -2.0 -3.0 5.0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));

		double[] vals = np.getReals();

		assertArrayEquals(new double[] {5.0}, vals, 0.0);
	}

	/**
	 * Verifies that multiple consecutive negatives each produce an
	 * additional prompt before the valid value is finally accepted.
	 */
	@Test
	public void test_getReals_output_multipleConsecutiveNegatives() {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream stream = new ByteArrayInputStream("1 -1.0 -2.0 5.0".getBytes());

		NumPrompter np = new NumPrompter(new Scanner(stream));
		np.setOutputStream(new PrintStream(out));

		np.getReals();

		// 1 valid value requested, but 2 negatives means 3 value prompts total
		String expected = "\n"
				+ "How many real numbers do you intend to provide? \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n"
				+ "Please enter your real numbers (negatives are ignored): \n";

		assertEquals(expected, out.toString());
	}

}
