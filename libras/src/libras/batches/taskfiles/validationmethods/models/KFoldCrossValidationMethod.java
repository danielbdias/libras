package libras.batches.taskfiles.validationmethods.models;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.taskfiles.models.TaskValidationMethod;
import libras.batches.validators.IValidationAlgorithm;
import libras.batches.validators.KFoldCrossValidation;

public class KFoldCrossValidationMethod extends TaskValidationMethod {
	
	private int K = 0;
	private int foldSize = 0;
	
	public int getK() {
		return K;
	}
	public void setK(int k) {
		K = k;
	}
	public int getFoldSize() {
		return foldSize;
	}
	public void setFoldSize(int foldSize) {
		this.foldSize = foldSize;
	}
	
	@Override
	public IValidationAlgorithm<Double> getValidationAlgorithm(IEvaluationAlgorithm<Double> evaluationAlgorithm) {
		return new KFoldCrossValidation<Double>(evaluationAlgorithm, this.K, this.foldSize);
	}
}
