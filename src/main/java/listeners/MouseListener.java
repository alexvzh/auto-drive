package listeners;

import object.ObjectHandler;
import scene.Scene;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener {

    private final ObjectHandler objectHandler;

    public MouseListener(Scene scene) {
        scene.addMouseListener(this);
        scene.addMouseMotionListener(this);
        scene.addMouseWheelListener(this);
        this.objectHandler = scene.getObjectHandler();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        objectHandler.hoverObject(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        objectHandler.pressObject(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        objectHandler.clickScene(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        objectHandler.clickScene(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

}
