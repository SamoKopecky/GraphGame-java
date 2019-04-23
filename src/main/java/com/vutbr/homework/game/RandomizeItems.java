package com.vutbr.homework.game;

import com.vutbr.homework.io.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Random;

class RandomizeItems {
    void randomizeItems(String dir) {
        XML xml = new XML();
        Random random = new Random();
        int randomNumber;
        Element element;
        Document document = xml.readFromXML("./resources/blank_map.xml");

        NodeList map = xml.getNodeListFromDoc(document, "//planet");
        NodeList planetInfo = xml.getNodeListFromDoc(xml.readFromXML("./resources" +
                "/elements_info.xml"), "//planetType");
        NodeList pathInfo = xml.getNodeListFromDoc(xml.readFromXML("./resources" +
                "/elements_info.xml"), "//pathType");

        for (int i = 0; i < map.getLength(); i++) {
            element = (Element) map.item(i);

            Element planetType = (Element) element.getElementsByTagName("planetType").item(0);
            Element planetDesc = (Element) element.getElementsByTagName("planetDescription").item(0);
            Element eventDesc = (Element) element.getElementsByTagName("eventDescription").item(0);

            if (planetType.getTextContent().equals("none")) {
                randomNumber = random.nextInt(planetInfo.getLength());
                Element planetInfoElement = (Element) planetInfo.item(randomNumber);

                String newPlanetType = planetInfoElement.getElementsByTagName("name").item(0).getTextContent();
                String newPlanetDesc =
                        planetInfoElement.getElementsByTagName("planetDescription").item(0).getTextContent();
                String newEventDesc =
                        planetInfoElement.getElementsByTagName("eventDescription").item(0).getTextContent();

                planetType.setTextContent(newPlanetType);
                planetDesc.setTextContent(newPlanetDesc);
                eventDesc.setTextContent(newEventDesc);
            }
            for (int j = 0; j < element.getElementsByTagName("path").getLength(); j++) {
                Element pathType = (Element) element.getElementsByTagName("pathType").item(j);
                Element pathDesc = (Element) element.getElementsByTagName("pathDescription").item(j);

                randomNumber = random.nextInt(pathInfo.getLength());
                Element pathInfoElement = (Element) pathInfo.item(randomNumber);

                String newPathType = pathInfoElement.getElementsByTagName("name").item(0).getTextContent();
                String newPathDesc = pathInfoElement.getElementsByTagName("pathDescription").item(0).getTextContent();

                pathType.setTextContent(newPathType);
                pathDesc.setTextContent(newPathDesc);
            }

        }
        xml.writeToXML("./resources/" + dir + "/current_map.xml", document);
    }
}
