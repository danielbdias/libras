package libras.batches.taskfiles.evaluationmethods.models;

import java.util.*;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.taskfiles.models.TaskEvaluationMethod;

public abstract class NeuralNetworkEvaluationMethod extends TaskEvaluationMethod {
	
	private List<TrainingPhaseDescription> trainingPhases = null;
	
	public List<TrainingPhaseDescription> getTrainingPhases() {
		return trainingPhases;
	}
	public void setTrainingPhases(List<TrainingPhaseDescription> trainingPhases) {
		this.trainingPhases = trainingPhases;
	}
	
	public abstract IEvaluationAlgorithm<Double> getEvaluationAlgorithm();
}
