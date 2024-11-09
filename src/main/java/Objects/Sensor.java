package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sensor extends Object {

    double offsetX;
    double offsetY;
    private final BufferedImage backround;

    public Sensor(double x, double y, double offsetX, double offsetY, ObjectHandler objectHandler) {
        super(x + offsetX, y + offsetY, objectHandler);
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        try {
            backround = ImageIO.read(getClass().getResourceAsStream("/map.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.BLACK);
        g2d.fillOval((int) x, (int) y, 3, 3);

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

    public boolean isPixelBlack() {

        int pixel = backround.getRGB((int) x, (int) y);

        //shifting bits and using bitwise AND to separate the red, green, and blue components from the integer
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = pixel & 0xff;

        return red == 0 && green == 0 && blue == 0;

    }

}
