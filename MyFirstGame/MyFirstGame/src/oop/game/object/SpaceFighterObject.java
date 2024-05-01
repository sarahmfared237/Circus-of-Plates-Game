/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.game.object;

/**
 *
 * @author michael.said
 */
public class SpaceFighterObject extends ImageObject {
    
    public static interface FireListener {
        void fire();
    }
    
    private final FireListener fireListener;
    
    public SpaceFighterObject(int x, int y, String imagePath, FireListener fireListener) {
        super(x, y, imagePath);
        this.fireListener = fireListener;
    }

    @Override
    public void setY(int y) {
        
        if (fireListener != null) {
            fireListener.fire();
        }
    }
    
    /*
        up and down  setY
        left and right setX
    
    */
    
}
