package object.behaviour;

import java.awt.*;

public interface Selectable {
    void onHover();
    void onUnhover();
    void onClick();
    Rectangle getBounds();
}
