import Objects.Neville;
import Objects.ObjectHandler;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {

    private final int FPS = 60;

    private Thread thread;
    private final ObjectHandler objectHandler;
    Neville neville;

    public Panel() {
        this.setPreferredSize(new Dimension(1128, 848));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.objectHandler = new ObjectHandler();

        neville = new Neville(177, 381, objectHandler);

    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (thread != null) {
            update();
            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;

            } catch (Exception ignored) {}
        }
    }

    public void update() {

        objectHandler.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(neville.getSensors().get(0).getBackround(), 0, 0, null);
        /////////////////////////////////////////

        objectHandler.draw(g2d);

        /////////////////////////////////////////
        g2d.dispose();
    }



}
