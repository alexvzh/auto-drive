package object;

import java.awt.*;

public class Wheel extends Object {

    double velocity;

    public Wheel(double x, double y, ObjectHandler objectHandler) {
        super(x, y, objectHandler);
        this.velocity = objectHandler.getNeville().getBaseSpeed();
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
