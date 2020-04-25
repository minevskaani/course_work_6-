package io.raytracing.geometry;

import io.raytracing.math.MathUtil;

public class RGBColor {
    public double r, g, b;

    public static final RGBColor WHITE = new RGBColor(1.);
    public static final RGBColor AMBIENT = new RGBColor(0.4);
    public static final RGBColor BLACK = new RGBColor(0.);

    private final double ONE_THIRD = 1. / 3;


    public RGBColor() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public RGBColor(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGBColor(double value) {
        this.r = value;
        this.g = value;
        this.b = value;
    }

    public RGBColor(RGBColor that) {
        this.r = that.r;
        this.g = that.g;
        this.b = that.b;
    }

    public RGBColor plus(RGBColor that) {
        return new RGBColor(r + that.r, g + that.g, b + that.b);
    }

    public RGBColor times(RGBColor that) {
        return new RGBColor(r * that.r, g * that.g, b * that.b);
    }

    public RGBColor times(double a) {
        return new RGBColor(r * a, g * a, b * a);
    }

    public RGBColor divide(double a) {
        // TODO check const method in c++?
        if (MathUtil.isZero(a))
            return new RGBColor(1);

        return new RGBColor(r / a, g / a, b / a);
    }

    public double avgColor() {
        return ONE_THIRD * (r + g + b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RGBColor)) return false;

        RGBColor rgbColor = (RGBColor) o;

        return MathUtil.isZero(r - rgbColor.r) &&
               MathUtil.isZero(g - rgbColor.g) &&
               MathUtil.isZero(b - rgbColor.b);
    }

}
