package com.vutbr.homework.game;

import com.vutbr.homework.planets.*;
import com.vutbr.homework.paths.*;
import com.vutbr.homework.parser.XMLManipulations;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

class Graph {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static final String MAP_FILE = "./resources/current_map.xml";
    private static final Scanner SC = new Scanner(System.in);
    private static int nodesVisited = 0;
    private Player player;
    private List<Planet> listOfNodes;
    private Planet currentNode;
    private boolean gameEnd;

    Graph () {
        player = new Player();
        listOfNodes = new ArrayList<>();
        gameEnd = false;
    }

    void generateMap() throws Exception {
        NodeList nodeList;
        Element element;

        nodeList = XMLManipulations.getNodeListFromDoc(XMLManipulations.readFromXML(MAP_FILE), "//planet");

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            String planetType = element.getElementsByTagName("planetType").item(0).getTextContent();
            String planetName = element.getElementsByTagName("name").item(0).getTextContent();
            String planetDesc = element.getElementsByTagName("planetDescription").item(0).getTextContent();
            String eventDesc = element.getElementsByTagName("eventDescription").item(0).getTextContent();

            if ("end".equals(planetType)) {
                listOfNodes.add(new EndPlanet(planetName, i, planetDesc, eventDesc));
            } else if ("key".equals(planetType)) {
                listOfNodes.add(new KeyPlanet(planetName, i, planetDesc, eventDesc));
            } else if ("fuelStation".equals(planetType)) {
                listOfNodes.add(new FuelStationPlanet(planetName, i, planetDesc, eventDesc));
            } else if ("repairStation".equals(planetType)) {
                listOfNodes.add(new RepairStationPlanet(planetName, i, planetDesc, eventDesc));
            } else if (planetType.isEmpty()) {
                listOfNodes.add(new BasicPlanet(planetName, i, planetDesc, eventDesc, PlanetType.NONE));
            } else {
                listOfNodes.add(new BasicPlanet(planetName, i, planetDesc, eventDesc, PlanetType.valueOf(planetType.toUpperCase())));
            }
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            NodeList neighbours = element.getElementsByTagName("neighbourID");
            for (int j = 0; j < neighbours.getLength(); j++) {
                int neighbourID = Integer.parseInt(neighbours.item(j).getTextContent());
                int pathLength = Integer.parseInt(element.getElementsByTagName("length").item(j).getTextContent());
                String pathDesc = element.getElementsByTagName("pathDescription").item(j).getTextContent();
                String pathType = element.getElementsByTagName("pathType").item(j).getTextContent();

                listOfNodes.get(i).setNeighbour(listOfNodes.get(neighbourID), new Path(pathLength, pathDesc,
                        PathType.valueOf(pathType.toUpperCase())));
            }
        }

        currentNode = listOfNodes.get(0);
        currentNode.setVisitedEvent(true);
    }

    void chooseWhatToDo() {
        List<Character> options = new ArrayList<>();
        char toDoNext;
        boolean choseRight;
        String toPrint;

        options.add('A');

        clearConsole();
        player.printStatus();
        toPrint = "Nachádzaš sa na planéte : " + currentNode.getName() + "(" + currentNode.getId() + ")";
        if (currentNode.isEventNotVisited()) {
            options.add('B');
            toPrint = toPrint.concat("\n" + currentNode.getPlanetDesc() + "\n"
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
            waitForUser();
        } else {
            clearConsole();
            gameEnd = currentNode.event(player);
            if (checkIfPlayerDead()) return;
            waitForUser();
        }
    }

    private void waitForUser() {
        System.out.println("Stlač enter aby si pokračoval");
        SC.nextLine();
        SC.nextLine();
    }

    private boolean checkIfPlayerDead() {
        if (gameEnd = player.getFuel() <= 0) {
            System.out.println("prehral si lebo ti doslo palivo");
            return true;
        } else if (gameEnd = player.getHull() <= 0) {
            System.out.println("prehral si lebo sa ti znicila lod");
            return true;
        }
        return false;
    }

    private void getNextNode() {
        char nextNode;
        int i = 0;
        boolean choseRight;
        Map<Character, Planet> choice = new HashMap<>();

        clearConsole();
        player.printStatus();
        System.out.println("Mozes letiet na :");
        for (Map.Entry<Planet, Path> entry : currentNode.getNeighbours().entrySet()) {
            choice.put(ALPHABET[i], entry.getKey());
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getName() + "(" + entry.getKey().getId() +
                    ") ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.\n\n" + entry.getValue().getPathDesc() + "\n");
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
            nodesVisited++;
            clearConsole();
            Planet nextPlanet = choice.get(nextNode);
            currentNode.getNeighbours().get(nextPlanet).event(player);
            currentNode = nextPlanet;
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