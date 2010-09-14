/**
 * Provides fuctionalities to teach neural networks.
 */
package libras.neuralnetworks.learning;

/**
 * Represents a strategy to train a network using LVQ.
 * @author Daniel Baptista Dias
 */
public enum LVQLearningStrategy
{
	/**
	 * Sequential use of data.
	 */
	Sequential,
	/**
	 * Randomize the use of data.
	 */
	Randomized
}
