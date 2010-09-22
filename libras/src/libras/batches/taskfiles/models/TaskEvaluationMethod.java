package libras.batches.taskfiles.models;

import libras.batches.evaluators.IEvaluationAlgorithm;

public abstract class TaskEvaluationMethod {
	
	public abstract IEvaluationAlgorithm<Double> getEvaluationAlgorithm();
}
