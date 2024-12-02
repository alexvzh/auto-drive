package object.guiobjects;

import object.Object;
import object.behaviour.Clickable;
import object.behaviour.Drawable;
import object.behaviour.Hoverable;
import scene.Scene;

import java.awt.*;

public class ExpanderElement extends Object implements Drawable, Clickable, Hoverable {

    public ExpanderElement(double x, double y, Scene scene) {
        super(x, y, scene);
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public void onClick() {

    }

    @Override
    public void onHover() {

    }

    @Override
    public void onUnhover() {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

}
