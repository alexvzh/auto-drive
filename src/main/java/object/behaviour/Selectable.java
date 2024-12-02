package object.behaviour;

import java.awt.*;

public interface Selectable {
    void onHover(Graphics2D g2d);
    void onClick();
    Rectangle getBounds();
}
