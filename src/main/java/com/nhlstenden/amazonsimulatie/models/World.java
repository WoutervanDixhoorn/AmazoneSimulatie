package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.graph.Dijkstra;
import com.nhlstenden.amazonsimulatie.graph.Graaf;
import com.nhlstenden.amazonsimulatie.graph.Knoop;

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

    //Intrieur
    public Graaf graaf;
    public static List<StorageRack> storageRacks = new ArrayList<>();

    //Automatische bestellingen
    public List<String> producten = new ArrayList<>();
    private List<String> productsAtDock = new ArrayList<>();
    private boolean isRobotAvailable = true;

    //Robot etc.
    private Robot robot1;
    private Robot robot2;

    private Truck truck;

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
        //Init above
        verzadigProducten("Sokken", "Lego", "Gitaar", "Water", "Tas", "Eenhoorn in blik", "Stoel", 
        "Laptop", "Schoenen", "TV", "Kamer plant", "Waterfles");
        buildWarehouse();
        //
       

        robot1 = new Robot(graaf);
        robot2 = new Robot(graaf);

        //robot1.setBestemming("Stellage1-0");
        //robot2.setBestemming("Stellage4-0");

        //robot1.setCurrentStorage(findStorageRack("Stellage1-0"));
        //robot2.setCurrentStorage(findStorageRack("Stellage4-0"));
        //decideStorageRack(robot1, "Stellage1-0");
        //decideStorageRack(robot2, "Stellage4-0");

        //Adding robots to world
        this.worldObjects.add(robot1);
        this.worldObjects.add(robot2);

        //Adding/Starting truck
        this.truck = new Truck(-50,15,-8,15, producten, this);
        this.worldObjects.add(truck);
    }

    /* 
     * Vind storage rack dat bij bepaald product hoort
     */
    private void decideStorageRack(Robot robot, String product) {
        for(StorageRack storageRack : storageRacks) {
            if(storageRack.getProducts().get(0).equals(product)) {
                robot.setCurrentStorage(storageRack);
                robot.setBestemming(storageRack.getNaam());
                //storageRacks.remove(storageRack);
            }
        }
    }

    private StorageRack findStorageRack(String name) {
        for(StorageRack storageRack : storageRacks) {
            if (storageRack.getNaam().equals(name)) {
                storageRack.setAttached(false);
                return storageRack;
            }
        }
        System.out.println("Couldnt find this storage rack");
        return null;
    }



    private void buildWarehouse(){
        //Graaf graaf = new Graaf();
        List<Knoop> knopen = new ArrayList<>();


        //Solution?
        int productCounter = 0;
        //SIZE needs to my uneven
        int SIZE = 5;
        int spacing = 5;
        int offset = 5;
        //Generating locations and nodes
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(i == 0){
                    if(j == 0 || j == SIZE-1){
                        //Empty node toevoegen zodat de dimensie van de lijst klopt
                        knopen.add(new Knoop("Empty", i*spacing + offset,j*spacing + offset));
                        continue;
                    }
                    if(j == SIZE/2){
                        //nodeModels.add(new NodeModel("source",i*spacing + offset,j*spacing + offset));
                        knopen.add(new Knoop("Source", i*spacing + offset,j*spacing + offset));
                        continue;
                    }
                    //nodeModels.add(new NodeModel(i*spacing + offset,j*spacing + offset));
                    knopen.add(new Knoop("Knoop" + i + "-" + j, i*spacing + offset,j*spacing + offset));
                    continue;
                }
                if(j%2 == 0){
                    //nodeModels.add(new NodeModel("stellage",i*spacing + offset,j*spacing + offset));
                    storageRacks.add(new StorageRack(i*spacing + offset,j*spacing + offset, "Stellage" + i + "-" + j, producten.get(productCounter)));
                    knopen.add(new Knoop("Stellage" + i + "-" + j, i*spacing + offset,j*spacing + offset));
                    productCounter++;
                    System.out.println(productCounter);
                    continue;
                }
               //nodeModels.add(new NodeModel(i*spacing + offset,j*spacing + offset));
                knopen.add(new Knoop("Knoop" + i + "-" + j, i*spacing + offset,j*spacing + offset));            
            }
        }

        for(StorageRack s : storageRacks){
            this.worldObjects.add(s);
        }

        //USAGE: nodeA.addBestemming(nodeB, 10);
        //Linking nodes
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(i == 0){
                //Skip first and last node
                    if(j == 0 || j == SIZE-1){
                        continue;
                    }
                    if(knopen.get(i*(SIZE)+j).getNaam().equals("Source")){
                        knopen.get(i*(SIZE)+j).addBestemming(knopen.get(i*(SIZE)+j-1), 1);
                        knopen.get(i*(SIZE)+j).addBestemming(knopen.get(i*(SIZE)+j+1), 1);
                        knopen.get(i*(SIZE)+j-1).addBestemming(knopen.get((i+1)*(SIZE)+j-1), 1);
                        knopen.get(i*(SIZE)+j+1).addBestemming(knopen.get((i+1)*(SIZE)+j+1), 1);
                    }     
                }else if(i > 0 && i < SIZE){
                    if(j%2 != 0 && j != 0){
                        knopen.get(i*(SIZE)+j).addBestemming(knopen.get(i*(SIZE)+j-1), 1);
                        knopen.get(i*(SIZE)+j).addBestemming(knopen.get(i*(SIZE)+j+1), 1);
                        if(i < SIZE-1){
                            knopen.get(i*(SIZE)+j).addBestemming(knopen.get((i+1)*(SIZE)+j), 1);
                        } 
                    }

                }
            }
        }
        //Graaf van warehouse
        graaf = new Graaf();

        for(Knoop k : knopen){
            graaf.addNode(k);
        }

        //Init alle paden
        graaf = Dijkstra.berekenPadVanafBegin(graaf, graaf.getKnoopByName("Source"));
    }

    private void setRacks(List<String> producten, List<StorageRack> racks){
        for(int i = 0; i < racks.size(); i++){
            racks.get(i).setRack(producten.get(i));
        }
    }

    private void verzadigProducten(String... producten){
        for(String p : producten){
            this.producten.add(p);
        }
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
        checkIfRobotAvailable();
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

    private void checkIfRobotAvailable(){
        if(robot1.getBestemming().isEmpty() || robot2.getBestemming().isEmpty()){
            isRobotAvailable = true;
        }
    }

    //Laat de robot de producten halen
    public void makeOrderAtRobot(String product){
        if(robot1.getBestemming().isEmpty()){
            decideStorageRack(robot1, product);
        }else if(robot2.getBestemming().isEmpty()){
            decideStorageRack(robot2, product);
        }
    }

    public boolean isaRobotBuzy(){
        //return if a robot is buzy
        if(robot1.getBestemming().isEmpty() && robot2.getBestemming().isEmpty()){
            return false;
        }else if(!robot1.getBestemming().isEmpty() || !robot2.getBestemming().isEmpty()){
            return true;
        }
        return false;
    }
    
    public boolean getIsRobotAvailable(){
        if(robot1.getBestemming().isEmpty() || robot2.getBestemming().isEmpty()){
            return true;
        }else if(!robot1.getBestemming().isEmpty() || !robot2.getBestemming().isEmpty()){
            return false;
        }
        return false;
    }

    public void addProductsAtDock(String product){
        productsAtDock.add(product);
    }

    public List<String> getProductsAtDock(){
        return productsAtDock;
    }
    
    public void removeProductAtDock(String product){
        productsAtDock.remove(product);
    }
}