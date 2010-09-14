/**
 * Package with classes used in Distance-Based Neural Networks.
 */
package libras.neuralnetworks.distancenetworks;

import libras.neuralnetworks.NeuronConnectionList;

/**
 * Represents a list of neuron connections of a distance-based neural network. 
 * @author Daniel Baptista Dias 
 */
public class DistanceNeuronConnectionList extends NeuronConnectionList
{
	/**
	 * Gets a connection in the list. 
	 * @see java.util.ArrayList#get(int)
	 */
	public DistanceNeuronConnection get(int index)
	{
		return (DistanceNeuronConnection) super.get(index);
	}
	
	/**
	 * Get the weight vector with the data of all connections of this list.
	 * @return Weight vector with the data of all connections of this list.
	 */
	double[] getWeightVector()
	{
		double[] weightVector = new double[this.size()];
		
		for (int i = 0; i < weightVector.length; i++)
			weightVector[i] = this.get(i).getWeight();
		
		return weightVector;
	}
	
	/**
	 * Get the weight vector with the data of all connections of this list.
	 * @param weightVector Weight vector with the data of all connections of this list.
	 * @throws Exception When an unexpected exception occurs.
	 */
	void setWeightVector(double[] weightVector) throws Exception
	{
		if (weightVector.length != this.size())
			throw new Exception(
					String.format("Incompatible weight vector. The weight vector dimension must be %d.", this.size()));
	
		for (int i = 0; i < weightVector.length; i++)
			this.get(i).setWeight(weightVector[i]);
	}
}
