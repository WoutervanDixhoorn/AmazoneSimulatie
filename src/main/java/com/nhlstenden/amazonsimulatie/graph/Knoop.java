import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Knoop{
    
    String naam;
    List<Knoop> kortsePad = new LinkedList<>();
    Integer afstand = Integer.MAX_VALUE;
    Map<Knoop, Integer> aangrenzendeKnopen = new HashMap<>();
     
    public void addBestemming(Knoop bestemming, int afstand){

    }

    public Knoop(String naam){
        this.naam = naam;
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
}