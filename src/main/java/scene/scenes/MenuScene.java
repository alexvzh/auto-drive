package scene.scenes;

import object.MenuBall;
import scene.Scene;
import scene.SceneFrequency;
import scene.SceneManager;

import java.awt.*;
import java.util.Random;

public class MenuScene extends Scene {

    Random r = new Random();

    public MenuScene(String id, SceneFrequency sceneFrequency, SceneManager sceneManager) {
        super(0, 0, id, sceneFrequency, sceneManager);
        this.setLayout(null);
        spawnBalls();
        addButton((getPreferredSize().width/2)-125,(getPreferredSize().height/3)-50, 250, 100, "Demo", e -> sceneManager.setScene("demo"));
        addButton((getPreferredSize().width/2)-125,(getPreferredSize().height/2)-50, 250, 100, "Pause!", e -> setRunning(!isRunning()));
        addButton((getPreferredSize().width/2)-125,(getPreferredSize().height/3)*2-50, 250, 100, "Pause!", e -> setRunning(!isRunning()));

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

    private void spawnBalls() {
        for (int i = 0; i < 150; i ++) {
            new MenuBall(r.nextInt(1100), r.nextInt(800), this);
        }
    }
}
