package experimental_2;

import experimental_2.models.Board;
import experimental_2.models.Square;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Handles game logic
 * Contains game objects
 * Has methods for drawing
 */
public class Listener extends MouseAdapter {
    private final ArrayList<Square> board;

    //
    // Constructor
    //
    public Listener(final int RESOLUTION) {
        this.board = Board.getBoard(RESOLUTION);
    }

    //
    // Methods
    //
    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            // Left-click
            case MouseEvent.BUTTON1 -> {
                // Draw selection
                Square square = getSquareAt(e.getX(), e.getY());
                Point p = square.getLocation();
            }
            // Right-click
            case MouseEvent.BUTTON3 -> {
                // Clear selection

            }
        }
    }

    public ArrayList<Square> getBoard() {
        return this.board;
    }

    /**
     * <p>Retrieves square with origin at X and Y</p>
     * @param x Square origin X
     * @param y Square origin Y
     * @return Square located at X and Y
     */
    public Square getSquareAt(int x, int y) {
        Point p = new Point(x, y);
        Square square = null;

        for (Square s : board) {
            if (s.contains(p)) {
                square = s;
            }
        }

        return square;
    }

    /**
     * <p>Retrieves square with origin at Point</p>
     * @param p Square origin <code>Point</code>
     * @return Square located at <code>Point</code>
     */
    public Square getSquareAt(Point p) {
        return getSquareAt((int) p.getX(), (int) p.getY());
    }

}
