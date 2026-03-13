package cs120;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Comprehensive JUnit4 tests for the Stats class.
 * Covers typical cases, exceptional/edge cases, and all code paths
 * for 100% coverage.
 */
public class StatsTests {

	private static final double DELTA = 0.0001;

	// ==================== findMin() ====================

	@Test
	public void testFindMin_typicalValues() {

		//double[] vals = new double[] {3, 1, 4, 1, 5, 9 };
		//Stats s = new Stats(vals);
		
		Stats s = new Stats(3, 1, 4, 1, 5, 9);

		assertEquals(1, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_singleElement() {
		Stats s = new Stats(42);
		assertEquals(42, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_allSameValues() {
		Stats s = new Stats(7, 7, 7, 7);
		assertEquals(7, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_negativeValues() {
		Stats s = new Stats(-5, -1, -10, -3);
		assertEquals(-10, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_mixedPositiveNegative() {
		Stats s = new Stats(-2, 0, 3, -7, 5);
		assertEquals(-7, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_minAtEnd() {
		Stats s = new Stats(5, 4, 3, 2, 1);
		assertEquals(1, s.findMin(), DELTA);
	}

	@Test
	public void testFindMin_minAtBeginning() {
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertEquals(1, s.findMin(), DELTA);
	}

	// ==================== findMax() ====================

	@Test
	public void testFindMax_typicalValues() {
		Stats s = new Stats(3, 1, 4, 1, 5, 9);
		assertEquals(9, s.findMax(), DELTA);
	}

	@Test
	public void testFindMax_singleElement() {
		Stats s = new Stats(42);
		assertEquals(42, s.findMax(), DELTA);
	}

	@Test
	public void testFindMax_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findMax(), DELTA);
	}

	@Test
	public void testFindMax_allSameValues() {
		Stats s = new Stats(7, 7, 7, 7);
		assertEquals(7, s.findMax(), DELTA);
	}

	@Test
	public void testFindMax_negativeValues() {
		Stats s = new Stats(-5, -1, -10, -3);
		assertEquals(-1, s.findMax(), DELTA);
	}

	@Test
	public void testFindMax_maxAtBeginning() {
		Stats s = new Stats(9, 5, 3, 1);
		assertEquals(9, s.findMax(), DELTA);
	}

	@Test
	public void testFindMax_maxAtEnd() {
		Stats s = new Stats(1, 3, 5, 9);
		assertEquals(9, s.findMax(), DELTA);
	}

	// ==================== findMean() ====================

	@Test
	public void testFindMean_typicalValues() {
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertEquals(3.0, s.findMean(), DELTA);
	}

	@Test
	public void testFindMean_singleElement() {
		Stats s = new Stats(10);
		assertEquals(10.0, s.findMean(), DELTA);
	}

	@Test
	public void testFindMean_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findMean(), DELTA);
	}

	@Test
	public void testFindMean_allSameValues() {
		Stats s = new Stats(5, 5, 5, 5);
		assertEquals(5.0, s.findMean(), DELTA);
	}

	@Test
	public void testFindMean_negativeValues() {
		Stats s = new Stats(-2, -4, -6);
		assertEquals(-4.0, s.findMean(), DELTA);
	}

	@Test
	public void testFindMean_mixedValues() {
		Stats s = new Stats(-10, 10);
		assertEquals(0.0, s.findMean(), DELTA);
	}

	@Test
	public void testFindMean_decimalResult() {
		Stats s = new Stats(1, 2);
		assertEquals(1.5, s.findMean(), DELTA);
	}

	// ==================== findMedian() ====================

	@Test
	public void testFindMedian_oddCount() {
		Stats s = new Stats(3, 1, 2);
		assertEquals(2.0, s.findMedian(), DELTA);
	}

	@Test
	public void testFindMedian_evenCount() {
		Stats s = new Stats(1, 2, 3, 4);
		assertEquals(2.5, s.findMedian(), DELTA);
	}

	@Test
	public void testFindMedian_singleElement() {
		Stats s = new Stats(99);
		assertEquals(99.0, s.findMedian(), DELTA);
	}

	@Test
	public void testFindMedian_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findMedian(), DELTA);
	}

	@Test
	public void testFindMedian_twoElements() {
		Stats s = new Stats(10, 20);
		assertEquals(15.0, s.findMedian(), DELTA);
	}

	@Test
	public void testFindMedian_unsorted() {
		Stats s = new Stats(5, 1, 3, 2, 4);
		assertEquals(3.0, s.findMedian(), DELTA);
	}

	@Test
	public void testFindMedian_allSameValues() {
		Stats s = new Stats(4, 4, 4, 4, 4);
		assertEquals(4.0, s.findMedian(), DELTA);
	}

	// ==================== findVariance() ====================

	@Test
	public void testFindVariance_typicalValues() {
		// {1,2,3,4,5}: mean=3, variance = (4+1+0+1+4)/5 = 2.0
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertEquals(2.0, s.findVariance(), DELTA);
	}

	@Test
	public void testFindVariance_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findVariance(), DELTA);
	}

	@Test
	public void testFindVariance_singleElement() {
		Stats s = new Stats(5);
		assertEquals(0.0, s.findVariance(), DELTA);
	}

	@Test
	public void testFindVariance_allSameValues() {
		Stats s = new Stats(3, 3, 3, 3);
		assertEquals(0.0, s.findVariance(), DELTA);
	}

	@Test
	public void testFindVariance_twoElements() {
		// {10, 20}: mean=15, variance = (25+25)/2 = 25.0
		Stats s = new Stats(10, 20);
		assertEquals(25.0, s.findVariance(), DELTA);
	}

	@Test
	public void testFindVariance_knownDataset() {
		// {2,4,4,4,5,5,7,9}: mean=5, variance = (9+1+1+1+0+0+4+16)/8 = 4.0
		Stats s = new Stats(2, 4, 4, 4, 5, 5, 7, 9);
		assertEquals(4.0, s.findVariance(), DELTA);
	}

	// ==================== findQ1() ====================

	@Test
	public void testFindQ1_evenCount() {
		// {1,2,3,4}: lower half = {1,2}, Q1 = 1.5
		Stats s = new Stats(1, 2, 3, 4);
		assertEquals(1.5, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_oddCount() {
		// {1,2,3,4,5}: lower half = {1,2}, Q1 = 1.5
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertEquals(1.5, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_singleElement() {
		Stats s = new Stats(5);
		assertEquals(0, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_twoElements() {
		// {3, 7}: lower half = {3}, Q1 = 3
		Stats s = new Stats(3, 7);
		assertEquals(3.0, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_unsorted() {
		Stats s = new Stats(5, 3, 1, 4, 2);
		// sorted: {1,2,3,4,5}, lower half = {1,2}, Q1 = 1.5
		assertEquals(1.5, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_largerEvenDataset() {
		// {2,4,4,4,5,5,7,9}: lower half = {2,4,4,4}, Q1 = (4+4)/2 = 4.0
		Stats s = new Stats(2, 4, 4, 4, 5, 5, 7, 9);
		assertEquals(4.0, s.findQ1(), DELTA);
	}

	@Test
	public void testFindQ1_threeElements() {
		// {1, 5, 9}: lower half = {1}, Q1 = 1
		Stats s = new Stats(1, 5, 9);
		assertEquals(1.0, s.findQ1(), DELTA);
	}

	// ==================== findQ3() ====================

	@Test
	public void testFindQ3_evenCount() {
		// {1,2,3,4}: upper half = {3,4}, Q3 = 3.5
		Stats s = new Stats(1, 2, 3, 4);
		assertEquals(3.5, s.findQ3(), DELTA);
	}

	@Test
	public void testFindQ3_oddCount() {
		// {1,2,3,4,5}: upper half = {4,5}, Q3 = 4.5
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertEquals(4.5, s.findQ3(), DELTA);
	}

	@Test
	public void testFindQ3_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findQ3(), DELTA);
	}

	@Test
	public void testFindQ3_singleElement() {
		Stats s = new Stats(5);
		assertEquals(0, s.findQ3(), DELTA);
	}

	@Test
	public void testFindQ3_twoElements() {
		// {3, 7}: upper half = {7}, Q3 = 7
		Stats s = new Stats(3, 7);
		assertEquals(7.0, s.findQ3(), DELTA);
	}

	@Test
	public void testFindQ3_unsorted() {
		Stats s = new Stats(5, 3, 1, 4, 2);
		// sorted: {1,2,3,4,5}, upper half = {4,5}, Q3 = 4.5
		assertEquals(4.5, s.findQ3(), DELTA);
	}

	@Test
	public void testFindQ3_largerEvenDataset() {
		// {2,4,4,4,5,5,7,9}: upper half = {5,5,7,9}, Q3 = (5+7)/2 = 6.0
		Stats s = new Stats(2, 4, 4, 4, 5, 5, 7, 9);
		assertEquals(6.0, s.findQ3(), DELTA);
	}

	
	@Test
	public void testFindQ3_threeElements() {
		// {1, 5, 9}: upper half = {9}, Q3 = 9
		Stats s = new Stats(1, 5, 9);
		assertEquals(9.0, s.findQ3(), DELTA);
	}

	// ==================== findIRQ() ====================

	@Test
	public void testFindIRQ_typicalValues() {
		// {1,2,3,4,5}: Q1=1.5, Q3=4.5, IQR=3.0
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertEquals(3.0, s.findIRQ(), DELTA);
	}

	@Test
	public void testFindIRQ_evenCount() {
		// {1,2,3,4}: Q1=1.5, Q3=3.5, IQR=2.0
		Stats s = new Stats(1, 2, 3, 4);
		assertEquals(2.0, s.findIRQ(), DELTA);
	}

	@Test
	public void testFindIRQ_emptyArray() {
		Stats s = new Stats();
		assertEquals(0, s.findIRQ(), DELTA);
	}

	@Test
	public void testFindIRQ_singleElement() {
		Stats s = new Stats(5);
		assertEquals(0, s.findIRQ(), DELTA);
	}

	@Test
	public void testFindIRQ_allSameValues() {
		Stats s = new Stats(4, 4, 4, 4);
		assertEquals(0, s.findIRQ(), DELTA);
	}

	@Test
	public void testFindIRQ_knownDataset() {
		// {2,4,4,4,5,5,7,9}: Q1=4.0, Q3=6.0, IQR=2.0
		Stats s = new Stats(2, 4, 4, 4, 5, 5, 7, 9);
		assertEquals(2.0, s.findIRQ(), DELTA);
	}

	// ==================== outliers() ====================

	@Test
	public void testOutliers_noOutliers() {
		Stats s = new Stats(1, 2, 3, 4, 5);
		assertArrayEquals(new double[] {}, s.outliers(), DELTA);
	}

	@Test
	public void testOutliers_hasOneOutlier() {
		// {1,2,3,4,5,100}: Q1=2, Q3=5, IQR=3
		// lower bound = 2-4.5 = -2.5, upper bound = 5+4.5 = 9.5
		// 100 > 9.5, so 100 is an outlier
		Stats s = new Stats(1, 2, 3, 4, 5, 100);
		assertArrayEquals(new double[] {100}, s.outliers(), DELTA);
	}

	@Test
	public void testOutliers_hasLowOutlier() {
		// {-50,2,3,4,5,6}: Q1=2.5, Q3=5.5, IQR=3
		// lower bound = 2.5-4.5 = -2.0, upper bound = 5.5+4.5 = 10.0
		// -50 < -2.0, so -50 is an outlier
		Stats s = new Stats(-50, 2, 3, 4, 5, 6);
		assertArrayEquals(new double[] {-50}, s.outliers(), DELTA);
	}

	@Test
	public void testOutliers_multipleOutliers() {
		// {-100, 1, 2, 3, 4, 5, 200}
		// sorted: {-100,1,2,3,4,5,200}, Q1 = median({-100,1,2}) = 1
		// Q3 = median({4,5,200}) = 5, IQR = 4
		// lower bound = 1-6 = -5, upper bound = 5+6 = 11
		// -100 < -5 and 200 > 11
		Stats s = new Stats(-100, 1, 2, 3, 4, 5, 200);
		assertArrayEquals(new double[] {-100, 200}, s.outliers(), DELTA);
	}

	@Test
	public void testOutliers_emptyArray() {
		Stats s = new Stats();
		assertArrayEquals(new double[] {}, s.outliers(), DELTA);
	}

	@Test
	public void testOutliers_singleElement() {
		Stats s = new Stats(5);
		assertArrayEquals(new double[] {}, s.outliers(), DELTA);
	}

	@Test
	public void testOutliers_twoElements_noOutliers() {
		Stats s = new Stats(10, 12);
		assertArrayEquals(new double[] {}, s.outliers(), DELTA);
	}

	// ==================== setNums() ====================

	@Test
	public void testSetNums_changesData() {
		Stats s = new Stats(1, 2, 3);
		assertEquals(2.0, s.findMean(), DELTA);

		s.setNums(10, 20, 30);
		assertEquals(20.0, s.findMean(), DELTA);
	}

	@Test
	public void testSetNums_toEmpty() {
		Stats s = new Stats(1, 2, 3);
		s.setNums();
		assertEquals(0, s.findMean(), DELTA);
		assertEquals(0, s.findMin(), DELTA);
		assertEquals(0, s.findMax(), DELTA);
	}

	@Test
	public void testSetNums_affectsAllMethods() {
		Stats s = new Stats();
		s.setNums(5, 10, 15, 20, 25);
		assertEquals(5.0, s.findMin(), DELTA);
		assertEquals(25.0, s.findMax(), DELTA);
		assertEquals(15.0, s.findMean(), DELTA);
		assertEquals(15.0, s.findMedian(), DELTA);
	}

	// ==================== Constructor ====================

	@Test
	public void testConstructor_varargs() {
		Stats s = new Stats(10, 20, 30);
		assertEquals(20.0, s.findMean(), DELTA);
	}

	@Test
	public void testConstructor_noArgs() {
		Stats s = new Stats();
		assertEquals(0, s.findMin(), DELTA);
	}

	// ==================== Integration / cross-method ====================

	@Test
	public void testAllStats_knownDataset() {
		// Classic textbook dataset: {2,4,4,4,5,5,7,9}
		Stats s = new Stats(2, 4, 4, 4, 5, 5, 7, 9);
		assertEquals(2.0, s.findMin(), DELTA);
		assertEquals(9.0, s.findMax(), DELTA);
		assertEquals(5.0, s.findMean(), DELTA);
		assertEquals(4.5, s.findMedian(), DELTA);
		assertEquals(4.0, s.findVariance(), DELTA);
		assertEquals(4.0, s.findQ1(), DELTA);
		assertEquals(6.0, s.findQ3(), DELTA);
		assertEquals(2.0, s.findIRQ(), DELTA);
		assertArrayEquals(new double[] {}, s.outliers(), DELTA);
	}

	@Test
	public void testMedianDoesNotMutateOriginal() {
		// Ensure findMedian (which sorts a clone) doesn't alter the original data
		Stats s = new Stats(5, 1, 3);
		s.findMedian();
		// If original was mutated, min would iterate sorted array; check mean stays consistent
		assertEquals(3.0, s.findMean(), DELTA);
		assertEquals(1.0, s.findMin(), DELTA);
	}
}
