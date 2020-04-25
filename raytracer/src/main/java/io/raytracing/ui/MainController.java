package io.raytracing.ui;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.RGBColor;
import io.raytracing.geometry.abstractentity.*;
import io.raytracing.geometry.entity.Amethyst;
import io.raytracing.geometry.entity.Rectangle;
import io.raytracing.geometry.entity.Triangle;
import io.raytracing.geometry.world.World;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static io.raytracing.math.MathUtil.DOUBLE_PI;

public class MainController implements Initializable, IRotate {

    @FXML
    public Canvas canvas;
    private World world;
    private List<IRotate> rotatebleObejcs = new ArrayList<>();

    // For animation
    private AnimationTimer timer;
    private final Timeline timeline = new Timeline();
    private final double angle = Math.PI / 45.;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println((int)canvas.getWidth());
        System.out.println((int)canvas.getHeight());
        world = new World((int)canvas.getWidth(), (int)canvas.getHeight(), 1);
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
        world.drawWidget = new IDrawer() {
            @Override
            public void colorPixel(int x, int y, RGBColor pixelColor) {
                pw.setColor(x, y, new Color(pixelColor.r, pixelColor.g, pixelColor.b, 1));
            }

            @Override
            public void repaint() {

            }
        };

        Amethyst amethyst = new Amethyst(new Point3d(45, -10, -20), 4, 30, Material.createGlass());
        amethyst.rotate(Math.PI * 0.5, 0, 0);
        Amethyst amethyst_behind = new Amethyst(new Point3d(45, -10, 20), 4, 30, Material.createGlass());
        Amethyst amethyst1 = new Amethyst(new Point3d(15, -5, 0), 5, 40, Material.createGlass());
        Amethyst amethyst2 = new Amethyst(new Point3d(-30, -5, 0), 4, 30, Material.createGlass());
        Amethyst amethyst2_behind = new Amethyst(new Point3d(-30, -5, 40), 4, 30, Material.createGlass());
        amethyst2_behind.rotate(Math.PI * 0.4, 0.1, 0.1);

        Material sideMaterial = Material.createMetal();
        sideMaterial.color = new RGBColor(1);
        Rectangle bottomSide = new Rectangle(new Point3d(-300, 40, -300), new Point3d(300, 40, -300), new Point3d(-300, 0, 300), sideMaterial);

        Triangle triangle = new Triangle(new Point3d(0, 0, 0), new Point3d(0, 10, 0), new Point3d(0, 0, 10), sideMaterial);
        System.out.println(triangle.getLocal_normal().toString());
        //addLight(new Light(.2, new RGBColor(1), new Point3d(0, 0, -30)));
        //addLight(new Light(.2, new RGBColor(1, 0, 0), new Point3d(0, -30, -30)));

        addObject(amethyst);
        addObject(amethyst_behind);
        addObject(amethyst1);
        addObject(amethyst2);
        addObject(amethyst2_behind);
        world.addObject(bottomSide);
        //world.addLight(new Light(.3, RGBColor.WHITE, new Point3d(0, -50, -50)));

        setUpTimeline();
    }

    private void setUpTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        // Adding a specific action when each frame is started.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                rotate(0, angle, 0);

                world.render(-120);
            }
        };
        /*
        // create a keyFrame, the keyValue is reached at time 2s
        Duration duration = new Duration(2000);
        KeyFrame keyFrame = new KeyFrame(duration);

        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
         */
        timeline.play();
        timer.start();
    }

    private void addLight(Light light) {
        world.addLight(light);
    }

    private void addObject(Object o) {
        if (o == null) {
            return;
        }

        if (o instanceof IRotate) {
            rotatebleObejcs.add((IRotate)o);
        }

        if (o instanceof GeometricObject) {
            world.addObject((GeometricObject)o);
        }
    }


    @Override
    public IRotate rotate(Point3d source, double a_x, double a_y, double a_z) {
        return null;
    }

    @Override
    public IRotate rotate(double a_x, double a_y, double a_z) {
        for (IRotate rotateble: rotatebleObejcs) {
            rotateble.rotate(a_x, a_y, a_z);
        }

        return this;
    }
}
