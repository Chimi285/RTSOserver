import java.util.LinkedList;

/**
 * Created by STEM on 14.01.2018.
 */
import java.io.Serializable;
public class Data implements Serializable{
    private LinkedList<Hero> players = new LinkedList<>();
    private  LinkedList<Build> buildings = new LinkedList<>();
    private item[] inventory = new item[9];
    private boolean menu;
    private item[] InInventory = new item[3];
    public Data(LinkedList<Hero> players,  LinkedList<Build> buildings, boolean menu, item[] inventory, item[] InInventory){
        this.buildings = buildings;
        this.players = players;
        this.menu = menu;
        this.inventory = inventory;
        this.InInventory = InInventory;
    }
    public Data(Data data){
        this.buildings = data.buildings;
        this.players = data.players;
        this.menu = data.menu;
        this.inventory = data.inventory;
        this.InInventory = data.InInventory;
    }
    public LinkedList<Hero> getPlayers(){
        return this.players;
    }
    public LinkedList<Build> getBuildings(){
        return this.buildings;
    }
    public boolean getMenu() {
        return this.menu;
    }
    public item[] getInventory() {
        return this.inventory;
    }
    public item[] getInInventory() {
        return this.InInventory;
    }
}
