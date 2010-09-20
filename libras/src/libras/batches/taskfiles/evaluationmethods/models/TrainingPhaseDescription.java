package libras.batches.taskfiles.evaluationmethods.models;

public class TrainingPhaseDescription {
	private int epochs;
	private double learningRate;
	private double learningDecreasingRate;
	
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
	public double getLearningDecreasingRate() {
		return learningDecreasingRate;
	}
	public void setLearningDecreasingRate(double learningDecreasingRate) {
		this.learningDecreasingRate = learningDecreasingRate;
	}
}
