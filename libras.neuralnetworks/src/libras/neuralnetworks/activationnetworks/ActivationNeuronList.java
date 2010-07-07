/**
 * Package with classes used in Activation-Based Neural Networks.
 */
package libras.neuralnetworks.activationnetworks;

import libras.neuralnetworks.*;

/**
 * Represents collection with lists of neurons of an activation-based neural network.
 * @author Daniel Baptista Dias
 */
public class ActivationNeuronList extends NeuronList
{
	/**
	 * Gets a neuron from the list.
	 * @see java.util.ArrayList#get(int)
	 */
	public ActivationNeuron get(int index)
	{
		return (ActivationNeuron) super.get(index);
	}
	
	/**
	 * Adds a neuron to the list.
	 * @param e Neuron to be added.
	 */
	public boolean add(ActivationNeuron e)
	{
		return super.add(e);
	}
}
