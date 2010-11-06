package libras.batches;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.outputfiles.OutputDataFileManager;
import libras.batches.outputfiles.models.OutputData;
import libras.batches.taskfiles.models.Task;
import libras.batches.validators.IValidationAlgorithm;
import libras.neuralnetworks.utils.TrainDataFileReader;
import libras.utils.ConverterHelper;
import libras.utils.Pair;

public class TaskExecution implements Runnable {

	private Task task = null;
	
	private BatchTaskResult result = null; 
	
	private IBatchProcessorObserver observer = null;
	
	public TaskExecution(Task task, IBatchProcessorObserver observer) {
		this.task = task;
	}

	public BatchTaskResult getResult() {
		return result;
	}

	@Override
	public void run() {
		try
		{
			this.executeItem(this.task);
			this.result = new BatchTaskResult(true, this.task.getName());
		}
		catch (Exception e)
		{
			this.result = new BatchTaskResult(e, this.task.getName());
			System.out.printf("Error executing task %s ...", task.getName());
			System.out.println();
			e.printStackTrace(System.err);
		}
		
		try {
			if (this.observer != null) this.observer.receiveResult(this.result, this.task);
		} catch (Exception e) {
			System.out.printf("Error on the observer of task %s ...", task.getName());
			System.out.println();
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Execute the training and validation of the item.
	 * @param batchItem Item to be executed.
	 * @throws Exception When an unexpected exception occurs.
	 */
	private void executeItem(Task task) throws Exception
	{
		long startTime = System.currentTimeMillis();
		
		System.out.printf("Executing task %s", task.getName());
		System.out.println();
		
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
		
		long endTime = System.currentTimeMillis();
		
		long executionTime = endTime - startTime;
		
		System.out.printf("Task %s executed.", task.getName());
		System.out.printf("This task (%s) took %d milliseconds to execute.", task.getName(), executionTime);
		System.out.println();
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
		
		OutputData data = new OutputData(task.getName(), hitTax, expectedOutput, computedOutput);
		
		OutputDataFileManager.createFile(data, task.getOutputReportFile());
	}
}
