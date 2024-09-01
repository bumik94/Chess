package main.java.components;

import main.java.components.models.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The main game class that draws UI and handles action events.
 * Selection is handled by this class and calls methods of the
 * components to change their state.
 */
public class ChessPanel extends JPanel {
    private static final Color YELLOW = new Color(255, 255, 0);

    private final int resolution;
    private final ChessGame     game;
    private final ChessBoard    board;

    private Square selectedSquare;


    public ChessPanel(int resolution) {
        this.resolution = resolution;
        this.game = new ChessGame(resolution);
        this.board = game.getBoard();

        /*
         * Left-click: selects a single square on the board
         * Right-click: deselects currently selected square
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton()) {
                    // Left-click
                    case MouseEvent.BUTTON1 -> {
                        Point p = new Point(e.getX(), e.getY());
                        Square square = board.getSquareAt(p);
                        setSelectedSquare(square);
                        System.out.println(square);
                    }
                    // Right-click
                    case MouseEvent.BUTTON3 -> {
                        if (selectedSquare != null) {
                            Rectangle r = selectedSquare.getBounds();
                            selectedSquare = null;
                            repaint(r);
                        }
                    }
                }
            }
        });
    }

    /**
     * Board size
     *
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(resolution, resolution);
    }

    /**
     * Main painting method
     *
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        /*
        Order of draw:
            1) board
            2) selection
            3) figures
         */
        board.paintBoard(g);    // 1)
        paintSelectedSquare(g); // 2)
        game.drawFigures(g);    // 3)
    }

    /**
     * Paints a yellow square at the location of a selected square
     *
     * @param g paintComponent graphics
     */
    private void paintSelectedSquare(Graphics g) {

        if (selectedSquare != null) {
            selectedSquare.paintSquare(g);
        }
    }

    /**
     * Keep track of currently selected square
     *
     * @param selectedSquare from <code>mousePressed</code> method
     */
    private void setSelectedSquare(Rectangle selectedSquare) {

        if (this.selectedSquare != null) {
            if (this.selectedSquare.getLocation().equals(selectedSquare.getLocation())) {
                return;
            }
            repaint(this.selectedSquare);
        }
        this.selectedSquare = new Square(selectedSquare, YELLOW);
        repaint(this.selectedSquare);
    }

}
