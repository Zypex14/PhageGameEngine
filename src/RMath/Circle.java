package RMath;

public class Circle {
    private double h;
    private double k;
    private double r;

    public Circle(double h, double k, double r) {
        this.h = h;
        this.k = k;
        this.r = r;
    }

    public Circle(Point center, double r) {
        this.h = center.x;
        this.k = center.y;
        this.r = r;
    }

    public double getH() {
        return h;
    }

    public double getK() {
        return k;
    }

    public double getR() {
        return r;
    }
}
