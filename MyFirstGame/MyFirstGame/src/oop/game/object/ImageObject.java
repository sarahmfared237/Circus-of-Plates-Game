/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.game.object;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author michael.said
 */
public class ImageObject implements GameObject {

    private static final int MAX_MSTATE = 1;

    private BufferedImage[] spriteImages;
    private int x;
    private int y;
    private boolean visible;
    private int type;

    public ImageObject(int x, int y, String imagePath) {
        this(x, y, 0, imagePath);
    }

    public ImageObject(int x, int y, int type, String imageName) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.spriteImages = new BufferedImage[MAX_MSTATE];
        this.visible = true;

        try {
//            getClass().getClassLoader().getResourceAsStream()
            spriteImages[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
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

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

}
