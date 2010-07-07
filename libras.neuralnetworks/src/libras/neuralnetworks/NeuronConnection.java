/**
 * Provides fuctionalities to use neural networks.
 */
package libras.neuralnetworks;

import libras.utils.ValidationHelper;

/**
 * Represents a connection between two neurons.
 * @author Daniel Baptista Dias
 */
public abstract class NeuronConnection
{
	/**
	 * Creates a new instance with a weight, an input neuron and a output neuron.
	 * @param weight Weight of this connection.
	 * @param input Neuron which will send signals by this connection.
	 * @param output Neuron which will receive signals by this connection.
	 */
	public NeuronConnection(double weight, Neuron input, Neuron output)
	{
		ValidationHelper.validateIfParameterIsNull(input, "input");
		ValidationHelper.validateIfParameterIsNull(output, "output");
		
		this.weight = weight;
		
		this.input = input;
		this.input.getOutputConnections().add(this);
		
		this.output = output;
		this.output.getInputConnections().add(this);
	}
	
	private double weight = 0.0;
	
	private Neuron input = null;
	
	private Neuron output = null;

	/**
	 * Gets the weight of this connection.
	 * @return Weight of this connection.
	 */
	public double getWeight()
	{
		return this.weight;
	}
	
	/**
	 * Sets the weight of this connection.
	 * @param weight New weight of this connection.
	 */
	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	/**
	 * Gets the input neuron of this connection.
	 * @return Input neuron of this connection.
	 */
	protected Neuron getInput()
	{
		return input;
	}

	/**
	 * Gets the output neuron of this connection.
	 * @return Output neuron of this connection.
	 */
	protected Neuron getOutput()
	{
		return output;
	}
}
