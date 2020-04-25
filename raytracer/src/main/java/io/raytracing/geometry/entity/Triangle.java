package io.raytracing.geometry.entity;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.Ray;
import io.raytracing.geometry.Vector3d;
import io.raytracing.geometry.abstractentity.GeometricObject;
import io.raytracing.geometry.abstractentity.IRotate;
import io.raytracing.geometry.abstractentity.Material;
import io.raytracing.geometry.abstractentity.Ref;

public class Triangle extends GeometricObject implements IRotate {

    private Point3d v0, v1, v2, center;
    private Vector3d local_normal;

    public Triangle(Material material) {
        super(material);
        this.v0 = new Point3d(0);
        this.v1 = new Point3d(0);
        this.v2 = new Point3d(1, 0, 0);

        initLocalNormal();
        initCenter();
    }

    public Triangle(Point3d v0, Point3d v1, Point3d v2, Material material) {
        super(material);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;

        initLocalNormal();
        initCenter();
    }

    public Triangle(Triangle that) {
        super(that);
        this.v0 = that.v0;
        this.v1 = that.v1;
        this.v2 = that.v2;
        this.local_normal = that.local_normal;
    }

    public Vector3d getLocal_normal() {
        return local_normal;
    }

    public void changeNormalDircetion() {
        this.local_normal = this.local_normal.negative();
    }

    @Override
    public boolean hit(Ray ray, Ref<Double> tmin, Ref<Ray> normal) {
        double a = v0.x -v1.x, b = v0.x - v2.x, c = ray.direction.x, d = v0.x - ray.origin.x;
        double e = v0.y - v1.y, f =v0.y - v2.y, g = ray.direction.y, h = v0.y - ray.origin.y;
        double i = v0.z - v1.z, j = v0.z - v2.z, k = ray.direction.z, v = v0.z - ray.origin.z;

        double m = f * k - g * j, n = h * k - g * v, p = f * v - h * j;
        double q = g * i - e * k, s = e * j - f * i;

        double inv_d = 1.0 / (a * m + b * q + c * s);
        double e1 = d * m - b * n - c * p;
        double beta = e1 * inv_d;
        if (beta < 0.0)
            return false;

        double r = e * v - h * i;
        double e2 = a * n + d * q + c * r;
        double gamma = e2 * inv_d;
        if (gamma < 0.0)
            return false;
        if (gamma + beta > 1.0)
            return false;

        double e3 = a * p - b * r + d * s;
        double t = e3 * inv_d;
        if (t < KEps)
            return false;

        tmin.value = t;
        normal.value.direction = local_normal;
        normal.value.origin = ray.origin.plus(ray.direction.times(t));

        return true;
    }

    @Override
    public IRotate rotate(Point3d source, double a_x, double a_y, double a_z) {
        v0.rotate(source, a_x, a_y, a_z);
        v1.rotate(source, a_x, a_y, a_z);
        v2.rotate(source, a_x, a_y, a_z);

        initLocalNormal();
        initCenter();

        return this;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        v0.rotate(center, a_x, a_y, a_z);
        v1.rotate(center, a_x, a_y, a_z);
        v2.rotate(center, a_x, a_y, a_z);

        initLocalNormal();
        initCenter();

        return this;
    }

    private void initLocalNormal() {
        local_normal = v1.minus(v0).vectornoePr(v2.minus(v0));
        local_normal.normalize();
    }

    private void initCenter() {
        center = new Point3d(
                (v0.x + v1.x + v2.x) / 3,
                (v0.y + v1.y + v2.y) / 3,
                (v0.z + v1.z + v2.z) / 3
        );
    }
}
