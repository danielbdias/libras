/**
 * Package with utility classes to use with the neural networks.
 */
package libras.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides common functionalities to parse a Xml file.
 * @author Daniel Baptista Dias
 */
public class XmlHelper
{
	/**
	 * Get a node with a specified name from a node list.
	 * @param nodeName Node to be found.
	 * @param list List where the node may be found.
	 * @return The node, or null (if the node wasn't found)
	 */
	public static org.w3c.dom.Node getNodeFromList(String nodeName, org.w3c.dom.NodeList list)
	{
		for (int i = 0; i < list.getLength(); i++)
		{
			org.w3c.dom.Node node = list.item(i);
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
			{
				String temp = node.getNodeName();
				if (temp != null && temp.equals(nodeName)) return node;
			}
		}
		return null;
	}
	
	/**
	 * Get the attribute data from a node.
	 * @param attributeName Attribute name.
	 * @param node Node where the attribute may be found.
	 * @return The attribute data, or null (if the attribute wasn't found)
	 */
	public static String getAttributeValueFromNode(String attributeName, org.w3c.dom.Node node)
	{
		org.w3c.dom.NamedNodeMap attributes = node.getAttributes();
		
		if (attributes == null || attributes.getLength() <= 0) return null;
	
		org.w3c.dom.Node tempNode = attributes.getNamedItem(attributeName);
		
		if (tempNode == null) return null;
		
		return tempNode.getNodeValue();
	}

	/**
	 * Get the child nodes from a node.
	 * @param parent Parent node.
	 * @return All child nodes that belongs to parent node.
	 */
	public static List<org.w3c.dom.Node> getChildNodes(org.w3c.dom.Node parent)
	{
		ArrayList<org.w3c.dom.Node> children = new ArrayList<org.w3c.dom.Node>();
		
		org.w3c.dom.NodeList list = parent.getChildNodes();
		
		for (int i = 0; i < list.getLength(); i++)
		{
			org.w3c.dom.Node node = list.item(i);
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) children.add(node);
		}
		
		return children;
	}

	public static void validateIfNodeHasChildren(org.w3c.dom.Node node, String validationMessage) throws Exception {
		List<org.w3c.dom.Node> childNodes = XmlHelper.getChildNodes(node);
		
		if (childNodes == null || childNodes.isEmpty())
			throw new Exception(validationMessage);
	}
	
	public static void validateIfNodeHasContent(org.w3c.dom.Node node, String validationMessage) throws Exception {
		String content = node.getTextContent();
		
		if (content == null || content.isEmpty())
			throw new Exception(validationMessage);
	}
	
	public static void validateIfNodeExists(org.w3c.dom.Node node, String nodeName, String validationMessage) throws Exception {
		if (node == null || !node.getNodeName().equals(nodeName))
			throw new Exception(validationMessage);
	}
	
	public static void validateIfAttributeExists(org.w3c.dom.Node node, String attributeName, String validationMessage) throws Exception {
		String attributeValue = XmlHelper.getAttributeValueFromNode(attributeName, node);
		
		if (attributeValue == null || attributeValue.isEmpty())
			throw new Exception(validationMessage);
	}
}
