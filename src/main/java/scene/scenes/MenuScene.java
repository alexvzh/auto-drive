package scene.scenes;

import object.objects.MenuBall;
import scene.Scene;
import scene.SceneFrequency;
import scene.SceneManager;

import java.awt.*;
import java.util.Random;

public class MenuScene extends Scene {

    private final Random r = new Random();
    private final SceneManager sceneManager;

    public MenuScene(String id, int initialObjectCapacity, SceneFrequency sceneFrequency, SceneManager sceneManager) {
        super(id, initialObjectCapacity, sceneFrequency, sceneManager);
        this.sceneManager = sceneManager;
        spawnBalls();
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
        addButton((getWidth()/2)-125,(getHeight()/3)-50, 250, 100, "Demo", e -> sceneManager.setScene("demo"));
        addButton((getWidth()/2)-125,(getHeight()/2)-50, 250, 100, "Pause!", e -> setRunning(!isRunning()));
        addButton((getWidth()/2)-125,(getHeight()/3)*2-50, 250, 100, "Track Editor", e -> sceneManager.setScene("editor"));
    }

    private void spawnBalls() {
        for (int i = 0; i < 250; i ++) {
            new MenuBall(r.nextInt(1100), r.nextInt(800), this);
        }
    }
}
