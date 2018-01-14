/**
 * Created by Виталий on 12.12.2017.
 */
import java.io.Serializable;
public class item implements Serializable{
    private String name, text, picture;
    private int place, type, x, y;
    public item(String name, String picture, int place, int type, String text, int x, int y){
        this.name = name;
        this.place = place;
        this.type = type;
        this.text = text;
        this.x = x;
        this.y = y;
        this.picture = picture;
    }
}
