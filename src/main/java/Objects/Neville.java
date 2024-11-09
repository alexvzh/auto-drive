package Objects;

import java.awt.*;
import java.util.ArrayList;

public class Neville extends Object {

    int SIZE = 90;
    private final double BASE_SPEED = 0.1;

    private ArrayList<Sensor> sensors;
    private ArrayList<Wheel> wheels;
    private double w; //angular velocity
    private double v; //linear velocity
    private double orientation;
    private boolean active;

    public Neville(double x, double y, ObjectHandler objectHandler) {
        super(x, y, objectHandler);
        addWheels(objectHandler);
        addSensors(objectHandler);
        orientation = 0;
        active = true;
    }

    @Override
    public void update() {

        updateAngularVelocity();
        updateLinearVelocity();
        updateOrientation();
        updatePosition();

    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) (x - (float) SIZE / 2), (int) (y - (float) SIZE / 2), SIZE, SIZE);
    }

    public void addSensors(ObjectHandler objectHandler) {
        sensors = new ArrayList<>();
        sensors.add(new Sensor(x, y, 33.59, -23.52, 0,  objectHandler));
        sensors.add(new Sensor(x, y, 40.15, -8.29, 1, objectHandler));
        sensors.add(new Sensor(x, y, 41, 0, 2, objectHandler));
        sensors.add(new Sensor(x, y, 40.15, 8.29, 3, objectHandler));
        sensors.add(new Sensor(x, y, 33.59, 23.52, 4, objectHandler));
    }

    public void addWheels(ObjectHandler objectHandler) {
        wheels = new ArrayList<>();
        wheels.add(new Wheel(x - 10, y - SIZE/2 - 5, objectHandler));
        wheels.add(new Wheel(x - 10, y + SIZE/2 - 5, objectHandler));
    }

    public void updateAngularVelocity() {
        Wheel wheelOne = wheels.get(0);
        Wheel wheelTwo = wheels.get(1);
        w = (wheelOne.getVelocity() - wheelTwo.getVelocity()) / wheelOne.calculateDistance(wheelTwo);
    }

    public void updateLinearVelocity() {
        v = (wheels.get(0).getVelocity() + wheels.get(1).getVelocity())/2;
    }

    public void updateOrientation() {
        // delta time = 60
        orientation += w * 60;
    }

    public void updatePosition() {
        // delta time = 60
        x += v*Math.cos(orientation) * 60;
        y += v*Math.sin(orientation) * 60;

        for (Sensor sensor : sensors) {

            double sensorOffsetX = sensor.getOffsetX();
            double sensorOffsetY = sensor.getOffsetY();

            // Rotate sensor's local offset by the robot's orientation to get its position in global coordinates
            double sensorGlobalX = x + (sensorOffsetX * Math.cos(orientation) - sensorOffsetY * Math.sin(orientation));
            double sensorGlobalY = y + (sensorOffsetX * Math.sin(orientation) + sensorOffsetY * Math.cos(orientation));

            sensor.setX(sensorGlobalX);
            sensor.setY(sensorGlobalY);
        }

    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public ArrayList<Wheel> getWheels() {
        return wheels;
    }

    public void stop() {
        this.getWheels().get(0).setVelocity(0);
        this.getWheels().get(1).setVelocity(0);
        this.active = false;
    }

    public double getBaseSpeed() {
        return BASE_SPEED;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSpeed(double speed1, double speed2) {
        this.getWheels().get(0).setVelocity(speed1);
        this.getWheels().get(1).setVelocity(speed2);
    }
}
