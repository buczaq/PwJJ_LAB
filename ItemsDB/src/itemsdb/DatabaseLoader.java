/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itemsdb;

import java.io.File;
import java.util.ArrayList;
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
 * @author buczak
 */
public class DatabaseLoader {
    
    public ArrayList<ArrayList<String>> loadDatabase()
    {
        ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
        
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            File db = new File("database.txt");
            Document doc = dBuilder.parse(db);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("item");

             for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) node;
                   String n = element.getAttribute("name");
                   String p = element
                                  .getElementsByTagName("price")
                                  .item(0)
                                  .getTextContent();
                   String s = element
                                  .getElementsByTagName("size")
                                  .item(0)
                                  .getTextContent();
                   String c = element
                                  .getElementsByTagName("color")
                                  .item(0)
                                  .getTextContent();
                   ArrayList<String> item = new ArrayList<String>();
                   item.add(n);
                   item.add(p);
                   item.add(s);
                   item.add(c);
                   items.add(item);
                }
             }
            } catch (Exception e) {
                    e.printStackTrace();
            }

            return items;
    }
    
}
