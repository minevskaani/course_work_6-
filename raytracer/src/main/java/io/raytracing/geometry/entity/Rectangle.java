package io.raytracing.geometry.entity;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.Ray;
import io.raytracing.geometry.Vector3d;
import io.raytracing.geometry.abstractentity.GeometricObject;
import io.raytracing.geometry.abstractentity.IRotate;
import io.raytracing.geometry.abstractentity.Material;
import io.raytracing.geometry.abstractentity.Ref;

public class Rectangle extends GeometricObject implements IRotate {
    private Point3d A; // p0
    private Vector3d a, b;
    private double da, db;
    private Vector3d localNormal;

    private Point3d center;
    private Point3d B, C;

    public Rectangle(Point3d A, Point3d B, Point3d C, Material material) {
        super(material);

        init(A, B, C);
    }

    public Rectangle(Rectangle that) {
        super(that);

        this.A = new Point3d(that.A);
        this.a = new Vector3d(that.a);
        this.b = new Vector3d(that.b);
        this.da = that.da;
        this.db = that.db;
        this.localNormal = new Vector3d(that.localNormal);
        this.center = new Point3d(that.center);
        this.A = new Point3d(that.A);
        this.B = new Point3d(that.B);
        this.C = new Point3d(that.C);
    }

    public void setPoint(Point3d point) {
        this.A = point;
    }

    @Override
    public boolean hit(Ray ray, Ref<Double> tmin, Ref<Ray> normal) {
        double t = (A.minus(ray.origin)).skalqrnoePr(localNormal) / (localNormal.skalqrnoePr(ray.direction));
        if ( t <= KEps)
            return false;
        Point3d p = ray.origin.plus(ray.direction.times(t));
        Vector3d d = p.minus(A);

        double check_a = d.skalqrnoePr(a);
        if (check_a < 0 || check_a > da)
            return false;

        double check_b = d.skalqrnoePr(b);
        if (check_b < 0 || check_b > db)
            return false;

        tmin.value = t;
        normal.value.origin = p;
        normal.value.direction = localNormal;

        return true;
    }

    @Override
    public IRotate rotate(Point3d source, double a_x, double a_y, double a_z) {
        A.rotate(source, a_x, a_y, a_z);
        B.rotate(source, a_x, a_y, a_z);
        C.rotate(source, a_x, a_y, a_z);
        init(A, B, C);

        return this;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        A.rotate(center, a_x, a_y, a_z);
        B.rotate(center, a_x, a_y, a_z);
        C.rotate(center, a_x, a_y, a_z);
        init(A, B, C);

        return this;
    }

    private void init(Point3d A, Point3d B, Point3d C) {
        this.A = new Point3d(A);
        this.B = new Point3d(B);
        this.C = new Point3d(C);

        this.a = B.minus(A);
        this.b = C.minus(A);

        this.da = this.a.length() * this.a.length();
        this.db = this.b.length() * this.b.length();

        this.localNormal = a.vectornoePr(b).normalize();

        this.center = new Point3d(
                (B.x + C.x) / 2,
                (B.y + C.y) / 2,
                (B.z + C.z) / 2
        );

    }
}
