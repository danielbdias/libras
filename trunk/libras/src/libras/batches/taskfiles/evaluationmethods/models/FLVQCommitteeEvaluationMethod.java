package libras.batches.taskfiles.evaluationmethods.models;

import java.io.File;

import libras.batches.evaluators.FLVQCommitteeEvaluationAlgorithm;
import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.taskfiles.models.TaskEvaluationMethod;

public class FLVQCommitteeEvaluationMethod extends TaskEvaluationMethod {
	private File networkSetupFile = null;
	
	private FLVQSupervisedTrainingPhaseDescription supervisedTrainingPhase = null;
	
	private FLVQUnsupervisedTrainingPhaseDescription unsupervisedTrainingPhase = null;
	
	public File getNetworkSetupFile() {
		return networkSetupFile;
	}
	public void setNetworkSetupFile(File networkSetupFile) {
		this.networkSetupFile = networkSetupFile;
	}
	
	public FLVQSupervisedTrainingPhaseDescription getSupervisedTrainingPhase() {
		return supervisedTrainingPhase;
	}
	public void setSupervisedTrainingPhase(FLVQSupervisedTrainingPhaseDescription supervisedTrainingPhase) {
		this.supervisedTrainingPhase = supervisedTrainingPhase;
	}
	
	public FLVQUnsupervisedTrainingPhaseDescription getUnsupervisedTrainingPhase() {
		return unsupervisedTrainingPhase;
	}
	public void setUnsupervisedTrainingPhase(FLVQUnsupervisedTrainingPhaseDescription unsupervisedTrainingPhase) {
		this.unsupervisedTrainingPhase = unsupervisedTrainingPhase;
	}
	
	@Override
	public IEvaluationAlgorithm<Double> getEvaluationAlgorithm() {
		return new FLVQCommitteeEvaluationAlgorithm(this);
	}

}
