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
		logger.info("Initializing Stats Class");
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
		logger.info("Called findMin()");
		
		if (nums.length == 0) {
			logger.info("nums.length = 0");
			return 0;
		}
		
		logger.debug("nums.length = {}", nums.length);
		
		double minimum = nums[0];
		for (int i = 1; i < nums.length; i++) {
			minimum = Math.min(minimum, nums[i]);
			logger.debug("calculates current minimum as {}", minimum);
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
		logger.info("Called findMax()");
		
		if (nums.length == 0) {
			logger.info("nums.length = 0");
			return 0;
		}
		
		logger.debug("nums.length = {}", nums.length);
		
		double maximum = nums[0];
		for (int i = 1; i < nums.length; i++) {
			maximum = Math.max(maximum, nums[i]);
			logger.debug("calculates current maximum as {}", maximum);
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
		logger.info("Called findMean()");
		
		if (nums.length == 0) {
			logger.info("nums.length = 0");
			return 0;
		}
		
		logger.debug("nums.length = {}", nums.length);
		
		double sum = 0;
		for (double value : nums) {
			sum += value;
			logger.debug("calculates current sum as {}", sum);
		}
		
		logger.debug("current mean is {}", sum / nums.length);

		return sum / nums.length;
	}

	/**
	 * Find the median value of the array of doubles.
	 * 
	 * @return - Median value of the array.
	 */
	@Override
	public double findMedian() {
		logger.info("Called findMedian()");
		
		if (nums.length == 0) {
			logger.info("nums.length = 0");
			return 0;
		}

		double[] localData = nums.clone();
		Arrays.sort(localData);
		
		logger.info("Cloned and sorted localData into an array");
		
		logger.debug("nums.length = {}", nums.length);

		if (nums.length % 2 == 0) {
			// Even number of elements
			int index1 = localData.length / 2 - 1;
			int index2 = localData.length / 2;
			double sum = localData[index1] + localData[index2];
			logger.debug("calculates current median as {}", sum/2);
			return sum / 2;
		} else {
			// Odd number of elements
			logger.debug("calculates current median as {}", localData[localData.length / 2]);
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
		logger.info("Called findVariance()");
		
		if (nums.length == 0) {
			logger.info("nums.length = 0");
			return 0;
		}
		
		logger.debug("nums.length = {}", nums.length);

		double mean = findMean();
		double sumSquaredDiffs = 0;
		for (double value : nums) {
			double diff = value - mean;
			sumSquaredDiffs += diff * diff;
		}
		
		logger.debug("calculates current variance as {}", sumSquaredDiffs / nums.length);

		return sumSquaredDiffs / nums.length;
	}
	
	/**
	 * Find the first quartile of the array of doubles.
	 * 
	 * @return - The first quartile of the array.
	 */
	@Override
	public double findQ1() {
		logger.info("Called findQ1()");
		
		logger.debug("nums.length = {}", nums.length);
		
		if (nums.length < 2) {
			logger.info("length is < 2, too short");
			return 0; // Too few values for a first quartile
		}

		double[] localNumbers = nums.clone();
		Arrays.sort(localNumbers);
		
		logger.info("Cloned and sorted localData into an array");

		// Integer division properly removes the middle number automatically
		double[] lowerHalf = Arrays.copyOfRange(localNumbers, 0, localNumbers.length / 2);
		
		logger.info("Cut array in half");

		return new Stats(lowerHalf).findMedian();
	}

	/**
	 * Find the third quartile of the array of doubles.
	 * 
	 * @return - The third quartile of the array.
	 */
	@Override
	public double findQ3() {
		logger.info("Called findQ3()");
		
		logger.debug("nums.length = {}", nums.length);
		
		if (nums.length < 2) {
			logger.info("length is < 2, too short");
			return 0; // Too few values for a third quartile
		}

		double[] localNumbers = nums.clone();
		Arrays.sort(localNumbers);
		
		logger.info("Cloned and sorted localData into an array");

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
		
		logger.info("Cut array in half");

		return new Stats(upperHalf).findMedian();
	}

	/**
	 * Find the interquartile range of the array of doubles.
	 * 
	 * @return - The interquartile range of the array.
	 */
	@Override
	public double findIRQ() {
		logger.info("Called findIQR()");
		
		return this.findQ3() - this.findQ1();
	}

	/**
	 * Find outliers in the array of doubles.
	 * 
	 * @return - An array of outliers present in the input array.
	 */
	@Override
	public double[] outliers() {
		logger.info("Called outliers()");
		
		logger.debug("nums.length = {}", nums.length);
		
		if (nums.length < 2) {
			logger.info("length is < 2, too short");
			return new double[] {}; // A data set with one value must not have an outlier
		}
		
		ArrayList<Double> outlierListBuilder = new ArrayList<Double>();
		logger.info("Created ArrayList");
		
		for (double value : nums) {
			double iqr = this.findIRQ();
			double q1 = this.findQ1();
			double q3 = this.findQ3();
			if (value < q1 - 1.5 * iqr || value > q3 + 1.5 * iqr) {
				outlierListBuilder.add(value);
				logger.debug("Added outlier value of {} to ArrayList", value);
			}
		}
		
		double[] outputArray = new double[outlierListBuilder.size()];
		for (int i = 0; i < outlierListBuilder.size(); i++) {
			outputArray[i] = outlierListBuilder.get(i);
		}
		logger.info("Converted ArrayList into an array");
		return outputArray;
	}

	@Override
	public void setNums(double ... numVals) {
		logger.info("Called setNums()");
		this.nums = numVals;
	}
}