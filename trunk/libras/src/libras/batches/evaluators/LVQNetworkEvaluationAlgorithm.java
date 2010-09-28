package libras.batches.evaluators;

import java.util.ArrayList;
import java.util.List;

import libras.batches.taskfiles.evaluationmethods.models.LVQNetworkEvaluationMethod;
import libras.batches.taskfiles.evaluationmethods.models.LVQTrainingPhaseDescription;
import libras.neuralnetworks.distancenetworks.DistanceNeuralNetwork;
import libras.neuralnetworks.functions.LinearFunction;
import libras.neuralnetworks.learning.LVQLearningStrategy;
import libras.neuralnetworks.learning.LearningVectorQuantization;
import libras.neuralnetworks.utils.LabelCollection;
import libras.neuralnetworks.utils.NetworkFileReader;
import libras.utils.ConverterHelper;
import libras.utils.Pair;

public class LVQNetworkEvaluationAlgorithm implements IEvaluationAlgorithm<Double> {
	public LVQNetworkEvaluationAlgorithm(LVQNetworkEvaluationMethod evaluationModel) {
		this.evaluationModel = evaluationModel;
		this.expectedLabels = new ArrayList<String>();
		this.computedLabels = new ArrayList<String>();
	}
	
	private LVQNetworkEvaluationMethod evaluationModel = null;
	
	private List<String> expectedLabels = null;
	
	private List<String> computedLabels = null;
	
	@Override
	public List<String> getComputedLabels() {
		return this.computedLabels;
	}
	
	@Override
	public List<String> getExpectedLabels() {
		return this.expectedLabels;
	}
	
	private Pair<DistanceNeuralNetwork, LabelCollection> setupNetwork(LVQNetworkEvaluationMethod evaluationModel) {
		if (evaluationModel.getNetworkSetupFile() != null)
		{
			NetworkFileReader reader = new NetworkFileReader();
			
			Pair<DistanceNeuralNetwork, LabelCollection> setup = null;
			
			try {
				setup = reader.getNetworkFromTextFile(evaluationModel.getNetworkSetupFile().getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return setup;
		}
		
		return null;
	}
	
	@Override
	public void evaluate(
		List<Double[]> trainData, List<String> trainDataLabels, 
		List<Double[]> evaluationData, List<String> evaluationDataLabels) throws Exception {
		
		Pair<DistanceNeuralNetwork, LabelCollection> pair = this.setupNetwork(this.evaluationModel);
		
		DistanceNeuralNetwork neuralNetwork = pair.getFirstElement();
		LabelCollection labels = pair.getSecondElement();
		
		this.teachNetwork(neuralNetwork, labels, trainData, trainDataLabels);
		
		List<String> computedLabels = this.validateNetwork(neuralNetwork, labels, evaluationData, evaluationDataLabels);
		
		this.expectedLabels.addAll(evaluationDataLabels);
		this.computedLabels.addAll(computedLabels);
	}
	
	/**
	 * Train the neural network.
	 * @param network Network to be trained.
	 * @param trainingData Training data.
	 * @param batchItem Item with the configuration of the training.
	 * @throws Exception When an unexpected exception occurs.
	 */
	private void teachNetwork(DistanceNeuralNetwork network, LabelCollection labels, List<Double[]> trainingData, List<String> trainingDataLabels) throws Exception
	{
		double[][] input = new double[trainingData.size()][];
		String[] expectedOutput = new String[trainingDataLabels.size()];
		
		this.prepareData(input, trainingData, expectedOutput, trainingDataLabels);
		
		for (LVQTrainingPhaseDescription trainingPhase : this.evaluationModel.getTrainingPhases()) {
			LearningVectorQuantization teacher =
				new LearningVectorQuantization(
						network, 
						trainingPhase.getLearningRate(), 
						labels,
						new LinearFunction(trainingPhase.getLearningDecreasingRate(), 0.0));
			
			for (int i = 0; i < trainingPhase.getEpochs(); i++)
				teacher.trainNetwork(input, expectedOutput, LVQLearningStrategy.Randomized);	
		}
	}

	/**
	 * Validate the training of the network.
	 * @param network Network to be trained.
	 * @param validationData Validation data.
	 * @param batchItem Item with the configuration of the training.
	 * @throws Exception When an unexpected exception occurs.
	 */
	private List<String> validateNetwork(DistanceNeuralNetwork network, LabelCollection labels, List<Double[]> validationData, List<String> validationDataLabels) throws Exception
	{
		double[][] input = new double[validationData.size()][];
		String[] expectedOutput = new String[validationData.size()];
		String[] computedOutput = new String[validationData.size()];
		
		this.prepareData(input, validationData, expectedOutput, validationDataLabels);
		
		for (int i = 0; i < validationData.size(); i++)
		{
			int winner = network.compute(input[i]);
			computedOutput[i] = labels.getLabel(winner);
		}
		
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < computedOutput.length; i++) {
			result.add(computedOutput[i]);
		}
		
		return result;
	}
	
	private void prepareData(double[][] input, List<Double[]> trainingData,
			String[] expectedOutput, List<String> trainingDataLabels) {
		
		for (int i = 0; i < input.length; i++) {
			double[] unboxedInputData = ConverterHelper.unboxArray(trainingData.get(i));
			
			input[i] = unboxedInputData;
			expectedOutput[i] = trainingDataLabels.get(i);
		}
	}
}
