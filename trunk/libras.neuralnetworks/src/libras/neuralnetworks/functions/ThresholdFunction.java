/**
 * Provides mathematical fuctionalities to use neural networks.
 */
package libras.neuralnetworks.functions;

/**
 * Makes a calculation using the threshold function.
 */
public class ThresholdFunction implements Function
{
	/**
	 * Creates a new instance.
	 */
	public ThresholdFunction()
	{
		
	}
	
	/**
	 * Creates a new instance with a specified threshold.
	 */
	public ThresholdFunction(double threshold)
	{
		this.threshold = threshold;
	}
	
	private double threshold = 0.0;
	
	/**
	 * Makes a calculation using the threshold function.
	 */
	public double calculate(double x)
	{
		return (x >= threshold ? 1.0 : 0.0);
	}
}
