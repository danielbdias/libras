package libras.batches.evaluators;

import java.util.*;
import flvq.*;

import libras.batches.taskfiles.evaluationmethods.models.FLVQCommitteeEvaluationMethod;
import libras.utils.Pair;

public class FLVQCommitteeEvaluationAlgorithm implements
		IEvaluationAlgorithm<Double> {
	public FLVQCommitteeEvaluationAlgorithm(FLVQCommitteeEvaluationMethod evaluationModel) {
		this.evaluationModel = evaluationModel;
		this.expectedLabels = new ArrayList<String>();
		this.computedLabels = new ArrayList<String>();
	}
	
	private FLVQCommitteeEvaluationMethod evaluationModel = null;
	
	private List<String> expectedLabels = null;
	
	private List<String> computedLabels = null;

	@Override
	public List<String> getComputedLabels() {
		return computedLabels;
	}

	@Override
	public List<String> getExpectedLabels() {
		return expectedLabels;
	}

	@Override
	public void evaluate(List<Double[]> trainData,
			List<String> trainDataLabels, List<Double[]> evaluationData,
			List<String> evaluationDataLabels) throws Exception {
		
		Pair<FLVQ, FLVQS[]> committee = this.getCommittee();
		
		this.teachCommittee(committee, trainData, trainDataLabels);
		
		List<String> computedLabels = this.validateCommittee(committee, evaluationData, evaluationDataLabels);
		
		this.expectedLabels.addAll(evaluationDataLabels);
		this.computedLabels.addAll(computedLabels);
	}

	private Pair<FLVQ, FLVQS[]> getCommittee() throws Exception {
		FLVQ unsupervisedLayer = new FLVQ(
			this.evaluationModel.getUnsupervisedTrainingPhase().getNumberOfClusters(),
			this.evaluationModel.getUnsupervisedTrainingPhase().getInitialFuzzyficationParameter(),
			this.evaluationModel.getUnsupervisedTrainingPhase().getFinalFuzzyficationParameter(),
			this.evaluationModel.getUnsupervisedTrainingPhase().getEpochs(),
			this.evaluationModel.getUnsupervisedTrainingPhase().getError());

		FLVQS[] supervisedLayer = 
			new FLVQS[this.evaluationModel.getUnsupervisedTrainingPhase().getNumberOfClusters()];
		
		for (int i = 0; i < supervisedLayer.length; i++) {
			supervisedLayer[i] = new FLVQS(
					this.evaluationModel.getSupervisedTrainingPhase().getFuzzyficationParameter(),
					this.evaluationModel.getSupervisedTrainingPhase().getLearningRate(),
					this.evaluationModel.getSupervisedTrainingPhase().getEpochs());
		}
		
		return new Pair<FLVQ, FLVQS[]>(unsupervisedLayer, supervisedLayer);
	}
	
	private void teachCommittee(Pair<FLVQ, FLVQS[]> committee,
			List<Double[]> trainData, List<String> trainDataLabels) throws Exception {
		
		FLVQ unsupervisedLayer = committee.getFirstElement();
		unsupervisedLayer.setaDados(trainData, trainDataLabels);
		
		unsupervisedLayer.clusteriza();
		
		Vetor[] clusters = unsupervisedLayer.separaDados();
		
		FLVQS[] supervisedLayer = committee.getSecondElement();
		
		for (int i = 0; i < supervisedLayer.length; i++) {
			Vetor cluster = clusters[i];
			
			FLVQS network = supervisedLayer[i];
			
			network.setarDados(cluster);
			
			network.treina();
			
//			Teste testeCamada = null;
		}
	}
	
	private List<String> validateCommittee(Pair<FLVQ, FLVQS[]> committee,
			List<Double[]> evaluationData, List<String> evaluationDataLabels) {
		
		
		// TODO Auto-generated method stub
		return null;
	}	
}
