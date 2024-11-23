package object;

import scene.Scene;

import java.awt.*;

public abstract class Object {

    double x,y;

    public Object(double x, double y, Scene scene) {
        this.x = x;
        this.y = y;
        scene.getObjectHandler().addObject(this);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2d);

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

