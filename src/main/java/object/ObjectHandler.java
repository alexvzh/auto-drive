package object;

import object.behaviour.Clickable;
import object.behaviour.Drawable;
import object.behaviour.Hoverable;
import object.behaviour.Updatable;
import object.objects.Neville;

import java.awt.*;
import java.util.ArrayList;

public class ObjectHandler {

    private final ArrayList<Object> objects;

    public ObjectHandler(int initalCapacity) {
        this.objects = new ArrayList<>(initalCapacity);
    }

    public void update() {
        for (int i = 0; i < objects.size(); i++) {
            if (!(objects.get(i) instanceof Updatable)) continue;
            ((Updatable)objects.get(i)).update();
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < objects.size(); i++) {
            if (!(objects.get(i) instanceof Drawable)) continue;
            ((Drawable)objects.get(i)).draw(g2d);
        }
    }

    public void pressObject(Point p) {
        for (int i = 0; i < objects.size(); i++) {
            if (!(objects.get(i) instanceof Clickable)) continue;
            Clickable object = (Clickable)objects.get(i);
            if (object.getBounds().contains(p)) {
                object.onClick();
            }
        }
    }

    public void hoverObject(Point p) {
        for (int i = 0; i < objects.size(); i++) {
            if (!(objects.get(i) instanceof Hoverable)) continue;
            Hoverable object = (Hoverable)objects.get(i);
            if (object.getBounds().contains(p)) {
                object.onHover();
            } else object.onUnhover();
        }
    }

    public void addObject(Object object) {
        this.objects.add(object);
    }

    public void removeObject(Object object) {
        this.objects.remove(object);
    }

    public Neville getNeville() {
        for (Object object : objects) {
            if (object instanceof Neville) {
                return (Neville) object;
            }
        }
        return null;
    }
}
