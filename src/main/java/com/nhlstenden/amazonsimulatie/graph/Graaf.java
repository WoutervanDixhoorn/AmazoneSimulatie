package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Graaf {

    Set<Knoop> knopen = new HashSet<>();

    public void addNode(Knoop n){
        knopen.add(n);
    }
    
    public Set<Knoop> getGraaf(){
        return knopen;
    }

    public Knoop getKnoopByName(String naam){
        Knoop temp = null;
        for(Knoop k : knopen){
            if(k.naam == naam){
                temp = k;
                break;
            }
        }
        return temp;
    }

}