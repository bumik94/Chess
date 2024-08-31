package main.java.components;

import main.java.components.figures.Pawn;
import main.java.components.models.Coordinate;
import main.java.components.models.Square;
import main.java.components.models.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The main game class that draws UI and handles action events.
 * <p>This class should contain instances of all components used
 * for the game, namely Board and Figures. Each figure keeps
 * track of its position on the board and this class uses this
 * information to draw the board.</p>
 * Selection is handled by this class and calls methods of the
 * components to change their state.
 */
public class DrawChess extends JPanel {

    private static final Color YELLOW = new Color(255, 255, 0);
    private static final int FRAME_XY = 512;

    private final Board board;
    private final Pawn pawn;

    private Square selectedSquare;


    public DrawChess() {
        board = new Board(FRAME_XY);
        pawn = new Pawn(Figure.Side.WHITE, Figure.Rank.PAWN, Coordinate.A8, FRAME_XY);

        /*
         * Left-click: selects a single square on the board
         * Right-click: deselects currently selected square
         *
         * This method should check all components of the Chess class
         * and compare their location on the board. If the selectedSquare
         * location contains a figure, get figure's available moves
         * and draw them on the board.
         *
         * If a figure is selected and user clicks on valid move square,
         * change figures location to the new square and redraw figure's
         * previous square and new square.
         *
         * If another figure is on the valid move square, check figures
         * side and if it's opponent, kick that figure and move to its position.
         *
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
        return new Dimension(FRAME_XY, FRAME_XY);
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
        board.paintBoard(g);
        paintSelectedSquare(g);
        pawn.paintFigure(g, board.getCoordinate(Coordinate.A8));
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
