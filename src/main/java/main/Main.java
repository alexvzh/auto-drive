package main;

import scene.SceneManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        SceneManager sceneManager = new SceneManager(window);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("auto-drive");

        window.add(sceneManager.getCurrentScene());
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        sceneManager.setScene("demo");


    }
}