package com.vutbr.homework.game;

import com.vutbr.homework.nodes.*;
import com.vutbr.homework.parser.XMLReader;
import com.vutbr.homework.paths.Path;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

public class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final String MAP_FILE = "./map.xml";
    private static final String EVENT_DESC_MAP = "./eventDescriptions.xml";
    private static final Scanner SC = new Scanner(System.in);
    private Player player = new Player();
    private List<Planet> listOfPlanets = new ArrayList<>();
    private Planet currentPlanet;
    private boolean gameEnd = false;

    void generateMap() throws Exception {
        NodeList mapList;
        Element mapElement;

        mapList = XMLReader.XMLParse(MAP_FILE, "//planet");

        for (int i = 0; i < mapList.getLength(); i++) {
            mapElement = (Element) mapList.item(i);
            String planetType = mapElement.getElementsByTagName("type").item(0).getTextContent();
            String planetName = mapElement.getElementsByTagName("name").item(0).getTextContent();
            String planetDesc = "temp";

            if ("none".equals(planetType)) {
                listOfPlanets.add(new BasicPlanet(planetName, i, planetDesc));
            } else if ("end".equals(planetType)) {
                listOfPlanets.add(new EndPlanet(planetName, i, planetDesc));
            } else if ("key".equals(planetType)) {
                listOfPlanets.add(new KeyPlanet(planetName, i, planetDesc));
            } else if ("fuelStation".equals(planetType)) {
                listOfPlanets.add(new FuelStationPlanet(planetName, i, planetDesc));
            } else if ("repairStation".equals(planetType)) {
                listOfPlanets.add(new RepairStationPlanet(planetName, i, planetDesc));
            }
        }

        for (int i = 0; i < mapList.getLength(); i++) {
            mapElement = (Element) mapList.item(i);
            NodeList neighbours = mapElement.getElementsByTagName("neighbourID");
            for (int j = 0; j < neighbours.getLength(); j++) {
                int neighbourID = Integer.parseInt(neighbours.item(j).getTextContent());
                listOfPlanets.get(i).setNeighbour(listOfPlanets.get(neighbourID), new Path());
            }
        }

        this.currentPlanet = listOfPlanets.get(0);
        this.currentPlanet.setVisitedEvent(true);
    }

    void chooseWhatToDo() {
        boolean eventVisited = this.currentPlanet.isVisitedEvent();
        char toDoNext;

        clearConsole();
        player.printStatus();
        System.out.println("Nachádzaš sa na planéte : "
                + this.currentPlanet.getName() + "(" + this.currentPlanet.getId() + ")");
        if (!eventVisited) {
            System.out.println(this.currentPlanet.getPlanetDesc());
        }
        System.out.println("Čo spravíš ďalej ?\nA: odleť na daľšiu planétu");
        if (!eventVisited) {
            System.out.println("B: pristáň na planétu");
            toDoNext = Character.toUpperCase(SC.next().charAt(0));
            while (toDoNext != 'A' && toDoNext != 'B') {
                System.out.println("Zvoľ si písmeno z listu na obrazovke");
                toDoNext = Character.toUpperCase(SC.next().charAt(0));
            }
        } else {
            toDoNext = Character.toUpperCase(SC.next().charAt(0));
            while (toDoNext != 'A') {
                System.out.println("Zvoľ si písmeno z listu na obrazovke");
                toDoNext = Character.toUpperCase(SC.next().charAt(0));
            }
        }

        if (toDoNext == 'A') {
            this.getNextNode();
        } else {
            clearConsole();
            this.gameEnd = this.currentPlanet.event(this.player);
            System.out.println("Stlač enter aby si pokračoval");
            SC.nextLine();
            SC.nextLine();
        }
    }

    private void getNextNode() {
        char nextNode;
        int i = 0;
        Map<Character, Planet> choice = new HashMap<>();

        clearConsole();
        player.printStatus();
        System.out.println("Mozes letiet na :");
        for (Map.Entry<Planet, Path> entry : this.currentPlanet.getNeighbours().entrySet()) {
            choice.put(ALPHABET[i], entry.getKey());
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getName() + "(" + entry.getKey().getId() +
                    ") ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
        System.out.println("X: nechcem este odist z tejto planety\nTvoje volba je :");
        nextNode = Character.toUpperCase(SC.next().charAt(0));
        while (!choice.keySet().contains(nextNode) && nextNode != 'X') {
            System.out.println("Zvol si pismeno z listu planet na obrazovke !");
            nextNode = Character.toUpperCase(SC.next().charAt(0));
        }
        if (nextNode != 'X') {
            this.currentPlanet = choice.get(nextNode);
        }
    }

    boolean isGameFinished() {
        return this.gameEnd;
    }

    public static void clearConsole() {
        String currentOs = System.getProperty("os.name").toLowerCase();
        if (currentOs.equals("linux")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else if (currentOs.equals("windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                System.out.println("can't clean console");
            }
        } else {
            System.out.println("unknown OS");
        }
    }
}
