package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.Updatable;
import scene.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Sensor extends Object implements Updatable, Drawable {

    private final static List<Double> ANGLES = List.of(-35.0, -35.0 / 3, 0.0, 35.0 / 3, 35.0);
    private final static BufferedImage BACKGROUND;

    public static final List<Integer> recentActivity = Arrays.asList(0, 0, 0, 0);
    public static int currentIndex = 0;

    static {
        try {
            BACKGROUND = ImageIO.read(Objects.requireNonNull(Sensor.class.getResourceAsStream("/map.png")));
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private final double offsetX;
    private final double offsetY;
    private final int id;
    private final double baseSpeed;
    private final Neville neville;
    private boolean isActive;
    private boolean wasActive;

    public Sensor(int id,  Scene scene) {
        super(0, 0, scene);
        this.id = id;
        this.neville = scene.getObjectHandler().getNeville();
        this.baseSpeed = neville.getBaseSpeed();
        this.offsetX = calculateSensorOffsets().get(0);
        this.offsetY = calculateSensorOffsets().get(1);
        this.x = neville.getX() + offsetX;
        this.y = neville.getY() + offsetY;
    }

    @Override
    public void update() {

        isActive = isOnTrack();

        if (!neville.isActive()) return;

        if (wasActive != isActive) {
            wasActive = isActive;

            if (isActive) {
                recentActivity.set(currentIndex, id);
                currentIndex = (currentIndex + 1) % recentActivity.size();
            }

            if (!neville.getSensors().get(0).isActive && !neville.getSensors().get(4).isActive) {
                if (!neville.isMovingStraight()) {
                    if (id == 1 && isActive) neville.setSpeed(baseSpeed / 1.3, baseSpeed);
                    else if (id == 3 && isActive) neville.setSpeed(baseSpeed, baseSpeed / 1.3);
                } else neville.setSpeed(baseSpeed * 1.5, baseSpeed * 1.5);
            }

            if (id == 0 && !neville.getSensors().get(4).isActive) changeSpeedHelper(0, baseSpeed * 1.5);
            else if (id == 4 && !neville.getSensors().get(0).isActive) changeSpeedHelper(baseSpeed * 1.5, 0);

        }

    }

    @Override
    public void draw(Graphics2D g2d) {

        Color color = isActive ? Color.GREEN : Color.RED;
        g2d.setColor(color);

        g2d.fillOval((int) x, (int) y, 5, 5);

    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public BufferedImage getBackround() {
        return BACKGROUND;
    }

    public boolean isOnTrack() {

        int pixel = BACKGROUND.getRGB((int) x, (int) y);

        //shifting bits and using bitwise AND to separate the red, green, and blue components from the integer
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = pixel & 0xff;

        return red < 128 && green < 128 && blue < 128;

    }

    public void changeSpeedHelper(double speed1, double speed2) {
        if (isActive) neville.setSpeed(speed1, speed2);
        else {
            if (neville.getSensors().get(1).isActive) neville.setSpeed(baseSpeed / 1.3, baseSpeed);
            else if (neville.getSensors().get(3).isActive) neville.setSpeed(baseSpeed, baseSpeed / 1.3);
        }
    }

    private ArrayList<Double> calculateSensorOffsets() {

        // Base sensor offsets
        double x0 = neville.getSIZE() * 0.4555555555;
        double y0 = 0.0;

        double distance = (double) neville.getSIZE() * 0.4555555555;
        double angleRadians = Math.toRadians(ANGLES.get(id));

        double xNew = x0 * Math.cos(angleRadians);
        double yNew = y0 + distance * Math.sin(angleRadians);

        return new ArrayList<>(Arrays.asList(xNew, yNew));
    }
}
