package io.raytracing.geometry.abstractentity;

import io.raytracing.geometry.Point3d;

public interface IRotate {
    IRotate rotate(Point3d source, double a_x, double a_y, double a_z);
    IRotate rotate(double a_x, double a_y, double a_z);
}
