package io.raytracing.geometry;

public class Point2d {
    public double x;
    public double y;

    public Point2d() {
        this.x = 0;
        this.y = 0;
    }

    public Point2d(double value) {
        this.x = value;
        this.y = value;
    }

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2d(Point2d that) {
        this.x = that.x;
        this.y = that.y;
    }

    public Point2d times(double a) {
        return new Point2d(x * a, y * a);
    }
}
