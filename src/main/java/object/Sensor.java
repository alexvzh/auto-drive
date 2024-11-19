package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Sensor extends Object {

    public final static ArrayList<Integer> recentActivity = new ArrayList<>(Collections.nCopies(4, 0));
    public static int currentIndex = 0;

    private final double offsetX;
    private final double offsetY;
    private final BufferedImage backround;
    private final int id;
    private final double baseSpeed;
    private final Neville neville;
    private boolean isActive;
    private boolean wasActive;

    public Sensor(double x, double y, double offsetX, double offsetY, int id,  ObjectHandler objectHandler) {
        super(x + offsetX, y + offsetY, objectHandler);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.id = id;
        this.neville = objectHandler.getNeville();
        this.baseSpeed = neville.getBaseSpeed();

        try {
            backround = ImageIO.read(getClass().getResourceAsStream("/map.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return backround;
    }

    public boolean isOnTrack() {

        int pixel = backround.getRGB((int) x, (int) y);

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
}
