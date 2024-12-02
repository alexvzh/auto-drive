package object.guiobjects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Expander extends Object implements Updatable, Drawable {

    private static final BufferedImage collapsedIcon;

    public Expander(double x, double y, Scene scene) {
        super(x, y, scene);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {

    }

}
