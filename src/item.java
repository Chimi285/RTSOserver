/**
 * Created by Виталий on 12.12.2017.
 */
import java.io.Serializable;
public class item implements Serializable{
    private String name, text, picture, choisedTexture;
    private int place, type, x, y;
    public item(String name, String picture, String choisedTexture, int place, int type, String text, int x, int y){
        this.name = name;
        this.place = place;
        this.type = type;
        this.text = text;
        this.x = x;
        this.y = y;
        this.picture = picture;
        this.choisedTexture = choisedTexture;
    }
    public String getTexture(){
        return picture;
    }
    public String getChoisedTexture(){
        return choisedTexture;
    }
}
