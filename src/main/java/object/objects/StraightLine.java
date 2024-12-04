package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.OnClickListener;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;

public class StraightLine extends Object implements Updatable, Drawable, OnClickListener {

    private double startX, startY, endX, endY;
    private Scene scene;
    private int size;
    private PlaceState placeState = PlaceState.MOVING;

    enum PlaceState {
        MOVING,
        SELECTING,
        PLACED
    }

    public StraightLine(Scene scene) {
        super(0, 0, scene);
        this.startX = x + 1;
        this.startY = y + 1;
        this.endY = startY;
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
    public void onClick(Point clickLocation) {
        if (placeState == PlaceState.MOVING) {

            startX = (int)((scene.getMousePosition().x-45)/size) * size;
            endX = startX + 90;
            startY = (int)((scene.getMousePosition().y-45)/size) * size + size*3;
            endY = size;
            placeState = PlaceState.SELECTING;

        } else if (placeState == PlaceState.SELECTING) {
            placeState = PlaceState.PLACED;
            new StraightLine(scene);
        }
    }

    private void updateEndCoords() {
        try {
            Point mousePosition = scene.getMousePosition();
            endX = (int)(mousePosition.x/size) * size + 30;
        } catch (Exception ignored) {}
    }

    private Rectangle getBoundingBox() {
        if (startX < endX) {
            return new Rectangle((int) startX, (int) startY, (int) (endX - startX) + size, size);
        } else
            return new Rectangle((int) endX, (int) startY, (int) (startX - endX) + size, size);
    }

    private Rectangle getUnplacedLine() {
        return new Rectangle(((scene.getMousePosition().x-45)/size) * size, ((scene.getMousePosition().y-45)/size) * size + size*3, 90 , size);
    }
}
