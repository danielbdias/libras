package libras.batches.taskfiles.validationmethods.models;

import libras.batches.taskfiles.models.TaskValidationMethod;

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
}
