/**
 * Package with classes used in Distance-Based Neural Networks.
 */
package libras.neuralnetworks.distancenetworks;

import libras.neuralnetworks.*;

/**
 * Represents collection with lists of neurons of a distance-based neural network.
 * @author Daniel Baptista Dias
 */
public class DistanceNeuronList extends NeuronList
{
	/**
	 * Gets a neuron from the list.
	 * @see java.util.ArrayList#get(int)
	 */
	public DistanceNeuron get(int index)
	{
		return (DistanceNeuron) super.get(index);
	}
	
	/**
	 * Adds a neuron to the list.
	 * @param e Neuron to be added.
	 */
	public boolean add(DistanceNeuron e)
	{
		return super.add(e);
	}
}
