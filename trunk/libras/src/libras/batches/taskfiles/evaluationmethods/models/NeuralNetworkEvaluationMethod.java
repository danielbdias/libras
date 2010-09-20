package libras.batches.taskfiles.evaluationmethods.models;

import java.io.*;
import java.util.*;

import libras.batches.taskfiles.models.TaskEvaluationMethod;

public class NeuralNetworkEvaluationMethod extends TaskEvaluationMethod {
	
	private File inputFile = null;
	private List<TrainingPhaseDescription> trainingPhases = null;
	private File outputResultFile = null;
	private File outputNetworkFile = null;
	
	public File getInputFile() {
		return inputFile;
	}
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}
	public File getOutputResultFile() {
		return outputResultFile;
	}
	public void setOutputResultFile(File outputResultFile) {
		this.outputResultFile = outputResultFile;
	}
	public File getOutputNetworkFile() {
		return outputNetworkFile;
	}
	public void setOutputNetworkFile(File outputNetworkFile) {
		this.outputNetworkFile = outputNetworkFile;
	}
	public List<TrainingPhaseDescription> getTrainingPhases() {
		return trainingPhases;
	}
	public void setTrainingPhases(List<TrainingPhaseDescription> trainingPhases) {
		this.trainingPhases = trainingPhases;
	}
}
