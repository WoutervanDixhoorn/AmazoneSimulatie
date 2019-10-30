package com.nhlstenden.amazonsimulatie.graph;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    // Geef aan of het stellage of source node is
    private String name;

    private Node previous;
    private int shortestDistance = Integer.MAX_VALUE;

    private boolean visited;

    private boolean deadend;

    // Waar bevindt de Node zich
    private int x;
    private int z;

    // HOEVEEL BOXEN STAAN ER IN DE STELLAGE(OM DE LEEGSTE STELLAGE TE PAKKEN)
    private int boxen;

    private ArrayList<Node> adjNodes;

    public Node(String name, int x, int z) {
        this.name = name;
        this.x = x;
        this.z = z;
        adjNodes = new ArrayList<>();
        visited = false;
    }

    public void setAdjNodes(Node node) {
        adjNodes.add(node);
    }

    public ArrayList<Node> getAdjNodes() {
        return adjNodes;
    }

    public String getName() {
        return name;
    }


    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public int getShortestDistance() {
        return shortestDistance;
    }

    public void setShortestDistance(int shortestDistance) {
        this.shortestDistance = shortestDistance;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isDeadend() {
        return deadend;
    }

    public void setDeadend(boolean deadend) {
        this.deadend = deadend;
    }

    @Override
    public int compareTo(Node o) {
        if(this.getShortestDistance() > o.getShortestDistance())
            return 1;
        else if (this.getShortestDistance() == o.getShortestDistance())
            return 0;
        return -1;
    }
}
