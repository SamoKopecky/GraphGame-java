package com.vutbr.homework.game;

import com.vutbr.homework.io.TXT;
import com.vutbr.homework.planets.*;
import com.vutbr.homework.paths.*;
import com.vutbr.homework.io.XML;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

class Graph {
    private final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private final String MAP_FILE;
    private final String PATH_FILE;
    private final Scanner sc = new Scanner(System.in);
    private int nodesVisited;
    private Player player;
    private Planet currentNode;
    private boolean gameFinished;

    Graph(String dir) {
        MAP_FILE = "./resources/" + dir + "/current_map.xml";
        PATH_FILE = "./resources/" + dir + "/path_taken.txt";
        nodesVisited = 0;
        player = new Player();
        gameFinished = false;
    }

    void generateMap() {
        XML xml = new XML();
        List<Planet> listOfNodes = new ArrayList<>();
        Map<Integer, Path> listOfPaths = new HashMap<>();
        NodeList nodeList;
        Element element;

        nodeList = xml.getNodeListFromDoc(xml.readFromXML(MAP_FILE), "//planet");

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

        currentNode = listOfNodes.get(0);
        currentNode.setVisitedEvent(true);
    }

    void nextChoice() {
        List<Character> options = new ArrayList<>();
        char toDoNext;
        boolean choseRight;
        StringBuilder sb = new StringBuilder();

        options.add('A');

        clearConsole();
        System.out.println(player);
        sb.append("Nachádzaš sa na planéte : " + currentNode.getName() + "(" + currentNode.getId() + ")");
        if (currentNode.isEventNotVisited()) {
            options.add('B');
            sb.append("\n" + currentNode.getPlanetDesc() + "\n"
                    + "Čo spravíš ďalej ?\nA: odleť na daľšiu planétu" + "\nB: pristáň na planétu");
        } else {
            sb.append("\nČo spravíš ďalej ?\nA: odleť na daľšiu planétu");
        }
        sb.append("\nTvoje volba je :");
        System.out.println(sb.toString());
        do {
            toDoNext = Character.toUpperCase(sc.next().charAt(0));
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

    private void getNextNode() {
        char nextNode;
        int i = 0;
        boolean choseRight;
        Map<Character, Planet> choice = new HashMap<>();

        clearConsole();
        System.out.println(player);
        System.out.println("Mozes letiet na :");
        for (Map.Entry<Planet, Path> entry : currentNode.getNeighbours().entrySet()) {
            choice.put(ALPHABET[i], entry.getKey());
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getName() + "(" + entry.getKey().getId() +
                    ") ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.\n" + entry.getValue().getPathDesc());
            i++;
        }
        System.out.println("X: nechcem este odist z tejto planety\nTvoje volba je :");

        do {
            nextNode = Character.toUpperCase(sc.next().charAt(0));
            choseRight = choice.keySet().contains(nextNode) || nextNode == 'X';
            if (!choseRight) {
                System.out.println("Zvol si pismeno z listu planet na obrazovke !");
            }
        } while (!choseRight);

        if (nextNode != 'X') {
            moveToNextPlanet(choice, nextNode);
        }
    }

    private void moveToNextPlanet(Map<Character, Planet> choice, char nextNode) {
        clearConsole();
        TXT txt = new TXT();
        nodesVisited++;
        Planet nextPlanet = choice.get(nextNode);
        String stringToWrite = nodesVisited + ". " + nextPlanet.getName() + "(" + nextPlanet.getId() + ")";
        txt.writeToFile(stringToWrite, PATH_FILE);
        currentNode.getNeighbours().get(nextPlanet).event(player);
        currentNode = nextPlanet;
    }

    void clearConsole() {
        String currentOs = System.getProperty("os.name").toLowerCase();

        if (currentOs.contains("linux")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else if (currentOs.contains("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println("can't clean console");
            }
        } else {
            System.out.println("unknown OS");
        }
    }

    private void waitForUser() {
        System.out.println("Stlač enter aby si pokračoval");
        sc.nextLine();
        sc.nextLine();
    }

    String getIntro() {
        clearConsole();
        return "Bol si teleportovany to druhe vesmiru. Tvojou ulohou v tejto hre je sa dostat na " +
                "poslednu\nplanetu z nazvom Azeroth. Na tejto planete sa nachadza portal do vesmiru z ktoreho si " +
                "prisiel. Ale\nna to aby presiel portal potrebujes 2 kluce ktore najdes na dvoch roznych planetach. " +
                "Po najdeni\ntychto klucov mozes prest portalom a prejdes hru. Ale po ceste sa budu vyskytovat " +
                "nebezpecenstva\nktore ti mozu poskodit trup lode. Ak tvoj sila tvojho trupu lode dosiahne 0% " +
                "vybuchnes, ak ti dojde\npalivo nebudes moct letiet na ine planety a ostanes navzdy lietat vo " +
                "vesmire bez sance zahrany. Na\nplnete zem sa nachadza obchod kde si mozes doplnit zasoby paliva(max" +
                " 5000 jednotiek). Po ceste\nvesmirom tiez budes ziskavat mineraly ktore mozes predat u obchodnika " +
                "ktory sa nachadza na marse. Tu\nsi mozes aj opravit svoju lod.\n\n";
    }

    Player getPlayer() {
        return player;
    }

    boolean isGameFinished() {
        return gameFinished;
    }

}
