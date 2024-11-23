package object;

import scene.Scene;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class MenuBall extends Object {

    private final Scene scene;
    private final Color color;
    private final int size;
    private double velX;
    private double velY;

    public MenuBall(double x, double y, Scene scene) {
        super(x, y, scene);
        this.scene = scene;
        Random r = new Random();
        this.size = r.nextInt(10) + 8;
        velX = (double) (r.nextInt(400) - 200) / 100;
        velY = (double) (r.nextInt(400) - 200) / 100;
        scene.getObjectHandler().addObject(this);
        this.color = Arrays.asList(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.WHITE).get(r.nextInt(3));
    }

    @Override
    public void update() {

        x+=velX;
        y+=velY;

        if (x < 0 || x > scene.getWidth() - (double) size) velX *= -1;
        if (y < 0 || y > scene.getHeight() - (double) size) velY *= -1;

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval((int)x, (int)y, size, size);
    }
}
