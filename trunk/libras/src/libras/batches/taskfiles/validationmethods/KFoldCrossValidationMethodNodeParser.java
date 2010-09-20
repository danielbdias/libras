package libras.batches.taskfiles.validationmethods;

import org.w3c.dom.Node;

import libras.batches.taskfiles.annotations.ValidationMethod;
import libras.batches.taskfiles.models.TaskValidationMethod;
import libras.batches.taskfiles.validationmethods.models.KFoldCrossValidationMethod;
import libras.utils.XmlHelper;

@ValidationMethod(nodeName="kFoldCrossValidation")
public class KFoldCrossValidationMethodNodeParser extends TaskValidationMethodNodeParser {

	private final static String NODE_NAME = "kFoldCrossValidation";
	
	@Override
	public TaskValidationMethod parseNode(Node node) throws Exception {
		KFoldCrossValidationMethod method = new KFoldCrossValidationMethod();
		
		method.setFoldSize(this.getFoldSize(node));
		
		method.setK(this.getK(node));
		
		return method;
	}

	private int getK(Node node) throws Exception {
		final String ATTRIBUTE_NAME = "K";
		
		String kAsString = XmlHelper.getAttributeValueFromNode(ATTRIBUTE_NAME, node);
		
		XmlHelper.validateIfAttributeExists(node, ATTRIBUTE_NAME, 
			String.format(
				"The \"%s\" attribute of the %s must exists and cannot be empty.", 
				ATTRIBUTE_NAME, NODE_NAME));
		
		if (!kAsString.matches("^[0-9]+$"))
			throw new Exception(
				String.format(
					"The \"%s\" attribute of the %s must be numeric.", 
					ATTRIBUTE_NAME, NODE_NAME));
		
		int k = Integer.parseInt(kAsString);
		
		if (k <= 0)
			throw new Exception(
				String.format(
					"The \"%s\" attribute of the %s must be a positive number.", 
					ATTRIBUTE_NAME, NODE_NAME));
		
		return k;
	}

	private int getFoldSize(Node node) throws Exception {
		final String ATTRIBUTE_NAME = "foldSize";
		
		String foldSizeAsString = XmlHelper.getAttributeValueFromNode(ATTRIBUTE_NAME, node);
		
		XmlHelper.validateIfAttributeExists(node, ATTRIBUTE_NAME, 
			String.format(
				"The \"%s\" attribute of the %s must exists and cannot be empty.", 
				ATTRIBUTE_NAME, NODE_NAME));
		
		if (!foldSizeAsString.matches("^[0-9]+$"))
			throw new Exception(
				String.format(
					"The \"%s\" attribute of the %s must be numeric.", 
					ATTRIBUTE_NAME, NODE_NAME));
		
		int foldSize = Integer.parseInt(foldSizeAsString);
		
		if (foldSize <= 0)
			throw new Exception(
				String.format(
					"The \"%s\" attribute of the %s must be a positive number.", 
					ATTRIBUTE_NAME, NODE_NAME));
		
		return foldSize;
	}

}
