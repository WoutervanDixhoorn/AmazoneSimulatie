package com.nhlstenden.amazonsimulatie.graph;

import java.util.Map.Entry;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dijkstra {

    public static Graaf berekenPadVanafBegin(Graaf graaf, Knoop begin) {
        begin.setAfstand(0);
     
        Set<Knoop> settledNodes = new HashSet<>();
        Set<Knoop> unsettledNodes = new HashSet<>();
     
        unsettledNodes.add(begin);
 
    while (unsettledNodes.size() != 0) {
        Knoop currentNode = getLowestDistanceNode(unsettledNodes);
        unsettledNodes.remove(currentNode);
        for (Entry < Knoop, Integer> adjacencyPair: 
          currentNode.getAangrezendeKnopen().entrySet()) {
            Knoop adjacentNode = adjacencyPair.getKey();
            Integer edgeWeight = adjacencyPair.getValue();
            if (!settledNodes.contains(adjacentNode)) {
                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                unsettledNodes.add(adjacentNode);
            }
        }
        settledNodes.add(currentNode);
    }
        return graaf;
    }

    private static Knoop getLowestDistanceNode(Set < Knoop > unsettledNodes) {
        Knoop lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Knoop node: unsettledNodes) {
            int nodeDistance = node.getAfstand();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Knoop evaluationNode, Integer edgeWeigh, Knoop sourceNode) {
        Integer sourceDistance = sourceNode.getAfstand();
        if (sourceDistance + edgeWeigh < evaluationNode.getAfstand()) {
            evaluationNode.setAfstand(sourceDistance + edgeWeigh);
            LinkedList<Knoop> shortestPath = new LinkedList<>(sourceNode.getKorstePad());
            shortestPath.add(sourceNode);
            evaluationNode.setKorstePad(shortestPath);
        }
    }
}

