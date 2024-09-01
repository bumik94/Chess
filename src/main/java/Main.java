package main.java;

import main.java.components.ChessPanel;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("main.java.components.Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChessPanel(512));
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