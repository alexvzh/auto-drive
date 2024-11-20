package scene;

import object.Neville;
import object.ObjectHandler;

import java.awt.*;

public class DemoScene extends Scene {

    private final ObjectHandler objectHandler;
    private final Neville neville;
    private final double startTime;

    public DemoScene(String id, SceneManager sceneManager) {
        super(0, 0, id, sceneManager);

        this.objectHandler = new ObjectHandler();
        this.neville = new Neville(177, 381, objectHandler);
        this.startTime = System.nanoTime();
    }

    @Override
    public void update() {

        objectHandler.update();
        if (!neville.isActive()) sceneManager.setScene("menu");

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
}
