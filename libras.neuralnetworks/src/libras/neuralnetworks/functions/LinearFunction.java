/**
 * Provides mathematical fuctionalities to use neural networks.
 */
package libras.neuralnetworks.functions;

/**
 * Represents a linear function (y = a*x + b).
 * @author Daniel Baptista Dias
 *
 */
public class LinearFunction implements Function
{
	/**
	 * Creates a new instance setting the values.
	 */
	public LinearFunction(double a, double b)
	{
		this.a = a;
		this.b = b;
	}
	
	private double a = 1.0;
	
	private double b = 0.0;
	
	protected double getA()
	{
		return a;
	}

	protected void setA(double a)
	{
		this.a = a;
	}

	protected double getB()
	{
		return b;
	}

	protected void setB(double b)
	{
		this.b = b;
	}

	/**
	 * Calculate the linear funtion.
	 * @see libras.neuralnetworks.functions.Function#calculate(double)
	 */
	public double calculate(double x)
	{
		return a * x + b;
	}
}
