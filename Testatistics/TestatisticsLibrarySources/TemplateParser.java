package utility;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
 * Klasa odpowiedzialna za parsowanie pliku z szablonem poprawnych odpowiedzi (xml)
 */
public class TemplateParser {
	
    /**
     * 
     * Zwraca mapę z szablonem poprawnych odpowiedzi
     * @param templateFile  plik z szablonem
     * @return              mapa poprawnych odpowiedzi
     */
	public TreeMap<String, String> generateAnswerPattern(File templateFile)
	{
		TreeMap<String, String> answerPattern = new TreeMap<>();
		
		try {
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(templateFile);
	        doc.getDocumentElement().normalize();
	        NodeList nodeList = doc.getElementsByTagName("question");
	         
	         for (int i = 0; i < nodeList.getLength(); i++) {
	            Node node = nodeList.item(i);
	            
	            if (node.getNodeType() == Node.ELEMENT_NODE) {
	               Element element = (Element) node;
	               String n = element.getAttribute("number");
	               String a = element
	            		      .getElementsByTagName("rightAnswer")
	            		      .item(0)
	            		      .getTextContent();
	               answerPattern.put(n, a);
	            }
	         }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return answerPattern;
	}

}
