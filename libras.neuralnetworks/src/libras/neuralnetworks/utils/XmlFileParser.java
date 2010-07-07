/**
 * Package with utility classes to use with the neural networks.
 */
package libras.neuralnetworks.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides common functionalities to parse a Xml file.
 * @author Daniel Baptista Dias
 */
public class XmlFileParser
{
	/**
	 * Get a node with a specified name from a node list.
	 * @param nodeName Node to be found.
	 * @param list List where the node may be found.
	 * @return The node, or null (if the node wasn't found)
	 */
	protected org.w3c.dom.Node getNodeFromList(String nodeName, org.w3c.dom.NodeList list)
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
	protected String getAttributeValueFromNode(String attributeName, org.w3c.dom.Node node)
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
	protected List<org.w3c.dom.Node> getChildNodes(org.w3c.dom.Node parent)
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
}
