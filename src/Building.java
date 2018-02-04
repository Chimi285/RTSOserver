import java.util.LinkedList;
import java.io.Serializable;
public class Building implements Serializable{
    private int X, Y, numberLut, enterX, enterY, Height, Width;
    private LinkedList<item> Lut = new LinkedList<>();
    private String texture;
    public Building(int X, int Y, int enterX, int enterY, int numberLut, String texture, int Height, int Width, LinkedList<item> Lut){
        this.X = X;
        this.Y = Y;
        this.enterX = enterX;
        this.enterY = enterY;
        this.numberLut = numberLut;
        this.texture = texture;
        this.Height = Height;
        this.Width = Width;
        this.Lut = Lut;
    }
    public int getX(){
        return this.X;
    }
    public int getY() {
        return this.Y;
    }
    public int getEnterX(){
        return enterX;
    }
    public int getEnterY() {
        return enterY;
    }
    public int getNumberLut() {
        return numberLut;
    }
    public int getHeight(){
        return Height;
    }
    public int getWidth(){
        return Width;
    }
    public String getTexture() {
        return texture;
    }
    public LinkedList<item> getLut(){
        return Lut;
    }
    public Build profile(){
        return new Build(X, Y, enterX, enterY, texture, Height, Width);
    }
}
