package object;

import scene.Scene;

import java.awt.*;

public class Wheel extends Object {

    double velocity;

    public Wheel(double x, double y, Scene scene) {
        super(x, y, scene);
        this.velocity = scene.getObjectHandler().getNeville().getBaseSpeed();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void addVelocity(double velocity) {
        this.velocity += velocity;
    }

    public void removeVelocity(double velocity) {
        this.velocity -= velocity;
    }

    public double calculateDistance(Wheel wheel) {
        return Math.hypot(this.x-wheel.getX(), this.y-wheel.getY());
    }
}
