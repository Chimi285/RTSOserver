import java.util.LinkedList;
import java.io.Serializable;
public class Build implements Serializable {
    private int X, Y, enterX, enterY, Height, Width;
    private String texture;

    public Build(int X, int Y, int enterX, int enterY, String texture, int Height, int Width) {
        this.X = X;
        this.Y = Y;
        this.enterX = enterX;
        this.enterY = enterY;
        this.texture = texture;
        this.Height = Height;
        this.Width = Width;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public int getEnterX() {
        return enterX;
    }

    public int getEnterY() {
        return enterY;
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    public String getTexture() {
        return texture;
    }


}

