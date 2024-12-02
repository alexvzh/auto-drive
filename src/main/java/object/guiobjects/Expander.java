package object.guiobjects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.Selectable;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Expander extends Object implements Updatable, Drawable {

    enum ExpansionState {
        COLLAPSED,
        EXPANDED
    }

    private static final BufferedImage collapsedIcon = createIcon(ExpansionState.COLLAPSED);
    private static final BufferedImage expandedIcon = createIcon(ExpansionState.EXPANDED);
    private static final BufferedImage COLLAPSED_ICON = createIcon(ExpansionState.COLLAPSED);
    private static final BufferedImage EXPANDED_ICON = createIcon(ExpansionState.EXPANDED);

    private ExpansionState expansionState = ExpansionState.COLLAPSED;

    public Expander(double x, double y, Scene scene) {
        super(x, y, scene);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    private static BufferedImage createIcon(ExpansionState state) {
        int size = 50;

        BufferedImage image = new BufferedImage(size, size/2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(0, 0, size, size);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(3));
        if (state == ExpansionState.EXPANDED) {
            g2d.drawLine(size/7, size/7, size/2, size/2-size/7);
            g2d.drawLine(size/2, size/2-size/7, size - size/7, size/7);
        } else {
            g2d.drawLine(size/7, size/2-size/7, size/2, size/7);
            g2d.drawLine(size/2, size/7, size - size/7, size/2 - size/7);
        }

        g2d.dispose();

        return image;
    }

}
