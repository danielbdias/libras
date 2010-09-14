/**
 * Package with classes used in Distance-Based Neural Networks.
 */
package libras.neuralnetworks.distancenetworks;

import libras.neuralnetworks.NeuronConnection;

/**
 * Represents a connection between two neurons in a distance-based neural networks.
 * @author Daniel Baptista Dias
 */
public class DistanceNeuronConnection extends NeuronConnection
{
	/**
	 * Creates a new instance with a weight, an input neuron and a output neuron.
	 * @param weight Weight of this connection.
	 * @param input Neuron which will send signals by this connection.
	 * @param output Neuron which will receive signals by this connection.
	 */
	public DistanceNeuronConnection(double weight, DistanceNeuron input, DistanceNeuron output)
	{
		super(weight, input, output);
	}

	/**
	 * Gets the input neuron of this connection.
	 * @return Input neuron of this connection.
	 * @see libras.neuralnetworks.NeuronConnection#getInput()
	 */
	protected DistanceNeuron getInput()
	{
		return (DistanceNeuron) super.getInput();
	}
	
	/**
	 * Gets the output neuron of this connection.
	 * @return Output neuron of this connection.
	 * @see libras.neuralnetworks.NeuronConnection#getOutput()
	 */
	protected DistanceNeuron getOutput()
	{
		return (DistanceNeuron) super.getOutput();
	}
}
