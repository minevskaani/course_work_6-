package io.raytracing.geometry.abstractentity;

import io.raytracing.geometry.RGBColor;

public class Material {
    public double diffuse_coef;         // коэффициент диффузного отражения
    public double reflect_coef;         // коэффициент зеркального отражения
    public int specular;                // степень косинуса для блестящих поверхностей
    public double refract_coef;         // коэффициент пропускания
    public double eta;
    public RGBColor color;


    public Material() {
        this.color = RGBColor.BLACK;
        this.diffuse_coef = 0.01; // чем меньше, тем больше "маттовость"
        this.reflect_coef = 0.20; //
        this.specular = 125; // чем меньше, тем больше блик
        this.refract_coef = 0.79;
        //eta = 1.0;
        this.eta = 1.0 / 1.42957113;
    }

    public Material(RGBColor c, double diffuse_coef, double reflect_coef, int specular, double refract_coef, double eta) {
        double tmp = diffuse_coef + reflect_coef + refract_coef;
        if (tmp > 1.0) {
            diffuse_coef /= tmp;
            reflect_coef /= tmp;
            refract_coef /= tmp;
        }

        this.color = new RGBColor(c);
        this.diffuse_coef = diffuse_coef;
        this.reflect_coef = reflect_coef;
        this.specular = specular;
        this.refract_coef = refract_coef;
        this.eta = eta;
    }

    public Material(double d, double g, int s, double r, double e) {
        double tmp = d + g + r;
        if (tmp > 1.0) {
            d /= tmp;
            g /= tmp;
            r /= tmp;
        }

        this.color = new RGBColor(RGBColor.BLACK);
        this.diffuse_coef = d;
        this.reflect_coef = g;
        this.specular = s;
        this.refract_coef = r;
        this.eta = e;
    }

    public Material(Material that) {
        this.color = new RGBColor(that.color);
        this.diffuse_coef = that.diffuse_coef;
        this.reflect_coef = that.reflect_coef;
        this.specular = that.specular;
        this.refract_coef = that.refract_coef;
        this.eta = that.eta;
    }

    public static Material createGlass() {
        return new Material(0.01, 0.20, 125, 0.79, 1.0 / 1.5);
    }

    public static Material createMetal() {
        return new Material(0.1, 0.7, 50, 0.0, 1.5);
    }

    public static Material createMirror() {
        return new Material(0.0, 1.0, 50, 0.0, 1.5);
    }

    public static Material createIvory() {
        return new Material(0.6, 0.4, 50, 0.0, 1.5);
    }

    public static Material createPlastic() {
        return new Material(0.8, 0.2, 100, 0.0, 1.5);
    }

    public static Material createRubber() {
        return new Material(0.99, 0.01, 10, 0.0, 1.5);
    }
}
