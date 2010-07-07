/**
 * Provides mathematical fuctionalities to use neural networks.
 */
package libras.neuralnetworks.functions;

/**
 * Makes a calculation using the sigmoidal function (1.0 / (1.0 - exp(-1.0*alpha*x))).
 * @author Daniel Baptista Dias
 */
public class SigmoidalFunction implements Function
{
	/**
	 * Creates a new instance of sigmoidal fuction, passing the alpha value.
	 */
	public SigmoidalFunction(double alpha)
	{
		this.alpha = alpha;
	}
	
	private double alpha = 0.0;
	
	/**
	 * Makes a calculation using the sigmoidal function.
	 */
	public double calculate(double x)
	{
		return 1.0 / (1.0 - Math.exp(-1.0*alpha*x));
	}
}
