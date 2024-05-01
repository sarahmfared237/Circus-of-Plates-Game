
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import eg.edu.alexu.csd.oop.game.GameObject;

public class PlateObject implements GameObject {

    public State getCurrentstState() {
        return currentstState;
    }

    public void setCurrentstState(State currentstState) {
        this.currentstState = currentstState;
    }

    public int getLeftOrRightBar() {
        return LeftOrRightBar;
    }

    public void setLeftOrRightBar(int LeftOrRightBar) {
        this.LeftOrRightBar = LeftOrRightBar;
    }

    private static final int MAX_MSTATE = 1;
    // an array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    private int x;
    private int y;
    private boolean visible;
    private int type;//0 moving 1 left 2 right
    private boolean horizontalOnly;
    private ClownObject c;
    private String path;
    private int LeftOrRightBar;//0 Left 1 right
    private State currentstState;

    public void setClown(ClownObject c) {
        this.c = c;
    }

    public PlateObject(int posX, int posY, String path) {
        this(posX, posY, path, 0);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PlateObject(int posX, int posY, String path, int type) {
        this.x = posX;
        this.y = posY;
        this.type = type;
        this.visible = true;
        this.path = path;
        this.currentstState = new BarState(this);
        // create a bunch of buffered images and place into an array, to be displayed sequentially
        try {
            spriteImages[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException ignored) {
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int mX) {
        if (type == 1) {
            this.x = c.getX();
        } else {
            this.x = mX;
        }
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int mY) {
        if (!horizontalOnly) {
            this.y = mY;
        }
    }

    public boolean isHorizontalOnly() {
        return horizontalOnly;
    }

    public void setHorizontalOnly(boolean horizontalOnly) {
        this.horizontalOnly = horizontalOnly;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
