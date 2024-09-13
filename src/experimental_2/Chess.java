package experimental_2;

import experimental_2.models.Board;
import experimental_2.models.Side;
import experimental_2.models.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The main game class that draws UI and handles action events.
 * Selection is handled by this class and calls methods of the
 * components to change their state.
 */
public class Chess extends JPanel {
    // TODO Stockfish chess AI
    private final int RESOLUTION;
    private Side turn;  // will be used in game loop

    protected static final Color YELLOW = new Color(191, 191, 29);
    protected final Board board;

    protected Square selectedSquare;

    //
    // Constructor
    //
    public Chess(int RESOLUTION) {
        this.addMouseListener(new Listener());
        this.RESOLUTION = RESOLUTION;
        this.board = new Board(RESOLUTION);
        // Temporary - implement loop to change player turns
        this.turn = Side.WHITE;
    }

    //
    // Methods
    //
    /**
     * <p>Main painting method</p>
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
        board.paintFigures(g);
    }

    /**
     * <p>Paints the selected square yellow</p>
     * @param g paintComponent graphics
     */
    private void paintSelectedSquare(Graphics g) {
        if (selectedSquare != null) {
            selectedSquare.paintSquare(g);
        }
    }

    /**
     * <p>Repaints selected square and its previous position.</p>
     * @param rectangle from <code>mousePressed</code> method
     */
    private void setSelectedSquare(Rectangle rectangle) {
        if (selectedSquare != null) {
            if (selectedSquare.getLocation().equals(rectangle.getLocation())) {
                return;
            }
            // Repaint old position
            repaint(selectedSquare);
        }
        // Assign and repaint new position
        selectedSquare = new Square(rectangle, YELLOW);
        repaint(selectedSquare);
    }

    private void setTurn(Side side) {
        this.turn = side;
    }

    private Side getTurn() {
        return this.turn;
    }

    private void changeTurn() {
        switch (getTurn()) {
            case WHITE -> setTurn(Side.BLACK);
            case BLACK -> setTurn(Side.WHITE);
        }
    }

    /**
     * <p>Board size</p>
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RESOLUTION, RESOLUTION);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    // Mouse Listener
    ///////////////////////////////////////////////////////////////////////////////////////


    class Listener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            switch (e.getButton()) {
                // Left-click
                case MouseEvent.BUTTON1 -> {
                    // Draw selection
                    Square square = board.getSquareAt(e.getX(), e.getY());
                    Point p = square.getLocation();

                    setSelectedSquare(square);
                }
                // Right-click
                case MouseEvent.BUTTON3 -> {
                    // Clear selection
                    if (selectedSquare != null) {
                        Rectangle r = selectedSquare.getBounds();
                        selectedSquare = null;
//                        selectedFigure = null;
                        repaint(r);
                    }

                }
            }
        }

    }
}
