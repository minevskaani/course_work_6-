package io.raytracing.geometry.world;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.RGBColor;
import io.raytracing.geometry.Ray;
import io.raytracing.geometry.Vector3d;
import io.raytracing.geometry.abstractentity.GeometricObject;
import io.raytracing.geometry.abstractentity.IDrawer;
import io.raytracing.geometry.abstractentity.Light;
import io.raytracing.geometry.abstractentity.Tracer;

public class World {
    public class ThreadParams {
        public int start;
        public int end;
    }

    public Tracer tracer;
    public IDrawer drawWidget = null;

    private int canvasHeight;
    private int canvasWidth;
    private int pixelSize;
    private double xCoef;
    private double yCoef;
    private final WorldData data = new WorldData();
    private ViewField vf = new VsiewField();

    private final int THREADS_COUNT = 4;


    public World(int w, int h, int s) {
        this.canvasWidth = w;
        this.canvasHeight = h;
        this.pixelSize = s;

        this.data.backgroundColor = RGBColor.BLACK;
        this.tracer = new Tracer();
        this.canvasWidth--;
        this.canvasHeight--;
        this.xCoef = (canvasWidth - 1.) * 0.5;
        this.yCoef = (canvasHeight - 1.) * 0.5;
    }

    public void addObject(GeometricObject o) {
        data.objects.add(o);
    }

    public void removeObject(int i) {
        data.objects.remove(i);
    }

    public void addLight(Light l) {
        data.lights.add(l);
    }

    public void removeLight(int i) {
        data.lights.remove(i);
    }

    public int getObjectSize() {
        return data.objects.size();
    }

    public int getLightsSize() {
        return data.lights.size();
    }


    public void render(int zoom) {

        int start = 0;
        int end = 0;
        double dcw = canvasWidth;
        double pstep = dcw / THREADS_COUNT;
        int step = (int)Math.round(pstep);

        for (int i = 0; i < THREADS_COUNT; i++)
        {
            end = start + step;
            ThreadParams params = new ThreadParams();
            params.start = start;
            params.end = end;
            // Thread for renderColumns params and zoom
            renderColumns(this, params, zoom);

            start += step;
        }
        /*
        for (int i = 0; i < THREADS_COUNT; i++)
        {
            Join all threads
        }
         */

        if (drawWidget != null) {
            drawWidget.repaint();
        }
    }

    private void renderColumns(World w, ThreadParams params, int z) {
        Ray ray = new Ray();
        ray.origin = new Point3d(0, 0, z);

        double r, c;
        for (int x = params.start; x < params.end; x++ )
        {
            for (int y = 0; y <= w.canvasHeight; y++ )
            {
                r = w.pixelSize * (x - w.xCoef);
                c = w.pixelSize * (y - w.yCoef);
                ray.direction = new Vector3d((r * w.vf.width) / w.canvasWidth, (c * w.vf.height) / w.canvasHeight, w.vf.d);
                ray.direction.normalize();
                RGBColor pixel_color = w.tracer.traceRay(ray, data, 2);
                if (w.drawWidget != null)
                    w.drawWidget.colorPixel(x, y, pixel_color);
            }
        }
    }
}
