package io.raytracing.geometry.abstractentity;

import io.raytracing.geometry.RGBColor;

public interface IDrawer {
    void colorPixel(int x, int y, RGBColor pixelColor);
    void repaint();
}
