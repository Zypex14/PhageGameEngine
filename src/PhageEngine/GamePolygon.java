package PhageEngine;


import java.util.ArrayList;

public class GamePolygon {

    private double[] rX;
    private double[] rY;
    private double[] X;
    private double[] Y;
    private Entity e;
    private double rotR, rotD;

    public GamePolygon(double[] x, double[] y, Entity e){
        this.e = e;
        rX = x;
        rY = y;
        updateArr(0);
        rotR = 0;
        rotD = 0;
    }

    public void setRotation(double d){
        rotD = d;
        rotR = Math.toRadians(d);

        updateArr(rotR);
    }

    public void setRotationRadians(double r){
        rotR = r;
        rotD = Math.toDegrees(r);

        updateArr(rotD);
    }

    public double getRotation(){
        return rotD;
    }

    public double getRotationRadians(){
        return  rotR;
    }

    public double[] getX(){
        return X;
    }

    public double[] getY(){
        return Y;
    }

    public int getLength(){
        return X.length;
    }

    public void updateArr(double r){
        double distance;
        double radians;

        ArrayList<Double> tempX = new ArrayList<>();
        ArrayList<Double> tempY = new ArrayList<>();

        for(int i = 0; i < rX.length; i++){
            distance = Math.sqrt(Math.pow(rX[i], 2) + Math.pow(rY[i], 2));
            radians = Math.atan2(rX[i], rY[i]);

            tempX.add(i, distance * (Math.cos(r + radians)) + e.x);
            tempY.add(i, distance * (Math.sin(r + radians)) + e.y);
        }

        X = tempX.stream().mapToDouble(Double::doubleValue).toArray();
        Y = tempY.stream().mapToDouble(Double::doubleValue).toArray();

    }

}
