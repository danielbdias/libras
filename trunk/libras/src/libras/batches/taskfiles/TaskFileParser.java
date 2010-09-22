/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches.taskfiles;

import java.io.File;
import java.util.*;

import org.w3c.dom.Node;

import libras.batches.taskfiles.annotations.*;
import libras.batches.taskfiles.evaluationmethods.*;
import libras.batches.taskfiles.models.*;
import libras.batches.taskfiles.validationmethods.*;
import libras.utils.*;

/**
 * Parser of xml batch files.
 * @author Daniel Baptista Dias
 */
public class TaskFileParser
{
	private final static String ROOT_NODE = "batch";
	private final static String ITEM_NODE = "task";
	
	/**
	 * Parse a task file.
	 * @param taskFile File to be parsed.
	 * @return The representation of the task file.
	 * @throws Exception When an unexpected error happens.
	 */
	public TaskFile parseFile(String taskFile) throws Exception
	{
		javax.xml.parsers.DocumentBuilderFactory factory =
			javax.xml.parsers.DocumentBuilderFactory.newInstance();
		
		javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document document = builder.parse(new java.io.File(taskFile));
		
		if (document == null) throw new Exception("Unable to parse batch file.");
		
		try
		{	
			return this.parseRootNode(document);
		}
		catch (Exception e)
		{
			throw new Exception(
				"Occurred an error in parse process of the batch file." +
				" See Cause for more details.", e);
		}
	}

	/**
	 * Parse the root node of the file.
	 * @param document Document to be parsed.
	 * @return The representation of the batch file.
	 * @throws Exception When an unexpected error happens.
	 */
	private TaskFile parseRootNode(org.w3c.dom.Document document) throws Exception
	{	
		org.w3c.dom.Node rootNode = XmlHelper.getNodeFromList(ROOT_NODE, document.getChildNodes());
		
		XmlHelper.validateIfNodeExists(rootNode, ROOT_NODE, 
				"The task file does not contain \"" + ROOT_NODE + "\" node.");
		
		TaskFile tasks = new TaskFile();
		
		XmlHelper.validateIfNodeHasChildren(
			rootNode, 
			"The \"" + ROOT_NODE + "\" node does not contains any \"" + ITEM_NODE + "\" node as child.");
		
		List<org.w3c.dom.Node> childNodes = XmlHelper.getChildNodes(rootNode);
		
		for (int i = 0; i < childNodes.size(); i++)
		{
			org.w3c.dom.Node childNode = childNodes.get(i);
			tasks.add(this.parseItemNode(childNode, i+1));
		}
		
		return tasks;
	}
	
	/**
	 * Parse a node of the batch file.
	 * @param node Node to be parsed.
	 * @param order Order of the node in the file.
	 * @return The representation of the batch item.
	 * @throws Exception When an unexpected error happens.
	 */
	private Task parseItemNode(org.w3c.dom.Node itemNode, int order) throws Exception
	{	
		XmlHelper.validateIfNodeExists(itemNode, ITEM_NODE, 
			"The " + order + "st \"" + ITEM_NODE + "\" node is corrupted.");
		
		Task task = new Task();
		
		this.getNameForItem(itemNode, task, order);
		
		this.getInputDataForItem(itemNode, task, order);
		
		this.getValidationMethodForItem(itemNode, task, order);
		
		this.getEvaluationMethodForItem(itemNode, task, order);
		
		this.getOutputDataForItem(itemNode, task, order);
		
		return task;
	}
	
	/**
	 * Get the item name of the task node.
	 * @param itemNode Node to be parsed.
	 * @param task Task item in composition.
	 * @param order Order of the item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getNameForItem(org.w3c.dom.Node itemNode, Task task, int order) throws Exception
	{	
		final String ATTRIBUTE_NAME = "name";
		
		XmlHelper.validateIfAttributeExists(itemNode, ATTRIBUTE_NAME, 
			String.format(
				"The \"%s\" attribute of %d st \"%s\" node must exists and cannot be empty.",
				ATTRIBUTE_NAME, order, ITEM_NODE));
		
		String name = XmlHelper.getAttributeValueFromNode(ATTRIBUTE_NAME, itemNode);

		task.setName(name);
	}

	private void getInputDataForItem(org.w3c.dom.Node itemNode, Task task, int order) throws Exception {
		final String INPUT_DATA_NODE_NAME = "inputData";
		final String FILE_NODE_NAME = "file";
		final String LABEL_NODE = "labels";
		
		Node inputDataNode = XmlHelper.getNodeFromList(INPUT_DATA_NODE_NAME, itemNode.getChildNodes());
		
		XmlHelper.validateIfNodeExists(inputDataNode, INPUT_DATA_NODE_NAME, 
			String.format(
				"The %d st \"%s\" node must have a \"%s\" child node.", 
				order, ITEM_NODE, INPUT_DATA_NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(inputDataNode, 
			String.format(
				"The %d st \"%s\" node must have a \"%s\" child node with at least one validation method.",
				order, ITEM_NODE, INPUT_DATA_NODE_NAME));
		
		List<Node> children = XmlHelper.getChildNodes(inputDataNode);
		
		if (children != null && children.size() > 0) {
			List<File> inputFiles = new ArrayList<File>();
			
			for (Node childNode : children) {
				if (childNode.getNodeName().equals(FILE_NODE_NAME)) {	
					XmlHelper.validateIfAttributeExists(childNode, "name", 
						String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
							FILE_NODE_NAME, INPUT_DATA_NODE_NAME, ITEM_NODE, "name"));
					
					String fileName = XmlHelper.getAttributeValueFromNode("name", childNode);
					
					File file = new File(fileName);
					
					if (!file.exists())
						throw new Exception(
							String.format("The \"%s\" of \"%s\" node of the %s must have a valid file.",
								FILE_NODE_NAME, INPUT_DATA_NODE_NAME, ITEM_NODE));
					
					inputFiles.add(file);
				}
				else if (childNode.getNodeName().equals(LABEL_NODE)) {
					XmlHelper.validateIfNodeHasContent(childNode, 
						String.format(
							String.format("The \"%s\" of \"%s\" node of the %s must have some text content.",
								LABEL_NODE, INPUT_DATA_NODE_NAME, ITEM_NODE)));
					
					List<String> labels = new ArrayList<String>();
					
					String data = childNode.getTextContent();
				
					String[] parsedData = data.split(",");

					for (String label : parsedData) {
						labels.add(label);
					}
					
					task.setLabels(labels);
				}
			}
			
			task.setInputFiles(inputFiles);
		}
	}
	
	private void getOutputDataForItem(org.w3c.dom.Node itemNode, Task task, int order) throws Exception {
		final String OUTPUT_DATA_NODE_NAME = "outputData";
		
		Node outputDataNode = XmlHelper.getNodeFromList(OUTPUT_DATA_NODE_NAME, itemNode.getChildNodes());
		
		XmlHelper.validateIfNodeExists(outputDataNode, OUTPUT_DATA_NODE_NAME, 
				String.format(
					"The %d st \"%s\" node must have a \"%s\" child node.", 
					order, ITEM_NODE, OUTPUT_DATA_NODE_NAME));
			
			XmlHelper.validateIfNodeHasChildren(outputDataNode, 
				String.format(
					"The %d st \"%s\" node must have a \"%s\" child node with at least one validation method.",
					order, ITEM_NODE, OUTPUT_DATA_NODE_NAME));
			
		List<Node> children = XmlHelper.getChildNodes(outputDataNode);
		
		if (children != null && children.size() > 0) {
			for (Node childNode : children) {
				if (childNode.getNodeName().equals("reportFile")) {
					XmlHelper.validateIfAttributeExists(childNode, "name", 
						String.format("The \"%s\" of \"%s\" node of the %s must have an attribute named \"%s\".",
							"dataFile", OUTPUT_DATA_NODE_NAME, ITEM_NODE, "name"));
						
					String fileName = XmlHelper.getAttributeValueFromNode("name", childNode);
					
					File file = new File(fileName);
					
					task.setOutputReportFile(file);
				}
			}
		}
	}
	
	/**
	 * Get the validation method of the task node.
	 * @param itemNode Node to be parsed.
	 * @param task Task item in composition.
	 * @param order Order of the item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getValidationMethodForItem(org.w3c.dom.Node itemNode, Task task, int order) throws Exception
	{	
		final String CHILD_NODE_NAME = "validationMethod";
		
		org.w3c.dom.Node validationMethodNode = XmlHelper.getNodeFromList(CHILD_NODE_NAME, itemNode.getChildNodes());
		
		XmlHelper.validateIfNodeExists(validationMethodNode, CHILD_NODE_NAME, 
			String.format(
				"The %d st \"%s\" node must have a \"%s\" child node.", 
				order, ITEM_NODE, CHILD_NODE_NAME));

		XmlHelper.validateIfNodeHasChildren(validationMethodNode, 
			String.format(
				"The %d st \"%s\" node must have a \"%s\" child node with at least one validation method.",
				order, ITEM_NODE, CHILD_NODE_NAME));
		
		List<org.w3c.dom.Node> nodes = XmlHelper.getChildNodes(validationMethodNode);
		
		org.w3c.dom.Node node = nodes.get(0);
		
		TaskValidationMethodNodeParser parser = 
			this.getTaskValidationMethodNodeParserFromNodeName(node.getNodeName());
		
		task.setValidationMethod(parser.parseNode(node));
	}
	
	private TaskValidationMethodNodeParser getTaskValidationMethodNodeParserFromNodeName(String nodeName) throws Exception {
		List<Class<?>> taskValidationMethodNodeParserClasses = 
			ReflectionHelper.getSubclasses(TaskValidationMethodNodeParser.class, TaskValidationMethodNodeParser.class.getPackage());
		
		TaskValidationMethodNodeParser parser = null;
		
		if (taskValidationMethodNodeParserClasses != null)
		{
			for (int i = 0; i < taskValidationMethodNodeParserClasses.size(); i++)
			{
				Class<?> taskValidationMethodNodeParserClass = taskValidationMethodNodeParserClasses.get(i);
				
				ValidationMethod validationMethod = 
					taskValidationMethodNodeParserClass.getAnnotation(ValidationMethod.class);
				
				if (validationMethod != null 
						&& validationMethod.nodeName() != null
						&& !validationMethod.nodeName().isEmpty())
				{
					if (validationMethod.nodeName().equals(nodeName)) {
						parser = (TaskValidationMethodNodeParser) taskValidationMethodNodeParserClass.newInstance();
						break;
					}
				}
			}
		}
		
		return parser;
	}
	
	/**
	 * Get the evaluation method of the task node.
	 * @param itemNode Node to be parsed.
	 * @param task Task item in composition.
	 * @param order Order of the item in the file.
	 * @throws Exception When an unexpected error happens.
	 */
	private void getEvaluationMethodForItem(org.w3c.dom.Node itemNode, Task task, int order) throws Exception
	{	
		final String CHILD_NODE_NAME = "evaluationMethod";
		
		org.w3c.dom.Node evaluationMethodNode = XmlHelper.getNodeFromList(CHILD_NODE_NAME, itemNode.getChildNodes());

		XmlHelper.validateIfNodeExists(evaluationMethodNode, CHILD_NODE_NAME, 
				String.format(
					"The %d st \"%s\" node must have a \"%s\" child node.", 
					order, ITEM_NODE, CHILD_NODE_NAME));
		
		XmlHelper.validateIfNodeHasChildren(evaluationMethodNode, 
			String.format(
				"The %d st \"%s\" node must have a \"%s\" child node with at least one evaluation method.",
				order, ITEM_NODE, CHILD_NODE_NAME));

		List<org.w3c.dom.Node> nodes = XmlHelper.getChildNodes(evaluationMethodNode);
		
		org.w3c.dom.Node node = nodes.get(0);
		
		TaskEvaluationMethodNodeParser parser = 
			this.getTaskEvaluationMethodNodeParserFromNodeName(node.getNodeName());
		
		task.setEvaluationMethod(parser.parseNode(node));
	}
	
	private TaskEvaluationMethodNodeParser getTaskEvaluationMethodNodeParserFromNodeName(String nodeName) throws Exception {
		List<Class<?>> taskEvaluationMethodNodeParserClasses = 
			ReflectionHelper.getSubclasses(TaskEvaluationMethodNodeParser.class, TaskEvaluationMethodNodeParser.class.getPackage());
		
		TaskEvaluationMethodNodeParser parser = null;
		
		if (taskEvaluationMethodNodeParserClasses != null)
		{
			for (int i = 0; i < taskEvaluationMethodNodeParserClasses.size(); i++)
			{
				Class<?> taskEvaluationMethodNodeParserClass = taskEvaluationMethodNodeParserClasses.get(i);
				
				EvaluationMethod evaluationMethod = 
					
					taskEvaluationMethodNodeParserClass.getAnnotation(EvaluationMethod.class);
				
				if (evaluationMethod != null 
						&& evaluationMethod.nodeName() != null
						&& !evaluationMethod.nodeName().isEmpty())
				{
					if (evaluationMethod.nodeName().equals(nodeName)) {
						parser = (TaskEvaluationMethodNodeParser) taskEvaluationMethodNodeParserClass.newInstance();
						break;
					}
				}
			}
		}
		
		return parser;
	}
}
