/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itemsdb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.Attribute;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author buczak
 */
public class ItemAdder {
    
    public void addItem(ArrayList<ArrayList<String>> items, ArrayList<String> newItem) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException
    {
        items.add(newItem);
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("database.txt");
        Element root = document.getDocumentElement();

        Element itemToAdd = document.createElement("item");

        //Attribute name = document.setAttribute("name");
        //name.appendChild(document.createTextNode(items.get(items.size() - 1).get(0)));
        itemToAdd.setAttribute("name", items.get(items.size() - 1).get(0));

        Element price = document.createElement("price");
        price.appendChild(document.createTextNode(items.get(items.size() - 1).get(1)));
        itemToAdd.appendChild(price);
        
        Element size = document.createElement("size");
        size.appendChild(document.createTextNode(items.get(items.size() - 1).get(2)));
        itemToAdd.appendChild(size);
        
        Element color = document.createElement("color");
        color.appendChild(document.createTextNode(items.get(items.size() - 1).get(3)));
        itemToAdd.appendChild(color);

        root.appendChild(itemToAdd);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("database.txt");
        transformer.transform(source, result);
    }
}
