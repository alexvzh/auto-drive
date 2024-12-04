package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.OnClickListener;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class VerticalLine extends Object implements Updatable, Drawable, OnClickListener {

    private double startX, startY, endX, endY;
    private Scene scene;
    private int size;
    private PlaceState placeState = PlaceState.SPAWNING;

    enum PlaceState {
        SPAWNING,
        MOVING,
        SELECTING,
        PLACED
    }

    public VerticalLine(Scene scene) {
        super(0, 0, scene);
        this.scene = scene;
        this.size = 15;
    }

    @Override
    public void update() {
        if (placeState == PlaceState.PLACED) return;
        updateEndCoords();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));

        if (placeState == PlaceState.MOVING) {
            g2d.setColor(Color.GRAY);
            try {g2d.fill(getUnplacedLine());} catch (Exception ignored) {}

        } else if (placeState == PlaceState.SELECTING) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fill(getBoundingBox());

        } else if (placeState == PlaceState.PLACED) {
            g2d.setColor(Color.BLACK);
            g2d.fill(getBoundingBox());

        }
    }

    @Override
    public void onClick(MouseEvent event) {
        if (placeState == PlaceState.SPAWNING) {
            placeState = PlaceState.MOVING;

        } else if (placeState == PlaceState.MOVING) {

            startX = (int)((scene.getMousePosition().x-45)/size) * size + size*3;
            endX = size;
            startY = (int)((scene.getMousePosition().y-45)/size) * size;
            endY = startY + 90;
            placeState = PlaceState.SELECTING;

        } else if (placeState == PlaceState.SELECTING) {
            placeState = PlaceState.PLACED;
        }
    }

    private void updateEndCoords() {
        try {
            Point mousePosition = scene.getMousePosition();
            endY = (int)(mousePosition.y/size) * size + 30;
        } catch (Exception ignored) {}
    }

    private Rectangle getBoundingBox() {
        if (startY < endY) {
            return new Rectangle((int) startX, (int) startY, size, (int) (endY - startY) + size);
        } else
            return new Rectangle((int) startX, (int) endY, size, (int) (startY - endY) + size);
    }

    private Rectangle getUnplacedLine() {
        return new Rectangle(((scene.getMousePosition().x-45)/size) * size  + size*3, ((scene.getMousePosition().y-45)/size) * size, size , 90);
    }
}
