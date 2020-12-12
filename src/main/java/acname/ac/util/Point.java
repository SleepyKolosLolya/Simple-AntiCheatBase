package acname.ac.util;

public class Point implements Cloneable {
    private double p1;
    private double p2;

    public Point() {
    }

    public Point(double p1, double p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }


    public void merge(Point p) {
        this.p1 += p.p1;
        this.p2 += p.p2;
    }

    public double atan2(Point point) {
        return Math.atan2(point.p1 - p1, point.p2 - p2);
    }

    @Override
    public String toString() {
        return "Point{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }

    @Override
    protected Point clone() {
        return new Point(p1, p2);
    }
}

