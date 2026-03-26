package cs120;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains methods to perform calculations on an array of doubles.
 */
public class Stats implements Statistician {
	
	private static final Logger logger = LoggerFactory.getLogger(Stats.class);
	
	private double[] nums;

	public Stats(double... nums) {
		this.nums = nums;
	}
	
	/**
	 * Added comment to push to Github.
	 */
	
	public int gitPractice() {
		int gitPract = 2;
		gitPract++;
		return gitPract;
	}

	/**
	 * Find the minimum value in the array of doubles.
	 * 
	 * @return - Minimum value in the array.
	 */
	@Override
	public double findMin() {
		if (nums.length == 0)
			return 0;

		double minimum = nums[0];
		for (int i = 1; i < nums.length; i++) {
			minimum = Math.min(minimum, nums[i]);
		}

		return minimum;
	}

	/**
	 * Find the maximum value in the array of doubles.
	 * 
	 * @return - Maximum value in the array.
	 */
	@Override
	public double findMax() {
		if (nums.length == 0)
			return 0;

		double maximum = nums[0];
		for (int i = 1; i < nums.length; i++) {
			maximum = Math.max(maximum, nums[i]);
		}

		return maximum;
	}

	/**
	 * Find the average (mean) value of the array of doubles.
	 * 
	 * @return - Average value of the array.
	 */
	@Override
	public double findMean() {
		if (nums.length == 0)
			return 0;

		double sum = 0;
		for (double value : nums) {
			sum += value;
		}

		return sum / nums.length;
	}

	/**
	 * Find the median value of the array of doubles.
	 * 
	 * @return - Median value of the array.
	 */
	@Override
	public double findMedian() {
		if (nums.length == 0)
			return 0;

		double[] localData = nums.clone();
		Arrays.sort(localData);

		if (nums.length % 2 == 0) {
			// Even number of elements
			int index1 = localData.length / 2 - 1;
			int index2 = localData.length / 2;
			double sum = localData[index1] + localData[index2];
			return sum / 2;
		} else {
			// Odd number of elements
			return localData[localData.length / 2];
		}
	}

	/**
	 * Find the population variance of the array of doubles.
	 * Variance = sum of (each value - mean)^2 / N
	 *
	 * @return - The population variance of the array.
	 */
	@Override
	public double findVariance() {
		if (nums.length == 0)
			return 0;

		double mean = findMean();
		double sumSquaredDiffs = 0;
		for (double value : nums) {
			double diff = value - mean;
			sumSquaredDiffs += diff * diff;
		}

		return sumSquaredDiffs / nums.length;
	}
	
	/**
	 * Find the first quartile of the array of doubles.
	 * 
	 * @return - The first quartile of the array.
	 */
	@Override
	public double findQ1() {
		if (nums.length < 2)
			return 0; // Too few values for a first quartile

		double[] localNumbers = nums.clone();
		Arrays.sort(localNumbers);

		// Integer division properly removes the middle number automatically
		double[] lowerHalf = Arrays.copyOfRange(localNumbers, 0, localNumbers.length / 2);

		return new Stats(lowerHalf).findMedian();
	}

	/**
	 * Find the third quartile of the array of doubles.
	 * 
	 * @return - The third quartile of the array.
	 */
	@Override
	public double findQ3() {
		if (nums.length < 2)
			return 0; // Too few values for a third quartile

		double[] localNumbers = nums.clone();
		Arrays.sort(localNumbers);

		double[] upperHalf;

		if (localNumbers.length % 2 == 0) {
			// Even number of numbers: upper half is easy
			upperHalf = Arrays.copyOfRange(localNumbers, localNumbers.length / 2,
					localNumbers.length);
		} else {
			// Odd number of numbers: exclude the middle number and take the upper half
			upperHalf = Arrays.copyOfRange(localNumbers, localNumbers.length / 2 + 1,
					localNumbers.length);
		}

		return new Stats(upperHalf).findMedian();
	}

	/**
	 * Find the interquartile range of the array of doubles.
	 * 
	 * @return - The interquartile range of the array.
	 */
	@Override
	public double findIRQ() {
		return this.findQ3() - this.findQ1();
	}

	/**
	 * Find outliers in the array of doubles.
	 * 
	 * @return - An array of outliers present in the input array.
	 */
	@Override
	public double[] outliers() {
		if (nums.length < 2)
			return new double[] {}; // A data set with one value must not have an outlier

		ArrayList<Double> outlierListBuilder = new ArrayList<Double>();
		for (double value : nums) {
			double iqr = this.findIRQ();
			double q1 = this.findQ1();
			double q3 = this.findQ3();
			if (value < q1 - 1.5 * iqr || value > q3 + 1.5 * iqr) {
				outlierListBuilder.add(value);
			}
		}
		
		double[] outputArray = new double[outlierListBuilder.size()];
		for (int i = 0; i < outlierListBuilder.size(); i++) {
			outputArray[i] = outlierListBuilder.get(i);
		}
		return outputArray;
	}

	@Override
	public void setNums(double ... numVals) {
		this.nums = numVals;
	}
}