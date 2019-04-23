package com.vutbr.homework.io;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class XML {

    public Document readFromXML(String fileName) {
        DocumentBuilder dBuilder;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(fileName);
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.getStackTrace();
            System.out.println("can't read XML file");
        }
        return document;
    }

    public NodeList getNodeListFromDoc(Document document, String nodeName) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xPath.compile(nodeName).evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.getStackTrace();
            System.out.println("can't create node list from doc");
        }
        return nodeList;
    }

    public void writeToXML(String fileName, Document document) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            byte[] bytes = xmlString.getBytes();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("writing to io failed");
        }
    }
}
