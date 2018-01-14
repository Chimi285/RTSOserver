import java.util.LinkedList;

/**
 * Created by STEM on 14.01.2018.
 */
import java.io.Serializable;
public class Data implements Serializable{
    private LinkedList<Hero> players = new LinkedList<>();
    private  LinkedList<item> items = new LinkedList<>();
    public Data(LinkedList<Hero> players,  LinkedList<item> items){
        this.items = items;
        this.players = players;
    }
    public Data(Data data){
        this.items = data.items;
        this.players = data.players;
    }
    public LinkedList<Hero> getPlayers(){
        return this.players;
    }
    public LinkedList<item> getItems(){
        return this.items;
    }
}
