import java.util.Map.Entry;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dijkstra {

    public static Graaf berekenPadVanafBegin(Graaf graaf, Knoop begin) {
        begin.setAfstand(0);
     
        Set<Knoop> bezochteKnopen = new HashSet<>();
        Set<Knoop> onbezochteKnopen = new HashSet<>();
     
        onbezochteKnopen.add(begin);
     
        while (onbezochteKnopen.size() != 0) {
            Knoop huidigeKnoop = getLowestDistanceNode(onbezochteKnopen);
            onbezochteKnopen.remove(huidigeKnoop);
            for (Entry<Knoop, Integer> adjacencyPair : 
            huidigeKnoop.getAangrezendeKnopen().entrySet()) {
                Knoop adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!bezochteKnopen.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, huidigeKnoop);
                    onbezochteKnopen.add(adjacentNode);
                }
            }
            bezochteKnopen.add(huidigeKnoop);
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

    private static void CalculateMinimumDistance(Knoop evaluationNode, Integer edgeWeigh, Knoop sourceNode) {
        Integer sourceDistance = sourceNode.getAfstand();
        if (sourceDistance + edgeWeigh < evaluationNode.getAfstand()) {
            evaluationNode.setAfstand(sourceDistance + edgeWeigh);
            LinkedList<Knoop> shortestPath = new LinkedList<>(sourceNode.getKorstePad());
            shortestPath.add(sourceNode);
            evaluationNode.setKorstePad(shortestPath);
        }
    }
}

