/**
 * Package with utility classes to use with the neural networks.
 */
package libras.neuralnetworks.utils;

import java.io.*;
import java.util.*;

import libras.neuralnetworks.distancenetworks.*;
import libras.utils.*;

/**
 * Create neural networks from xml data files. 
 * @author Daniel Baptista Dias
 */
public class NetworkFileReader
{
	private final static String ROOT_NODE = "network";
	private final static String ITEM_NODE = "neuron";
	
	/**
	 * Retrieve a neural network from a Xml file.
	 * @param fileName File used in the network retrieval.
	 * @return Neural network retrieved from Xml file.
	 * @throws Exception When an unexpected exception happens.
	 */
	public DistanceNeuralNetwork getNetworkFromXmlFile(String fileName) throws Exception
	{
		ValidationHelper.validateIfParameterIsNullOrEmpty(fileName, "fileName");
		
		File file = new File(fileName);
		
		ValidationHelper.validateIfFileParameterExists(file, fileName);
		
		double[][] weights = getWeightsFromFile(file);
		
		if (weights != null && weights.length > 0)
		{
			int inputLayerLength = weights[0].length, outputLayerLength = weights.length;
			
			DistanceNeuralNetwork network = new DistanceNeuralNetwork(new int[] { inputLayerLength, outputLayerLength });
			
			for (int i = 0; i < weights.length; i++)
				network.getOutputLayer().get(i).setWeightVector(weights[i]);
			
			return network;
		}
		
		return null;
	}
	
	/**
	 * Retrieve a neural network from a text file.
	 * @param fileName File used in the network retrieval.
	 * @return Neural network retrieved from text file.
	 * @throws Exception When an unexpected exception happens.
	 */
	public Pair<DistanceNeuralNetwork, LabelCollection> getNetworkFromTextFile(String fileName) throws Exception
	{
		ValidationHelper.validateIfParameterIsNullOrEmpty(fileName, "fileName");
		
		File file = new File(fileName);
		
		ValidationHelper.validateIfFileParameterExists(file, fileName);
		
		List<Pair<String, double[]>> labeledWeights = TrainDataFileReader.getDataFromFile(fileName);
		
		if (labeledWeights != null && labeledWeights.size() > 0)
		{
			int inputLayerLength = labeledWeights.get(0).getSecondElement().length;
			int outputLayerLength = labeledWeights.size();
			
			LabelCollection labels = new LabelCollection();
			DistanceNeuralNetwork network = new DistanceNeuralNetwork(new int[] { inputLayerLength, outputLayerLength });
			
			Hashtable<String, Integer> labelCount = new Hashtable<String, Integer>();
			
			for (int i = 0; i < labeledWeights.size(); i++)
			{
				//Set neuron data
				Pair<String, double[]> pair = labeledWeights.get(i);
				network.getOutputLayer().get(i).setWeightVector(pair.getSecondElement());
				
				//Count label occurrence to populate label collection
				Integer count = labelCount.get(pair.getFirstElement());
				if (count == null) count = 0;
				count++;
				labelCount.put(pair.getFirstElement(), count);
			}
			
			int totalNeurons = 0;
			
			//Populate label collection, in alphabetical order
			TreeSet<String> orderedLabels = new TreeSet<String>(labelCount.keySet());
			
			for (String label : orderedLabels)
			{
				Integer count = labelCount.get(label);
				if (count == null) count = 0;
				
				int[] neuronIndexes = new int[count];
				
				for (int i = 0; i < neuronIndexes.length; i++)
					neuronIndexes[i] = i + totalNeurons;
				
				totalNeurons += neuronIndexes.length;
				labels.addLabelData(label, neuronIndexes);
			}
			
			return new Pair<DistanceNeuralNetwork, LabelCollection>(network, labels);
		}
		
		return null;
	}
	
	/**
	 * Get weight data from the network xml file.
	 * @param file File where the data will be retrieved.
	 * @return An array with the weights of the network.
	 * @throws Exception When an unexpected exception happens.
	 */
	private double[][] getWeightsFromFile(File file) throws Exception
	{
		javax.xml.parsers.DocumentBuilderFactory factory =
			javax.xml.parsers.DocumentBuilderFactory.newInstance();
		
		javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document document = builder.parse(file);
		
		if (document == null) throw new Exception("Unable to parse network file.");
		
		try
		{	
			return this.parseRootNode(document);
		}
		catch (Exception e)
		{
			throw new Exception(
				"Occurred an error in parse process of the network file." +
				" See Cause for more details.", e);
		}
	}
	
	/**
	 * Get weight data from the network xml file.
	 * @param document Parsed xml file where the data will be retrieved.
	 * @return An array with the weights of the network.
	 * @throws Exception When an unexpected exception happens.
	 */
	private double[][] parseRootNode(org.w3c.dom.Document document) throws Exception
	{	
		org.w3c.dom.Node batchFileNode = XmlHelper.getNodeFromList(ROOT_NODE, document.getChildNodes());
		
		if (batchFileNode == null || !batchFileNode.getNodeName().equals(ROOT_NODE))
			throw new Exception("The network file does not contain \"" + ROOT_NODE + "\" node.");
		
		List<org.w3c.dom.Node> neuronNodes = XmlHelper.getChildNodes(batchFileNode);
		
		if (neuronNodes == null || neuronNodes.isEmpty())
			throw new Exception("The \"" + ROOT_NODE + "\" node does not contains any \"" + ITEM_NODE + "\" node as child.");
		
		double[][] weights = new double[neuronNodes.size()][];
		
		for (int i = 0; i < neuronNodes.size(); i++)
		{
			org.w3c.dom.Node neuronNode = neuronNodes.get(i);
			weights[i] = this.parseItemNode(neuronNode, i+1);
		}
		
		return weights;
	}
	
	/**
	 * Retrieve data from a neuron node in the xml file.
	 * @param neuronNode Node where the data will be retrieved.
	 * @param order Order of the node in the file.
	 * @return Weights of the retrieved neuron.
	 * @throws Exception When an unexpected exception happens.
	 */
	private double[] parseItemNode(org.w3c.dom.Node neuronNode, int order) throws Exception
	{	
		if (neuronNode == null || !neuronNode.getNodeName().equals(ITEM_NODE))
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node is corrupted.");
		
		String neuronAsString = neuronNode.getTextContent();
		
		if (neuronAsString == null || neuronAsString.isEmpty())
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node must exists and cannot be empty.");
		
		String[] weightsAsString = neuronAsString.split(" ");
		
		double[] weights = new double[weightsAsString.length];
		
		for (int i = 0; i < weightsAsString.length; i++)
		{
			if (!weightsAsString[i].matches("-{0,1}[0-9]+\\.[0-9]+"))
				throw new Exception("The value of " + order + "st \"" + ITEM_NODE + "\" node must be composed by numeric values and spaces.");
			
			double weight = Double.parseDouble(weightsAsString[i]);
			weights[i] = weight;
		}

		return weights;
	}
}
