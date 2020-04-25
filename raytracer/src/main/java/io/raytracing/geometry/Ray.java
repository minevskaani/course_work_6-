package io.raytracing.geometry;

public class Ray {
    public Point3d origin;           // origin
    public Vector3d direction;          // direction

    public Ray() {
        this.origin = new Point3d(0.0);
        this.direction = new Vector3d(0.0, 0.0, 1.0);
    }

    public Ray(Point3d origin, Vector3d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Ray(Ray ray) {
        this.origin = ray.origin;
        this.direction = ray.direction;
    }
}
