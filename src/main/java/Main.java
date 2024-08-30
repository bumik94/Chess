package main.java;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("main.java.Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Chess());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}