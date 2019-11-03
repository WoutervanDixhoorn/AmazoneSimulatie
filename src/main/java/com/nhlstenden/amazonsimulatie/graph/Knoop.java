package com.nhlstenden.amazonsimulatie.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Knoop{
    
    private String naam;
    private List<Knoop> kortsePad = new LinkedList<>();
    private Integer afstand = Integer.MAX_VALUE;
    private Map<Knoop, Integer> aangrenzendeKnopen = new HashMap<>();

    private int x;
    private int z;

    public void addBestemming(Knoop bestemming, int afstand){
        aangrenzendeKnopen.put(bestemming, afstand);
    }

    public Knoop(String naam, int x, int z){
        this.naam = naam;
        this.x = x;
        this.z = z;
    }

    public void setAfstand(int afstand){
        this.afstand = afstand;
    }

    public int getAfstand(){
        return this.afstand;
    }

    public Map<Knoop, Integer> getAangrezendeKnopen(){
        return aangrenzendeKnopen;
    }

    public List<Knoop> getKorstePad(){
        return kortsePad;
    }

    public void setKorstePad(List<Knoop> korstePad){
        this.kortsePad = korstePad;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public String getNaam(){
        return naam;
    }
}