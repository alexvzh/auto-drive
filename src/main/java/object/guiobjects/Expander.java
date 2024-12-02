package object.guiobjects;

import object.Object;
import object.behaviour.Clickable;
import object.behaviour.Drawable;
import object.behaviour.Hoverable;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Expander extends Object implements Updatable, Drawable, Clickable, Hoverable {

    enum ExpansionState {
        COLLAPSED,
        EXPANDED
    }

    enum HoverState {
        HOVERED,
        NOT_HOVERED
    }

    private static final BufferedImage COLLAPSED_ICON = createIcon(ExpansionState.COLLAPSED);
    private static final BufferedImage EXPANDED_ICON = createIcon(ExpansionState.EXPANDED);
    private static final int SIZE = 50;

    private ExpansionState expansionState = ExpansionState.COLLAPSED;
    private HoverState hoverState = HoverState.NOT_HOVERED;

    public Expander(double x, double y, Scene scene) {
        super(x, y, scene);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        if (expansionState == ExpansionState.EXPANDED) {
            g2d.drawImage(EXPANDED_ICON, (int) getX(), (int) getY(), null);
        } else {
            g2d.drawImage(COLLAPSED_ICON, (int) getX(), (int) getY(), null);
        }

        if (hoverState == HoverState.HOVERED) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);
            g2d.draw(getBounds());
        }
    }

    @Override
    public void onHover() {
        this.hoverState = HoverState.HOVERED;
    }

    @Override
    public void onUnhover() {
        this.hoverState = HoverState.NOT_HOVERED;
    }

    @Override
    public void onClick() {
        if (expansionState == ExpansionState.COLLAPSED) expansionState = ExpansionState.EXPANDED;
        else expansionState = ExpansionState.COLLAPSED;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), SIZE, SIZE/2);
    }

    private static BufferedImage createIcon(ExpansionState state) {

        BufferedImage image = new BufferedImage(SIZE, SIZE/2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(0, 0, SIZE, SIZE);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(3));
        if (state == ExpansionState.EXPANDED) {
            g2d.drawLine(SIZE/7, SIZE/7, SIZE/2, SIZE/2-SIZE/7);
            g2d.drawLine(SIZE/2, SIZE/2-SIZE/7, SIZE - SIZE/7, SIZE/7);
        } else {
            g2d.drawLine(SIZE/7, SIZE/2-SIZE/7, SIZE/2, SIZE/7);
            g2d.drawLine(SIZE/2, SIZE/7, SIZE - SIZE/7, SIZE/2 - SIZE/7);
        }

        g2d.dispose();

        return image;
    }

}
