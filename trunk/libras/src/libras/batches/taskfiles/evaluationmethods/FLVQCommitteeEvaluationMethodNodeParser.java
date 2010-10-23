package libras.batches.taskfiles.evaluationmethods;

import java.io.File;

import libras.batches.taskfiles.annotations.EvaluationMethod;
import libras.batches.taskfiles.evaluationmethods.models.FLVQCommitteeEvaluationMethod;
import libras.batches.taskfiles.evaluationmethods.models.FLVQSupervisedTrainingPhaseDescription;
import libras.batches.taskfiles.evaluationmethods.models.FLVQUnsupervisedTrainingPhaseDescription;
import libras.batches.taskfiles.models.TaskEvaluationMethod;
import libras.utils.XmlHelper;

import org.w3c.dom.Node;

@EvaluationMethod(nodeName="flvqCommittee")
public class FLVQCommitteeEvaluationMethodNodeParser extends
		TaskEvaluationMethodNodeParser {

	private final static String NODE_NAME = "flvqCommittee";
	
	@Override
	public TaskEvaluationMethod parseNode(Node node) throws Exception {

		FLVQCommitteeEvaluationMethod evaluationMethod = new FLVQCommitteeEvaluationMethod();
		
		this.setUpNetworkSetupForEvaluationMethod(evaluationMethod, node);
		
		this.setUpSupervisedTrainingPhaseForEvaluationMethod(evaluationMethod, node);
		
		this.setUpUnsupervisedTrainingPhaseForEvaluationMethod(evaluationMethod, node);
		
		return evaluationMethod;
	}

	private void setUpNetworkSetupForEvaluationMethod(FLVQCommitteeEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String NETWORK_SETUP_NODE_NAME = "networkSetup";
		
		Node networkSetupNode = XmlHelper.getNodeFromList(NETWORK_SETUP_NODE_NAME, node.getChildNodes());
		
		if (networkSetupNode != null) {
			XmlHelper.validateIfNodeExists(networkSetupNode, NETWORK_SETUP_NODE_NAME, 
					String.format("The \"%s\" node of the %s must exists and cannot be empty.", NETWORK_SETUP_NODE_NAME, NODE_NAME));
			
			XmlHelper.validateIfAttributeExists(networkSetupNode, "fromFile", 
					String.format("The \"%s\" node of the %s must have an attribute named \"%s\".", NETWORK_SETUP_NODE_NAME, NODE_NAME, "fromFile"));
			
			String networkSetupFile = XmlHelper.getAttributeValueFromNode("fromFile", networkSetupNode);
			
			File file = new File(networkSetupFile);
			
			if (!file.exists())
				throw new Exception(String.format("The \"%s\" node of the %s must have a valid file.", NETWORK_SETUP_NODE_NAME, NODE_NAME));
			
			evaluationMethod.setNetworkSetupFile(file);
		}
	}

	private void setUpSupervisedTrainingPhaseForEvaluationMethod(FLVQCommitteeEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String TRAINING_PHASES_NODE_NAME = "networkTraining";
		final String TRAINING_NODE_NAME = "supervisedTraining";
		
		Node trainingPhasesNode = XmlHelper.getNodeFromList(TRAINING_PHASES_NODE_NAME, node.getChildNodes());
		
		XmlHelper.validateIfNodeExists(trainingPhasesNode, TRAINING_PHASES_NODE_NAME, 
			String.format("The \"%s\" node of the %s must exists and cannot be empty.", TRAINING_PHASES_NODE_NAME, NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(trainingPhasesNode, 
				String.format("The \"%s\" node of the %s must have at least one child.", 
						TRAINING_PHASES_NODE_NAME, NODE_NAME));
		
		Node supervisedTrainingPhase = XmlHelper.getNodeFromList(TRAINING_NODE_NAME, trainingPhasesNode.getChildNodes());
		
		XmlHelper.validateIfNodeExists(supervisedTrainingPhase, TRAINING_NODE_NAME,
			String.format("The \"%s\" node of the %s must have only childs nodes with name \"%s\".",
					TRAINING_PHASES_NODE_NAME, NODE_NAME, TRAINING_NODE_NAME));	
		
		XmlHelper.validateIfAttributeExists(supervisedTrainingPhase, "epochs", 
			String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
					TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "epochs"));
		
		XmlHelper.validateIfAttributeExists(supervisedTrainingPhase, "learningRate", 
				String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
						TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "learningRate"));
		
		XmlHelper.validateIfAttributeExists(supervisedTrainingPhase, "fuzzyficationParameter", 
			String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
					TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "fuzzyficationParameter"));
	
		String epochsAsString = XmlHelper.getAttributeValueFromNode("epochs", supervisedTrainingPhase);
		
		if (!epochsAsString.matches("^[0-9]+$"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"epochs", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		String learningRateAsString = XmlHelper.getAttributeValueFromNode("learningRate", supervisedTrainingPhase);
		
		if (!learningRateAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"learningRate", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		String fuzzyficationParameterAsString = XmlHelper.getAttributeValueFromNode("fuzzyficationParameter", supervisedTrainingPhase);
		
		if (!fuzzyficationParameterAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"fuzzyficationParameter", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		FLVQSupervisedTrainingPhaseDescription description = new FLVQSupervisedTrainingPhaseDescription();
		
		description.setEpochs(Integer.parseInt(epochsAsString));
		description.setLearningRate(Double.parseDouble(learningRateAsString));
		description.setFuzzyficationParameter(Double.parseDouble(fuzzyficationParameterAsString));
	
		evaluationMethod.setSupervisedTrainingPhase(description);
	}
	
	private void setUpUnsupervisedTrainingPhaseForEvaluationMethod(FLVQCommitteeEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String TRAINING_PHASES_NODE_NAME = "networkTraining";
		final String TRAINING_NODE_NAME = "unsupervisedTraining";
		
		Node trainingPhasesNode = XmlHelper.getNodeFromList(TRAINING_PHASES_NODE_NAME, node.getChildNodes());
		
		XmlHelper.validateIfNodeExists(trainingPhasesNode, TRAINING_PHASES_NODE_NAME, 
			String.format("The \"%s\" node of the %s must exists and cannot be empty.", TRAINING_PHASES_NODE_NAME, NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(trainingPhasesNode, 
				String.format("The \"%s\" node of the %s must have at least one child.", 
						TRAINING_PHASES_NODE_NAME, NODE_NAME));
		
		Node unsupervisedTrainingPhase = XmlHelper.getNodeFromList(TRAINING_NODE_NAME, trainingPhasesNode.getChildNodes());
		
		XmlHelper.validateIfNodeExists(unsupervisedTrainingPhase, TRAINING_NODE_NAME,
			String.format("The \"%s\" node of the %s must have only childs nodes with name \"%s\".",
					TRAINING_PHASES_NODE_NAME, NODE_NAME, TRAINING_NODE_NAME));	
		
		XmlHelper.validateIfAttributeExists(unsupervisedTrainingPhase, "epochs", 
			String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
					TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "epochs"));
		
		XmlHelper.validateIfAttributeExists(unsupervisedTrainingPhase, "numberOfClusters", 
				String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
						TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "numberOfClusters"));
	
		XmlHelper.validateIfAttributeExists(unsupervisedTrainingPhase, "initialFuzzyficationParameter", 
				String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
						TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "initialFuzzyficationParameter"));
		
		XmlHelper.validateIfAttributeExists(unsupervisedTrainingPhase, "finalFuzzyficationParameter", 
				String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
						TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "finalFuzzyficationParameter"));
		
		XmlHelper.validateIfAttributeExists(unsupervisedTrainingPhase, "error", 
				String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
						TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "error"));
		
		String epochsAsString = XmlHelper.getAttributeValueFromNode("epochs", unsupervisedTrainingPhase);
		
		if (!epochsAsString.matches("^[0-9]+$"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"epochs", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		String numberOfClustersAsString = XmlHelper.getAttributeValueFromNode("numberOfClusters", unsupervisedTrainingPhase);
		
		if (!numberOfClustersAsString.matches("^[0-9]+$"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"numberOfClusters", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		String initialFuzzyficationParameterAsString = XmlHelper.getAttributeValueFromNode("initialFuzzyficationParameter", unsupervisedTrainingPhase);
		
		if (!initialFuzzyficationParameterAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"initialFuzzyficationParameter", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		String finalFuzzyficationParameterAsString = XmlHelper.getAttributeValueFromNode("finalFuzzyficationParameter", unsupervisedTrainingPhase);
		
		if (!finalFuzzyficationParameterAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"finalFuzzyficationParameter", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		String errorAsString = XmlHelper.getAttributeValueFromNode("error", unsupervisedTrainingPhase);
		
		if (!errorAsString.matches("[0-9]+\\.[0-9]+"))
			throw new Exception(
				String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
				"error", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
		
		FLVQUnsupervisedTrainingPhaseDescription description = new FLVQUnsupervisedTrainingPhaseDescription();
		
		description.setEpochs(Integer.parseInt(epochsAsString));
		description.setNumberOfClusters(Integer.parseInt(numberOfClustersAsString));
		description.setInitialFuzzyficationParameter(Double.parseDouble(initialFuzzyficationParameterAsString));
		description.setFinalFuzzyficationParameter(Double.parseDouble(finalFuzzyficationParameterAsString));
		description.setError(Double.parseDouble(errorAsString));
	
		evaluationMethod.setUnsupervisedTrainingPhase(description);
	}
}
