package com.vutbr.homework.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.FileOutputStream;
import java.io.StringWriter;

public class XMLManipulations {

    public static Document readFromXML(String fileName) throws Exception {

        DocumentBuilder dBuilder;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fileName);
        document.getDocumentElement().normalize();
        return document;
    }

    public static NodeList getNodeListFromDoc(Document document, String nodeName) throws Exception{
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (NodeList) xPath.compile(nodeName).evaluate(document, XPathConstants.NODESET);
    }

    public static void writeToXML(String fileName, Document document) throws Exception {
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
    }
}
