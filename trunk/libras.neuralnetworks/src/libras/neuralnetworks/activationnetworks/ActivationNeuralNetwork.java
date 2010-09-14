/**
 * Package with classes used in Activation-Based Neural Networks.
 */
package libras.neuralnetworks.activationnetworks;

import java.security.InvalidParameterException;
import java.util.*;
import libras.neuralnetworks.*;
import libras.neuralnetworks.functions.Function;
import libras.utils.ValidationHelper;

/**
 * Represents an activation-based neural network.
 * @author Daniel Baptista Dias
 */
public class ActivationNeuralNetwork extends NeuralNetwork
{
	/**
	 * Creates a new instance with information about the layers, 
	 * the activation function of the neurons and the activation threshold. 
	 * @param neuronsPerLayer An array with the number of neurons per index. 
	 * @param activationFunction Activation function of the neurons.
	 * @param threshold Threshold value to activate the network.
	 */
	public ActivationNeuralNetwork(int[] neuronsPerLayer,
			Function activationFunction, double threshold)
	{
		super(new ActivationNeuronListCollection(), new ActivationNeuronConnectionListCollection());
		
		ValidationHelper.validateIfParameterIsNull(neuronsPerLayer, "neuronsPerLayer");
		ValidationHelper.validateIfParameterIsNull(activationFunction, "activationFunction");
		
		for (int i = 0; i < neuronsPerLayer.length; i++)
		{
			ActivationNeuronList layer = new ActivationNeuronList();
			
			for (int j = 0; j < neuronsPerLayer[i]; j++)
				layer.add(new ActivationNeuron(activationFunction, threshold));
			
			this.getLayers().add(layer);
		}
		
		 //add at least one connection list
		this.getConnectionLayers().add(new ActivationNeuronConnectionList());
		
		for (int i = 0; i < this.getLayers().size()-2; i++)
			this.getConnectionLayers().add(new ActivationNeuronConnectionList());	
		
		for (int currentLayer = 0; currentLayer < this.getConnectionLayers().size(); currentLayer++)
		{
			int nextLayer = currentLayer+1;
			if (nextLayer > this.getLayers().size()-1) nextLayer = currentLayer;
			
			ArrayList<Neuron> leftLayer = this.getLayers().get(currentLayer);
			ArrayList<Neuron> rightLayer = this.getLayers().get(nextLayer);
			
			for (int i = 0; i < leftLayer.size(); i++)
			{
				ActivationNeuron inputNeuron = (ActivationNeuron) leftLayer.get(i);
				
				for (int j = 0; j < rightLayer.size(); j++)
				{
					ActivationNeuron outputNeuron = (ActivationNeuron) rightLayer.get(j);
					this.getConnectionLayers().get(currentLayer).add(new ActivationNeuronConnection(Math.random(), inputNeuron, outputNeuron));
				}
			}
		}
	}
	
	/**
	 * Get the input layer of the neural network.
	 * @return Input layer of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getInputLayer()
	 */
	public ActivationNeuronList getInputLayer()
	{
		return (ActivationNeuronList) super.getInputLayer();
	}
	
	/**
	 * Get the output layer of the neural network.
	 * @return Output layer of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getOutputLayer()
	 */
	public ActivationNeuronList getOutputLayer()
	{
		return (ActivationNeuronList) super.getOutputLayer();
	}

	/**
	 * Get a list with the connection between the layers of the neural network.
	 * @return List with the connection between the layers of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getConnectionLayers()
	 */
	protected ActivationNeuronConnectionListCollection getConnectionLayers()
	{
		return (ActivationNeuronConnectionListCollection) super.getConnectionLayers();
	}
	
	/**
	 * Get the layers of the neural network.
	 * @return Layers of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getLayers()
	 */
	protected ActivationNeuronListCollection getLayers()
	{
		return (ActivationNeuronListCollection) super.getLayers();
	}

	/**
	 * Compute the output through the network based in the input value.
	 * @param input Input data.
	 * @return Output of the network process.
	 */
	public double[] compute(double[] input)
	{
		ValidationHelper.validateIfParameterIsNull(input, "input");
		
		if (input.length != this.getInputLayer().size())
		{
			throw new InvalidParameterException(
				String.format("Invalid input. The input dimension must be %d.",
					this.getInputLayer().size()));
		}
		
		for (int i = 0; i < this.getInputLayer().size(); i++)
			((ActivationNeuron)  this.getInputLayer().get(i)).receivePulse(input[i]);
		
		for (int i = 0; i < this.getConnectionLayers().size(); i++)
			((ActivationNeuronConnectionList) this.getConnectionLayers().get(i)).doSynapse();
		
		double[] output = new double[this.getOutputLayer().size()];
		
		for (int i = 0; i < this.getOutputLayer().size(); i++)
			output[i] = ((ActivationNeuron) this.getOutputLayer().get(i)).emitPulse();
		
		return output;
	}
}

