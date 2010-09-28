package libras.batches.evaluators;

import java.util.ArrayList;
import java.util.List;

import libras.batches.taskfiles.evaluationmethods.models.FLVQCommitteeEvaluationMethod;

public class FLVQCommitteeEvaluationAlgorithm implements
		IEvaluationAlgorithm<Double> {
	public FLVQCommitteeEvaluationAlgorithm(FLVQCommitteeEvaluationMethod evaluationModel) {
		this.evaluationModel = evaluationModel;
		this.expectedLabels = new ArrayList<String>();
		this.computedLabels = new ArrayList<String>();
	}
	
	private FLVQCommitteeEvaluationMethod evaluationModel = null;
	
	private List<String> expectedLabels = null;
	
	private List<String> computedLabels = null;
	
	@Override
	public void evaluate(List<Double[]> trainData,
			List<String> trainDataLabels, List<Double[]> evaluationData,
			List<String> evaluationDataLabels) throws Exception {
		// TODO Auto-generated method stub
		this.evaluationModel.getEvaluationAlgorithm();
	}

	@Override
	public List<String> getComputedLabels() {
		return computedLabels;
	}

	@Override
	public List<String> getExpectedLabels() {
		return expectedLabels;
	}

}
