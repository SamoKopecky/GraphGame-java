package com.vutbr.homework.game;

import com.vutbr.homework.nodes.*;
import com.vutbr.homework.parser.XMLParser;
import com.vutbr.homework.paths.Path;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;


class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final String XML_PATH = "./src/main/java/com/vutbr/homework/files/map.xml";
    private Player player = new Player();
    private List<Planet> listOfPlanets = new ArrayList<>();
    private Planet currentPlanet;
    private boolean gameEnd = false;
    private static final Scanner sc = new Scanner(System.in);

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
                    listOfPlanets.add(new BasicPlanet(planetName, i));
                    break;
                case "end":
                    listOfPlanets.add(new EndPlanet(planetName, i));
                    break;
                case "firstKey":
                case "secondKey":
                    listOfPlanets.add(new KeyPlanet(planetName, i));
                    break;
                case "FuelStation":
                    listOfPlanets.add(new FuelStationPlanet(planetName, i));
                    break;
                case "RepairStation":
                    listOfPlanets.add(new RepairStationPlanet(planetName, i));
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

    void chooseWhatToDo() {
        boolean eventVisited = this.currentPlanet.isVisitedEvent();
        char toDoNext;

        System.out.println("Nachadzas sa na planete : " + this.currentPlanet.getName());
        if (!eventVisited) {
            System.out.println("Tu bude pre kazdu planetu daco ine");
        }
        System.out.println("Co spravis dalej ?");
        System.out.println("A: odlet na dalsiu planetu");
        if (!eventVisited) {
            System.out.println("B: pristan na planetu");
            toDoNext = Character.toUpperCase(sc.next().charAt(0));
            while (toDoNext != 'A' && toDoNext != 'B') {
                System.out.println("Zvol si pismeno z listu na obrazovke");
                toDoNext = Character.toUpperCase(sc.next().charAt(0));
            }
        } else {
            toDoNext = Character.toUpperCase(sc.next().charAt(0));
            while (toDoNext != 'A') {
                System.out.println("Zvol si pismeno z listu na obrazovke");
                toDoNext = Character.toUpperCase(sc.next().charAt(0));
            }
        }

        if (toDoNext == 'A') {
            this.getNextNode();
        } else {
            this.gameEnd = this.currentPlanet.event(this.player);
        }
    }

    private void getNextNode() {
        char nextNode;
        int i = 0;
        Map<Character, Planet> choice = new HashMap<>();
        boolean isCharInMap = false;

        for (Map.Entry<Planet, Path> entry : this.currentPlanet.getNeighbours().entrySet()) {
            choice.put(ALPHABET[i], entry.getKey());
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getName() + "(" + entry.getKey().getId() +
                    ") ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
        System.out.println("\nX: nechcem este odist z tejto planety");

        System.out.println("Tvoje volba je :");
        nextNode = Character.toUpperCase(sc.next().charAt(0));
        while (!choice.keySet().contains(nextNode) && nextNode != 'X') {
            System.out.println("Zvol si pismeno z listu planet na obrazovke !");
            nextNode = Character.toUpperCase(sc.next().charAt(0));
        }
        if (nextNode != 'X') {
            this.currentPlanet = choice.get(nextNode);
        }
    }

    boolean isGameFinished() {
        return this.gameEnd;
    }
}
