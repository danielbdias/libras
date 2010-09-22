package libras.batches.taskfiles.models;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.validators.IValidationAlgorithm;

public abstract class TaskValidationMethod {
	public abstract IValidationAlgorithm<Double> getValidationAlgorithm(IEvaluationAlgorithm<Double> evaluationAlgorithm);
}
