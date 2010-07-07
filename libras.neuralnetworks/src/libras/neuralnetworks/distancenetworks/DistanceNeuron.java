/**
 * Package with classes used in Distance-Based Neural Networks.
 */
package libras.neuralnetworks.distancenetworks;

import libras.neuralnetworks.Neuron;

/**
 * Represents a neuron from a distance neural network.
 * @author Daniel Baptista Dias
 */
public class DistanceNeuron extends Neuron
{
	/**
	 * Initiate a new instance of this class.
	 */
	public DistanceNeuron()
	{
		super(new DistanceNeuronConnectionList(), new DistanceNeuronConnectionList());
	}
	
	/**
	 * Get the weight vector (Voronoi vector) of this neuron.
	 * @return Weight vector (Voronoi vector) of this neuron.
	 */
	public double[] getWeightVector()
	{
		return this.getInputConnections().getWeightVector();
	}
	
	/**
	 * Get the weight vector (Voronoi vector) of this neuron.
	 * @param weightVector Weight vector (Voronoi vector) of this neuron.
	 * @throws Exception When an unexpected exception occurs.
	 */
	public void setWeightVector(double[] weightVector) throws Exception
	{
		this.getInputConnections().setWeightVector(weightVector);
	}
	
	/**
	 * Gets the input connections of this neuron.
	 * @return Input connections of this neuron.
	 * @see libras.neuralnetworks.Neuron#getInputConnections()
	 */
	public DistanceNeuronConnectionList getInputConnections()
	{
		return (DistanceNeuronConnectionList) super.getInputConnections();
	}
	
	/**
	 * Gets the output connections of this neuron.
	 * @return Output connections of this neuron.
	 * @see libras.neuralnetworks.Neuron#getOutputConnections()
	 */
	public DistanceNeuronConnectionList getOutputConnections()
	{
		return (DistanceNeuronConnectionList) super.getOutputConnections();
	}
}
