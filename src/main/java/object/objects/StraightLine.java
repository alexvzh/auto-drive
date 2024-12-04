package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.OnClickListener;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class StraightLine extends Object implements Updatable, Drawable, OnClickListener {

    private double startX, startY, endX, endY;
    private Scene scene;
    private int size;
    private PlaceState placeState = PlaceState.SPAWNING;
    private boolean isHorizontal = true;

    enum PlaceState {
        SPAWNING,
        MOVING,
        SELECTING,
        PLACED
    }

    public StraightLine(Scene scene) {
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
        // Mouse scroll
        if (event.getButton() == 0) {
            if (placeState != PlaceState.MOVING) return;
            isHorizontal = !isHorizontal;
            return;
        }

        if (placeState == PlaceState.SPAWNING) {
            placeState = PlaceState.MOVING;

        } else if (placeState == PlaceState.MOVING) {

            if (isHorizontal) {
                startX = (int) ((scene.getMousePosition().x - 45) / size) * size;
                startY = (int) ((scene.getMousePosition().y - 45) / size) * size + size * 3;
            } else {
                startX = (int)((scene.getMousePosition().x-45)/size) * size + size*3;
                startY = (int)((scene.getMousePosition().y-45)/size) * size;
            }

            placeState = PlaceState.SELECTING;

        } else if (placeState == PlaceState.SELECTING) {
            placeState = PlaceState.PLACED;
            new StraightLine(scene);
        }
    }

    private void updateEndCoords() {
        try {
            Point mousePosition = scene.getMousePosition();
            if (isHorizontal) {
                endX = (int) (mousePosition.x / size) * size + 45;
            } else {
                endY = (int)(mousePosition.y/size) * size + 45;
            }
        } catch (Exception ignored) {}
    }

    private Rectangle getBoundingBox() {
        if (isHorizontal) {
            if (startX < endX) {
                return new Rectangle((int) startX, (int) startY, (int) (endX - startX) + size, size);
            } else
                return new Rectangle((int) endX, (int) startY, (int) (startX - endX) + size, size);
        } else {
            if (startY < endY) {
                return new Rectangle((int) startX, (int) startY, size, (int) (endY - startY) + size);
            } else
                return new Rectangle((int) startX, (int) endY, size, (int) (startY - endY) + size);
        }
    }

    private Rectangle getUnplacedLine() {
        if (isHorizontal) {
            return new Rectangle(((scene.getMousePosition().x - 45) / size) * size, ((scene.getMousePosition().y - 45) / size) * size + size * 3, size * 7, size);
        } else {
            return new Rectangle(((scene.getMousePosition().x - 45) / size) * size  + size * 3, ((scene.getMousePosition().y - 45) / size) * size, size , size * 7);
        }
    }
}
