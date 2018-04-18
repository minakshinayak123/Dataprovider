package utilities;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XMLParser - The class contains methods to access data from an XML document
 */
public class XMLParser {

	InputStream inputStream;
	Document doc;
	String output;
	String inputFile;
	boolean saveTranslations = false;

	public XMLParser(String inputFile) {
		this.inputStream = getClass().getResourceAsStream(inputFile);
		this.inputFile = new File("src" + File.separator + inputFile).getAbsolutePath();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputStream);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {}
	}

	public Document getDoc() {
		return this.doc;
	}

	public Element getXMLNode(String... nodePath) throws Exception {
		Node n = doc.getElementsByTagName("data").item(0);
		Element e = (Element) n;
		for (String nodeName : nodePath) {
			n = e.getElementsByTagName(nodeName).item(0);
			e = (Element) n;
		}
		if (e.getTextContent().equals("")) {
			throw new Exception();
		}
		return e;
	}

	public String getXMLData(String... nodePath) {
		try {
			Element e = getXMLNode(nodePath);
			String retVal = e.getTextContent();
			if (saveTranslations && output != null) {
				e.setTextContent(output);
				output = null;
			}
			return retVal;
		} catch (Exception e2) {
			return null;
		}
	}

	public List<String> getSimilarXMLData(String... nodePath)
	{
		ArrayList<String> nodeValueList = new ArrayList<String>();
		int nodePathLength = nodePath.length;
		try
		{
			NodeList nodes = doc.getElementsByTagName(nodePath[0]);
			for(int i=0;i<nodes.getLength();i++)
			{
				Node n = nodes.item(i);
				Element e = (Element)n.getChildNodes();
				String nodeVal = e.getElementsByTagName(nodePath[nodePathLength-1]).item(0).getTextContent();
				nodeValueList.add(nodeVal);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return nodeValueList;
	}
}