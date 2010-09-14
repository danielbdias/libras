package libras.utils.validation;

import java.util.List;

public interface IEvaluationAlgorithm<T> {
	public void evaluate(List<Fold<T>> trainData, Fold<T> evaluationData);
}
