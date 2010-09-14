/**
 * Package with utility classes to use with the neural networks.
 */
package libras.neuralnetworks.utils;

import java.util.*;

/**
 * Represents a collection with the labels used in the clusters of some networks.
 * @author Daniel Baptista Dias
 */
public class LabelCollection
{
	/**
	 * Creates a new instance.
	 */
	public LabelCollection()
	{
		this.labelByNeuron = new TreeMap<Integer, String>();
		this.neuronsByLabel = new TreeMap<String, Integer[]>();
	}
	
	private TreeMap<Integer, String> labelByNeuron;
	private TreeMap<String, Integer[]> neuronsByLabel;
	
	/**
	 * Add a label, with the respective indexes of this label.
	 * @param label Label to be added.
	 * @param data Indexes of this label.
	 */
	public void addLabelData(String label, List<Integer> data)
	{
		Integer[] dataAsArray = new Integer[data.size()];
		data.toArray(dataAsArray);
		this.addLabelData(label, dataAsArray);
	}
	
	/**
	 * Add a label, with the respective indexes of this label.
	 * @param label Label to be added.
	 * @param data Indexes of this label.
	 */
	public void addLabelData(String label, int[] data)
	{
		Integer[] dataAsArray = new Integer[data.length];
		
		for (int i = 0; i < data.length; i++)
			dataAsArray[i] = data[i];
		
		this.addLabelData(label, dataAsArray);
	}
	
	/**
	 * Add a label, with the respective indexes of this label.
	 * @param label Label to be added.
	 * @param data Indexes of this label.
	 */
	public void addLabelData(String label, Integer[] data)
	{
		for (int i = 0; i < data.length; i++)
			this.labelByNeuron.put(data[i], label);
		
		this.neuronsByLabel.put(label, data);
	}
	
	/**
	 * Get a label that represents a index.
	 * @param i Index of the label.
	 * @return Label that represents the index.
	 */
	public String getLabel(int i)
	{
		return this.labelByNeuron.get(i);
	}
	
	/**
	 * Verify if exists a label for an index
	 * @param i Index to be verified.
	 * @return true, if exists label, otherwise, false.
	 */
	public boolean existLabel(int i)
	{	
		return this.labelByNeuron.containsKey(i);
	}
	
	/**
	 * Get a set of labels used in this collection.
	 * @return Set of labels used in this collection.
	 */
	public Set<String> getLabels()
	{	
		return this.neuronsByLabel.keySet();
	}
	
	/**
	 * Get the indexes represented by this label
	 * @param label Label that represent the indexes.
	 * @return Indexes represented by this label
	 */
	public Integer[] getNeuronIndexes(String label)
	{
		return this.neuronsByLabel.get(label);
	}
}
