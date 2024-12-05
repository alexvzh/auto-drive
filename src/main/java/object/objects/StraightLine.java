package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.OnClickListener;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class StraightLine extends Object implements Updatable, Drawable, OnClickListener {

    private final List<Integer> angles = createAngleList(15);
    private int angleIndex = 0;
    private int angle = angles.get(angleIndex);

    private static final ArrayList<StraightLine> lines = new ArrayList<>();

    private final Scene scene;
    private PlaceState placeState = PlaceState.SPAWNING;
    private int size = 15;
    private int endX, endY;

    private boolean snapToGrid = false;

    private int anchorX, anchorY;
    private Shape boundingBox;
    private StraightLine snappedLine;

    enum PlaceState {
        SPAWNING,
        MOVING,
        SELECTING,
        PLACED
    }

    public StraightLine(Scene scene) {
        super(0, 0, scene);
        this.scene = scene;
        lines.add(this);
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g2d) {

        if (placeState == PlaceState.MOVING) {
            g2d.setColor(Color.GRAY);
            try {g2d.fill(getMovingLine());} catch (Exception ignored) {}

        } else if (placeState == PlaceState.SELECTING) {
            g2d.setColor(new Color(69,69,69));
            g2d.fill(getSelectingLine());

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
            if (((MouseWheelEvent) event).getWheelRotation() > 0) angleIndex = (angleIndex + 1) % angles.size();
            else angleIndex = (angleIndex - 1 + angles.size()) % angles.size();

            angle = angles.get(angleIndex);
            return;
        }

        if (placeState == PlaceState.SPAWNING) {
            placeState = PlaceState.MOVING;

        } else if (placeState == PlaceState.MOVING) {
            placeState = PlaceState.SELECTING;

        } else if (placeState == PlaceState.SELECTING) {
            placeState = PlaceState.PLACED;
            boundingBox = getSelectingLine();
            new StraightLine(scene);
        }
    }

    private Shape getBoundingBox() {
        return boundingBox;
    }

    private Shape getSelectingLine() {

        Point2D startPoint= new Point2D.Double(x, y);
        Point2D endPoint = new Point2D.Double(scene.getMousePosition().x, scene.getMousePosition().y);

        int distance = snapToGrid((int) startPoint.distance(endPoint));

        Rectangle rect = new Rectangle((int)x, (int)y, distance, size);

        double angleInRadians = Math.toRadians(angle);
        endX = (int) (x + distance * Math.cos(angleInRadians));
        endY = (int) (y + distance * Math.sin(angleInRadians));

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), anchorX, anchorY);

        return transform.createTransformedShape(rect);
    }

    private Shape getMovingLine() {

        x = snapToGrid(scene.getMousePosition().x);
        y = snapToGrid(scene.getMousePosition().y);

        snapToLine();

        anchorX = (int) x;
        anchorY = (int) (angleIndex > angles.size()/4*3 ? y + size : y);

        updateAnchors();

        Rectangle rect = new Rectangle((int) x, (int) y, size * 7, size);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), anchorX, anchorY);


        return transform.createTransformedShape(rect);
    }

    private int snapToGrid(int coordinate) {
        if (this.snapToGrid) return (coordinate / size) * size;
        return coordinate;
    }

    private void snapToLine() {
        for (StraightLine line : lines) {
            if (Math.abs(line.getEndX() - x) < 25 && Math.abs(line.getEndY() - y) < 25) {
                x = line.getEndX();
                y = line.getEndY();
                snappedLine = line;
                return;
            }
        }
        snappedLine = null;
    }

    private void updateAnchors() {
        if (snappedLine == null) return;
        anchorX = snappedLine.getEndX();
        anchorY = (angleIndex > angles.size()/2 ? snappedLine.getEndY() + size : snappedLine.getEndY());
    }

    private static List<Integer> createAngleList(int angleIncrement) {
        List<Integer> angles = new ArrayList<>();
        for (int i = 0; i < 360; i += angleIncrement) {
            angles.add(i);
        }
        return angles;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getAngle() {
        return angle;
    }

}
