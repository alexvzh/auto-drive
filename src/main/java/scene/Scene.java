package scene;

import object.ObjectHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Scene extends JPanel implements Runnable {

    private final int FPS = 100000;
    private final double DELTA_TIME = (double) 1 / FPS;

    private Thread thread;
    private boolean running;
    private final String id;
    private final SceneManager sceneManager;
    private final ObjectHandler objectHandler;
    private final SceneFrequency sceneFrequency;

    public Scene(int width, int height, String id, SceneFrequency sceneFrequency, SceneManager sceneManager) {
        if (width == 0 || height == 0) {
            this.setPreferredSize(new Dimension(1128, 848));
        } else this.setPreferredSize(new Dimension(width, height));

        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.running = false;
        this.id = id;
        this.sceneManager = sceneManager;
        this.sceneFrequency = sceneFrequency;
        this.objectHandler = new ObjectHandler();

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
        int updatesPerRender = 100;
        int updateCount = 0;

        while (thread != null) {

            if (running) {
                update();
                updateCount++;

                if (updateCount >= updatesPerRender) {
                    repaint();
                    updateCount = 0;
                }
            }

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

    public double getDELTA_TIME() {
        return DELTA_TIME;
    }

    public SceneFrequency getSceneFrequency() {
        return sceneFrequency;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public ObjectHandler getObjectHandler() {
        return objectHandler;
    }

    public void addButton(int x, int y, int width, int height, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(actionListener);
        this.add(button);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2d);
    public abstract void init();

}
