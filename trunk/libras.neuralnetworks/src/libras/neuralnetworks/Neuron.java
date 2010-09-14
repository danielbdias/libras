/**
 * Provides fuctionalities to use neural networks.
 */
package libras.neuralnetworks;

import libras.utils.ValidationHelper;

/**
 * Represents a single neuron of a network.
 * @author Daniel Baptista Dias
 */
public abstract class Neuron
{	
	/**
	 * Creates a new instance of this class setting up the input and output connections.
	 * @param inputConnections Input connections of the neuron.
	 * @param outputConnections Output connections of the neuron.
	 */
	protected Neuron(NeuronConnectionList inputConnections, NeuronConnectionList outputConnections)
	{
		ValidationHelper.validateIfParameterIsNull(inputConnections, "inputConnections");
		ValidationHelper.validateIfParameterIsNull(outputConnections, "outputConnections");
		
		this.inputConnections = inputConnections;
		this.outputConnections = outputConnections;
	}
	
	private NeuronConnectionList inputConnections = null;
	
	private NeuronConnectionList outputConnections = null;

	/**
	 * Gets the input connections of this neuron.
	 * @return Input connections of this neuron.
	 */
	public NeuronConnectionList getInputConnections()
	{
		return inputConnections;
	}
	
	/**
	 * Gets the output connections of this neuron.
	 * @return Output connections of this neuron.
	 */
	public NeuronConnectionList getOutputConnections()
	{
		return outputConnections;
	}
}
