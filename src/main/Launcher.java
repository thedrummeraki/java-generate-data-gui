package main;

import config.Config;
import ui.MainWindow;
import work.Coordinate;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        MainWindow window = new MainWindow("3D Visualization - Data Generation");
        window.show();
    }
}
