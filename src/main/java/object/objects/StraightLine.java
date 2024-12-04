package object.objects;

import object.Object;
import object.behaviour.Drawable;
import object.behaviour.OnClickListener;
import object.behaviour.Updatable;
import scene.Scene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class StraightLine extends Object implements Updatable, Drawable, OnClickListener {

    private static final List<Integer> angles = List.of(0, 45, 90, 135, 180, 225, 270, 315);
    private static int angleIndex = 0;
    public static int currentAngle = angles.get(angleIndex);

    private final Scene scene;
    private int startX, startY, endX, endY;
    private int size = 15;
    private PlaceState placeState = PlaceState.SPAWNING;
    private boolean isHorizontal = true;
    private int anchorX;
    private int anchorY;
    Shape boundingBox;

    enum PlaceState {
        SPAWNING,
        MOVING,
        SELECTING,
        PLACED
    }

    public StraightLine(Scene scene) {
        super(0, 0, scene);
        this.scene = scene;
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
            angleIndex = (angleIndex + 1) % angles.size();
            currentAngle = angles.get(angleIndex);
            return;
        }

        if (placeState == PlaceState.SPAWNING) {
            placeState = PlaceState.MOVING;

        } else if (placeState == PlaceState.MOVING) {

            startX = ((scene.getMousePosition().x - size * 3) / size) * size;
            startY = ((scene.getMousePosition().y - size * 3) / size) * size + size * 3;

            placeState = PlaceState.SELECTING;

        } else if (placeState == PlaceState.SELECTING) {
            placeState = PlaceState.PLACED;
            boundingBox = getSelectingLine();
            angleIndex = 0;
            currentAngle = 0;
            new StraightLine(scene);
        }
    }

    private void updateEndCoords() {
        try {
            Point mousePosition = scene.getMousePosition();

            endX = (mousePosition.x / size) * size + size * 3;
            endY = (mousePosition.y / size) * size + size * 3;

        } catch (Exception ignored) {
        }
    }

    private Shape getBoundingBox() {
        return boundingBox;
    }

    private Shape getSelectingLine() {

        Point2D startPoint= new Point2D.Double(startX, startY);
        Point2D endPoint = new Point2D.Double(scene.getMousePosition().x, scene.getMousePosition().y);

        int distance = (int) (startPoint.distance(endPoint) / size) * size;

        Rectangle rect = new Rectangle(startX, startY, distance + size, size);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(currentAngle), anchorX, anchorY);

        return transform.createTransformedShape(rect);
    }

    private Shape getMovingLine() {

        Rectangle rect = new Rectangle(((scene.getMousePosition().x - size * 3) / size) * size,
                ((scene.getMousePosition().y) / size) * size, size * 7, size);

        anchorX = ((scene.getMousePosition().x - size * 3) / size) * size;
        anchorY = angleIndex == 7 ? (scene.getMousePosition().y / size) * size + size :
                (scene.getMousePosition().y / size) * size;

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(currentAngle), anchorX, anchorY);

        return transform.createTransformedShape(rect);
    }
}
