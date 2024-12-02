package listeners;

import object.ObjectHandler;
import scene.Scene;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener {

    ObjectHandler objectHandler;

    public MouseListener(Scene scene) {
        scene.addMouseListener(this);
        scene.addMouseMotionListener(this);
        this.objectHandler = scene.getObjectHandler();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        objectHandler.pressObject(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        objectHandler.hoverObject(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

}
