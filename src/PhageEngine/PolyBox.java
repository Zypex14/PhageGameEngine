package PhageEngine;

import RMath.*;

import java.util.ArrayList;
import java.util.Arrays;

public class PolyBox {

    public double x, y, rot, size;
    protected ArrayList<ConvexPoly> subPolys;
    protected ArrayList<Point> points;

    public PolyBox(Point[] points, double x, double y, double rot, double size) {
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.size = size;
        this.points = toPolygon(points);
        convexify(this.points);
    }

    public PolyBox(double x, double y, double rot, double size, Point... points){
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.size = size;
        this.points = toPolygon(points);
        convexify(this.points);
    }

    public PolyBox(ArrayList<Point> points, double x, double y, double rot, double size) {
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.size = size;
        this.points = toPolygon(points);
        convexify(this.points);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public boolean isIntersecting(Point point) {

        point = rotate(point, -rot);
        point.x -= x;
        point.y -= y;
        point.x *= 1.0 / size;
        point.y *= 1.0 / size;

        for (ConvexPoly p : getSubPolys()) {
            if (p.isInside(point)) {
                return true;
            }
        }

        return false;

    }

    public ArrayList<Point> getActualPoints() {

        ArrayList<Point> out = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            Point point = rotate(points.get(i), rot);
            point.x *= size;
            point.y *= size;
            point.x += x;
            point.y += y;
            out.add(point);
        }

        return out;

    }

    public Point rotate(Point point, double angle) {
        PolarPoint polar = new PolarPoint(point);
        polar.theta += angle;

        return new Point(polar);

    }

    public ArrayList<ConvexPoly> getSubPolys() {
        return subPolys;
    }

    private void convexify(ArrayList<Point> points) {
        ArrayList<ArrayList<Point>> polys = new ArrayList<>();
        subPolys = new ArrayList<>();
        polys.add(points);

//        This will keep repeating until there are no concave polygons left
        while (polys.size() > 0) {
            System.out.println("Iteration begins");
            ArrayList<Integer> concave = new ArrayList<>();
            ArrayList<Double> thetas;
            System.out.println("Listing points: ");
            for (Point point : polys.get(0)) {
                System.out.println();
            }

            split:
            {
                System.out.println("Getting angles...");
                thetas = polyToAngles(polys.get(0));

                System.out.println("Successful!");
//                Creates a list of concave angles
                for (int j = 0; j < thetas.size(); j++) {
                    if (thetas.get(j) > 180) {
                        concave.add(j);
                    }
                    if (thetas.get(j) == 180) {
                        thetas.remove(j);
                        polys.get(0).remove(j);
                    }
                }

                System.out.print("Found " + concave.size() + " concave angles:\n[");
                for (double d : thetas) {
                    System.out.print(d + ", ");
                }
                System.out.println("]");

                System.out.println("Checking if convex...");
//                If the polygon is convex, add it to the final list
                if (concave.size() == 0) {
                    subPolys.add(new ConvexPoly(polys.get(0)));
                    polys.remove(0);
                    System.out.println("Convex found, adding it to final list");
                    break split;
                }
                System.out.println("Not a convex, splitting now");

                ArrayList<Point> currentPoly = polys.get(0);

                Point p1;
                Point p2;

                if (concave.size() > 1) {
                    System.out.println("More than one concave angle found, generating splitting line");

                    for (int j = 1; j < concave.size(); j++) {
                        System.out.println("Testing split line " + j);
                        p1 = currentPoly.get(concave.get(0));
                        p2 = currentPoly.get(concave.get(j));
                        Segment s1 = new Segment(p1, p2, false);

//                    Checking if the new line intersects any of the sides
                        boolean intersected = false;
                        for (int i = 0; i < currentPoly.size() - 1; i++) {
                            Point l2p1 = currentPoly.get(i);
                            Point l2p2 = currentPoly.get(i + 1);
                            Segment s2 = new Segment(l2p1, l2p2, false);

                            if (!Double.isNaN(RMath.getIntersection(s1, s2).x) && RMath.getIntersection(s1, s2).x != Double.POSITIVE_INFINITY) {
                                intersected = true;
                                System.out.println("Test failed. Trying another point");
                                break;
                            }
                        }
//                        If the new line doesn't intersect the polygon, then create two new polygons
                        if (!intersected) {
                            System.out.println("Line passed. Splitting polygon");

                            ArrayList<ArrayList<Point>> newPolys = splitPoly(currentPoly, concave.get(0), concave.get(j));

                            polys.remove(0);
                            polys.add(toPolygon(newPolys.get(0)));
                            polys.add(toPolygon(newPolys.get(1)));

                            System.out.println("Points separated, both new polys were added to processing");
                            break split;

                        }
                    }

                    System.out.println("All lines failed. Trying any corner instead");

                    for (int j = 1; j < currentPoly.size(); j++) {
                        int index = (concave.get(0) + j) % currentPoly.size();
                        p1 = currentPoly.get(concave.get(0));
                        p2 = currentPoly.get(index);
                        Segment s1 = new Segment(p1, p2, false);

                        boolean intersected = false;
                        for (int i = 0; i < currentPoly.size() - 1; i++) {
                            Point l2p1 = currentPoly.get(i);
                            Point l2p2 = currentPoly.get(i + 1);
                            Segment s2 = new Segment(l2p1, l2p2, false);

                            if (!Double.isNaN(RMath.getIntersection(s1, s2).x) && RMath.getIntersection(s1, s2).x != Double.POSITIVE_INFINITY) {
                                intersected = true;
                                System.out.println("Test failed. Trying another point");
                                break;
                            }
                        }

                        if (!intersected) {
                            System.out.println("Line passed, splitting polygon");

                            ArrayList<ArrayList<Point>> newPolys = splitPoly(currentPoly, concave.get(0), index);

                            polys.remove(0);
                            polys.add(toPolygon(newPolys.get(0)));
                            polys.add(toPolygon(newPolys.get(1)));

                            System.out.println("Points separated, both new polys were added to processing");
                            break split;

                        }

                    }

                    System.err.println("Fatal error. All lines failed. Terminating program");
                    System.exit(-1);

                } else {
                    System.out.println("Only one concave angle found, splitting polygon");

                    ArrayList<ArrayList<Point>> newPolys = splitPoly(currentPoly, concave.get(0), (concave.get(0) + 2) % (currentPoly.size() - 1));

                    polys.remove(0);
                    polys.add(toPolygon(newPolys.get(0)));
                    polys.add(toPolygon(newPolys.get(1)));

                    System.out.println("Points separated, both new polys were added to processing");
                    break split;

                }

            }


            System.out.println("Amount of to-be-processed polygons: " + polys.size() + "\n\n");
            if(polys.size() == 0){
                System.out.println("Finished with " + getSubPolys().size() + " sub-polygons");
            }
//            counter ++;
//            if(counter > 15) {
//                System.exit(69);
//            }
        }

    }

    protected ArrayList<Point> toPolygon(Point[] array) {
        return toPolygon(new ArrayList<>(Arrays.asList(array)));
    }

    protected ArrayList<Point> toPolygon(ArrayList<Point> array) {

        ArrayList<Point> out = array;

        if (!out.get(0).toString().equals(out.get(out.size() - 1).toString())) {
            out.add(new Point(out.get(0).x, out.get(0).y));
        }

        return out;

    }

    private ArrayList<ArrayList<Point>> splitPoly(ArrayList<Point> polygon, int p1Index, int p2Index) {
        ArrayList<Point> poly1 = new ArrayList<>();
        ArrayList<Point> poly2 = new ArrayList<>();

        if (p2Index < p1Index) {
            int temp1 = p1Index;
            p1Index = p2Index;
            p2Index = temp1;
        }

        System.out.println("Split line: " + polygon.get(p1Index).toString() + ", " + polygon.get(p2Index).toString());
        System.out.println("Separating points");
//                        Separate the points on each side of the line
        for (int i = 0; i < polygon.size(); i++) {
            if (i >= p1Index && i <= p2Index) {
                poly1.add(polygon.get(i));
            }

            if (i <= p1Index || i >= p2Index) {
                poly2.add(polygon.get(i));
            }
        }

        ArrayList<ArrayList<Point>> out = new ArrayList<>();
        out.add(toPolygon(poly1));
        out.add(toPolygon(poly2));

        return out;

    }


    private ArrayList<Double> polyToAngles(ArrayList<Point> polygon) {

        ArrayList<Point> points = toPolygon(polygon);

//        We will now get an array of thetas from the sides of the polygon
        ArrayList<Double> thetas = new ArrayList<>();
//            Here is the actual calculation
        double theta = (RMath.angle(polygon.get(0), polygon.get(1)) * 180) / Math.PI;
        double lastt;
        double interior = theta;
        thetas.add(interior);
        for (int i = 1; i < points.size() - 1; i++) {
//            This is finding the change in the two point positions so we can use atan2 to find theta
//            Here is the actual calculation
            lastt = theta;

            theta = (RMath.angle(points.get(0), points.get(1)) * 180) / Math.PI;
            interior = ((180 - lastt + theta) % 360 + 360) % 360;
            thetas.add(interior);
        }

        interior = ((180 - theta + thetas.get(0)) % 360 + 360) % 360;
        thetas.set(0, interior);

        int concave = 0;

        for (double d : thetas) {
            if (d > 180) {
                concave++;
            }
        }

        double total = 0;
        for (double angle : thetas) {
            total += angle;
        }

        if (total == (polygon.size() - 2) * 180) {
            System.out.println("Exterior angles calculated. Reversing");
            for (int i = 0; i < thetas.size(); i++) {
                thetas.set(i, 360 - thetas.get(i));
            }
        }

        return thetas;

    }


    public class ConvexPoly {

        private ArrayList<Side> sides;
        private ArrayList<Point> points;
        private Point inPoint;

        public ConvexPoly(ArrayList<Point> points) {

            inPoint = new Point(0,0);
            this.points = points;

            for (int i = 0; i < points.size() - 1; i++) {
                inPoint.x += points.get(i).x;
                inPoint.y += points.get(i).y;
            }

            inPoint.x /= points.size();
            inPoint.y /= points.size();

            sides = new ArrayList<>();

            for (int i = 0; i < points.size() - 1; i++) {
                sides.add(new Side(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y, inPoint));
            }


        }

        public ArrayList<Point> getPoints() {
            return points;
        }

        public Point getInsidePoint() {
            return inPoint;
        }

        public boolean isInside(Point p) {

            for (Side s : sides) {
                if (!s.checkPoint(p)) {
                    return false;
                }
            }

            return true;

        }

    }

    private class Side {

        private double[] p1, p2;
        private double m, b;
        private boolean larger;
        private boolean vertical;

        public Side(double x1, double y1, double x2, double y2, Point reference) {

            p1 = new double[]{x1, y1};
            p2 = new double[]{x2, y2};

            m = (y2 - y1) / (x2 - x1);
            b = m * -1 * x1 + y1;

            vertical = x1 == x2;

            if (vertical) {

                if (reference.x > x1) {
                    larger = true;
                } else {
                    larger = false;
                }

            } else {

                if (reference.y > m * reference.x + b) {
                    larger = true;
                } else {
                    larger = false;
                }
            }

        }

        public double[] getStart() {
            return p1;
        }

        public double[] getEnd() {
            return p2;
        }

        public boolean checkPoint(Point p) {

            double x = p.x;
            double y = p.y;

            if (vertical) {

                if (larger) {
                    if (x >= p1[0]) {
                        return true;
                    }
                } else {
                    if (x <= p1[0]) {
                        return true;
                    }
                }

            } else {
                if (larger) {
                    if (y >= m * x + b) {
                        return true;
                    }
                } else {
                    if (y <= m * x + b) {
                        return true;
                    }
                }
            }

            return false;
        }

    }

}

