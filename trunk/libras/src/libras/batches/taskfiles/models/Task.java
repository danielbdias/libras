/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches.taskfiles.models;

/**
 * Represents a task in the batch file.
 * @author Daniel Baptista Dias
 */
public class Task
{
	/**
	 * Creates a new instance. 
	 */
	public Task()
	{
		
	}
	
	private String name = null;

	private TaskValidationMethod validationMethod = null;
	
	private TaskEvaluationMethod evaluationMethod = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskValidationMethod getValidationMethod() {
		return validationMethod;
	}

	public void setValidationMethod(TaskValidationMethod validationMethod) {
		this.validationMethod = validationMethod;
	}

	public TaskEvaluationMethod getEvaluationMethod() {
		return evaluationMethod;
	}

	public void setEvaluationMethod(TaskEvaluationMethod evaluationMethod) {
		this.evaluationMethod = evaluationMethod;
	}
}
