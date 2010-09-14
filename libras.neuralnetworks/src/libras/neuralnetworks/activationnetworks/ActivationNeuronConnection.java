/**
 * Package with classes used in Activation-Based Neural Networks.
 */
package libras.neuralnetworks.activationnetworks;

import libras.neuralnetworks.NeuronConnection;

/**
 * Represents a connection between two neurons in a activation-based neural networks.
 * @author Daniel Baptista Dias
 */
class ActivationNeuronConnection extends NeuronConnection
{
	/**
	 * Creates a new instance with a weight, an input neuron and a output neuron.
	 * @param weight Weight of this connection.
	 * @param input Neuron which will send signals by this connection.
	 * @param output Neuron which will receive signals by this connection.
	 */
	public ActivationNeuronConnection(double weight, ActivationNeuron input, ActivationNeuron output)
	{
		super(weight, input, output);
	}

	/**
	 * Do the synapse process between the input neuron and the output neuron.
	 */
	public void doSynapse()
	{
		double pulse = this.getInput().emitPulse();
		
		pulse *= this.getWeight();
		
		this.getOutput().receivePulse(pulse);
	}
	
	/**
	 * Gets the input neuron of this connection.
	 * @return Input neuron of this connection.
	 * @see libras.neuralnetworks.NeuronConnection#getInput()
	 */
	protected ActivationNeuron getInput()
	{
		return (ActivationNeuron) super.getInput();
	}
	
	/**
	 * Gets the output neuron of this connection.
	 * @return Output neuron of this connection.
	 * @see libras.neuralnetworks.NeuronConnection#getOutput()
	 */
	protected ActivationNeuron getOutput()
	{
		return (ActivationNeuron) super.getOutput();
	}
}
