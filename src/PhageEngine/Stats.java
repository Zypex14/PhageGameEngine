package PhageEngine;

public class Stats {
    private static int fps = 0;
    private static double screenHeight = 0;
    private static double screenWidth = 0;
    private static double screenMaxX = 0;
    private static double screenMaxY = 0;
    private static double maxY = 0;
    private static double maxX = 0;
    private static double screenX = 0;
    private static double screenY = 0;


    public static void update() {
        screenMaxX = screenX + screenWidth;
        screenMaxY = screenY + screenHeight;
    }

    public static double getScreenMaxX() {
        return screenMaxX;
    }

    public static void setScreenMaxX(double screenMaxX) {
        Stats.screenMaxX = screenMaxX;
    }

    public static double getScreenMaxY() {
        return screenMaxY;
    }

    public static void setScreenMaxY(double screenMaxY) {
        Stats.screenMaxY = screenMaxY;
    }

    public static double getScreenX() {
        return screenX;
    }

    public static void setScreenX(double screenX) {
        Stats.screenX = screenX;
    }

    public static double getScreenY() {
        return screenY;
    }

    public static void setScreenY(double screenY) {
        Stats.screenY = screenY;
    }

    public static double getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(double screenHeight) {
        Stats.screenHeight = screenHeight;
    }

    public static double getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(double screenWidth) {
        Stats.screenWidth = screenWidth;
    }

    public static int getFps() {
        return fps;
    }

    public static double getMaxY() {
        return maxY;
    }

    public static void setMaxY(double maxY) {
        Stats.maxY = maxY;
    }

    public static double getMaxX() {
        return maxX;
    }

    public static void setMaxX(double maxX) {
        Stats.maxX = maxX;
    }

}