package Objects;

import java.awt.*;
import java.util.ArrayList;

public class ObjectHandler {

    private final ArrayList<Object> objects = new ArrayList<>();

    public void update() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update();
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).draw(g2d);
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
