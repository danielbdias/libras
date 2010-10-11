package libras.batches.outputfiles;

import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import libras.batches.outputfiles.models.OutputData;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OutputDataFileManager {
	
	public static OutputData parseFile(File outputFile) throws Exception {

		OutputData outputData = new OutputData();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true); 
		DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
		Document doc = builder.parse(outputFile);
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		
		XPathExpression nameExpression = xpath.compile("//outputFile//batchName");
		XPathExpression hitTaxExpression = xpath.compile("//outputFile//hitTax");
		XPathExpression expectedLabelExpression = xpath.compile("//outputFile//tests/test/expectedLabel");
		XPathExpression computedLabelExpression = xpath.compile("//outputFile//tests/test/expectedLabel");
		
		Node nameNode = (Node) nameExpression.evaluate(doc, XPathConstants.NODE);
		Node hitTaxNode = (Node) hitTaxExpression.evaluate(doc, XPathConstants.NODE);
		NodeList expectedLabelNodeList = (NodeList) expectedLabelExpression.evaluate(doc, XPathConstants.NODESET);
		NodeList computedLabelNodeList = (NodeList) computedLabelExpression.evaluate(doc, XPathConstants.NODESET);
		
		outputData.setTaskName(nameNode.getTextContent());
		
		String hitTaxAsString = hitTaxNode.getTextContent();
		outputData.setHitTax(Double.parseDouble(hitTaxAsString));
		
		for (int i = 0; i < expectedLabelNodeList.getLength(); i++) {
			Node expectedLabelNode = expectedLabelNodeList.item(i);
			outputData.getExpectedLabels().add(expectedLabelNode.getTextContent());
		}
		
		for (int i = 0; i < computedLabelNodeList.getLength(); i++) {
			Node computedLabelNode = computedLabelNodeList.item(i);
			outputData.getComputedLabels().add(computedLabelNode.getTextContent());
		}
		
		return outputData;
	}

	public static void createFile(OutputData data, File outputFile) throws Exception {
		File directory = outputFile.getParentFile();
		if (!directory.exists()) directory.mkdirs();
		
		FileWriter writer = new FileWriter(outputFile);
		
		writer.write("<outputFile>\r\n");
		
		writer.write(String.format("\t<batchName>%s</batchName>\r\n", data.getTaskName()));
		writer.write(String.format("\t<hitTax>%f</hitTax>\r\n", data.getHitTax()));
		
		writer.write("\t<tests size=\"" + data.getComputedLabels().size() + "\">\r\n");
		
		for (int i = 0; i < data.getComputedLabels().size(); i++)
		{
			writer.write("\t\t<test>\r\n");
			writer.write("\t\t\t<expectedLabel>" + data.getExpectedLabels().get(i) + "</expectedLabel>\r\n");
			writer.write("\t\t\t<computedLabel>" + data.getComputedLabels().get(i) + "</computedLabel>\r\n");
			writer.write("\t\t</test>\r\n");
		}
		
		writer.write("\t</tests>\r\n");
		
		writer.write("</outputFile>\r\n");
		
		writer.close();
	}
}
