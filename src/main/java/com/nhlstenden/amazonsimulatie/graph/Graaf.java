package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Graaf {

    private Set<Knoop> knopen = new HashSet<>();

    public void addNode(Knoop n){
        knopen.add(n);
    }
    
    public Set<Knoop> getGraaf(){
        return knopen;
    }

    public Knoop getKnoopByName(String naam){
        Knoop temp = null;
        for(Knoop k : knopen){
            if(k.getNaam().equals(naam)){
                temp = k;
                break;
            }
        }
        return temp;
    }

    public Knoop getKnoopByLocation(int x, int z){
        Knoop temp = null;
        for(Knoop k : knopen){
            if(k.getX() == x && k.getZ() == z){
                temp = k;
                break;
            }
        }
        return temp;
    }

}