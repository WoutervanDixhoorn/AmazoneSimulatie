package com.nhlstenden.amazonsimulatie.models;

import java.util.List;
import java.util.UUID;

public class StorageRack implements Object3D, Updatable{
    private UUID uuid;

    private double x = 0;
    private double y = 0.15;
    private double z = 0;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;

    private List<String> producten;
    private String naam;

    public void setAttached(boolean attached) {
        isAttached = attached;
    }

    public boolean isAttached() {
        return isAttached;
    }

    private boolean isAttached = false;

    
    public StorageRack(double x, double z, String naam, String producten[]){
        this(x,z, naam);
        for(String s : producten){
            this.producten.add(s);
        }
    }

    public StorageRack(double x, double z, String naam){
        this.uuid = UUID.randomUUID();
        this.naam = naam;
        this.x = x;
        this.z = z;      
    }

    @Override
    public boolean update() {
        if(isAttached){
            return true;
        }

        return false;
    }

    public String getNaam(){
        return naam;
    }

    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {
        return StorageRack.class.getSimpleName().toLowerCase();
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setZ(double z){
        this.z = z;
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