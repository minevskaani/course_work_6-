package io.raytracing.geometry.world;

import io.raytracing.geometry.RGBColor;
import io.raytracing.geometry.abstractentity.GeometricObject;
import io.raytracing.geometry.abstractentity.Light;

import java.util.ArrayList;
import java.util.List;

public class WorldData {
    public final List<GeometricObject> objects = new ArrayList<>();
    public final List<Light> lights = new ArrayList<>();
    public RGBColor backgroundColor; // TODO: init

    public WorldData() {

    }
}
