import Objects.Neville;
import Objects.ObjectHandler;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {

    private int FPS = 1000;

    private Thread thread;
    private final ObjectHandler objectHandler;
    private final Neville neville;
    private final double startTime;

    public Panel() {
        this.setPreferredSize(new Dimension(1128, 848));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.objectHandler = new ObjectHandler();
        this.neville = new Neville(177, 381, objectHandler);
        this.startTime = System.nanoTime();

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
                drawInterval = 1000000000 / FPS;
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

        double timer = neville.getEndTime() - startTime;
        if (neville.isActive()) timer = (System.nanoTime() - startTime);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString((int) (timer / 1000000000  * 10000) / 10000.0 + " Seconds ", 5, 40);


        /////////////////////////////////////////
        g2d.dispose();
    }

}
