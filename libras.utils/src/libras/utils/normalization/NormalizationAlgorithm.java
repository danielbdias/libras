/**
 * Provides normalization methods for a set.
 */
package libras.utils.normalization;

/**
 * Represents a normalization algorithm.
 * @author Daniel Baptista Dias
 */
public abstract class NormalizationAlgorithm
{
	/**
	 * Normalize a set.
	 * @param set Set to be normalized.
	 * @return Normalized set.
	 */
	public abstract double[] normalize(double[] set);
}
