/**
 * Provides fuctionalities to teach neural networks.
 */
package libras.neuralnetworks.learning;

import java.security.InvalidParameterException;
import java.util.*;
import libras.utils.*;
import libras.neuralnetworks.distancenetworks.*;
import libras.neuralnetworks.functions.Function;
import libras.neuralnetworks.functions.LinearFunction;
import libras.neuralnetworks.utils.LabelCollection;

/**
 * Represents the Learning Vector Quantization (LVQ) learning algorithm.
 * @author Daniel Baptista Dias
 */
public class LearningVectorQuantization extends SupervisedLearning 
{
	/**
	 * Creates a new instance with the network to be trained, the learning rate and the labels used in the training.
	 * @param network Network that will be trained.
	 * @param learningRate Learning rate of this training.
	 * @param labels Labels used in the training to validate the result of the network.
	 */
	public LearningVectorQuantization(DistanceNeuralNetwork network, double learningRate, LabelCollection labels) 
	{
		this(network, learningRate, labels, new LinearFunction(0.9, 0.0));
	}

	/**
	 * Creates a new instance with the network to be trained, the learning rate, 
	 * the labels used in the training and the learning function.
	 * @param network Network that will be trained.
	 * @param learningRate Learning rate of this training.
	 * @param labels Labels used in the training to validate the result of the network.
	 * @param learningFunction Learning function used to change the learning rate.
	 */
	public LearningVectorQuantization(DistanceNeuralNetwork network, double learningRate, LabelCollection labels, Function learningFunction)
	{
		super(network, learningRate);
		
		ValidationHelper.validateIfParameterIsNull(labels, "labels");
		ValidationHelper.validateIfParameterIsNull(learningFunction, "learningFunction");
		
		this.learningFunction = learningFunction;
		this.labels = labels;
	}
	
	private Function learningFunction = null;

	private LabelCollection labels = null;
	
	/**
	 * Get the network to be trained.
	 * @return Network to be trained.
	 * @see libras.neuralnetworks.learning.Learning#getNetwork()
	 */
	protected DistanceNeuralNetwork getNetwork()
	{
		return (DistanceNeuralNetwork) super.getNetwork();
	}
	
	/**
	 * Train the network.
	 * @param input Input data to the network.
	 * @param expectedOutput Expected output of the network.
	 * @throws Exception When an unexpected exception occurs.
	 */
	public void trainNetwork(double[][] input, String[] expectedOutput) throws Exception
	{
		this.trainNetwork(input, expectedOutput, LVQLearningStrategy.Sequential);
	}

	/**
	 * Train the network with a specified strategy.
	 * @param input Input data to the network.
	 * @param expectedOutput Expected output of the network.
	 * @param strategy Strategy to be used.
	 * @throws Exception When an unexpected exception occurs.
	 */
	public void trainNetwork(double[][] input, String[] expectedOutput, LVQLearningStrategy strategy) throws Exception
	{
		if (input.length != expectedOutput.length && input.length > 0)
			throw new InvalidParameterException("Invalid input and/or output."
					+ " The first dimension of input and output must be equal and greater than 0 (zero).");
	
		List<Integer> trainingOrder = this.getTrainingOrder(strategy, this.getNetwork().getOutputLayer().size());
		
		for (Integer order : trainingOrder)
		{
			int i = order.intValue();
			this.train(input[i], expectedOutput[i]);
		}
		
		double currentLearningRate = this.getLearningRate();
		this.setLearningRate(this.learningFunction.calculate(currentLearningRate));
	}
	
	/**
	 * Train the network.
	 * @param input Input data.
	 * @param expectedClass Expected class.
	 * @throws Exception When an unexpected exception occurs.
	 */
	protected void train(double[] input, String expectedClass) throws Exception
	{
		DistanceNeuralNetwork network =  this.getNetwork();
		DistanceNeuronList outputLayer = network.getOutputLayer();
		
		int winner = network.compute(input);
		String computedClass = labels.getLabel(winner);
		
		DistanceNeuron neuron = outputLayer.get(winner);
		
		double[] voronoiVector = neuron.getWeightVector();
		
		if (expectedClass.equals(computedClass))
		{
			//w(n+1) = w(n) + a * (x - w(n))
			voronoiVector = 
				MathHelper.sumVectors(
					voronoiVector, 
					MathHelper.scalarMultiplication(
						MathHelper.subtractVectors(input, voronoiVector), 
						this.getLearningRate()));
		}
		else
		{
			//w(n+1) = w(n) - a * (x - w(n))
			voronoiVector = 
				MathHelper.subtractVectors(
					voronoiVector, 
					MathHelper.scalarMultiplication(
						MathHelper.subtractVectors(input, voronoiVector), 
						this.getLearningRate()));
		}	
		
		neuron.setWeightVector(voronoiVector);
	}

	/**
	 * Get the order of the items used in the network training.
	 * @param strategy Strategy to be used.
	 * @param expectedOutputLenght Size of the expected output.
	 * @return Order of the items used in the network training.
	 */
	private List<Integer> getTrainingOrder(LVQLearningStrategy strategy, int expectedOutputLenght)
	{
		List<Integer> trainingOrder = new ArrayList<Integer>();
		
		if (strategy == LVQLearningStrategy.Randomized)
		{
			for (int i = 0; i < expectedOutputLenght; i++)
				trainingOrder.add(i);
			
			Random rnd = new Random();
			
			//Make multiple swaps between numbers
			for (int i = 0; i < expectedOutputLenght * 2; i++)
			{
				int firstIndex = rnd.nextInt(expectedOutputLenght);
				int secondIndex = rnd.nextInt(expectedOutputLenght);
				
				int temp = trainingOrder.get(firstIndex);
				
				trainingOrder.set(firstIndex, trainingOrder.get(secondIndex));
				trainingOrder.set(secondIndex, temp);
			}
		}
		else
		{
			for (int i = 0; i < expectedOutputLenght; i++)
				trainingOrder.add(i);
		}
		
		return trainingOrder;
	}
}
