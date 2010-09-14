/**
 * Provides fuctionalities to teach neural networks.
 */
package libras.neuralnetworks.learning;

import libras.neuralnetworks.NeuralNetwork;
import libras.utils.ValidationHelper;

/**
 * Represents the learning of a neural network.
 * @author Daniel Baptista Dias
 */
public abstract class Learning
{
	/**
	 * Instantiate a learning algorithm telling which network will be coach.
	 * @param networkToTeach Network that will be trained.
	 * @param learningRate Learning rate of this training.
	 */
	public Learning(NeuralNetwork networkToTeach, double learningRate)
	{
		ValidationHelper.validateIfParameterIsNull(networkToTeach, "networkToTeach");
		
		this.network = networkToTeach;
		this.setLearningRate(learningRate);
	}
	
	private NeuralNetwork network = null;
	
	private double learningRate = 1.0;
	
	/**
	 * Sets the learning rate of this algorithm.
	 * @param learningRate Learning rate of this algorithm.
	 */
	public void setLearningRate(double learningRate)
	{
		this.learningRate = Math.max(0.0, Math.min(1.0, learningRate));
	}
	
	/**
	 * Gets the learning rate of this algorithm.
	 * @return Learning rate of this algorithm.
	 */
	public double getLearningRate()
	{
		return this.learningRate;
	}

	/**
	 * Gets the network used to teach.
	 * @return Network used to teach.
	 */
	protected NeuralNetwork getNetwork()
	{
		return this.network;
	}
}
