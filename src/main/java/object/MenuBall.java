package object;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class MenuBall extends Object {

    private final Color color;
    private final int size;
    private double velX;
    private double velY;

    public MenuBall(double x, double y, ObjectHandler objectHandler) {
        super(x, y, objectHandler);
        Random r = new Random();
        this.size = r.nextInt(10) + 8;
        velX = (double) (r.nextInt(60) + 5)/ 100;
        velY = (double) (r.nextInt(60) + 5)/ 100;
        objectHandler.addObject(this);
        this.color = Arrays.asList(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.WHITE).get(r.nextInt(3));
    }

    @Override
    public void update() {

        x+=velX;
        y+=velY;

        if (x < 0 - (double) size / 2 || x > 1128 - (double) size / 2) velX *= -1;
        if (y < 0 - (double) size / 2 || y > 848 - (double) size / 2) velY *= -1;

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval((int)x, (int)y, size, size);
    }
}
