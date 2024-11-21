package scene.scenes;

import object.MenuBall;
import scene.Scene;
import scene.SceneManager;

import java.awt.*;
import java.util.Random;

public class MenuScene extends Scene {

    Random r = new Random();

    public MenuScene(String id, SceneManager sceneManager) {
        super(0, 0, id, sceneManager);
        this.setLayout(null);
        spawnBalls();
        addButton((getPreferredSize().width/2)-125,(getPreferredSize().height/3)-50, 250, 100, "Test", e -> System.out.println(getWidth()/2));
    }

    @Override
    public void update() {
        objectHandler.update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        objectHandler.draw(g2d);
    }

    private void spawnBalls() {
        for (int i = 0; i < 150; i ++) {
            new MenuBall(r.nextInt(1100), r.nextInt(800), objectHandler);
        }
    }
}
