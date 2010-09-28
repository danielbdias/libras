package libras.batches.taskfiles.evaluationmethods;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import libras.batches.taskfiles.annotations.*;
import libras.batches.taskfiles.evaluationmethods.models.*;
import libras.batches.taskfiles.models.*;
import libras.utils.XmlHelper;

import org.w3c.dom.Node;

@EvaluationMethod(nodeName="lvqNetwork")
public class LVQNetworkEvaluationMethodNodeParser extends TaskEvaluationMethodNodeParser {

	private final static String NODE_NAME = "lvqNetwork";
	
	@Override
	public TaskEvaluationMethod parseNode(Node node) throws Exception {
		
		LVQNetworkEvaluationMethod evaluationMethod = new LVQNetworkEvaluationMethod();
		
		this.setUpNetworkSetupForEvaluationMethod(evaluationMethod, node);
		
		this.setUpTrainingPhasesForEvaluationMethod(evaluationMethod, node);
		
		return evaluationMethod;
	}
	
	private void setUpNetworkSetupForEvaluationMethod(LVQNetworkEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String NETWORK_SETUP_NODE_NAME = "networkSetup";
		
		Node networkSetupNode = XmlHelper.getNodeFromList(NETWORK_SETUP_NODE_NAME, node.getChildNodes());
		
		XmlHelper.validateIfNodeExists(networkSetupNode, NETWORK_SETUP_NODE_NAME, 
			String.format("The \"%s\" node of the %s must exists and cannot be empty.", NETWORK_SETUP_NODE_NAME, NODE_NAME));
		
		XmlHelper.validateIfAttributeExists(networkSetupNode, "fromFile", 
				String.format("The \"%s\" node of the %s must have an attribute named \"%s\".",
						NETWORK_SETUP_NODE_NAME, NODE_NAME, "fromFile"));
		
		String networkSetupFile = XmlHelper.getAttributeValueFromNode("fromFile", networkSetupNode);
		
		File file = new File(networkSetupFile);
		
		if (!file.exists())
			throw new Exception(
				String.format("The \"%s\" node of the %s must have a valid file.",
						NETWORK_SETUP_NODE_NAME, NODE_NAME));
		
		evaluationMethod.setNetworkSetupFile(file);
	}

	private void setUpTrainingPhasesForEvaluationMethod(LVQNetworkEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String TRAINING_PHASES_NODE_NAME = "networkTraining";
		final String TRAINING_NODE_NAME = "training";
		
		Node trainingPhasesNode = XmlHelper.getNodeFromList(TRAINING_PHASES_NODE_NAME, node.getChildNodes());
		
		XmlHelper.validateIfNodeExists(trainingPhasesNode, TRAINING_PHASES_NODE_NAME, 
			String.format("The \"%s\" node of the %s must exists and cannot be empty.", TRAINING_PHASES_NODE_NAME, NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(trainingPhasesNode, 
				String.format("The \"%s\" node of the %s must have at least one child.", 
						TRAINING_PHASES_NODE_NAME, NODE_NAME));
			
		List<Node> children = XmlHelper.getChildNodes(trainingPhasesNode);
		
		if (children != null && children.size() > 0) {
							
			List<LVQTrainingPhaseDescription> trainingPhases = new ArrayList<LVQTrainingPhaseDescription>();
			
			for (Node childNode : children) {
				XmlHelper.validateIfNodeExists(childNode, TRAINING_NODE_NAME,
					String.format("The \"%s\" node of the %s must have only childs nodes with name \"%s\".",
							TRAINING_PHASES_NODE_NAME, NODE_NAME, TRAINING_NODE_NAME));	
				
				XmlHelper.validateIfAttributeExists(childNode, "epochs", 
					String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
							TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "epochs"));
				
				XmlHelper.validateIfAttributeExists(childNode, "learningRate", 
						String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
								TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "learningRate"));
				
				XmlHelper.validateIfAttributeExists(childNode, "learningDecreasingRate", 
						String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
								TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME, NODE_NAME, "learningDecreasingRate"));
				
				String epochsAsString = XmlHelper.getAttributeValueFromNode("epochs", childNode);
				
				if (!epochsAsString.matches("^[0-9]+$"))
					throw new Exception(
						String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
						"epochs", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
				
				String learningRateAsString = XmlHelper.getAttributeValueFromNode("learningRate", childNode);
				
				if (!learningRateAsString.matches("[0-9]+\\.[0-9]+"))
					throw new Exception(
						String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
						"learningRate", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
				
				String learningDecreasingRateAsString = XmlHelper.getAttributeValueFromNode("learningDecreasingRate", childNode);
				
				if (!learningDecreasingRateAsString.matches("[0-9]+\\.[0-9]+"))
					throw new Exception(
						String.format("The \"%s\" of \"%s\" node in the %s must be a number.", 
						"learningDecreasingRate", TRAINING_NODE_NAME, TRAINING_PHASES_NODE_NAME));
				
				LVQTrainingPhaseDescription description = new LVQTrainingPhaseDescription();
				
				description.setEpochs(Integer.parseInt(epochsAsString));
				description.setLearningRate(Double.parseDouble(learningRateAsString));
				description.setLearningDecreasingRate(Double.parseDouble(learningDecreasingRateAsString));
	
				trainingPhases.add(description);
			}
			
			evaluationMethod.setTrainingPhases(trainingPhases);
		}
	}
}
