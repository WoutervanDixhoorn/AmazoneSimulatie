package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.graph.Graaf;
import com.nhlstenden.amazonsimulatie.graph.Knoop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot implements Object3D, Updatable {
    private UUID uuid;

    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;
    private double angle = Math.toDegrees(0);

    private double rotationSpeed = 15;
    private double movementSpeed = 0.5;

    private List<Knoop> knopen;

    private int nodeGetter = 0;

    Graaf graaf;
    Graaf pad;
    private String bestemming = "";
    StorageRack currentStorage = null;

    private int tripCounter = 0;

    public Robot(Graaf graaf) {
        this.uuid = UUID.randomUUID();
        this.graaf = graaf;
        x = graaf.getKnoopByName("Source").getX();
        z = graaf.getKnoopByName("Source").getZ();
    }

    /*
     * Deze update methode wordt door de World aangeroepen wanneer de
     * World zelf geupdate wordt. Dit betekent dat elk object, ook deze
     * robot, in de 3D wereld steeds een beetje tijd krijgt om een update
     * uit te voeren. In de updatemethode hieronder schrijf je dus de code
     * die de robot steeds uitvoert (bijvoorbeeld positieveranderingen). Wanneer
     * de methode true teruggeeft (zoals in het voorbeeld), betekent dit dat
     * er inderdaad iets veranderd is en dat deze nieuwe informatie naar de views
     * moet worden gestuurd. Wordt false teruggegeven, dan betekent dit dat er niks
     * is veranderd, en de informatie hoeft dus niet naar de views te worden gestuurd.
     * (Omdat de informatie niet veranderd is, is deze dus ook nog steeds hetzelfde als
     * in de view)
     */
    @Override
    public boolean update() {

        if(tripCounter == 3 && x == graaf.getKnoopByName(bestemming).getX() && z == graaf.getKnoopByName(bestemming).getZ()) {
            currentStorage.setAttached(false);
        }

        if(x==graaf.getKnoopByName("Source").getX()&&z==graaf.getKnoopByName("Source").getZ()) {
            if(bestemming.equals("")){
                return false;
            }

            if(tripCounter == 3) {
                knopen = null;
                currentStorage = null;
                bestemming = "";
                tripCounter = 1;
                return false;
            }else {
                this.knopen = new ArrayList<>();
                this.knopen = graaf.getKnoopByName(bestemming).getKorstePad();
                if (!this.knopen.contains(graaf.getKnoopByName(bestemming))) {
                    this.knopen.add(graaf.getKnoopByName(bestemming));
                }
            }
            tripCounter++;
        }

        if(nodeGetter == (knopen.size()-1)) {
            System.out.println("Arrived" + "Trip: " + tripCounter);
            Collections.reverse(knopen);
            if(tripCounter == 2) {
                currentStorage.setAttached(true);
            }
            nodeGetter = 0;
            return false;
        }
        //System.out.println(angle);



        if(x < knopen.get(nodeGetter).getX()) {

            if(Math.toRadians(angle) == Math.PI/2){
                this.rotationY = Math.toRadians(angle);
                this.x += movementSpeed;
            }else{
                angle -= rotationSpeed;
                this.rotationY = Math.toRadians(angle);
            }
            //this.x += movementSpeed;
            if(currentStorage.isAttached()) {
                currentStorage.setX(x);
            }
//            if(currentStorage.isAttached()) {
//                currentStorage.setX(x);
//                currentStorage.setRotationY(rotationY);
//            }
        }
        if(x > knopen.get(nodeGetter).getX()) {

            if(Math.toRadians(angle) == Math.PI/2){
                this.rotationY = Math.toRadians(angle);;
                this.x -= movementSpeed;
            }else{
                angle -= rotationSpeed;
                this.rotationY = Math.toRadians(angle);
            }
            //this.x -= movementSpeed;
            if(currentStorage.isAttached()) {
                currentStorage.setX(x);
            }
//            if(currentStorage.isAttached()) {
//                currentStorage.setX(x);
//                currentStorage.setRotationY(rotationY);
//            }
        }
        if(z < knopen.get(nodeGetter).getZ()) {

            if(Math.toRadians(angle) == Math.PI){
                this.rotationY = Math.toRadians(angle);;
                this.z += movementSpeed;
            }else{
                angle += rotationSpeed;
                this.rotationY = Math.toRadians(angle);
            }
            this.z += movementSpeed;
            if(currentStorage.isAttached()) {
                currentStorage.setZ(z);
            }
//            if(currentStorage.isAttached()) {
//                currentStorage.setZ(z);
//                currentStorage.setRotationY(rotationY);
//            }
        }
        if(z > knopen.get(nodeGetter).getZ()) {

            if(Math.toRadians(angle) == Math.PI){
            this.rotationY = Math.toRadians(angle);
            this.z -= movementSpeed;
            }else{
                angle += rotationSpeed;
                this.rotationY = Math.toRadians(angle);
            }
            this.z -= movementSpeed;
            if(currentStorage.isAttached()) {
                currentStorage.setZ(z);
            }
//            if(currentStorage.isAttached()) {
//                currentStorage.setZ(z);
//                currentStorage.setRotationY(rotationY);
//            }
        }
        if(x == knopen.get(nodeGetter).getX() && z == knopen.get(nodeGetter).getZ()) {
            nodeGetter++;
        }



        //TEMP
        //TODO: Optimise updates
        return true;
    }

    public void setCurrentStorage(StorageRack storage) {
        this.currentStorage = storage;
    }

    private void decideDestination(StorageRack storageRack) {
        bestemming = storageRack.getNaam();
    }

    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {
        /*
         * Dit onderdeel wordt gebruikt om het type van dit object als stringwaarde terug
         * te kunnen geven. Het moet een stringwaarde zijn omdat deze informatie nodig
         * is op de client, en die verstuurd moet kunnen worden naar de browser. In de
         * javascript code wordt dit dan weer verder afgehandeld.
         */
        return Robot.class.getSimpleName().toLowerCase();
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public double getRotationX() {
        return this.rotationX;
    }

    @Override
    public double getRotationY() {
        return this.rotationY;
    }

    @Override
    public double getRotationZ() {
        return this.rotationZ;
    }

    public void setBestemming(String bestemming) {
        this.bestemming = bestemming;
    }

    public String getBestemming(){
        return bestemming;
    }

}