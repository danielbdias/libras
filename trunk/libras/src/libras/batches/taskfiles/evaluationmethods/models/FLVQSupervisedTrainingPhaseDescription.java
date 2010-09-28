package libras.batches.taskfiles.evaluationmethods.models;

public class FLVQSupervisedTrainingPhaseDescription {
	private int epochs;
	private double learningRate;
	private double fuzzyficationParameter;
	
	public int getEpochs() {
		return epochs;
	}
	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}
	public double getLearningRate() {
		return learningRate;
	}
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	public double getFuzzyficationParameter() {
		return fuzzyficationParameter;
	}
	public void setFuzzyficationParameter(double fuzzyficationParameter) {
		this.fuzzyficationParameter = fuzzyficationParameter;
	}
}
