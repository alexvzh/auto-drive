package scene.scenes;

import object.Neville;
import scene.Scene;
import scene.SceneManager;

import java.awt.*;

public class DemoScene extends Scene {

    private final Neville neville;
    private double startTime;

    public DemoScene(String id, SceneManager sceneManager) {
        super(0, 0, id, sceneManager);

        this.neville = new Neville(0, 0, objectHandler);
        init();
    }

    @Override
    public void update() {
        objectHandler.update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(neville.getSensors().get(0).getBackround(), 0, 0, null);
        objectHandler.draw(g2d);

        double timer = neville.getEndTime() - startTime;
        if (neville.isActive()) timer = (System.nanoTime() - startTime);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString((int) (timer / 1000000000  * 10000) / 10000.0 + " Seconds ", 5, 40);

    }

    @Override
    public void init() {
        this.setStartTime(System.nanoTime());
        neville.setX(177);
        neville.setY(381);
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
}
