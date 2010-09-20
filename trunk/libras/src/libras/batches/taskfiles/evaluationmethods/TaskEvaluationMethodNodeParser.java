package libras.batches.taskfiles.evaluationmethods;

import libras.batches.taskfiles.models.TaskEvaluationMethod;

public abstract class TaskEvaluationMethodNodeParser {
	public abstract TaskEvaluationMethod parseNode(org.w3c.dom.Node node) throws Exception;
}
