package scene;

import listeners.MouseListener;
import object.ObjectHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Scene extends JPanel implements Runnable {

    private final int FPS;
    private final double DELTA_TIME;

    private Thread thread;
    private boolean running;
    private final String id;
    private int updateCount = 0;
    private Color backroundColor;
    private final SceneManager sceneManager;
    private final ObjectHandler objectHandler;
    private final SceneFrequency sceneFrequency;

    public Scene(int width, int height, String id, int initialObjectCapacity, SceneFrequency sceneFrequency, SceneManager sceneManager) {

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);

        this.FPS = sceneFrequency.equals(SceneFrequency.HIGH) ? 100000 : 240;
        this.DELTA_TIME = (double) 1 / FPS;
        this.running = false;
        this.id = id;
        this.backroundColor = Color.GRAY;
        this.sceneManager = sceneManager;
        this.sceneFrequency = sceneFrequency;
        this.objectHandler = new ObjectHandler(initialObjectCapacity);
        new MouseListener(this);

        sceneManager.addScene(this);

    }

    public Scene(String id, int initialObjectCapacity, SceneFrequency sceneFrequency, SceneManager sceneManager) {
        this(1200, 850, id, initialObjectCapacity, sceneFrequency, sceneManager);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2d);
    public abstract void init();

    public void startThread() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    @Override
    public void run() {

        double drawInterval = (double) 1_000_000_000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (thread != null) {

            if (running) processFrame(sceneFrequency);

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1_000_000;

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
        g2d.setColor(backroundColor);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        draw(g2d);
    }

    public void addButton(int x, int y, int width, int height, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(actionListener);
        this.add(button);
    }

    private void processFrame(SceneFrequency sceneFrequency) {

        if (sceneFrequency.equals(SceneFrequency.LOW)) {
            update();
            repaint();
        } else {
            update();
            updateCount++;

            int updatesPerRender = 100;
            if (updateCount >= updatesPerRender) {
                repaint();
                updateCount = 0;
            }
        }
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

    public void setBackroundColor(Color backroundColor) {
        this.backroundColor = backroundColor;
    }
}
