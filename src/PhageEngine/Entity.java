package PhageEngine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Entity {

    private static ArrayList<Entity> instances = new ArrayList<>(), deleteQueue = new ArrayList<>();
    private static HashMap<KeyCode, Boolean> key = new HashMap();
    public double height = 0, width = 0, x = 0, y = 0;

    public static void updateObjects(GraphicsContext gc, HashMap<KeyCode, Boolean> key) {


//        Deletes game objects without throwing an exception
        deleteQueue.forEach(object -> instances.remove(object));
        deleteQueue.clear();

//        This will update all game objects
        instances.forEach(object -> object.onUpdate(gc));

//        Updates all the key inputs
        Entity.key = key;
//        System.out.println(isKeyHeld(KeyCode.D));
    }


    public static void removeObject(Entity o) {
        deleteQueue.add(o);
    }

    public static void attachToWorld(Entity o) {
        instances.add(o);
    }

    public boolean isKeyHeld(KeyCode key) {
        this.key.putIfAbsent(key, false);
        return this.key.get(key);
    }

    public double getHeight() {
        return height;
    }
    public double getScreenHeight(){
        return (height / Stats.getMaxY()) * Stats.getScreenHeight();
    }

    public void setHeight(double height) {
        this.height = height;
    }
    public double getWidth() {
        return width;
    }

    public double getScreenWidth(){
        return (width / Stats.getMaxX()) * Stats.getScreenWidth();
    }
    public void setWidth(double width) {
        this.width = width;
    }

    public double getScreenX(){
        return (x / Stats.getMaxX()) * Stats.getScreenWidth() + Stats.getScreenX();
    }
    public double getScreenY(){
        return (y / Stats.getMaxY()) * Stats.getScreenHeight() + Stats.getScreenY();
    }

    public double getCenterX() {
        return x + width / 2;
    }
    public void setCenterX(double centerX) {
        x = centerX - width / 2;
    }

    public double getCenterY() {
        return y + height / 2;
    }
    public void setCenterY(double centerY) {
        y = centerY - height / 2;
    }

    public double getRightX() {
        return x + width;
    }
    public void setRightX(double rightX) {
        x = rightX - width;
    }

    public double getBottomY() {
        return y + height;
    }
    public void setBottomY(double bottomY) {
        y = bottomY - height;
    }

    protected abstract void onUpdate(GraphicsContext gc);

    protected static class DirectionalMovement {
        public static double getX(boolean up, boolean right, boolean down, boolean left) {
            if((right ? -1 : 0) + (left ? 1 : 0) == 0){
                return 0;
            }
            else {
                return Math.cos(Math.atan2((up ? 1 : 0) + (down ? -1 : 0), (right ? -1 : 0) + (left ? 1 : 0)));
            }
        }

        public static double getX(double up, double right, double down, double left) {
            if(right + left == 0) {
                return 0;
            } else{
                return Math.cos(Math.atan2(up - down, left - right));
            }
        }

        public static double getY(boolean up, boolean right, boolean down, boolean left) {
            if((up ? -1 : 0) + (down ? 1 : 0) == 0){
                return 0;
            }
            else {
                return -1 * Math.sin(Math.atan2((up ? 1 : 0) + (down ? -1 : 0), (right ? -1 : 0) + (left ? 1 : 0)));
            }
        }

        public static double getY(double up, double right, double down, double left) {
            if(up + down == 0){
                return 0;
            } else {
                return Math.sin(Math.atan2(up - down, left - right));
            }
        }
    }
}
