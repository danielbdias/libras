/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches.taskfiles.models;

import java.io.File;
import java.util.List;

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
	
	private List<File> inputFiles = null;
	
	private List<String> labels = null;
	
	private File outputReportFile = null;
	
	public List<File> getInputFiles() {
		return inputFiles;
	}
	
	public void setInputFiles(List<File> inputFiles) {
		this.inputFiles = inputFiles;
	}
	
	public File getOutputReportFile() {
		return outputReportFile;
	}
	
	public void setOutputReportFile(File outputReportFile) {
		this.outputReportFile = outputReportFile;
	}
	
	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

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
