package cs120;

public interface Statistician {

	/**
	 * Find the minimum value in the array of doubles.
	 * 
	 * @return - Minimum value in the array.
	 */
	double findMin();

	/**
	 * Find the maximum value in the array of doubles.
	 * 
	 * @return - Maximum value in the array.
	 */
	double findMax();

	/**
	 * Find the average (mean) value of the array of doubles.
	 * 
	 * @return - Average value of the array.
	 */
	double findMean();

	/**
	 * Find the median value of the array of doubles.
	 * 
	 * @return - Median value of the array.
	 */
	double findMedian();

	
	/**
	 * Finds the variance of the array of doubles.
	 * @return
	 */
	double findVariance();

	/**
	 * Find the first quartile of the array of doubles.
	 * 
	 * @return - The first quartile of the array.
	 */
	double findQ1();

	/**
	 * Find the third quartile of the array of doubles.
	 * 
	 * @return - The third quartile of the array.
	 */
	double findQ3();

	/**
	 * Find the interquartile range of the array of doubles.
	 * 
	 * @return - The interquartile range of the array.
	 */
	double findIRQ();

	/**
	 * Find outliers in the array of doubles.
	 * 
	 * @return - An array of outliers present in the input array.
	 */
	double[] outliers();

	void setNums(double... numVals);

}