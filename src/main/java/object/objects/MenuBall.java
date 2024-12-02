package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class MenuBall extends Object implements Updatable, Drawable {

    private static final Random RANDOM = new Random();
    private static final List<Color> COLORS = List.of(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.WHITE);

    private final Scene scene;
    private final Color color;
    private final int size;
    private double velX;
    private double velY;
    private BufferedImage circleImage;

    public MenuBall(double x, double y, Scene scene) {
        super(x, y, scene);
        this.scene = scene;
        this.size = RANDOM.nextInt(10) + 8;
        this.velX = (double) (RANDOM.nextInt(400) - 200) / 100;
        this.velY = (double) (RANDOM.nextInt(400) - 200) / 100;
        this.color = COLORS.get(RANDOM.nextInt(3));
        createCircleImage(size, color);
    }

    @Override
    public void update() {

        x += velX;
        y += velY;

        if (x < 0 || x > scene.getWidth() - (double) size) velX *= -1;
        if (y < 0 || y > scene.getHeight() - (double) size) velY *= -1;

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawImage(circleImage, (int) x, (int) y, null);
    }


    private void createCircleImage(int size, Color color) {
        circleImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circleImage.createGraphics();
        // Enable antialiasing for smooth edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fillOval(0, 0, size, size);

        g2d.dispose();
    }
}
