
import java.util.LinkedList;
import java.util.List;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import java.awt.Color;

public class CircusWorld implements World {

    private static final int MAX_TIME = 1 * 60 * 1000;	// 1 minute
    private int score = 0;
    private final long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final List<GameObject> constant = new LinkedList<GameObject>();
    private final List<GameObject> moving = new LinkedList<GameObject>();
    private final List<GameObject> control = new LinkedList<GameObject>();
    private List<GameObject> left = new LinkedList<GameObject>();
    private IStrategy current;

    public CircusWorld(IStrategy x, int screenWidth, int screenHeight) {
        current = x;
        width = screenWidth;
        height = screenHeight;
        // control objects (hero)
        control.add(new ImageObject(screenWidth / 3, (int) (screenHeight * 0.6), "/clown.png"));
        constant.add(new BarObject(0, 50, 250, true, Color.BLACK));
        constant.add(new BarObject(width - 250, 50, 250, true, Color.BLACK));
        // moving objects (enemy)
        for (int i = 0; i < 20; i++) {
            moving.add(new PlateObject(0 - 50 * (3 * i), 43, "/plate" + ((int) (Math.random() * 1000) % 2 + 1) + ".png"));
        }
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth()) && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
    }

    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        GameObject clown = control.get(0);
        // moving starts
        for (int i = 0; i < moving.size(); i++) {
            GameObject m = moving.get(i);
            if (m.getX() + 150 < width / 2) {
                m.setX((int) (m.getX() + current.getSpeed()));
            } else {
                m.setX((int) (m.getX() + (m.getX() * 1000) % 3));
                m.setY((int) (m.getY() + Math.random() * 1000 % 3));
            }
            if (left.isEmpty()) {
                if (clownIntersectleft(m)) {
                    moving.remove(m);
                    PlateObject p = (PlateObject) m;
                    p.setC((ImageObject) clown);
                    p.setHorizontalOnly(true);
                    p.setType(1);
                    control.add(m);
                    left.add(m);
                }
            } else {
                if (intersect(m, left.get(left.size() - 1))) {
                    moving.remove(m);
                    PlateObject p = (PlateObject) m;
                    p.setC((ImageObject) clown);
                    p.setHorizontalOnly(true);
                    p.setY(left.get(left.size() - 1).getY() - p.getWidth());
                    p.setType(1);
                    control.add(m);
                    left.add(m);
                }
                if (m.getX() > width || m.getY() > height) {

                }
            }
            updateLeft();

            if (left.size() == 10) {
                return true;
            }
        }
        return !timeout;
    }

    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);	// update status
    }

    private boolean clownIntersectleft(GameObject o) {
        ImageObject clown = (ImageObject) control.get(0);
        return (Math.abs(clown.getX() - o.getX()) <= o.getWidth() - 10
                && o.getY() == control.get(0).getY() - 10);
    }

    private void updateLeft() {
        if (left.size() >= 3) {
            PlateObject p1 = (PlateObject) left.get(left.size() - 1);
            PlateObject p2 = (PlateObject) left.get(left.size() - 2);
            PlateObject p3 = (PlateObject) left.get(left.size() - 3);
            if (p1.getPath().equals(p2.getPath()) && p2.getPath().equals(p3.getPath())) {
                left.remove(left.size() - 1);
                left.remove(left.size() - 1);
                left.remove(left.size() - 1);
                control.remove(p1);
                control.remove(p2);
                control.remove(p3);
                score++;
            }
        }
    }
}
