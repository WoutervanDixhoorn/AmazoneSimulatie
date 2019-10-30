package com.nhlstenden.amazonsimulatie.graph;

import java.util.ArrayList;
import java.util.Collections;

public class DijkstraGraph {
    private ArrayList<Edge> edges;

    private ArrayList<Node> nodes;

    private Node shortestNextNode;

    public DijkstraGraph() {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public void addOneWayConnection(Node from, Node to, int cost) {
        from.setAdjNodes(to);
        to.setAdjNodes(from);

        if(findNode(to.getName()) == null) {
            this.nodes.add(to);
        } else {
            to = findNode(to.getName());
        }


        if(findNode(from.getName()) == null) {
            this.nodes.add(from);
        } else {
            from = findNode(from.getName());
        }

        Edge edge = new Edge(from, to, cost);
        Edge edge2 = new Edge(to, from, cost);
        this.edges.add(edge);
        this.edges.add(edge2);
    }

    private boolean Dijkstra(String source, String to) {
        if(source.equals("end")) {
            return false;
        }

        if(findNode(source) == null) {
            System.out.println("Kan deze node niet vinden");
        }

        Node sourceNode = findNode(source);

        shortestNextNode = null;

        sourceNode.setVisited(true);

        if(sourceNode.getPrevious() == null)
            sourceNode.setShortestDistance(0);

        //ArrayList<Node> adjNodes = sourceNode.getAdjNodes();

        ArrayList<Edge> edgeList;

        //for (Node node : adjNodes) {
        edgeList = findEdge(sourceNode.getName());
        //}

        for (Edge edge : edgeList) {
            if (sourceNode.getShortestDistance() + edge.getCost() < edge.getTo().getShortestDistance()) {
                edge.getTo().setShortestDistance(sourceNode.getShortestDistance() + edge.getCost());

            }
            edge.getTo().setPrevious(sourceNode);
        }

        int prevDist = Integer.MAX_VALUE;
        for(Edge edge : edgeList) {
            if(edge.getTo().getShortestDistance() < prevDist && !edge.getTo().isVisited()) {
                shortestNextNode = edge.getTo();
            }
            prevDist = edge.getTo().getShortestDistance();
        }

        if(shortestNextNode == null) {
            return Dijkstra("end", to);
        }

        return Dijkstra(shortestNextNode.getName(), to);
    }

    public void shortestPath(String source, String to) {
        Dijkstra(source, to);

        Collections.sort(nodes);

        for(Node node : nodes) {
            System.out.println(node.getName() + ": " + node.getShortestDistance() + "\n");
            node.setVisited(false);
        }
    }

    public ArrayList<Node> returnShortestPathToNode(String source, String to) {
        shortestPath(source, to);

        Node toNode = findNode(to);

        ArrayList<Node> pathNodes = new ArrayList<>();

        Collections.sort(nodes);

        for(Node node : nodes) {
            if(node.getShortestDistance() < toNode.getShortestDistance()) {
                pathNodes.add(node);
            }
            if(node.getName().equals(toNode.getName())) {
                pathNodes.add(node);
                return pathNodes;
            }
        }

        return null;
    }

    public void reset() {
        for(Node node : nodes) {
            node.setPrevious(null);
            node.setVisited(false);
            node.setShortestDistance(Integer.MAX_VALUE);
        }
    }

    private Node findNode(String name) {
        for(Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    private ArrayList<Edge> findEdge(String name) {
        ArrayList<Edge> edgeList = new ArrayList<>();

        for(Edge edge : edges) {
            if(edge.getFrom().getName().equals(name)) {
                edgeList.add(edge);
            }
        }
        return edgeList;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
