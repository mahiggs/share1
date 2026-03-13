package cs120;

import java.util.Arrays;

/**
 * This class sets the output we want to the method from stats that calculates it so it can be printed
 */

public class Reporter {

	protected double[] nums;
	protected Statistician statHelper;

	public Reporter( double[] theNumbers ) {
		this.nums = theNumbers;
		statHelper = new Stats(this.nums);
	}

	/**
	 * Constructs and returns a string that reports the current
	 * array of valid numbers followed by the statistical metrics
	 * derived from those numbers using the statHelper.
	 *
	 * @return - A formatted string containing the numbers and all statistics.
	 */
	public String reportStatistics() {
		statHelper.setNums(this.nums);

		StringBuilder sb = new StringBuilder();
		sb.append("Numbers: ").append(Arrays.toString(nums)).append("\n\n");
		sb.append("Min: ").append(statHelper.findMin()).append("\n");
		sb.append("Max: ").append(statHelper.findMax()).append("\n");
		sb.append("Mean: ").append(statHelper.findMean()).append("\n");
		sb.append("Median: ").append(statHelper.findMedian()).append("\n");
		sb.append("Variance: ").append(statHelper.findVariance()).append("\n");
		sb.append("Q1: ").append(statHelper.findQ1()).append("\n");
		sb.append("Q3: ").append(statHelper.findQ3()).append("\n");
		sb.append("IQR: ").append(statHelper.findIRQ()).append("\n\n");
		sb.append("Outliers: ").append(Arrays.toString(statHelper.outliers()));
		return sb.toString();
	}

	public double[] getNums() {
		return nums;
	}

	public void setNums(double[] nums) {
		this.nums = nums;
	}

	public Statistician getStatHelper() {
		return statHelper;
	}

	public void setStatHelper(Statistician statHelper) {
		this.statHelper = statHelper;
	}


}
