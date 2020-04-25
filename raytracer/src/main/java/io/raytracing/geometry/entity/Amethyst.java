package io.raytracing.geometry.entity;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.RGBColor;
import io.raytracing.geometry.Ray;
import io.raytracing.geometry.abstractentity.GeometricObject;
import io.raytracing.geometry.abstractentity.IRotate;
import io.raytracing.geometry.abstractentity.Material;
import io.raytracing.geometry.abstractentity.Ref;

import static io.raytracing.math.MathUtil.DOUBLE_PI;

public class Amethyst extends GeometricObject implements IRotate {

    private Point3d center;
    private final Triangle[] triangles = new Triangle[6];
    private final Rectangle[] rectangles = new Rectangle[6];
    private RegularPolygon base;

    private final double kHugeValue = 1e10;

    public Amethyst(Point3d lowerBaseCenter, double r, double h, Material material) {
        super(material);
        material.color = new RGBColor(128. / 255, 0, 128. / 255);

        this.center = new Point3d(lowerBaseCenter.x, lowerBaseCenter.y, lowerBaseCenter.z + h * 0.5);

        Point3d upperBaseCenter = new Point3d(lowerBaseCenter.x, lowerBaseCenter.y, lowerBaseCenter.z + h * 0.7);
        Point3d upperCenter     = new Point3d(lowerBaseCenter.x, lowerBaseCenter.y, lowerBaseCenter.z + h);

        final double step = DOUBLE_PI / 6;
        double angle = 0;
        for (int i = 0; i < 6; angle += step, i++) {
            this.triangles[i] = new Triangle(
                    new Point3d(upperCenter),
                    new Point3d(upperBaseCenter, 0, 0, angle, r),
                    new Point3d(upperBaseCenter, 0, 0, angle + step, r),
                    material);
            this.rectangles[i] = new Rectangle(
                    new Point3d(lowerBaseCenter, 0, 0, angle, r),
                    new Point3d(lowerBaseCenter, 0, 0, angle + step, r),
                    new Point3d(upperBaseCenter, 0, 0, angle, r),
                    material
            );
        }
        this.base = new RegularPolygon(new Point3d(lowerBaseCenter), 6, r, material);
        this.base.changeNormalDirection();
    }

    public Amethyst(Amethyst that) {
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

        for (Rectangle rectangle : rectangles) {
            if (rectangle.hit(ray, cur_t, cur_normal) && cur_t.value < min_t) {
                min_t = cur_t.value;
                normal.value = cur_normal.value;
                hit = true;
            }
        }

        if (base.hit(ray, cur_t, cur_normal) && cur_t.value < min_t) {
            min_t = cur_t.value;
            normal.value = cur_normal.value;
            hit = true;
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

        for (Rectangle rectangle: rectangles) {
            rectangle.rotate(source, a_x, a_y, a_z);
        }
        base.rotate(source, a_x, a_y, a_z);
        center.rotate(source, a_x, a_y, a_z);

        return this;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        for (Triangle triangle: triangles) {
            triangle.rotate(center, a_x, a_y, a_z);
        }

        for (Rectangle rectangle: rectangles) {
            rectangle.rotate(center, a_x, a_y, a_z);
        }
        base.rotate(center, a_x, a_y, a_z);

        return this;
    }
}
