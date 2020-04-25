package io.raytracing.ui.config;

import io.raytracing.geometry.abstractentity.Material;

public class Config {
    public static final Material glass   = new Material(0.01, 0.20, 125, 0.79, 1.0 / 1.5);
    public static final Material metal   = new Material(0.1, 0.7, 50, 0.0, 1.5);
    public static final Material mirror  = new Material(0.0, 1.0, 50, 0.0, 1.5);
    public static final Material ivory   = new Material(0.6, 0.4, 50, 0.0, 1.5);
    public static final Material plastic = new Material(0.8, 0.2, 100, 0.0, 1.5);
    public static final Material rubber  = new Material(0.99, 0.01, 10, 0.0, 1.5);

    public static int zoom = -10;
}
