package com.vutbr.homework.game;

import com.vutbr.homework.nodes.Planet;
import com.vutbr.homework.parser.XMLParser;
import com.vutbr.homework.paths.Path;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;

public class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final String XML_PATH = "./src/main/java/com/vutbr/homework/files/map.xml";
    private Player player;
    private ArrayList<Planet> listOfPlanets = new ArrayList<>();
    private Planet currentPlanet;

    public void generateMap() throws Exception {
        NodeList nodeList;
        Element element;

        nodeList = XMLParser.XMLParse(XML_PATH, "//planet");
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            listOfPlanets.add(new Planet(element.getElementsByTagName("name").item(0).getTextContent(), i));
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            NodeList neighbours = element.getElementsByTagName("neighbourId");
            for (int j = 0; j < neighbours.getLength(); j++) {
                int neighbourID = Integer.parseInt(neighbours.item(j).getTextContent());
                listOfPlanets.get(i).setNeighbour(listOfPlanets.get(neighbourID), new Path());
            }
        }

        this.currentPlanet = listOfPlanets.get(0);
    }

    public void printNeighbours() {
        int i = 0;
        System.out.println("Mozes letiet na planety : ");
        for (Map.Entry<Planet, Path> entry : this.currentPlanet.getNeighbours().entrySet()) {
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getId() +
                    " ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
    }

    public void getNextNode() {
        Scanner sc = new Scanner(System.in);
        char nextNode;
        int i = 0;
        Map<Character, Planet> choice = new HashMap<>();

        for (Planet planet : this.currentPlanet.getNeighbours().keySet()) {
            choice.put(ALPHABET[i], planet);
            i++;
        }

        System.out.println("Tvoje volba je :");
        nextNode = Character.toUpperCase(sc.next().charAt(0));

        this.currentPlanet = choice.get(nextNode);
    }


}
