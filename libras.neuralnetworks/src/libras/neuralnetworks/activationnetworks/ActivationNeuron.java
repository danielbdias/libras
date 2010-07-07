/**
 * Package with classes used in Activation-Based Neural Networks.
 */
package libras.neuralnetworks.activationnetworks;

import libras.neuralnetworks.Neuron;
import libras.neuralnetworks.functions.Function;
import libras.utils.ValidationHelper;

/**
 * Represents a neuron used in activation-based networks.
 * @author Daniel Baptista Dias
 */
public class ActivationNeuron extends Neuron
{
	/**
	 * Creates a new instance with the activation function and the activation threshold.
	 * @param function Activation function of this neuron.
	 * @param threshold Activation threshold of this neuron.
	 */
	public ActivationNeuron(Function function, double threshold)
	{
		this(function, threshold, 0.0);
	}
	
	/**
	 * Creates a new instance with the activation function, the activation threshold and the bias.
	 * @param function Activation function of this neuron.
	 * @param threshold Activation threshold of this neuron.
	 * @param bias Bias value to be added for each input.
	 */
	public ActivationNeuron(Function function, double threshold, double bias)
	{
		super(new ActivationNeuronConnectionList(), new ActivationNeuronConnectionList());
		
		ValidationHelper.validateIfParameterIsNull(function, "function");
		
		this.function = function;
		this.threshold = threshold;
		this.bias = bias;
	}
	
	private double input = 0.0;
	
	private double threshold = 0.0;
	
	private double bias = 0.0;
	
	private Function function = null;
	
	/**
	 * Receive a pulse, adding the value to the input of the network.
	 * @param pulse Pulse received.
	 */
	void receivePulse(double pulse)
	{
		this.input += pulse;
	}
	
	/**
	 * Emit a pulse, based in the input values.
	 * @return Calculated pulse, based in the activation function.
	 */
	double emitPulse()
	{
		double result = function.calculate(input + bias);
		input = 0.0; //Reset the input
		
		return (result >= threshold ? result : 0.0);
	}
	
	/**
	 * Gets the input connections of this neuron.
	 * @return Input connections of this neuron.
	 * @see libras.neuralnetworks.Neuron#getInputConnections()
	 */
	public ActivationNeuronConnectionList getInputConnections()
	{
		return (ActivationNeuronConnectionList) super.getInputConnections();
	}
	
	/**
	 * Gets the output connections of this neuron.
	 * @return Output connections of this neuron.
	 * @see libras.neuralnetworks.Neuron#getOutputConnections()
	 */
	public ActivationNeuronConnectionList getOutputConnections()
	{
		return (ActivationNeuronConnectionList) super.getOutputConnections();
	}
}
