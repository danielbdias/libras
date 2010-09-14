/**
 * Package with classes used in Activation-Based Neural Networks.
 */
package libras.neuralnetworks.activationnetworks;

import libras.neuralnetworks.NeuronConnectionList;

/**
 * Represents a list of neuron connections of an activation-based neural network. 
 * @author Daniel Baptista Dias 
 */
public class ActivationNeuronConnectionList extends NeuronConnectionList
{
	/**
	 * Executes the synapse process in all connections of the list.
	 */
	public void doSynapse()
	{
		for (int i = 0; i < this.size(); i++)
			this.get(i).doSynapse();
	}
	
	/**
	 * Gets a connection in the list. 
	 * @see java.util.ArrayList#get(int)
	 */
	public ActivationNeuronConnection get(int index)
	{
		return (ActivationNeuronConnection) super.get(index);
	}
}
