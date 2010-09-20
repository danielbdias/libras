/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches;

//import java.io.*;
//import java.util.*;
//
//import libras.batches.taskfiles.models.Task;
import libras.batches.taskfiles.models.TaskFile;
//import libras.neuralnetworks.distancenetworks.*;
//import libras.neuralnetworks.functions.*;
//import libras.neuralnetworks.learning.*;
//import libras.neuralnetworks.utils.*;
//import libras.utils.*;

/**
 * Execute the items of a batch file, executing the training and validation of each item of a batch file. 
 * @author Daniel Baptista Dias
 */
public class BatchProcessor
{
	/**
	 * Process a batch file, executing the training and validation of each item of a batch file.
	 * @param batch Batch file to be processed.
	 * @return The result of each process.
	 */
	public BatchTaskResult[] process(TaskFile taskFile)
	{
		BatchTaskResult[] results = new BatchTaskResult[taskFile.size()];
		
//		for (int i = 0; i < taskFile.size(); i++)
//		{
//			Task item = taskFile.get(i);
//			
//			try
//			{
//				this.executeItem(item);
//				results[i] = new BatchTaskResult(true, item.getName());
//			}
//			catch (Exception e)
//			{
//				results[i] = new BatchTaskResult(e, item.getName());
//			}
//		}
		
		return results;
	}
	
//	/**
//	 * Execute the training and validation of the item.
//	 * @param batchItem Item to be executed.
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private void executeItem(Task batchItem) throws Exception
//	{
//		List<Pair<String, double[]>> inputData = new ArrayList<Pair<String, double[]>>();
//		
//		for (File inputFile : batchItem.getInputFiles())
//			inputData.addAll(TrainDataFileReader.getDataFromFile(inputFile.getAbsolutePath()));
//		
//		if (!inputData.isEmpty())
//		{
//			List<Pair<String, double[]>> trainingData = new ArrayList<Pair<String,double[]>>();
//			List<Pair<String, double[]>> validationData = new ArrayList<Pair<String,double[]>>();
//			
//			DistanceNeuralNetwork network = this.getNetworkSetup(batchItem);
//			
//			this.getTrainingData(batchItem, inputData, trainingData, validationData);
//		
//			this.teachNetwork(network, trainingData, batchItem);
//			
//			this.validateNetwork(network, validationData, batchItem);
//		}
//	}
//
//	/**
//	 * Get the training data, separing the input data in two categories: training data and validation data.
//	 * @param batchItem Item in process.
//	 * @param inputData Input data.
//	 * @param trainingData Training data.
//	 * @param validationData Validation data.
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private void getTrainingData(Task batchItem,
//			List<Pair<String, double[]>> inputData,
//			List<Pair<String, double[]>> trainingData,
//			List<Pair<String, double[]>> validationData) throws Exception
//	{
//		if (batchItem.getCrossValidationFoldNumber() != null)
//		{
//			this.separeInputData(inputData, trainingData, validationData, 
//				batchItem.getLabels(), batchItem.getCrossValidationFoldNumber());
//		}
//		
//		if (batchItem.getValidationFiles() != null && batchItem.getValidationFiles().size() > 0)
//		{
//			trainingData.addAll(inputData);
//			for (File validationFile : batchItem.getValidationFiles())
//				validationData.addAll(TrainDataFileReader.getDataFromFile(validationFile.getAbsolutePath()));
//		}
//	}
//
//	/**
//	 * Get a distance-based network representation from the network setup data section.
//	 * @param batchItem Item in process.
//	 * @return Distance-based network representation from the network setup data section
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private DistanceNeuralNetwork getNetworkSetup(Task batchItem) throws Exception
//	{
//		DistanceNeuralNetwork network = null;
//		
//		if (batchItem.getNetworkSetupFile() != null)
//		{
//			NetworkFileReader reader = new NetworkFileReader();
//			
//			if (batchItem.getNetworkSetupFileFormat().equalsIgnoreCase("text"))
//			{
//				Pair<DistanceNeuralNetwork, LabelCollection> setup = 
//					reader.getNetworkFromTextFile(batchItem.getNetworkSetupFile().getAbsolutePath());
//				
//				network = setup.getFirstElement();
//				batchItem.setLabels(setup.getSecondElement());
//			}
//			else if (batchItem.getNetworkSetupFileFormat().equalsIgnoreCase("xml"))
//			{
//				network = reader.getNetworkFromXmlFile(batchItem.getNetworkSetupFile().getAbsolutePath());
//			}
//		}
//		else
//		{
//			network =
//				new DistanceNeuralNetwork(
//					new int[] { 
//						batchItem.getInputNeuronsCount(), 
//						batchItem.getOutputNeuronsCount() });
//		}
//		
//		return network;
//	}
//	
//	/**
//	 * Separe the input data between the training data and the validation data using k-fold cross validation.
//	 * @param inputData Input data.
//	 * @param trainingData Training data.
//	 * @param validationData Validation data.
//	 * @param labels Labels of the data input data.
//	 * @param foldNumber Fold number used in cross validation.
//	 */
//	private void separeInputData(List<Pair<String, double[]>> inputData, List<Pair<String, double[]>> trainingData, 
//			List<Pair<String, double[]>> validationData, LabelCollection labels, int foldNumber)
//	{	
//		Random rnd = new Random();
//		
//		//Randomize input data
//		for (int i = 0; i < inputData.size() * inputData.size(); i++)
//		{
//			int firstElement = rnd.nextInt(inputData.size()-1);
//			int secondElement = rnd.nextInt(inputData.size()-1);
//			
//			Pair<String, double[]> pair = inputData.get(firstElement);
//			inputData.set(firstElement, inputData.get(secondElement));
//			inputData.set(secondElement, pair);
//		}
//		
//		for (String label : labels.getLabels())
//		{	
//			ArrayList<Pair<String, double[]>> labelItems = new ArrayList<Pair<String,double[]>>();
//			
//			//Get all pairs of the label
//			for (int i = 0; i < inputData.size(); i++)
//			{
//				Pair<String, double[]> pair = inputData.get(i);
//				
//				if (pair.getFirstElement().equals(label))
//					labelItems.add(pair);
//			}
//			
//			//Separe data of the label
//			for (int j = 0; j < labelItems.size(); j++)
//			{
//				if (foldNumber != 0 && j % foldNumber != 0)
//					trainingData.add(labelItems.get(j));
//				else
//					validationData.add(labelItems.get(j));
//			}
//		}
//	}
//	
//	/**
//	 * Get the data to train the neural network.
//	 * @param input Input data.
//	 * @param expectedOutput Expected output of the network.
//	 * @param trainingData Training data to the network.
//	 */
//	private void populateNetworkData(double[][] input, String[] expectedOutput, List<Pair<String, double[]>> trainingData)
//	{
//		for (int i = 0; i < trainingData.size(); i++)
//		{
//			Pair<String, double[]> pair = trainingData.get(i);
//			
//			expectedOutput[i] = pair.getFirstElement();
//			input[i] = pair.getSecondElement();
//		}
//	}
//
//	/**
//	 * Train the neural network.
//	 * @param network Network to be trained.
//	 * @param trainingData Training data.
//	 * @param batchItem Item with the configuration of the training.
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private void teachNetwork(DistanceNeuralNetwork network, List<Pair<String, double[]>> trainingData, Task batchItem) throws Exception
//	{
//		double[][] input = new double[trainingData.size()][];
//		String[] expectedOutput = new String[trainingData.size()];
//		
//		this.populateNetworkData(input, expectedOutput, trainingData);
//		
//		LearningVectorQuantization teacher =
//			new LearningVectorQuantization(
//					network, 
//					batchItem.getInitialLearningRate(), 
//					batchItem.getLabels(),
//					new LinearFunction(batchItem.getLearningRateDecreasingRate(), 0.0));
//		
//		for (int i = 0; i < batchItem.getEpochsToExecute(); i++)
//			teacher.trainNetwork(input, expectedOutput, LVQLearningStrategy.Randomized);
//	}
//
//	/**
//	 * Validate the training of the network.
//	 * @param network Network to be trained.
//	 * @param validationData Validation data.
//	 * @param batchItem Item with the configuration of the training.
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private void validateNetwork(DistanceNeuralNetwork network, List<Pair<String, double[]>> validationData, Task batchItem) throws Exception
//	{
//		double[][] input = new double[validationData.size()][];
//		String[] expectedOutput = new String[validationData.size()];
//		String[] computedOutput = new String[validationData.size()];
//		
//		this.populateNetworkData(input, expectedOutput, validationData);
//		
//		for (int i = 0; i < validationData.size(); i++)
//		{
//			int winner = network.compute(input[i]);
//			computedOutput[i] = batchItem.getLabels().getLabel(winner);
//		}
//		
//		this.createReportDataFile(batchItem, input, expectedOutput, computedOutput);
//		this.createNetworkFile(batchItem, network);
//	}
//	
//	/**
//	 * Creates a report file based in the execution of the training and validation of the network.
//	 * @param batchItem Item with the configuration of the process.
//	 * @param input Input data of the network.
//	 * @param expectedOutput Expected output of the network.
//	 * @param computedOutput Computed output of the network.
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private void createReportDataFile(Task batchItem, double[][] input, String[] expectedOutput, String[] computedOutput) throws Exception
//	{
//		double hitTax = 0.0;
//		
//		for (int i = 0; i < computedOutput.length; i++)
//		{
//			if (expectedOutput[i].equals(computedOutput[i])) 
//				hitTax++;
//		}
//		
//		hitTax /= computedOutput.length;
//		
//		File directory = batchItem.getReportDataFile().getParentFile();
//		if (!directory.exists()) directory.mkdirs();
//		
//		FileWriter writer = new FileWriter(batchItem.getReportDataFile());
//		
//		writer.write("<reportFile>\r\n");
//		
//		writer.write(String.format("\t<batchName>%s</batchName>\r\n", batchItem.getBatchName()));
//		writer.write(String.format("\t<hitTax>%f</hitTax>\r\n", hitTax));
//		writer.write(String.format("\t<learningRate>%f</learningRate>\r\n", batchItem.getInitialLearningRate()));
//		writer.write(String.format("\t<learningRateDecreaseTax>%f</learningRateDecreaseTax>\r\n", batchItem.getLearningRateDecreasingRate()));
//		writer.write(String.format("\t<epochs>%d</epochs>\r\n", batchItem.getEpochsToExecute()));
//		
//		writer.write("\t<testItems size=\"" + input.length + "\">\r\n");
//		
//		for (int i = 0; i < input.length; i++)
//		{
//			writer.write("\t\t<item>\r\n");
//			
//			writer.write("\t\t\t<expectedLabel>" + expectedOutput[i] + "</expectedLabel>\r\n");
//			writer.write("\t\t\t<computedLabel>" + computedOutput[i] + "</computedLabel>\r\n");
//			writer.write("\t\t\t<trainingData>");
//			
//			for (int j = 0; j < input[i].length; j++)
//				writer.write(String.format((j != input[i].length - 1 ? "%f " : "%f"), input[i][j]));
//			
//			writer.write("</trainingData>\r\n");
//			
//			writer.write("\t\t</item>\r\n");
//		}
//		
//		writer.write("\t</testItems>\r\n");
//		
//		writer.write("</reportFile>\r\n");
//		
//		writer.close();
//	}
//	
//	/**
//	 * Creates a data file of the network.
//	 * @param batchItem Item with the configuration of the process.
//	 * @param network Network to be saved in a file.
//	 * @throws Exception When an unexpected exception occurs.
//	 */
//	private void createNetworkFile(Task batchItem, DistanceNeuralNetwork network) throws Exception
//	{
//		File directory = batchItem.getReportDataFile().getParentFile();
//		if (!directory.exists()) directory.mkdirs();
//		
//		FileWriter writer = new FileWriter(batchItem.getNetworkDataFile());
//		
//		writer.write("<network>\r\n");
//		
//		writer.write("\t<labels>\r\n");
//		
//		for (String label : batchItem.getLabels().getLabels())
//		{
//			Integer[] neuronsOfLabel = batchItem.getLabels().getNeuronIndexes(label);
//			String neuronsOfLabelAsString = "";
//			
//			for (int i = 0; i < neuronsOfLabel.length; i++)
//			{
//				neuronsOfLabelAsString += 
//					String.format((i != neuronsOfLabel.length - 1 ? "%d, " : "%d"), neuronsOfLabel[i]);
//			}
//			
//			writer.write(String.format("\t\t<label name=\"%s\" neurons=\"%s\">\r\n", label, neuronsOfLabelAsString));
//		}
//		
//		writer.write("\t</labels>\r\n");
//		
//		DistanceNeuronList list = network.getOutputLayer();
//		
//		for (int i = 0; i < list.size(); i++)
//		{
//			DistanceNeuron neuron = list.get(i);
//			
//			double[] weightVector = neuron.getWeightVector();
//			
//			writer.write(String.format("\t<neuron index=\"%d\">", i));
//			
//			for (int j = 0; j < weightVector.length; j++)
//				writer.write(String.format((j != weightVector.length - 1 ? "%f " : "%f"), weightVector[j]));	
//			
//			writer.write("</neuron>\r\n");
//		}
//		
//		writer.write("</network>\r\n");
//		
//		writer.close();
//	}
}
