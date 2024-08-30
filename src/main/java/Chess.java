package main.java;

import main.java.components.Board;
import main.java.components.Pawn;
import main.java.components.Square;
import main.java.components.models.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Chess extends JPanel {

    private static final Color YELLOW = new Color(255, 255, 0);
    private static final int FRAME_XY = 1024;
    /*
     * Board class for drawing playing field
     */
    private final Board board;
    private final Pawn pawn;

    private Square selectedSquare;


    public Chess() {
        board = new Board(FRAME_XY);
        pawn = new Pawn(Figure.Side.WHITE, Figure.Rank.PAWN, FRAME_XY);

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
     * Panel size
     *
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FRAME_XY, FRAME_XY);
    }

    /**
     * main.java.Main painting method
     *
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        board.paintBoard(g);
        paintSelectedSquare(g);
        pawn.paintFigure(g, new Rectangle(0, 0, FRAME_XY / 8, FRAME_XY / 8));
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
