package main;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import scene.SceneManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        FlatMacDarkLaf.setup();

        JFrame window = new JFrame();
        SceneManager sceneManager = new SceneManager(window);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("auto-drive");

        window.getContentPane().add(sceneManager.getCurrentScene());
        window.pack();
        window.setLocationRelativeTo(null);

        sceneManager.setScene("menu");
        window.setVisible(true);
    }
}