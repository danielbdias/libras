/**
 * Provides fuctionalities to use neural networks.
 */
package libras.neuralnetworks;

import libras.utils.ValidationHelper;

/**
 * Represents a neural network.
 * @author Daniel Baptista Dias
 */
public abstract class NeuralNetwork
{	
	/**
	 * Creates a new instance of a neural network initializing the layers and neuron connetions lists.
	 * @param layers Collection with the network layers.
	 * @param connectionLayers Collection with the connection between the layers.
	 */
	public NeuralNetwork(NeuronListCollection layers, NeuronConnectionListCollection connectionLayers)
	{	
		ValidationHelper.validateIfParameterIsNull(layers, "layers");
		ValidationHelper.validateIfParameterIsNull(connectionLayers, "connectionLayers");
		
		this.connectionLayers = connectionLayers;
		this.layers = layers;
	}
	
	private NeuronListCollection layers = null;
	
	private NeuronConnectionListCollection connectionLayers = null;
	
	/**
	 * Get the layers of the neural network.
	 * @return Layers of the neural network.
	 */
	protected NeuronListCollection getLayers()
	{
		return layers;
	}

	/**
	 * Get a list with the connection between the layers of the neural network.
	 * @return List with the connection between the layers of the neural network.
	 */
	protected NeuronConnectionListCollection getConnectionLayers()
	{
		return connectionLayers;
	}

	/**
	 * Get the input layer of the neural network.
	 * @return Input layer of the neural network.
	 */
	public NeuronList getInputLayer()
	{
		return layers.get(0);
	}
	
	/**
	 * Get the output layer of the neural network.
	 * @return Output layer of the neural network.
	 */
	public NeuronList getOutputLayer()
	{
		return layers.get(layers.size()-1);
	}
}
