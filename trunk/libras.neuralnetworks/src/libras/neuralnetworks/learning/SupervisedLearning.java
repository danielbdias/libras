/**
 * Provides fuctionalities to teach neural networks.
 */
package libras.neuralnetworks.learning;

import libras.neuralnetworks.NeuralNetwork;

/**
 * Represents the supervised learning of a neural network.
 * @author Daniel Baptista Dias
 */
public abstract class SupervisedLearning extends Learning
{
	/**
	 * Instantiate a learning algorithm telling which network will be coach.
	 * @param network Network that will be trained.
	 * @param learningRate Learning rate of this training.
	 */
	public SupervisedLearning(NeuralNetwork network, double learningRate)
	{
		super(network, learningRate);
	}
}
