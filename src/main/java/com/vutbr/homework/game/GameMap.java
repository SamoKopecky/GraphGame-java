package com.vutbr.homework.game;

import com.vutbr.homework.nodes.*;
import com.vutbr.homework.parser.XMLManipulations;
import com.vutbr.homework.paths.Path;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final String MAP_FILE = "./XMLs/current_map.xml";
    private static final Scanner SC = new Scanner(System.in);
    private Player player = new Player();
    private List<Planet> listOfPlanets = new ArrayList<>();
    private Planet currentPlanet;
    private boolean gameEnd = false;

    void generateMap() throws Exception {
        NodeList mapList;
        Element mapElement;

        mapList = XMLManipulations.getNodeListFromDoc(XMLManipulations.readFromXML(MAP_FILE), "//planet");

        for (int i = 0; i < mapList.getLength(); i++) {
            mapElement = (Element) mapList.item(i);
            String planetType = mapElement.getElementsByTagName("type").item(0).getTextContent();
            String planetName = mapElement.getElementsByTagName("name").item(0).getTextContent();
            String planetDesc = mapElement.getElementsByTagName("planetDescription").item(0).getTextContent();
            String eventDesc = mapElement.getElementsByTagName("eventDescription").item(0).getTextContent();

            if ("end".equals(planetType)) {
                listOfPlanets.add(new EndPlanet(planetName, i, planetDesc, eventDesc));
            } else if ("key".equals(planetType)) {
                listOfPlanets.add(new KeyPlanet(planetName, i, planetDesc, eventDesc));
            } else if ("fuelStation".equals(planetType)) {
                listOfPlanets.add(new FuelStationPlanet(planetName, i, planetDesc, eventDesc));
            } else if ("repairStation".equals(planetType)) {
                listOfPlanets.add(new RepairStationPlanet(planetName, i, planetDesc, eventDesc));
            } else {
                listOfPlanets.add(new BasicPlanet(planetName, i, planetDesc, eventDesc, PlanetTypes.valueOf(planetType.toUpperCase())));
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

        currentPlanet = listOfPlanets.get(0);
        currentPlanet.setVisitedEvent(true);
    }

    void chooseWhatToDo() {
        List<Character> options = new ArrayList<>();
        char toDoNext;
        String toPrint;
        boolean choseRight;
        options.add('A');

        clearConsole();
        player.printStatus();
        toPrint = "Nachádzaš sa na planéte : " + currentPlanet.getName() + "(" + currentPlanet.getId() + ")";

        if (!currentPlanet.isVisitedEvent()) {
            options.add('B');
            toPrint = toPrint.concat("\n" + currentPlanet.getPlanetDesc() + "\n"
                    + "Čo spravíš ďalej ?\nA: odleť na daľšiu planétu" + "\nB: pristáň na planétu");
        } else {
            toPrint = toPrint.concat("\nČo spravíš ďalej ?\nA: odleť na daľšiu planétu");
        }
        toPrint = toPrint.concat("\nTvoje volba je :");
        System.out.println(toPrint);
        do {
            toDoNext = Character.toUpperCase(SC.next().charAt(0));
            choseRight = options.contains(toDoNext);
            if (!choseRight) {
                System.out.println("Zvol si pismeno z listu planet na obrazovke !");
            }
        } while (!choseRight);


        if (toDoNext == 'A') {
            this.getNextNode();
        } else {
            clearConsole();
            gameEnd = currentPlanet.event(player);
            if (gameEnd = player.getFuel() <= 0) {
                System.out.println("prehral si lebo ti doslo palivo");
                return;
            } else if (gameEnd = player.getHull() <= 0) {
                System.out.println("prehral si lebo sa ti znicila lod");
                return;
            }
            System.out.println("Stlač enter aby si pokračoval");
            SC.nextLine();
            SC.nextLine();
        }
    }

    private void getNextNode() {
        char nextNode;
        int i = 0;
        boolean choseRight;
        Map<Character, Planet> choice = new HashMap<>();

        clearConsole();
        player.printStatus();
        System.out.println("Mozes letiet na :");
        for (Map.Entry<Planet, Path> entry : currentPlanet.getNeighbours().entrySet()) {
            choice.put(ALPHABET[i], entry.getKey());
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getName() + "(" + entry.getKey().getId() +
                    ") ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
        System.out.println("X: nechcem este odist z tejto planety\nTvoje volba je :");
        do {
            nextNode = Character.toUpperCase(SC.next().charAt(0));
            choseRight = choice.keySet().contains(nextNode) || nextNode == 'X';
            if (!choseRight) {
                System.out.println("Zvol si pismeno z listu planet na obrazovke !");
            }
        } while (!choseRight);
        if (nextNode != 'X') {
            currentPlanet = choice.get(nextNode);
        }
    }

    private void clearConsole() {
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

    boolean isGameFinished() {
        return gameEnd;
    }
}
