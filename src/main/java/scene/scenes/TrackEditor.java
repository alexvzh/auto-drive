package scene.scenes;

import object.guiobjects.Expander;
import scene.Scene;
import scene.SceneFrequency;
import scene.SceneManager;

import java.awt.*;

public class TrackEditor extends Scene {
    public TrackEditor(String id, int initialObjectCapacity, SceneFrequency sceneFrequency, SceneManager sceneManager) {
        super(id, initialObjectCapacity, sceneFrequency, sceneManager);
        setBackroundColor(Color.WHITE);
        new Expander((double) getPreferredSize().width/2-25, getPreferredSize().height-30, this);
    }

    @Override
    public void update() {
        getObjectHandler().update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        getObjectHandler().draw(g2d);
    }

    @Override
    public void init() {

    }
}
