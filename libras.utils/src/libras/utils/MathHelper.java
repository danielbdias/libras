/**
 * Provides utility classes to multiple purposes.
 */
package libras.utils;

/**
 * Contains some mathematical functions used to deal with neural networks.
 * @author Daniel Baptista Dias
 */
public final class MathHelper
{
	/**
	 * This method cannot be called, because this class has only static methods.
	 */
	private MathHelper()
	{ }
	
	/**
	 * Calculates the Euclidean distance between two vectors.
	 * @param firstVector First vector to be used.
	 * @param secondVector Seconde vector to be used.
	 * @return The distance between the two vectors.
	 */
	public static int euclideanDistance(int[] firstVector, int[] secondVector)
	{
		ValidationHelper.validateIfParameterIsNull(firstVector, "firstVector");
		ValidationHelper.validateIfParameterIsNull(secondVector, "secondVector");
		
		ValidationHelper.validateIfArraysHaveSameLenght(firstVector.length, "firstVector", secondVector.length, "secondVector");
	
		int sum = 0;
		
		for (int i = 0; i < firstVector.length; i++)
			sum += (int) Math.pow(firstVector[i] - secondVector[i], 2);
		
		return (int) Math.sqrt(sum);
	}
	
	/**
	 * Calculates the Euclidean distance between two vectors.
	 * @param firstVector First vector to be used.
	 * @param secondVector Seconde vector to be used.
	 * @return The distance between the two vectors.
	 */
	public static double euclideanDistance(double[] firstVector, double[] secondVector)
	{
		ValidationHelper.validateIfParameterIsNull(firstVector, "firstVector");
		ValidationHelper.validateIfParameterIsNull(secondVector, "secondVector");
		
		ValidationHelper.validateIfArraysHaveSameLenght(firstVector.length, "firstVector", secondVector.length, "secondVector");
			
		double sum = 0;
		
		for (int i = 0; i < firstVector.length; i++)
			sum +=  Math.pow(firstVector[i] - secondVector[i], 2);
		
		return  Math.sqrt(sum);
	}

	/**
	 * Sum two vectors.
	 * @param firstVector First vector to be used.
	 * @param secondVector Seconde vector to be used.
	 * @return The result of the sum of the two vectors.
	 */
	public static double[] sumVectors(double[] firstVector, double[] secondVector)
	{
		ValidationHelper.validateIfParameterIsNull(firstVector, "firstVector");
		ValidationHelper.validateIfParameterIsNull(secondVector, "secondVector");
		
		ValidationHelper.validateIfArraysHaveSameLenght(firstVector.length, "firstVector", secondVector.length, "secondVector");
	
		double[] result = new double[firstVector.length];
		
		for (int i = 0; i < result.length; i++)
			result[i] = firstVector[i] + secondVector[i];
		
		return result;
	}
	
	/**
	 * Subtract a vector from another.
	 * @param firstVector First vector to be used.
	 * @param secondVector Seconde vector to be used.
	 * @return The result of the subtraction of the two vectors.
	 */
	public static double[] subtractVectors(double[] firstVector, double[] secondVector)
	{
		ValidationHelper.validateIfParameterIsNull(firstVector, "firstVector");
		ValidationHelper.validateIfParameterIsNull(secondVector, "secondVector");
		
		ValidationHelper.validateIfArraysHaveSameLenght(firstVector.length, "firstVector", secondVector.length, "secondVector");
	
		double[] result = new double[firstVector.length];
			
		for (int i = 0; i < result.length; i++)
			result[i] = firstVector[i] - secondVector[i];
		
		return result;
	}

	/**
	 * Multiply all components of a vector by a scalar value.
	 * @param vector Target vector.
	 * @param number Scalar value.
	 * @return The result of the scalar multiplication.
	 */
	public static double[] scalarMultiplication(double[] vector, double number)
	{
		ValidationHelper.validateIfParameterIsNull(vector, "vector");
		
		double[] result = new double[vector.length];
		
		for (int i = 0; i < result.length; i++)
			result[i] = vector[i] * number;
		
		return result;
	}
}
