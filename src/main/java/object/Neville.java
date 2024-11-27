package object;

import scene.Scene;

import java.awt.*;
import java.util.ArrayList;

public class Neville extends Object {

    private final int SIZE = 90;
    private final double BASE_SPEED = 900;

    private final Scene scene;
    private ArrayList<Sensor> sensors;
    private ArrayList<Wheel> wheels;
    private double angularVelocity;
    private double linearVelocity;
    private double orientation;
    private boolean active;
    private double startTime;
    private double endTime;

    public Neville(double x, double y, Scene scene) {
        super(x, y, scene);
        this.scene = scene;
        addSensors(scene.getObjectHandler());
        addWheels(scene.getObjectHandler());
        this.orientation = 0;
        this.active = true;
    }

    @Override
    public void update() {

        updateAngularVelocity();
        updateLinearVelocity();
        updateOrientation();
        updatePosition();

        if (destinationReached() && isActive()) stop();

    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval((int) (x - (float) SIZE / 2), (int) (y - (float) SIZE / 2), SIZE, SIZE);
    }

    public void addSensors(ObjectHandler objectHandler) {
        sensors = new ArrayList<>();
        sensors.add(new Sensor(x, y, 33.59, -23.52, 0,  scene));
        sensors.add(new Sensor(x, y, 40.15, -8.29, 1, scene));
        sensors.add(new Sensor(x, y, 41, 0, 2, scene));
        sensors.add(new Sensor(x, y, 40.15, 8.29, 3, scene));
        sensors.add(new Sensor(x, y, 33.59, 23.52, 4, scene));
    }

    public void addWheels(ObjectHandler objectHandler) {
        wheels = new ArrayList<>();
        wheels.add(new Wheel(x - 10, y - SIZE/2 - 5, scene));
        wheels.add(new Wheel(x - 10, y + SIZE/2 - 5, scene));
    }

    public void updateAngularVelocity() {
        Wheel wheelOne = wheels.get(0);
        Wheel wheelTwo = wheels.get(1);
        angularVelocity = (wheelOne.getVelocity() - wheelTwo.getVelocity()) / wheelOne.calculateDistance(wheelTwo);
    }

    public void updateLinearVelocity() {
        linearVelocity = (wheels.get(0).getVelocity() + wheels.get(1).getVelocity())/2;
    }

    public void updateOrientation() {
        orientation += angularVelocity * scene.getDELTA_TIME();
    }

    public void updatePosition() {
        x += linearVelocity * Math.cos(orientation) * scene.getDELTA_TIME();
        y += linearVelocity * Math.sin(orientation) * scene.getDELTA_TIME();

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

    public double getBaseSpeed() {
        return BASE_SPEED;
    }

    public double getStartTime() {
        return startTime;
    }

    public void updateStartTime() {
        this.startTime = System.nanoTime();
    }

    public double getEndTime() {
        return endTime;
    }

    public void updateEndTime() {
        this.endTime = System.nanoTime();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (!active) updateEndTime();
        this.active = active;
    }

    public void setSpeed(double speed1, double speed2) {
        this.getWheels().get(0).setVelocity(speed1);
        this.getWheels().get(1).setVelocity(speed2);
    }

    public boolean isMovingStraight() {

        int firstActivity = Sensor.recentActivity.get(0);
        int secondActivity = Sensor.recentActivity.get(1);

        for (int i = 0; i < Sensor.recentActivity.size(); i++) {
            int currentActivity = Sensor.recentActivity.get(i);
            if ((i % 2 == 0 && currentActivity != firstActivity) ||
                (i % 2 != 0 && currentActivity != secondActivity)) {
                return false;
            }
        }
        return true;
    }

    public boolean destinationReached() {
        return getSensors().stream().allMatch(Sensor::isOnTrack);
    }

    public void stop() {
        this.setSpeed(0, 0);
        this.setActive(false);
    }
}
