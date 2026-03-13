package cs120;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Comprehensive JUnit4 tests for the Reporter class.
 * Covers typical cases, exceptional/edge cases, and all code paths.
 */
public class ReporterTest {

	private static final double DELTA = 0.0001;

	// ==================== Constructor ====================

	@Test
	public void testConstructor_setsNums() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		assertArrayEquals(new double[] {1, 2, 3}, r.getNums(), DELTA);
	}

	@Test
	public void testConstructor_createsStatHelper() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		assertNotNull(r.getStatHelper());
	}

	@Test
	public void testConstructor_emptyArray() {
		Reporter r = new Reporter(new double[] {});
		assertArrayEquals(new double[] {}, r.getNums(), DELTA);
	}

	// ==================== reportStatistics() — typical ====================

	@Test
	public void testReportStatistics_typicalDataset() {
		Reporter r = new Reporter(new double[] {1, 2, 3, 4, 5});
		String report = r.reportStatistics();

		assertTrue(report.contains("Numbers: [1.0, 2.0, 3.0, 4.0, 5.0]"));
		assertTrue(report.contains("Min: 1.0"));
		assertTrue(report.contains("Max: 5.0"));
		assertTrue(report.contains("Mean: 3.0"));
		assertTrue(report.contains("Median: 3.0"));
		assertTrue(report.contains("Variance: 2.0"));
		assertTrue(report.contains("Q1: 1.5"));
		assertTrue(report.contains("Q3: 4.5"));
		assertTrue(report.contains("IQR: 3.0"));
		assertTrue(report.contains("Outliers: []"));
	}

	@Test
	public void testReportStatistics_containsAllLabels() {
		Reporter r = new Reporter(new double[] {10, 20});
		String report = r.reportStatistics();

		String[] expectedLabels = {
			"Numbers:", "Min:", "Max:", "Mean:", "Median:",
			"Variance:", "Q1:", "Q3:", "IQR:", "Outliers:"
		};
		for (String label : expectedLabels) {
			assertTrue("Report missing label: " + label, report.contains(label));
		}
	}

	@Test
	public void testReportStatistics_knownDataset() {
		// {2,4,4,4,5,5,7,9}: well-known stats
		Reporter r = new Reporter(new double[] {2, 4, 4, 4, 5, 5, 7, 9});
		String report = r.reportStatistics();

		assertTrue(report.contains("Min: 2.0"));
		assertTrue(report.contains("Max: 9.0"));
		assertTrue(report.contains("Mean: 5.0"));
		assertTrue(report.contains("Median: 4.5"));
		assertTrue(report.contains("Variance: 4.0"));
		assertTrue(report.contains("Q1: 4.0"));
		assertTrue(report.contains("Q3: 6.0"));
		assertTrue(report.contains("IQR: 2.0"));
		assertTrue(report.contains("Outliers: []"));
	}

	@Test
	public void testReportStatistics_withOutliers() {
		// {1,2,3,4,5,100}: 100 is an outlier
		Reporter r = new Reporter(new double[] {1, 2, 3, 4, 5, 100});
		String report = r.reportStatistics();

		assertTrue(report.contains("Numbers: [1.0, 2.0, 3.0, 4.0, 5.0, 100.0]"));
		assertTrue(report.contains("Outliers: [100.0]"));
	}

	// ==================== reportStatistics() — edge cases ====================

	@Test
	public void testReportStatistics_emptyArray() {
		Reporter r = new Reporter(new double[] {});
		String report = r.reportStatistics();

		assertTrue(report.contains("Numbers: []"));
		assertTrue(report.contains("Min: 0.0"));
		assertTrue(report.contains("Max: 0.0"));
		assertTrue(report.contains("Mean: 0.0"));
		assertTrue(report.contains("Median: 0.0"));
		assertTrue(report.contains("Variance: 0.0"));
		assertTrue(report.contains("Q1: 0.0"));
		assertTrue(report.contains("Q3: 0.0"));
		assertTrue(report.contains("IQR: 0.0"));
		assertTrue(report.contains("Outliers: []"));
	}

	@Test
	public void testReportStatistics_singleElement() {
		Reporter r = new Reporter(new double[] {42});
		String report = r.reportStatistics();

		assertTrue(report.contains("Numbers: [42.0]"));
		assertTrue(report.contains("Min: 42.0"));
		assertTrue(report.contains("Max: 42.0"));
		assertTrue(report.contains("Mean: 42.0"));
		assertTrue(report.contains("Median: 42.0"));
		assertTrue(report.contains("Variance: 0.0"));
		assertTrue(report.contains("Outliers: []"));
	}

	@Test
	public void testReportStatistics_twoElements() {
		Reporter r = new Reporter(new double[] {10, 20});
		String report = r.reportStatistics();

		assertTrue(report.contains("Numbers: [10.0, 20.0]"));
		assertTrue(report.contains("Min: 10.0"));
		assertTrue(report.contains("Max: 20.0"));
		assertTrue(report.contains("Mean: 15.0"));
		assertTrue(report.contains("Median: 15.0"));
		assertTrue(report.contains("Variance: 25.0"));
	}

	@Test
	public void testReportStatistics_negativeValues() {
		Reporter r = new Reporter(new double[] {-3, -1, -2});
		String report = r.reportStatistics();

		assertTrue(report.contains("Numbers: [-3.0, -1.0, -2.0]"));
		assertTrue(report.contains("Min: -3.0"));
		assertTrue(report.contains("Max: -1.0"));
		assertTrue(report.contains("Mean: -2.0"));
		assertTrue(report.contains("Median: -2.0"));
	}

	// ==================== reportStatistics() — format verification ====================

	@Test
	public void testReportStatistics_numbersOnFirstLine() {
		Reporter r = new Reporter(new double[] {5, 10});
		String report = r.reportStatistics();
		String firstLine = report.split("\n")[0];
		assertEquals("Numbers: [5.0, 10.0]", firstLine);
	}

	@Test
	public void testReportStatistics_lineCount() {
		// Should have 10 lines: Numbers, Min, Max, Mean, Median,
		// Variance, Q1, Q3, IQR, Outliers
		Reporter r = new Reporter(new double[] {1, 2, 3});
		String report = r.reportStatistics();
		String[] lines = report.split("\n");
		assertEquals(12, lines.length);
	}

	// ==================== setNums / getNums ====================

	@Test
	public void testGetNums_returnsConstructorArray() {
		double[] data = {1, 2, 3};
		Reporter r = new Reporter(data);
		assertArrayEquals(data, r.getNums(), DELTA);
	}

	@Test
	public void testSetNums_changesNums() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		r.setNums(new double[] {10, 20});
		assertArrayEquals(new double[] {10, 20}, r.getNums(), DELTA);
	}

	@Test
	public void testSetNums_reportReflectsNewData() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		r.setNums(new double[] {100, 200});
		String report = r.reportStatistics();

		// reportStatistics syncs statHelper, so new data should appear
		assertTrue(report.contains("Numbers: [100.0, 200.0]"));
		assertTrue(report.contains("Min: 100.0"));
		assertTrue(report.contains("Max: 200.0"));
		assertTrue(report.contains("Mean: 150.0"));
	}

	// ==================== getStatHelper / setStatHelper ====================

	@Test
	public void testGetStatHelper_notNull() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		assertNotNull(r.getStatHelper());
	}

	@Test
	public void testSetStatHelper_replacesHelper() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		Statistician newHelper = new Stats(99, 100);
		r.setStatHelper(newHelper);
		assertEquals(newHelper, r.getStatHelper());
	}

	@Test
	public void testSetStatHelper_reportUsesNewHelper() {
		Reporter r = new Reporter(new double[] {1, 2, 3});

		// Inject a Stats helper with different data
		Statistician customHelper = new Stats(50, 60, 70);
		r.setStatHelper(customHelper);

		// reportStatistics calls setNums on statHelper with Reporter's nums,
		// so the helper gets synced to Reporter's nums {1,2,3}
		String report = r.reportStatistics();
		assertTrue(report.contains("Mean: 2.0"));
	}

	// ==================== Integration ====================

	@Test
	public void testReportStatistics_calledTwice_sameResult() {
		Reporter r = new Reporter(new double[] {1, 2, 3, 4, 5});
		String first = r.reportStatistics();
		String second = r.reportStatistics();
		assertEquals(first, second);
	}

	@Test
	public void testReportStatistics_afterSetNums_thenAgain() {
		Reporter r = new Reporter(new double[] {1, 2, 3});
		String report1 = r.reportStatistics();
		assertTrue(report1.contains("Mean: 2.0"));

		r.setNums(new double[] {10, 20, 30});
		String report2 = r.reportStatistics();
		assertTrue(report2.contains("Mean: 20.0"));
	}

}
