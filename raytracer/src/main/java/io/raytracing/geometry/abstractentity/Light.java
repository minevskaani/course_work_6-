package io.raytracing.geometry.abstractentity;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.RGBColor;
import io.raytracing.geometry.Vector3d;

public class Light {
    private double intensity;
    private RGBColor color;
    private Point3d possition;

    public Light() {
        this.intensity = 0.1;
        color = RGBColor.WHITE;
        possition = new Point3d(0, -10, 0);
    }

    public Light(double intensity, RGBColor color, Point3d possition) {
        this.intensity = intensity;
        this.color = new RGBColor(color);
        this.possition = new Point3d(possition);
    }

    public Light(Light that) {
        this.intensity = that.intensity;
        this.color = new RGBColor(that.color);
        this.possition = new Point3d(that.possition);
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public void setColor(RGBColor color) {
        this.color = color;
    }

    public Point3d getPossition() {
        return possition;
    }

    public void setPossition(Point3d possition) {
        this.possition = possition;
    }

    public Vector3d getLightRay(Point3d p) {
        return possition.minus(p);
    }

    public RGBColor getLight() {
        return color.times(intensity);
    }
}
