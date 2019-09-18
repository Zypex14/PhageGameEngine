package RMath;

public class Point{
    public double x;
    public double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(PolarPoint p) {
        x = Math.cos(p.getTheta()) * p.getR();
        y = Math.sin(p.getTheta()) * p.getR();
    }

    public Point closestPoint(Line l) {

        return RMath.getIntersection(l, new Line(this, l.perpendicular()));

    }

    public Point closestPoint(Segment s) {
        Point cp = closestPoint(s.getLine());
        if (s.isWithin(cp))
            return cp;
        else if (RMath.dist(s.getP1(), this) < RMath.dist(s.getP2(), this))
            return s.getP1();
        else
            return s.getP2();
    }

    public Point closestPoint(Circle c) {
        Line l = new Line(this, new Point(c.getH(), c.getK()));
        Point[] i = RMath.getIntersection(c, l);
        Point closest = i[0];
        for (Point p : i)
            if (RMath.dist(p, this) < RMath.dist(closest, this))
                closest = p;

        return closest;
    }


    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
