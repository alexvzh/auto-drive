package object.behaviour;

import java.awt.*;

public interface Hoverable {
    void onHover();
    void onUnhover();
    Rectangle getBounds();
}
