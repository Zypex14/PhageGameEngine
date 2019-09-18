package PhageEngine;

public class GameSettings {
    private int fps = 60, height = 600, width = 600, aWidth = 16, aHeight = 9;
    private double ratio = 16 / 9;
    private String title = "Untitled Game";
    private boolean fullscreen = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getaWidth() {
        return aWidth;
    }

    public int getaHeight() {
        return aHeight;
    }

    public void setAspectRatio(int w, int h) {
        this.ratio = (double) w / h;

        double tolerance = 1.0E-6;
        double h1 = 1;
        double h2 = 0;
        double k1 = 0;
        double k2 = 1;
        double b = ratio;
        do {
            double a = Math.floor(b);
            double aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            b = 1 / (b - a);
        } while (Math.abs(ratio - h1 / k1) > ratio * tolerance);

        int i = 1;
        while(!((h1 * i > 1000 || k1 * i > 1000) && (h1 * i % 10 == 0 && k1 * i % 10 == 0))){
            i++;
        }

        aWidth = (int)h1 * i;
        aHeight = (int)k1 * i;

        System.out.println(aWidth + ", " + aHeight);

    }

    public double getRatio() {
        return ratio;
    }

    public boolean getFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public int getHertz() {
        return fps;
    }

    public void setHertz(int fps) {
        this.fps = fps;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
