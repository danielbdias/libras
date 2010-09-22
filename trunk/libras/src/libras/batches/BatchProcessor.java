/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches;

import java.io.*;
import java.util.*;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.taskfiles.models.Task;
import libras.batches.taskfiles.models.TaskFile;
import libras.batches.validators.IValidationAlgorithm;
import libras.neuralnetworks.utils.*;
import libras.utils.*;

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
		
		for (int i = 0; i < taskFile.size(); i++)
		{
			Task item = taskFile.get(i);
			
			try
			{
				this.executeItem(item);
				results[i] = new BatchTaskResult(true, item.getName());
			}
			catch (Exception e)
			{
				results[i] = new BatchTaskResult(e, item.getName());
			}
		}
		
		return results;
	}
	
	/**
	 * Execute the training and validation of the item.
	 * @param batchItem Item to be executed.
	 * @throws Exception When an unexpected exception occurs.
	 */
	private void executeItem(Task task) throws Exception
	{
		IEvaluationAlgorithm<Double> evaluationAlgorithm = 
			task.getEvaluationMethod().getEvaluationAlgorithm();
		
		IValidationAlgorithm<Double> validationAlgorithm =
			task.getValidationMethod().getValidationAlgorithm(evaluationAlgorithm);
		
		ArrayList<Double[]> inputData = new ArrayList<Double[]>(); 
		ArrayList<String> labels = new ArrayList<String>();
		
		this.separeInputDataAndLabels(task, inputData, labels);
		
		validationAlgorithm.doValidation(inputData, labels);
		
		if (task.getOutputReportFile() != null)
			this.createReportDataFile(task, evaluationAlgorithm.getExpectedLabels(), evaluationAlgorithm.getComputedLabels());
	}
	
	private void separeInputDataAndLabels(Task task, ArrayList<Double[]> inputDataWithoutLabels, ArrayList<String> labels) throws Exception {
		List<Pair<String, double[]>> inputData = new ArrayList<Pair<String, double[]>>();
		
		for (File inputFile : task.getInputFiles())
			inputData.addAll(TrainDataFileReader.getDataFromFile(inputFile.getAbsolutePath()));
		
		for (Pair<String,double[]> pair : inputData) {
			Double[] boxedArray = ConverterHelper.boxArray(pair.getSecondElement());
			
			inputDataWithoutLabels.add(boxedArray);
			labels.add(pair.getFirstElement());
		}
	}

	
	/**
	 * Creates a report file based in the execution of the training and validation of the network.
	 * @param batchItem Item with the configuration of the process.
	 * @param input Input data of the network.
	 * @param expectedOutput Expected output of the network.
	 * @param computedOutput Computed output of the network.
	 * @throws Exception When an unexpected exception occurs.
	 */
	private void createReportDataFile(Task task, List<String> expectedOutput, List<String> computedOutput) throws Exception
	{
		double hitTax = 0.0;
		
		for (int i = 0; i < computedOutput.size(); i++)
		{
			if (expectedOutput.get(i).equals(computedOutput.get(i))) 
				hitTax++;
		}
		
		hitTax /= computedOutput.size();
		
		File directory = task.getOutputReportFile().getParentFile();
		if (!directory.exists()) directory.mkdirs();
		
		FileWriter writer = new FileWriter(task.getOutputReportFile());
		
		writer.write("<reportFile>\r\n");
		
		writer.write(String.format("\t<batchName>%s</batchName>\r\n", task.getName()));
		writer.write(String.format("\t<hitTax>%f</hitTax>\r\n", hitTax));
//		writer.write(String.format("\t<learningRate>%f</learningRate>\r\n", batchItem.getInitialLearningRate()));
//		writer.write(String.format("\t<learningRateDecreaseTax>%f</learningRateDecreaseTax>\r\n", batchItem.getLearningRateDecreasingRate()));
//		writer.write(String.format("\t<epochs>%d</epochs>\r\n", batchItem.getEpochsToExecute()));
		
		writer.write("\t<tests size=\"" + computedOutput.size() + "\">\r\n");
		
		for (int i = 0; i < computedOutput.size(); i++)
		{
			writer.write("\t\t<test>\r\n");
			writer.write("\t\t\t<expectedLabel>" + expectedOutput.get(i) + "</expectedLabel>\r\n");
			writer.write("\t\t\t<computedLabel>" + computedOutput.get(i) + "</computedLabel>\r\n");
			writer.write("\t\t</test>\r\n");
		}
		
		writer.write("\t</tests>\r\n");
		
		writer.write("</reportFile>\r\n");
		
		writer.close();
	}
}
