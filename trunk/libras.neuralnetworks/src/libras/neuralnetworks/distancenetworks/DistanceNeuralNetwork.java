/**
 * Package with classes used in Distance-Based Neural Networks.
 */
package libras.neuralnetworks.distancenetworks;

import java.security.InvalidParameterException;
import java.util.*;
import libras.neuralnetworks.*;
import libras.utils.ValidationHelper;

/**
 * Represents an distance-based neural network.
 * @author Daniel Baptista Dias
 */
public class DistanceNeuralNetwork extends NeuralNetwork
{
	/**
	 * Creates a new instance with information about the layers. 
	 * @param neuronsPerLayer An array with the number of neurons per index. 
	 */
	public DistanceNeuralNetwork(int[] neuronsPerLayer)
	{
		super(new DistanceNeuronListCollection(), new DistanceNeuronConnectionListCollection());
		
		ValidationHelper.validateIfParameterIsNull(neuronsPerLayer, "neuronsPerLayer");
		
		for (int i = 0; i < neuronsPerLayer.length; i++)
		{
			DistanceNeuronList layer = new DistanceNeuronList();
			
			for (int j = 0; j < neuronsPerLayer[i]; j++)
				layer.add(new DistanceNeuron());
			
			this.getLayers().add(layer);
		}
		
		 //add at least one connection list
		this.getConnectionLayers().add(new DistanceNeuronConnectionList());
		
		for (int i = 0; i < this.getLayers().size()-2; i++)
			this.getConnectionLayers().add(new DistanceNeuronConnectionList());	
		
		for (int currentLayer = 0; currentLayer < this.getConnectionLayers().size(); currentLayer++)
		{
			int nextLayer = currentLayer+1;
			if (nextLayer > this.getLayers().size()-1) nextLayer = currentLayer;
			
			ArrayList<Neuron> leftLayer = this.getLayers().get(currentLayer);
			ArrayList<Neuron> rightLayer = this.getLayers().get(nextLayer);
			
			for (int i = 0; i < leftLayer.size(); i++)
			{
				DistanceNeuron inputNeuron = (DistanceNeuron) leftLayer.get(i);
				
				for (int j = 0; j < rightLayer.size(); j++)
				{
					DistanceNeuron outputNeuron = (DistanceNeuron) rightLayer.get(j);
					this.getConnectionLayers().get(currentLayer).add(new DistanceNeuronConnection(Math.random(), inputNeuron, outputNeuron));
				}
			}
		}
	}

	/**
	 * Get the input layer of the neural network.
	 * @return Input layer of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getInputLayer()
	 */
	public DistanceNeuronList getInputLayer()
	{
		return (DistanceNeuronList) super.getInputLayer();
	}
	
	/**
	 * Get the output layer of the neural network.
	 * @return Output layer of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getOutputLayer()
	 */
	public DistanceNeuronList getOutputLayer()
	{
		return (DistanceNeuronList) super.getOutputLayer();
	}
	
	/**
	 * Get a list with the connection between the layers of the neural network.
	 * @return List with the connection between the layers of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getConnectionLayers()
	 */
	protected DistanceNeuronConnectionListCollection getConnectionLayers()
	{
		return (DistanceNeuronConnectionListCollection) super.getConnectionLayers();
	}
	
	/**
	 * Get the layers of the neural network.
	 * @return Layers of the neural network.
	 * @see libras.neuralnetworks.NeuralNetwork#getLayers()
	 */
	protected DistanceNeuronListCollection getLayers()
	{
		return (DistanceNeuronListCollection) super.getLayers();
	}
	
	/**
	 * Compute the output through the network based in the input value.
	 * @param input Input data.
	 * @return Output of the network process.
	 */
	public int compute(double[] input)
	{
		ValidationHelper.validateIfParameterIsNull(input, "input");
		
		if (input.length != this.getInputLayer().size())
		{
			throw new InvalidParameterException(
				String.format("Invalid input. The input dimension must be %d.",
					this.getInputLayer().size()));
		}
		
		TreeMap<Double, Integer> distanceMap = new TreeMap<Double, Integer>();
		
		for (int i = 0; i < this.getOutputLayer().size(); i++)
		{
			DistanceNeuron neuron = this.getOutputLayer().get(i);
			
			double distance = libras.utils.MathHelper.euclideanDistance(input, neuron.getWeightVector());
			distanceMap.put(distance, i);
		}
		
		return distanceMap.get(distanceMap.firstKey());
	}
}
