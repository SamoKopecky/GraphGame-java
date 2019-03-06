package com.vutbr.homework.game;

import com.vutbr.homework.nodes.Planet;
import com.vutbr.homework.paths.Path;

import java.util.*;

class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private Player player;
    private ArrayList<Planet> listOfPlanets = new ArrayList<>();
    private Planet currentPlanet;

    void generateMap() {
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                this.listOfPlanets.add(new Planet("Start", i));
            } else {
                this.listOfPlanets.add(new Planet("" + i + 50, i));
            }
        }

        listOfPlanets.get(0).setNeighbour(listOfPlanets.get(1), new Path());
        listOfPlanets.get(0).setNeighbour(listOfPlanets.get(5), new Path());
        listOfPlanets.get(1).setNeighbour(listOfPlanets.get(3), new Path());
        listOfPlanets.get(3).setNeighbour(listOfPlanets.get(4), new Path());
        listOfPlanets.get(4).setNeighbour(listOfPlanets.get(2), new Path());
        listOfPlanets.get(2).setNeighbour(listOfPlanets.get(0), new Path());

        this.currentPlanet = listOfPlanets.get(0);
        //Document document = XMLParser.XMLParse("./files/map.xml");
        //List<Node> nodes = document.selectNodes("/Map/planet");
        //System.out.println(nodes.get(0).selectNodes("name"));
    }

    void printNeighbours() {
        int i = 0;
        System.out.println("Mozes letiet na planety : ");
        for (Map.Entry<Planet, Path> entry : this.currentPlanet.getNeighbours().entrySet()) {
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getId() +
                    " ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
    }

    void getNextNode() {
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
