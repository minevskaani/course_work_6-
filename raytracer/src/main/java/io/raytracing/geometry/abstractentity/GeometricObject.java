package io.raytracing.geometry.abstractentity;

import io.raytracing.geometry.Ray;

public abstract class GeometricObject {
    protected Material material;
    protected final double KEps = 0.001;

    public GeometricObject(Material material) {
        this.material = material;
    }

    public GeometricObject(GeometricObject that) {
        if (that.material != null) {
            this.material = new Material(that.material);
        } else {
            this.material = null;
        }
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public abstract boolean hit(Ray ray, Ref<Double> tmin, Ref<Ray> normal);
    boolean hit(Ray ray) {
        double dummy1 = 0;
        Ray dummy2 = new Ray();

        return this.hit(ray, new Ref(dummy1), new Ref(dummy2));
    }
}
