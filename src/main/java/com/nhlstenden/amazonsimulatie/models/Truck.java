package com.nhlstenden.amazonsimulatie.models;

import java.util.List;
import java.util.UUID;

public class Truck implements Object3D, Updatable{
    private UUID uuid;

    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double laadDockX = 0;
    private double laadDockZ = 0;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;

    private double movementSpeed = 0.5;

    private List<String> neededProducts;
    private List<String> products;
   

    public Truck(int x, int z, int laadDockX, int laadDockZ){
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.z = z;
        this.laadDockX = laadDockX;
        this.laadDockZ = laadDockZ;
    }

    @Override
    public boolean update() {
        
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

        return true;
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