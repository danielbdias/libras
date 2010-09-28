package libras.batches.taskfiles.evaluationmethods.models;

public class FLVQUnsupervisedTrainingPhaseDescription {
	private int epochs;
	private int numberOfClusters;
	private double initialFuzzyficationParameter;
	private double finalFuzzyficationParameter;
	private double error;
	
	public int getEpochs() {
		return epochs;
	}
	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}
	public int getNumberOfClusters() {
		return numberOfClusters;
	}
	public void setNumberOfClusters(int numberOfClusters) {
		this.numberOfClusters = numberOfClusters;
	}
	public double getInitialFuzzyficationParameter() {
		return initialFuzzyficationParameter;
	}
	public void setInitialFuzzyficationParameter(
			double initialFuzzyficationParameter) {
		this.initialFuzzyficationParameter = initialFuzzyficationParameter;
	}
	public double getFinalFuzzyficationParameter() {
		return finalFuzzyficationParameter;
	}
	public void setFinalFuzzyficationParameter(double finalFuzzyficationParameter) {
		this.finalFuzzyficationParameter = finalFuzzyficationParameter;
	}
	public double getError() {
		return error;
	}
	public void setError(double error) {
		this.error = error;
	}
}
