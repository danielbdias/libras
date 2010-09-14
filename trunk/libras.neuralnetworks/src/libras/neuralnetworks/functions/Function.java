/**
 * Provides mathematical fuctionalities to use neural networks.
 */
package libras.neuralnetworks.functions;

/**
 * Represents a mathematical function to activate a neuron.
 * @author Daniel Baptista Dias
 */
public interface Function
{
	/**
	 * Makes a calculation.
	 */
	double calculate(double x);
}
