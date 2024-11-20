package scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Scene extends JPanel implements Runnable {

    private int FPS = 1000;
    private Thread thread;
    private boolean running;
    private final String id;
    public final SceneManager sceneManager;

    public Scene(int width, int height, String id, SceneManager sceneManager) {
        if (width == 0 || height == 0) {
            this.setPreferredSize(new Dimension(1128, 848));
        } else this.setPreferredSize(new Dimension(width, height));

        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.running = false;
        this.id = id;
        this.sceneManager = sceneManager;

        sceneManager.addScene(this);

    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (thread != null && running) {

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        draw(g2d);
    }

    public String getId() {
        return id;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public void addButton(int x, int y, int width, int height, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(actionListener);
        this.add(button);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2d);

}
