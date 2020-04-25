package io.raytracing.geometry;

import io.raytracing.geometry.abstractentity.IRotate;
import io.raytracing.math.MathUtil;

public class Vector3d implements IRotate {
    public double x, y, z;

    public Vector3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3d(double value) {
        this.x = value;
        this.y = value;
        this.z = value;
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(Vector3d that) {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    public Vector3d(Point3d that) {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double sqaredLength() {
        return x * x + y * y + z * z;
    }

    public Vector3d times(double a) {
        return new Vector3d(x * a, y * a, z * a);
    }

    public Vector3d divide(double a) {
        return this.times(1 / a);
    }

    public Vector3d plus(Vector3d v) {
        return new Vector3d(x + v.x, y + v.y, z + v.z);
    }

    public Vector3d negative() {
        return new Vector3d(-x, -y, -z);
    }

    public Vector3d minus(Vector3d v) {
        return new Vector3d(x - v.x, y - v.y, z - v.z);
    }

    public double skalqrnoePr(Vector3d v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3d vectornoePr(Vector3d v) {
        return new Vector3d(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public Vector3d normalize() {
        double inv_len = 1.0 / (this.length());
        x *= inv_len;
        y *= inv_len;
        z *= inv_len;

        return this;
    }

    public Vector3d getNormal() {
        double inv_len = 1.0 / (this.length());

        return new Vector3d(x * inv_len, y * inv_len, z * inv_len);
    }

    public boolean isNull() {
        return MathUtil.isZero(x) && MathUtil.isZero(y) && MathUtil.isZero(z);
    }


    @Override
    public IRotate rotate(Point3d source, double a_x, double a_y, double a_z) {
        rotateX(source, a_x);
        rotateY(source, a_y);
        rotateZ(source, a_z);

        return null;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        return null;
    }

    private void rotateX(Point3d center, double angle) {
        double newY = (y - center.y) * Math.cos(angle) - (z - center.z) * Math.sin(angle) + center.y;
        double newZ = (y - center.y) * Math.sin(angle) + (z - center.z) * Math.cos(angle) + center.z;

        this.y = newY;
        this.z = newZ;
    }

    private void rotateY(Point3d center, double angle) {
        double newX =  (x - center.x) * Math.cos(angle) + (z - center.z) * Math.sin(angle) + center.x;
        double newZ = -(x - center.x) * Math.sin(angle) + (z - center.z) * Math.cos(angle) + center.z;

        this.x = newX;
        this.z = newZ;
    }

    private void rotateZ(Point3d center, double angle) {
        double newX = (x - center.x) * Math.cos(angle) - (y - center.y) * Math.sin(angle) + center.x;
        double newY = (x - center.x) * Math.sin(angle) + (y - center.y) * Math.cos(angle) + center.y;

        this.x = newX;
        this.y = newY;
    }

    @Override
    public String toString() {
        return "Vector3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
