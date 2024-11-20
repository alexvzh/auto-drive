package scene.scenes;

import scene.Scene;
import scene.SceneManager;

import java.awt.*;

public class MenuScene extends Scene {
    public MenuScene(String id, SceneManager sceneManager) {
        super(100, 100, id, sceneManager);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.drawString("ag", 100, 100);
    }
}
