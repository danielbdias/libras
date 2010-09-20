package libras.batches.taskfiles.validationmethods;

import libras.batches.taskfiles.models.TaskValidationMethod;

public abstract class TaskValidationMethodNodeParser {
	public abstract TaskValidationMethod parseNode(org.w3c.dom.Node node) throws Exception;
}
