import java.util.LinkedList;

/**
 * Created by STEM on 14.01.2018.
 */
import java.io.Serializable;
public class Data implements Serializable{
    private LinkedList<Hero> players = new LinkedList<>();
    private  LinkedList<Building> buildings = new LinkedList<>();
    public Data(LinkedList<Hero> players,  LinkedList<Building> buildings){
        this.buildings = buildings;
        this.players = players;
    }
    public Data(Data data){
        this.buildings = data.buildings;
        this.players = data.players;
    }
    public LinkedList<Hero> getPlayers(){
        return this.players;
    }
    public LinkedList<Building> getBuildings(){
        return this.buildings;
    }
}
