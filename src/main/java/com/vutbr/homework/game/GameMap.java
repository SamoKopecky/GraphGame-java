package com.vutbr.homework.game;

import com.vutbr.homework.nodes.*;
import com.vutbr.homework.parser.XMLParser;
import com.vutbr.homework.paths.Path;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final String XML_PATH = "./src/main/java/com/vutbr/homework/files/map.xml";
    private Player player;
    private ArrayList<Planet> listOfPlanets = new ArrayList<>();
    private Planet currentPlanet;
    private boolean gameEnd = false;

    void callEventOfCurrentPlanet() {
        System.out.println("Nachadzas sa na planete : " + this.currentPlanet.getName());
        this.gameEnd = this.currentPlanet.event(this.player);
    }

    void generateMap() throws Exception {
        NodeList nodeList;
        Element element;

        nodeList = XMLParser.XMLParse(XML_PATH, "//planet");
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            String planetType = element.getElementsByTagName("type").item(0).getTextContent();
            String planetName = element.getElementsByTagName("name").item(0).getTextContent();
            switch (planetType) {
                case "none":
                    listOfPlanets.add(new OrdinaryPlanet(planetName, i));
                    break;
                case "end":
                    listOfPlanets.add(new EndPlanet(planetName, i));
                    break;
                case "firstKey":
                case "secondKey":
                    listOfPlanets.add(new KeyPlanet(planetName, i));
                    break;
            }
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            NodeList neighbours = element.getElementsByTagName("neighbourID");
            for (int j = 0; j < neighbours.getLength(); j++) {
                int neighbourID = Integer.parseInt(neighbours.item(j).getTextContent());
                listOfPlanets.get(i).setNeighbour(listOfPlanets.get(neighbourID), new Path());
            }
        }

        this.currentPlanet = listOfPlanets.get(0);
    }

    void printNeighbours() {
        int i = 0;
        System.out.println("Mozes letiet na planety : ");
        for (Map.Entry<Planet, Path> entry : this.currentPlanet.getNeighbours().entrySet()) {
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getName() + "(" + entry.getKey().getId() +
                    ") ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
    }

    void getNextNode() {
        Scanner sc = new Scanner(System.in);
        char nextNode;
        int i = 0;
        Map<Character, Planet> choice = new HashMap<>();
        boolean isCharInMap = false;

        for (Planet planet : this.currentPlanet.getNeighbours().keySet()) {
            choice.put(ALPHABET[i], planet);
            i++;
        }

        System.out.println("Tvoje volba je :");
        do {
            nextNode = Character.toUpperCase(sc.next().charAt(0));
            if (choice.keySet().contains(nextNode)) {
                isCharInMap = true;
            } else {
                System.out.println("Zvol si pismeno z listu planet na obrazovke !");
            }
        } while (!isCharInMap);

        this.currentPlanet = choice.get(nextNode);
    }

    public boolean isGameFinished() {
        return this.gameEnd;
    }
}
