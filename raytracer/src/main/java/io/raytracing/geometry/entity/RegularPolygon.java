package io.raytracing.geometry.entity;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.Ray;
import io.raytracing.geometry.abstractentity.GeometricObject;
import io.raytracing.geometry.abstractentity.IRotate;
import io.raytracing.geometry.abstractentity.Material;
import io.raytracing.geometry.abstractentity.Ref;

import static io.raytracing.math.MathUtil.DOUBLE_PI;

public class RegularPolygon extends GeometricObject implements IRotate {

    private Triangle[] triangles;
    private Point3d center;

    /**
     *
     * @param n - number of sides
     * @param r - radius
     * @param material - material
     */
    public RegularPolygon(Point3d center, int n, double r, Material material) {
        super(material);

        this.triangles = new Triangle[n];
        this.center = new Point3d(center);

        final double step = DOUBLE_PI / n;
        double angle = 0;
        for (int i = 0; i < n; angle += step, i++) {
            this.triangles[i] = new Triangle(
                    new Point3d(center),
                    new Point3d(center, 0, 0, angle, r),
                    new Point3d(center, 0, 0, angle + step, r), material);
        }
    }

    public void changeNormalDirection() {
        for (Triangle t: triangles) {
            t.changeNormalDircetion();
        }
    }

    public RegularPolygon(GeometricObject that) {
        super(that);
    }

    @Override
    public boolean hit(Ray ray, Ref<Double> tmin, Ref<Ray> normal) {
        double min_t = 1e10; // hugeValue TODO fix it)
        Ref<Double> cur_t = new Ref<>();
        Ref<Ray> cur_normal = new Ref<>(normal.value);
        boolean hit = false;

        for (Triangle triangle : triangles) {
            if (triangle.hit(ray, cur_t, cur_normal) && cur_t.value < min_t) {
                min_t = cur_t.value;
                normal.value = cur_normal.value;
                hit = true;
            }
        }

        if (hit)
            tmin.value = min_t;

        return hit;
    }

    @Override
    public IRotate rotate(Point3d source, double a_x, double a_y, double a_z) {
        for (Triangle triangle: triangles) {
            triangle.rotate(source, a_x, a_y, a_z);
        }
        center.rotate(source, a_x, a_y, a_z);

        return this;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        for (Triangle triangle: triangles) {
            triangle.rotate(center, a_x, a_y, a_z);
        }

        return this;
    }
}
