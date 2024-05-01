
import eg.edu.alexu.csd.oop.game.GameObject;

public class GameObjectFactory {

    public GameObject getShape(String type, int x, int y) {
        if (type == null) {
            return null;
        }
        if (type.equals("plate")) {
            return new PlateObject(x, y, "plate");
        }
        return null;
    }
}
