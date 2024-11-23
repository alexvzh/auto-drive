package scene.scenes;

import object.Neville;
import scene.Scene;
import scene.SceneManager;

import java.awt.*;

public class DemoScene extends Scene {

    private final Neville neville;

    public DemoScene(String id, SceneManager sceneManager) {
        super(0, 0, id, sceneManager);

        this.neville = new Neville(177, 0, this);
        init();
    }

    @Override
    public void update() {
        getObjectHandler().update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(neville.getSensors().get(0).getBackround(), 0, 0, null);
        getObjectHandler().draw(g2d);

        double timer = neville.getEndTime() - neville.getStartTime();
        if (neville.isActive()) timer = (System.nanoTime() - neville.getStartTime());

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString((int) (timer / 1000000000  * 10000) / 10000.0 + " Seconds ", 5, 40);

    }

    @Override
    public void init() {
        neville.updateStartTime();
        neville.setX(177);
        neville.setY(381);
    }

}
