package libras.batches.taskfiles.evaluationmethods.models;

import java.io.File;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.evaluators.LVQNetworkEvaluationAlgorithm;

public class LVQNetworkEvaluationMethod extends NeuralNetworkEvaluationMethod {
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
