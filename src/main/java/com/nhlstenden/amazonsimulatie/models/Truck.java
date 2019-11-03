package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;

public class Truck implements Object3D, Updatable{
    private UUID uuid;

    //Position variables
    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double restPosX = 0;
    private double restPosZ = 0;

    private double laadDockX = 0;
    private double laadDockZ = 0;

    private double rotationX = 0;
    private double rotationY = -Math.PI/2;
    private double rotationZ = 0;

    private double movementSpeed = 0.5;

    //Order variables
    private List<String> allProducts = new ArrayList<>();
    private Stack<String> neededProducts = new Stack<>();
    private List<String> products = new ArrayList<>();
    private List<String> placedOrders = new ArrayList<String>();

    private boolean hasOrder = false;
   
    private World world;

    public Truck(int x, int z, int laadDockX, int laadDockZ, List<String> producten, World world){
        this.uuid = UUID.randomUUID();
        this.world = world;
        this.x = x;
        this.z = z;
        this.restPosX = x;
        this.restPosZ = z;
        this.laadDockX = laadDockX;
        this.laadDockZ = laadDockZ;
        this.allProducts = producten;
    }

    @Override
    public boolean update() {
        //Move
        move();

        //If back in position generate and get order
        if(!hasOrder && x == restPosX && z == restPosZ){
            generateOrder(3);
            hasOrder = true;
        }

        //Get product
       if(hasOrder && placedOrders.equals(products) && !world.isaRobotBuzy()){ //Reset the truck
            hasOrder = false;
            neededProducts = new Stack<>();
            placedOrders = new ArrayList<>();
            products = new ArrayList<>();
        }
        else if(hasOrder && products != placedOrders){
            //Load product in truck if in position
            if(x == laadDockX && z == laadDockZ){
                //System.out.println("Arrived at loading dock");
                //Make order to robot and load it
                if(world.getIsRobotAvailable() && neededProducts.size() > 0 ){
                    String productToGet = neededProducts.pop(); 
                    world.makeOrderAtRobot(productToGet);
                    products.add(productToGet);
                }
                //products = neededProducts; //DEBUG

                return false;
            }
        }

        return true;
    }

    //Moves truck to wanted location at specific state
    private void move(){
        if(hasOrder){
            if(x < laadDockX){
                this.x += movementSpeed;
            }
            if(x > laadDockX){
                this.x -= movementSpeed;
            }
            if(z < laadDockZ){
                this.z += movementSpeed;
            }
            if(z > laadDockZ){
                this.z -= movementSpeed;
            }
        }else if(!hasOrder){
            if(x < restPosX){
                this.x += movementSpeed;
            }
            if(x > restPosX){
                this.x -= movementSpeed;
            }
            if(z < restPosZ){
                this.z += movementSpeed;
            }
            if(z > restPosZ){
                this.z -= movementSpeed;
            }
        }
    }

    private void generateOrder(int sizeOfOrder){
        Random rand = new Random(); 
  
        List<String> clone = allProducts;
        List<String> temp = new ArrayList<>(); 
        for (int i = 0; i < sizeOfOrder; i++) { 
  
            int randomIndex = rand.nextInt(clone.size()); 
            // add element in temporary list 
            temp.add(clone.get(randomIndex)); 
            clone.remove(randomIndex);
        } 
        
        for(String s : temp){
            placedOrders.add(s);
            neededProducts.push(s);
        }
        Collections.reverse(placedOrders);
    }
    
    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {

        return Truck.class.getSimpleName().toLowerCase();
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

}