package libras.batches.taskfiles.evaluationmethods.models;

import java.io.File;
import java.util.List;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.evaluators.LVQNetworkEvaluationAlgorithm;
import libras.batches.taskfiles.models.TaskEvaluationMethod;

public class LVQNetworkEvaluationMethod extends TaskEvaluationMethod {
	private List<LVQTrainingPhaseDescription> trainingPhases = null;
	
	public List<LVQTrainingPhaseDescription> getTrainingPhases() {
		return trainingPhases;
	}
	public void setTrainingPhases(List<LVQTrainingPhaseDescription> trainingPhases) {
		this.trainingPhases = trainingPhases;
	}
	
	private File networkSetupFile = null;
	
	public File getNetworkSetupFile() {
		return networkSetupFile;
	}
	public void setNetworkSetupFile(File networkSetupFile) {
		this.networkSetupFile = networkSetupFile;
	}
	@Override
	public IEvaluationAlgorithm<Double> getEvaluationAlgorithm() {
		return new LVQNetworkEvaluationAlgorithm(this);
	}
}
