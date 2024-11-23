package scene;

import scene.scenes.DemoScene;
import scene.scenes.MenuScene;

import javax.swing.*;
import java.util.ArrayList;

public class SceneManager {

    private final JFrame frame;
    private final ArrayList<Scene> scenes;
    private Scene currentScene;

    public SceneManager(JFrame frame) {
        this.frame = frame;
        this.scenes = new ArrayList<>();
        currentScene = new MenuScene("menu", this);
        new DemoScene("demo", this);
    }

    public void setScene(String sceneID) {
        Scene scene = getScene(sceneID);
        frame.remove(currentScene);
        frame.add(scene);
        frame.pack();
        frame.setLocationRelativeTo(null);
        scene.init();
        scene.startThread();
        currentScene = scene;
    }

    public void addScene(Scene scene) {
        this.scenes.add(scene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Scene getScene(String sceneID) {
        for (Scene scene : scenes) {
            if (scene.getId().equals(sceneID)) return scene;
        }
        return null;
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }
}
