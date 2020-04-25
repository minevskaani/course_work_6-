package io.raytracing.geometry;

import io.raytracing.geometry.abstractentity.IRotate;

public class Point3d implements IRotate {
    public double x;
    public double y;
    public double z;

    public Point3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point3d(double value) {
        this.x = value;
        this.y = value;
        this.z = value;
    }

    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3d(Point3d source, double a_x, double a_y, double a_z, double length) {
        this.x = source.x + length;
        this.y = source.y;
        this.z = source.z;

        rotate(source, a_x, a_y, a_z);
    }

    public Point3d(Point3d that) {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    public Vector3d minus(Point3d p) {
        return new Vector3d(x - p.x, y - p.y, z - p.z);
    }

    public Point3d minus(Vector3d v) {
        return new Point3d(x - v.x, y - v.y, z - v.z);
    }

    public Point3d plus(Vector3d v) {
        return new Point3d(x + v.x, y + v.y, z + v.z);
    }

    public Point3d times(double a) {
        return new Point3d(x * a, y * a, z * a);
    }

    public double squared_distance(Point3d p) {
        double  dx = p.x - x,
                dy = p.y - y,
                dz = p.z - z;

        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Point3d p) {
        double  dx = p.x - x,
                dy = p.y - y,
                dz = p.z - z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public IRotate rotate(Point3d center, double angleX, double angleY, double angleZ) {
        rotateX(center, angleX);
        rotateY(center, angleY);
        rotateZ(center, angleZ);

        return this;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        return this;
    }

    public void rotateX(Point3d center, double angle) {
        double newY = (y - center.y) * Math.cos(angle) - (z - center.z) * Math.sin(angle) + center.y;
        double newZ = (y - center.y) * Math.sin(angle) + (z - center.z) * Math.cos(angle) + center.z;

        this.y = newY;
        this.z = newZ;
    }

    public void rotateY(Point3d center, double angle) {
        double newX =  (x - center.x) * Math.cos(angle) + (z - center.z) * Math.sin(angle) + center.x;
        double newZ = -(x - center.x) * Math.sin(angle) + (z - center.z) * Math.cos(angle) + center.z;

        this.x = newX;
        this.z = newZ;
    }

    public void rotateZ(Point3d center, double angle) {
        double newX = (x - center.x) * Math.cos(angle) - (y - center.y) * Math.sin(angle) + center.x;
        double newY = (x - center.x) * Math.sin(angle) + (y - center.y) * Math.cos(angle) + center.y;

        this.x = newX;
        this.y = newY;
    }
}
