package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.graph.DijkstraGraph;
import com.nhlstenden.amazonsimulatie.graph.Graph;
import com.nhlstenden.amazonsimulatie.graph.Node;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/*
 * Deze class is een versie van het model van de simulatie. In dit geval is het
 * de 3D wereld die we willen modelleren (magazijn). De zogenaamde domain-logic,
 * de logica die van toepassing is op het domein dat de applicatie modelleerd, staat
 * in het model. Dit betekent dus de logica die het magazijn simuleert.
 */
public class World implements Model {
    /*
     * De wereld bestaat uit objecten, vandaar de naam worldObjects. Dit is een lijst
     * van alle objecten in de 3D wereld. Deze objecten zijn in deze voorbeeldcode alleen
     * nog robots. Er zijn ook nog meer andere objecten die ook in de wereld voor kunnen komen.
     * Deze objecten moeten uiteindelijk ook in de lijst passen (overerving). Daarom is dit
     * een lijst van Object3D onderdelen. Deze kunnen in principe alles zijn. (Robots, vrachrtwagens, etc)
     */
    private List<Object3D> worldObjects;


    private DijkstraGraph graph = new DijkstraGraph();

    /*
     * Dit onderdeel is nodig om veranderingen in het model te kunnen doorgeven aan de controller.
     * Het systeem werkt al as-is, dus dit hoeft niet aangepast te worden.
     */
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /*
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin wordt nu één robot gestopt.
     * Deze methode moet uitgebreid worden zodat alle objecten van de 3D wereld hier worden gemaakt.
     */
    public World() {
        this.worldObjects = new ArrayList<>();
        this.worldObjects.add(new Robot(this.buildDijkstraGraph()));
    }


    public ArrayList<Node> buildDijkstraGraph() {
        Node node1 = new Node("Source", 0, 0);
        Node node2 = new Node("Stellage1", 5, 0);
        Node node3 = new Node("Stellage2", 5, 5);
        Node node4 = new Node("Stellage3", 5, 10);
        Node node5 = new Node("Stellage4", 5, 15);
        Node node6 = new Node("Stellage5", 0, 15);
        Node node7 = new Node("Stellage6", 10, 15);
        Node node8 = new Node("Stellage7", 10, 10);

        graph.addOneWayConnection(node1, node2, 1);
        graph.addOneWayConnection(node2, node3, 1);
        graph.addOneWayConnection(node3, node4, 1);
        graph.addOneWayConnection(node4, node5, 1);
        graph.addOneWayConnection(node5, node6, 1);
        graph.addOneWayConnection(node6, node7, 1);
        graph.addOneWayConnection(node7, node8, 1);

        ArrayList<Node> nodes = graph.returnShortestPathToNode("Source", "Stellage7");

        return nodes;
    }

    /*
     * Deze methode wordt gebruikt om de wereld te updaten. Wanneer deze methode wordt aangeroepen,
     * wordt op elk object in de wereld de methode update aangeroepen. Wanneer deze true teruggeeft
     * betekent dit dat het onderdeel daadwerkelijk geupdate is (er is iets veranderd, zoals een positie).
     * Als dit zo is moet dit worden geupdate, en wordt er via het pcs systeem een notificatie gestuurd
     * naar de controller die luisterd. Wanneer de updatemethode van het onderdeel false teruggeeft,
     * is het onderdeel niet veranderd en hoeft er dus ook geen signaal naar de controller verstuurd
     * te worden.
     */
    @Override
    public void update() {
        for (Object3D object : this.worldObjects) {
            if(object instanceof Updatable) {
                if (((Updatable)object).update()) {
                    pcs.firePropertyChange(Model.UPDATE_COMMAND, null, new ProxyObject3D(object));
                }
            }
        }
    }

    /*
     * Standaardfunctionaliteit. Hoeft niet gewijzigd te worden.
     */
    @Override
    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /*
     * Deze methode geeft een lijst terug van alle objecten in de wereld. De lijst is echter wel
     * van ProxyObject3D objecten, voor de veiligheid. Zo kan de informatie wel worden gedeeld, maar
     * kan er niks aangepast worden.
     */
    @Override
    public List<Object3D> getWorldObjectsAsList() {
        ArrayList<Object3D> returnList = new ArrayList<>();

        for(Object3D object : this.worldObjects) {
            returnList.add(new ProxyObject3D(object));
        }

        return returnList;
    }
}