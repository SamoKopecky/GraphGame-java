package com.vutbr.homework.game;

import com.vutbr.homework.files.TXT;
import com.vutbr.homework.planets.*;
import com.vutbr.homework.paths.*;
import com.vutbr.homework.files.XML;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

class Graph {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static String MAP_FILE;
    private static String PATH_FILE;
    private static final Scanner SC = new Scanner(System.in);
    private static int nodesVisited;
    private static String visitedPlanets;
    private static String dir;
    private Player player;
    private List<Planet> listOfNodes;
    private Map<Integer, Path> listOfPaths;
    private Planet currentNode;
    private boolean gameFinished;

    Graph(String dir) {
        this.dir = dir;
        MAP_FILE = "./resources/" + dir + "/current_map.xml";
        PATH_FILE = "./resources/" + dir + "/path_taken.txt";
        nodesVisited = 0;
        visitedPlanets = "";
        player = new Player();
        listOfNodes = new ArrayList<>();
        listOfPaths = new HashMap<>();
        gameFinished = false;
    }

    void generateMap() {
        NodeList nodeList;
        Element element;

        nodeList = XML.getNodeListFromDoc(XML.readFromXML(MAP_FILE), "//planet");

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
                listOfNodes.add(new BasicPlanet(planetName, i, planetDesc, eventDesc,
                        PlanetType.valueOf(planetType.toUpperCase())));
            }
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            NodeList neighbours = element.getElementsByTagName("neighbourID");

            for (int j = 0; j < neighbours.getLength(); j++) {

                int neighbourID = Integer.parseInt(neighbours.item(j).getTextContent());
                int pathID = Integer.parseInt(element.getElementsByTagName("pathID").item(j).getTextContent());
                int pathLength = Integer.parseInt(element.getElementsByTagName("length").item(j).getTextContent());
                String pathDesc = element.getElementsByTagName("pathDescription").item(j).getTextContent();
                String pathType = element.getElementsByTagName("pathType").item(j).getTextContent();

                if (!listOfPaths.keySet().contains(pathID)) {
                    listOfPaths.put(pathID, new Path(pathLength, pathDesc, PathType.valueOf(pathType.toUpperCase()),
                            pathID));
                }
                listOfNodes.get(i).setNeighbour(listOfNodes.get(neighbourID), listOfPaths.get(pathID));
            }
        }

        currentNode = listOfNodes.get(1);
        currentNode.setVisitedEvent(true);
    }

    void nextChoice() {
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
            gameFinished = currentNode.event(player);
            waitForUser();
        }
    }

    private static void waitForUser() {
        System.out.println("Stlač enter aby si pokračoval");
        SC.nextLine();
        SC.nextLine();
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
            String stringToWrite = nodesVisited + ". " + nextPlanet.getName() + "(" + nextPlanet.getId() + ")";
            TXT.writeToFile(stringToWrite, PATH_FILE);
            currentNode.getNeighbours().get(nextPlanet).event(player);
            currentNode = nextPlanet;
        }
    }

    private static void clearConsole() {
        String currentOs = System.getProperty("os.name").toLowerCase();

        if (currentOs.equals("linux")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else if (currentOs.contains("windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                System.out.println("can't clean console");
            }
        } else {
            System.out.println("unknown OS");
        }
    }

    Player getPlayer() {
        return player;
    }

    String getVisitedPlanets() {
        return visitedPlanets;
    }

    boolean isGameFinished() {
        return gameFinished;
    }

    static void printInto() {
        clearConsole();
        System.out.print("Bol si teleportovany to druhe vesmiru. Tvojou ulohou v tejto hre je sa dostat na " +
                "poslednu\nplanetu z nazvom Azeroth. Na tejto planete sa nachadza portal do vesmiru z ktoreho si " +
                "prisiel. Ale\nna to aby presiel portal potrebujes 2 kluce ktore najdes na dvoch roznych planetach. " +
                "Po najdeni\ntychto klucov mozes prest portalom a prejdes hru. Ale po ceste sa budu vyskytovat " +
                "nebezpecenstva\nktore ti mozu poskodit trup lode. Ak tvoj sila tvojho trupu lode dosiahne 0% " +
                "vybuchnes, ak ti dojde\npalivo nebudes moct letiet na ine planety a ostanes navzdy lietat vo " +
                "vesmire bez sance zahrany. Na\nplnete zem sa nachadza obchod kde si mozes doplnit zasoby paliva(max" +
                " 5000 jednotiek). Po ceste\nvesmirom tiez budes ziskavat mineraly ktore mozes predat u obchodnika " +
                "ktory sa nachadza na marse. Tu\nsi mozes aj opravit svoju lod.\n\n");
        SC.nextLine();
        clearConsole();
    }
}
