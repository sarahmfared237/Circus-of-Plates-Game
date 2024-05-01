/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import java.util.LinkedList;
import java.util.List;
import oop.game.object.ImageObject;
import oop.game.object.SpaceFighterObject;

/**
 *
 * @author michael.said
 */
public class StarWar implements World, SpaceFighterObject.FireListener {

    private static int MAX_TIME = 10 * 60 * 1000; // 1 minute
    private static int RIGHT_ROCKET = 123456;
    private static int LEFT_ROCKET = 654321;

    private static int DEFAULT_SEPEED = 10;
    private static int DEFAULT_CONTROL_SEPEED = 20;
    private static int NUM_OF_MOVING_OBJ = 10;

    private long startTime;
    private int score;
    private boolean fireRocket;

    private final int width;
    private final int height;

    private final List<GameObject> constant;
    private final List<GameObject> moving;
    private final List<GameObject> control;

    public StarWar(int width, int height) {
        this.width = width;
        this.height = height;

        this.startTime = System.currentTimeMillis();
        this.score = 0;
        this.fireRocket = false;

        this.constant = new LinkedList<>();
        this.moving = new LinkedList<>();
        this.control = new LinkedList<>();

        createGameObject();
    }

    private void createGameObject() {
        //Add backgroud
//        constant.add(new ImageObject(score, score, imagePath))

        //Add moving Objects
        for (int i = 0; i < NUM_OF_MOVING_OBJ; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height / 2);
            int imgNum = (int) (Math.random() * 3 + 1);
            String imagePath = "alien" + imgNum + ".png";
            moving.add(new ImageObject(x, y, imagePath));
        }

        //Add control objects
        SpaceFighterObject fighterObject = new SpaceFighterObject(width / 3, (int) (height * 0.8), "spaceship.png", this);
        control.add(fighterObject);
        control.add(new ImageObject(fighterObject.getX() + 10, fighterObject.getY() + 40, LEFT_ROCKET, "rocket.png"));
        control.add(new ImageObject(fighterObject.getX() + 62, fighterObject.getY() + 40, RIGHT_ROCKET, "rocket.png"));

    }

    @Override
    public List<GameObject> getConstantObjects() {
        return this.constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return this.moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return this.control;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth())
                && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
    }

    @Override
    public boolean refresh() {
        
        //1- movable objs 
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        ImageObject fighter = (ImageObject) control.get(0);
        if (fireRocket) {
            fireRocket = false;
            if (control.size() > 1) {
                moving.add(control.remove(1));	// release the rocket
            }
        }
        for (GameObject o : moving) {
            int type = ((ImageObject) o).getType();
            if (type != 0) {	// rocket
                if (o.getY() < -50) {
                    o.setX(fighter.getX() + (type == LEFT_ROCKET ? 10 : 62));
                    o.setY(fighter.getY() + 40);
                    control.add(o);
                    moving.remove(o);
                } else {
                    o.setY(o.getY() - 10);//Move UPs
                }
                // check rocket intersection with aliens
                for (GameObject o2 : moving) {
                    if (o != o2) {
                        if (o2.isVisible()) {
                            if (intersect(o, o2)) {
                                ((ImageObject) o2).setVisible(false);
                                score++;	// gain score
                            }
                        } else {
                            // reuse the alien in another position
                            o2.setX((int) (Math.random() * width));
                            o2.setY((int) (Math.random() * height / 3));
                            ((ImageObject) o2).setVisible(true);
                        }
                    }
                }
            } else {
                //alien
                o.setY((o.getY() + 1));//move Down
                if (o.getY() == getHeight()) {
                    // reuse the alien in another position
                    o.setY(-1 * (int) (Math.random() * getHeight()));
                    o.setX((int) (Math.random() * getWidth()));
                }
                o.setX(o.getX() + (Math.random() > 0.5 ? 2 : -2));
                if (!timeout & o.isVisible() && intersect(o, fighter)) {
                    score = Math.max(0, score - 10);	// lose score
                }
            }
        }
        return !timeout;
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);// update status
    }

    @Override
    public int getSpeed() {
        return DEFAULT_SEPEED;
    }

    @Override
    public int getControlSpeed() {
        return DEFAULT_CONTROL_SEPEED;
    }

    @Override
    public void fire() {
        this.fireRocket = true;
    }

}
