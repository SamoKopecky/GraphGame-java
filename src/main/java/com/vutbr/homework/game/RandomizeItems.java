package com.vutbr.homework.game;

import com.vutbr.homework.parser.XMLManipulations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Random;

class RandomizeItems {
    static void randomizeItems() throws Exception {
        Random random = new Random();
        int randomNumber;
        Element element;
        Document document = XMLManipulations.readFromXML("./XMLs/blank_map.xml");
        NodeList map = XMLManipulations.getNodeListFromDoc(document, "//planet");
        NodeList info = XMLManipulations.getNodeListFromDoc(XMLManipulations.readFromXML("./XMLs/planets_info.xml"),
                "//planetType");

        for (int i = 0; i < map.getLength(); i++) {
            element = (Element) map.item(i);
            Element type = (Element) element.getElementsByTagName("type").item(0);
            Element planetDesc = (Element) element.getElementsByTagName("planetDescription").item(0);
            Element eventDesc = (Element) element.getElementsByTagName("eventDescription").item(0);
            if (type.getTextContent().equals("none")) {
                randomNumber = random.nextInt(info.getLength());
                Element infoElement = (Element) info.item(randomNumber);
                String newType = infoElement.getElementsByTagName("name").item(0).getTextContent();
                String newPlanetDesc = infoElement.getElementsByTagName("planetDescription").item(0).getTextContent();
                String newEventDesc = infoElement.getElementsByTagName("eventDescription").item(0).getTextContent();


                type.setTextContent(newType);
                planetDesc.setTextContent(newPlanetDesc);
                eventDesc.setTextContent(newEventDesc);
            }
        }
        XMLManipulations.writeToXML("./XMLs/current_map.xml", document);
    }
}
