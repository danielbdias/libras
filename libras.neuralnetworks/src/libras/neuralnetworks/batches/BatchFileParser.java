/**
 * Package that provide classes to automatize batch process.
 */
package libras.neuralnetworks.batches;

import java.io.*;
import java.util.*;

import libras.neuralnetworks.utils.*;

/**
 * Parser of xml batch files.
 * @author Daniel Baptista Dias
 */
public class BatchFileParser extends XmlFileParser
{
	private final static String ROOT_NODE = "batch";
	private final static String ITEM_NODE = "item";
	
	/**
	 * Parse a back file.
	 * @param batchFile File to be parsed.
	 * @return The representation of the batch file.
	 * @throws Exception When an unexpected error happens.
	 */
	public Batch parseBatchFile(String batchFile) throws Exception
	{
		javax.xml.parsers.DocumentBuilderFactory factory =
			javax.xml.parsers.DocumentBuilderFactory.newInstance();
		
		javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document document = builder.parse(new java.io.File(batchFile));
		
		if (document == null) throw new Exception("Unable to parse batch file.");
		
		try
		{	
			return this.parseRootNode(document);
		}
		catch (Exception e)
		{
			throw new Exception(
				"Occurred an error in parse process of the batch file." +
				" See Cause for more details.", e);
		}
	}

	/**
	 * Parse the root node of the file.
	 * @param document Document to be parsed.
	 * @return The representation of the batch file.
	 * @throws Exception When an unexpected error happens.
	 */
	private Batch parseRootNode(org.w3c.dom.Document document) throws Exception
	{	
		org.w3c.dom.Node batchFileNode = this.getNodeFromList(ROOT_NODE, document.getChildNodes());
		
		if (batchFileNode == null || !batchFileNode.getNodeName().equals(ROOT_NODE))
			throw new Exception("The batch file does not contain \"" + ROOT_NODE + "\" node.");
		
		Batch batchItems = new Batch();
		
		List<org.w3c.dom.Node> batchNodes = this.getChildNodes(batchFileNode);
		
		if (batchNodes == null || batchNodes.isEmpty())
			throw new Exception("The \"" + ROOT_NODE + "\" node does not contains any \"" + ITEM_NODE + "\" node as child.");
		
		for (int i = 0; i < batchNodes.size(); i++)
		{
			org.w3c.dom.Node batchNode = batchNodes.get(i);
			batchItems.add(this.parseItemNode(batchNode, i+1));
		}
		
		return batchItems;
	}
	
	/**
	 * Parse a node of the batch file.
	 * @param batchNode Node to be parsed.
	 * @param order Order of the node in the file.
	 * @return The representation of the batch item.
	 * @throws Exception When an unexpected error happens.
	 */
	private BatchItem parseItemNode(org.w3c.dom.Node batchNode, int order) throws Exception
	{	
		if (batchNode == null || !batchNode.getNodeName().equals(ITEM_NODE))
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node is corrupted.");
		
		BatchItem item = new BatchItem();
		
		this.getBatchNameForItem(batchNode, item, order);
		
		this.getEpochForItem(batchNode, item, order);
		
		this.getLearningRateForItem(batchNode, item, order);
		
		this.getNetworkSetupForItem(batchNode, item, order);
		
		this.getTrainingDataForItem(batchNode, item, order);
		
		this.getReportForItem(batchNode, item, order);
		
		return item;
	}
	
	/**
	 * Get the item name of the batch node.
	 * @param batchNode Node to be parsed.
	 * @param item Batch item in composition.
	 * @param order Order of the batch item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getBatchNameForItem(org.w3c.dom.Node batchNode, BatchItem item, int order) throws Exception
	{	
		String batchName = this.getAttributeValueFromNode("name", batchNode);
		
		if (batchName == null || batchName.isEmpty())
			throw new Exception("The \"name\" attribute of " + order + "st \"batchItem\" node must exists and cannot be empty.");

		item.setBatchName(batchName);
	}

	/**
	 * Get the number of epochs of the batch node.
	 * @param batchNode Node to be parsed.
	 * @param item Batch item in composition.
	 * @param order Order of the batch item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getEpochForItem(org.w3c.dom.Node batchNode, BatchItem item, int order) throws Exception
	{
		org.w3c.dom.Node epochNode = this.getNodeFromList("epoch", batchNode.getChildNodes());
		
		if (epochNode == null)
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"epoch\".");
		
		String epochNumberAsString = this.getAttributeValueFromNode("number", epochNode);
		
		if (epochNumberAsString == null || epochNumberAsString.isEmpty())
			throw new Exception("The \"number\" attribute of the \"epoch\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must exists and cannot be empty.");
		
		if (!epochNumberAsString.matches("^[0-9]+$"))
			throw new Exception("The \"number\" attribute of the \"epoch\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be numeric.");

		int epochNumber = Integer.parseInt(epochNumberAsString);
		
		if (epochNumber <= 0)
			throw new Exception("The \"number\" attribute of the \"epoch\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be greater than 0 (zero).");
		
		item.setEpochsToExecute(epochNumber);
	}
	
	/**
	 * Get the learning rate of the batch node.
	 * @param batchNode Node to be parsed.
	 * @param item Batch item in composition.
	 * @param order Order of the batch item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getLearningRateForItem(org.w3c.dom.Node batchNode, BatchItem item, int order) throws Exception
	{
		org.w3c.dom.Node learningRateNode = this.getNodeFromList("learningRate", batchNode.getChildNodes());
		
		if (learningRateNode == null)
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"learningRate\"");
		
		String learningRateAsString = this.getAttributeValueFromNode("number", learningRateNode);
		
		if (learningRateAsString == null || learningRateAsString.isEmpty())
			throw new Exception("The \"number\" attribute of the \"learningRate\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must exists and cannot be empty.");
		
		if (!learningRateAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception("The \"number\" attribute of the \"learningRate\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be numeric.");

		double learningRate = Double.parseDouble(learningRateAsString);
		
		if (learningRate <= 0.0)
			throw new Exception("The \"number\" attribute of the \"learningRate\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be greater than 0 (zero).");
		
		item.setInitialLearningRate(learningRate);
		
		String learningRateDecreasingRateAsString = this.getAttributeValueFromNode("decreasingRate", learningRateNode);
		
		if (learningRateDecreasingRateAsString == null || learningRateDecreasingRateAsString.isEmpty())
			throw new Exception("The \"decreasingRate\" attribute of the \"learningRate\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must exists and cannot be empty.");
		
		if (!learningRateDecreasingRateAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception("The \"decreasingRate\" attribute of the \"learningRate\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be numeric.");

		double learningRateDecreasingRate = Double.parseDouble(learningRateDecreasingRateAsString);
		
		if (learningRateDecreasingRate <= 0.0)
			throw new Exception("The \"decreasingRate\" attribute of the \"learningRate\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be greater than 0 (zero).");
		
		item.setLearningRateDecreasingRate(learningRateDecreasingRate);
	}
	
	/**
	 * Get the network setup of the batch node.
	 * @param batchNode Node to be parsed.
	 * @param item Batch item in composition.
	 * @param order Order of the batch item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getNetworkSetupForItem(org.w3c.dom.Node batchNode, BatchItem item, int order) throws Exception
	{
		org.w3c.dom.Node networkSetupNode = this.getNodeFromList("networkSetup", batchNode.getChildNodes());
		
		if (networkSetupNode == null)
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"networkSetup\".");
		
		String inputNeuronsCountAsString = this.getAttributeValueFromNode("inputCount", networkSetupNode);
		
		if (inputNeuronsCountAsString != null && !inputNeuronsCountAsString.isEmpty())
		{		
			if (!inputNeuronsCountAsString.matches("^[0-9]+$"))
				throw new Exception("The \"inputCount\" attribute of the \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be numeric.");
	
			Integer inputNeuronsCount = Integer.parseInt(inputNeuronsCountAsString);
			
			if (inputNeuronsCount <= 0)
				throw new Exception("The \"inputCount\" attribute of the \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be greater than 0 (zero).");		
			
			item.setInputNeuronsCount(inputNeuronsCount);
		}
			
		String outputNeuronsCountAsString = this.getAttributeValueFromNode("outputCount", networkSetupNode);
		
		if (outputNeuronsCountAsString != null && !outputNeuronsCountAsString.isEmpty())
		{	
			if (!outputNeuronsCountAsString.matches("^[0-9]+$"))
				throw new Exception("The \"outputCount\" attribute of the \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be numeric.");
	
			int outputNeuronsCount = Integer.parseInt(outputNeuronsCountAsString);
			
			if (outputNeuronsCount <= 0)
				throw new Exception("The \"outputCount\" attribute of the \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must be greater than 0 (zero).");		
			
			item.setOutputNeuronsCount(outputNeuronsCount);
		}
		
		String networkFilePath = this.getAttributeValueFromNode("fromFile", networkSetupNode);
		
		if (networkFilePath != null && !networkFilePath.isEmpty())
		{
			File networkFile = new File(networkFilePath);
			
			if (!networkFile.exists())
				throw new Exception("The \"fromFile\" attribute from \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node contains an inexistent file.");
			
			item.setNetworkSetupFile(networkFile);
			
			String networkFileFormat = this.getAttributeValueFromNode("fileFormat", networkSetupNode);
			item.setNetworkSetupFileFormat(networkFileFormat);
		}
		
		org.w3c.dom.Node outputLabelsNode = this.getNodeFromList("outputLabels", networkSetupNode.getChildNodes());
		
		if (outputLabelsNode == null && !item.getNetworkSetupFileFormat().equalsIgnoreCase("text"))
			throw new Exception("The \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"outputLabels\".");
		
		if (outputLabelsNode != null)
		{
			List<org.w3c.dom.Node> childNodes = this.getChildNodes(outputLabelsNode);
	
			if (childNodes == null || childNodes.size() <= 0)
				throw new Exception("The \"outputLabels\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have at least one child node named \"label\".");
			
			LabelCollection labels = new LabelCollection();		
			
			int usedNeurons = 0;
			
			for (int i = 0; i < childNodes.size(); i++)
			{
				org.w3c.dom.Node node = childNodes.get(i);
				
				String label = this.getAttributeValueFromNode("name", node);
				
				if (label == null || label.isEmpty())
					throw new Exception("The \"label\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"name\".");
				
				String neuronsAsString = this.getAttributeValueFromNode("neurons", node);
			
				if (neuronsAsString == null || label.isEmpty())
					throw new Exception("The \"label\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"neurons\".");
	
				if (neuronsAsString.matches("^[0-9]+$"))
					throw new Exception("The \"label\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"neurons\" with a numeric value.");
				
				int neurons = Integer.parseInt(neuronsAsString);
				
				int[] neuronsIndexes = new int[neurons];
				
				for (int j = 0; j < neurons; j++)
					neuronsIndexes[j] = usedNeurons + j;
				
				usedNeurons += neuronsIndexes.length;
				
				labels.addLabelData(label, neuronsIndexes);
			}
			
			item.setLabels(labels);
		}
	}

	/**
	 * Get the training data of the batch node.
	 * @param batchNode Node to be parsed.
	 * @param item Batch item in composition.
	 * @param order Order of the batch item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getTrainingDataForItem(org.w3c.dom.Node batchNode, BatchItem item, int order) throws Exception
	{
		org.w3c.dom.Node trainingDataNode = this.getNodeFromList("trainingData", batchNode.getChildNodes());
		
		if (trainingDataNode == null)
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"trainingData\".");
		
		org.w3c.dom.Node crossValidationNode = this.getNodeFromList("crossValidation", trainingDataNode.getChildNodes());
		
		if (crossValidationNode != null)
		{
			String foldNumberAsString = this.getAttributeValueFromNode("foldNumber", crossValidationNode);
			
			if (foldNumberAsString == null || foldNumberAsString.isEmpty())
				throw new Exception("The \"foldNumber\" attribute of the \"crossValidation\" node inside the \"trainingData\" in the " + order + "st \"" + ITEM_NODE + "\" node must exists and cannot be empty.");
			
			if (!foldNumberAsString.matches("^[0-9]+$"))
				throw new Exception("The \"foldNumber\" attribute of the \"crossValidation\" node inside the \"trainingData\" in the " + order + "st \"" + ITEM_NODE + "\" node must be numeric.");
	
			int foldNumber = Integer.parseInt(foldNumberAsString);
			
			if (foldNumber <= 0)
				throw new Exception("The \"foldNumber\" attribute of the \"crossValidation\" node inside the \"trainingData\" in the " + order + "st \"" + ITEM_NODE + "\" node must be greater than 0.");		
			
			item.setCrossValidationFoldNumber(foldNumber);
		}
		
		org.w3c.dom.Node validationFilesNode = this.getNodeFromList("validationFiles", trainingDataNode.getChildNodes());
		
		if (validationFilesNode != null)
		{
			List<org.w3c.dom.Node> childNodes = this.getChildNodes(validationFilesNode);

			if (childNodes == null || childNodes.size() <= 0)
				throw new Exception("The \"validationFiles\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have at least one child node named \"file\".");
			
			List<File> validationFiles = new ArrayList<File>();		
			
			for (int i = 0; i < childNodes.size(); i++)
			{
				org.w3c.dom.Node node = childNodes.get(i);
				
				String fullName = this.getAttributeValueFromNode("name", node);
				
				if (fullName == null || fullName.isEmpty())
					throw new Exception("The \"file\" node inside of \"validationFiles\" in the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"name\".");
				
				File file = new File(fullName);
				
				if (!file.exists())
					throw new Exception("The \"file\" node inside of \"validationFiles\" in the " + order + "st \"" + ITEM_NODE + "\" node must have a value to attribute named \"name\" of a valid file.");
				
				validationFiles.add(file);
			}
			
			item.setValidationFiles(validationFiles);
		}
		
		org.w3c.dom.Node inputFilesNode = this.getNodeFromList("inputFiles", trainingDataNode.getChildNodes());
		
		if (inputFilesNode == null)
			throw new Exception("The \"networkSetup\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"inputFiles\".");
		
		List<org.w3c.dom.Node> childNodes = this.getChildNodes(inputFilesNode);

		if (childNodes == null || childNodes.size() <= 0)
			throw new Exception("The \"inputFiles\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have at least one child node named \"file\".");
		
		List<File> inputFiles = new ArrayList<File>();		
		
		for (int i = 0; i < childNodes.size(); i++)
		{
			org.w3c.dom.Node node = childNodes.get(i);
			
			String fullName = this.getAttributeValueFromNode("name", node);
			
			if (fullName == null || fullName.isEmpty())
				throw new Exception("The \"file\" node inside of \"inputFiles\" in the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"name\".");
			
			File file = new File(fullName);
			
			if (!file.exists())
				throw new Exception("The \"file\" node inside of \"inputFiles\" in the " + order + "st \"" + ITEM_NODE + "\" node must have a value to attribute named \"name\" of a valid file.");
			
			inputFiles.add(file);
		}
		
		item.setInputFiles(inputFiles);
	}

	/**
	 * Get the report data of the batch node.
	 * @param batchNode Node to be parsed.
	 * @param item Batch item in composition.
	 * @param order Order of the batch item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getReportForItem(org.w3c.dom.Node batchNode, BatchItem item, int order) throws Exception
	{
		org.w3c.dom.Node reportsNode = this.getNodeFromList("reports", batchNode.getChildNodes());
		
		if (reportsNode == null)
			throw new Exception("The " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"reports\".");
		
		org.w3c.dom.Node dataFileNode = this.getNodeFromList("dataFile", reportsNode.getChildNodes());
		
		if (dataFileNode == null)
			throw new Exception("The \"dataFile\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"reports\".");
		
		String fileName = this.getAttributeValueFromNode("name", dataFileNode);
		
		if (fileName == null || fileName.isEmpty())
			throw new Exception("The \"dataFile\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"name\".");
		
		item.setReportDataFile(new File(fileName));
		
		org.w3c.dom.Node networkFileNode = this.getNodeFromList("networkFile", reportsNode.getChildNodes());
		
		if (networkFileNode == null)
			throw new Exception("The \"networkFile\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have a child node named \"reports\".");
		
		fileName = this.getAttributeValueFromNode("name", networkFileNode);
		
		if (fileName == null || fileName.isEmpty())
			throw new Exception("The \"networkFile\" node inside the " + order + "st \"" + ITEM_NODE + "\" node must have an attribute named \"name\".");
		
		item.setNetworkDataFile(new File(fileName));
	}
}
