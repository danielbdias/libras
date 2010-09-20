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
		
		this.SetUpInputFilesForEvaluationMethod(evaluationMethod, node);
		
		this.SetUpTrainingPhasesForEvaluationMethod(evaluationMethod, node);
		
		this.SetUpOutputFilesForEvaluationMethod(evaluationMethod, node);
		
		return evaluationMethod;
	}

	private void SetUpInputFilesForEvaluationMethod(LVQNetworkEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String INPUT_FILES_NODE_NAME = "inputFiles";
		final String FILE_NODE_NAME = "file";
		
		Node inputFilesNode = XmlHelper.getNodeFromList(INPUT_FILES_NODE_NAME, node.getChildNodes());
		
		XmlHelper.validateIfNodeExists(inputFilesNode, INPUT_FILES_NODE_NAME, 
			String.format("The \"%s\" node of the %s must exists and cannot be empty.", INPUT_FILES_NODE_NAME, NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(inputFilesNode, 
			String.format("The \"%s\" node of the %s must have at least one child.", 
					INPUT_FILES_NODE_NAME, NODE_NAME));
		
		List<Node> children = XmlHelper.getChildNodes(inputFilesNode);
		
		if (children != null && children.size() > 0) {
			Node childNode = children.get(0);
		
			XmlHelper.validateIfNodeExists(childNode, FILE_NODE_NAME,
				String.format("The \"%s\" node of the %s must have only childs nodes with name \"%s\".",
					INPUT_FILES_NODE_NAME, NODE_NAME, FILE_NODE_NAME));
			
			XmlHelper.validateIfAttributeExists(childNode, "name", 
				String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
					FILE_NODE_NAME, INPUT_FILES_NODE_NAME, NODE_NAME, "name"));
			
			String fileName = XmlHelper.getAttributeValueFromNode("name", childNode);
			
			File file = new File(fileName);
			
			if (!file.exists())
				throw new Exception(
					String.format("The \"%s\" of \"%s\" node of the %s must have a valid file.",
						FILE_NODE_NAME, INPUT_FILES_NODE_NAME, NODE_NAME));
			
			evaluationMethod.setInputFile(file);
		}
	}

	private void SetUpTrainingPhasesForEvaluationMethod(LVQNetworkEvaluationMethod evaluationMethod, Node node) throws Exception {
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
							
			List<TrainingPhaseDescription> trainingPhases = new ArrayList<TrainingPhaseDescription>();
			
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
				
				TrainingPhaseDescription description = new TrainingPhaseDescription();
				
				description.setEpochs(Integer.parseInt(epochsAsString));
				description.setLearningRate(Double.parseDouble(learningRateAsString));
				description.setLearningDecreasingRate(Double.parseDouble(learningDecreasingRateAsString));
	
				trainingPhases.add(description);
			}
			
			evaluationMethod.setTrainingPhases(trainingPhases);
		}
	}

	private void SetUpOutputFilesForEvaluationMethod(LVQNetworkEvaluationMethod evaluationMethod, Node node) throws Exception {
		final String OUTPUT_FILES_NODE_NAME = "outputFiles";
		
		Node outputFilesNode = XmlHelper.getNodeFromList(OUTPUT_FILES_NODE_NAME, node.getChildNodes());
		
		XmlHelper.validateIfNodeExists(outputFilesNode, OUTPUT_FILES_NODE_NAME, 
			String.format("The \"%s\" node of the %s must exists and cannot be empty.", OUTPUT_FILES_NODE_NAME, NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(outputFilesNode, 
			String.format("The \"%s\" node of the %s must have at least one child.", 
					OUTPUT_FILES_NODE_NAME, NODE_NAME));
			
		List<Node> children = XmlHelper.getChildNodes(outputFilesNode);
		
		if (children != null && children.size() > 0) {
			for (Node childNode : children) {
				if (childNode.getNodeName().equals("dataFile")) {
					XmlHelper.validateIfAttributeExists(childNode, "name", 
						String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
							"dataFile", OUTPUT_FILES_NODE_NAME, NODE_NAME, "name"));
						
					String fileName = XmlHelper.getAttributeValueFromNode("name", childNode);
					
					File file = new File(fileName);
					
					evaluationMethod.setOutputResultFile(file);
				}
				else if (childNode.getNodeName().equals("networkFile")) {
					XmlHelper.validateIfAttributeExists(childNode, "name", 
						String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
							"dataFile", OUTPUT_FILES_NODE_NAME, NODE_NAME, "name"));
							
					String fileName = XmlHelper.getAttributeValueFromNode("name", childNode);
					
					File file = new File(fileName);
					
					evaluationMethod.setOutputNetworkFile(file);
				}
			}
		}
	}
}
