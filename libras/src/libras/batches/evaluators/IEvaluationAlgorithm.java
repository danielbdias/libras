package libras.batches.evaluators;

import java.util.List;

public interface IEvaluationAlgorithm<T> {
	public void evaluate(
		List<T[]> trainData, List<String> trainDataLabels, 
		List<T[]> evaluationData, List<String> evaluationDataLabels) throws Exception;
	
	public List<String> getExpectedLabels();
	
	public List<String> getComputedLabels();
}
