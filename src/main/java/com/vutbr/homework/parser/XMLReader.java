package com.vutbr.homework.parser;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

public class XMLReader {

    public static NodeList XMLParse(String fileName, String nodeName) throws
            ParserConfigurationException,
            IOException,
            SAXException,
            XPathExpressionException {

        DocumentBuilder dBuilder;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fileName);
        document.getDocumentElement().normalize();
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (NodeList) xPath.compile(nodeName).evaluate(document, XPathConstants.NODESET);
    }
}
