package Objects;

import java.awt.*;
import java.util.ArrayList;

public class Neville extends Object {

    int SIZE = 90;

    ArrayList<Sensor> sensors;
    ArrayList<Wheel> wheels;
    double w; //angular velocity
    double v; //linear velocity
    double orientation;

    public Neville(double x, double y, ObjectHandler objectHandler) {
        super(x, y, objectHandler);
        orientation = 0;
        addSensors(objectHandler);
        addWheels(objectHandler);
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
        sensors.add(new Sensor(x, y, 33.59, -23.52, objectHandler));
        sensors.add(new Sensor(x, y, 40.15, -8.29, objectHandler));
        sensors.add(new Sensor(x, y, 41, 0, objectHandler));
        sensors.add(new Sensor(x, y, 40.15, 8.29, objectHandler));
        sensors.add(new Sensor(x, y, 33.59, 23.52, objectHandler));
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
}
