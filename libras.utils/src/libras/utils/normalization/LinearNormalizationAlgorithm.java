/**
 * Provides normalization methods for a set.
 */
package libras.utils.normalization;

import java.security.InvalidParameterException;

/**
 * Represents a liner normalization algorithm that normalizes the set applying the 
 * follow function for each item in the set:
 * item = item - min(set) / max(set) - min(set)
 * @author Daniel Baptista Dias
 */
public class LinearNormalizationAlgorithm extends NormalizationAlgorithm
{

	/**
	 * Normalizes the set using the follow function for each item in the set:
	 * item = item - min(set) / max(set) - min(set)
	 * @see libras.utils.normalization.NormalizationAlgorithm#normalize(double[])
	 */
	@Override
	public double[] normalize(double[] set)
	{
		if (set == null)
			throw new InvalidParameterException("The parameter \"set\" cannot be null.");
		
		double[] maximumAndMinimum = this.getMaximumAndMininum(set);
		
		double max = maximumAndMinimum[0], min = maximumAndMinimum[1];
		
		double[] normalizedSet = new double[set.length];
		
		for (int i = 0; i < set.length; i++)
			normalizedSet[i] = (set[i] - min) / (max - min);
		
		return normalizedSet;
	}

	private double[] getMaximumAndMininum(double[] set)
	{
		double[] result = new double[] { Double.NaN, Double.NaN };
		
		for (int i = 0; i < set.length; i++)
		{
			if (set[i] > result[0])
				result[0] = set[i];
			else if (set[i] < result[1])
				result[1] = set[i];
		}
		
		return result;
	}
}
