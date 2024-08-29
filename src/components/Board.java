package components;

import java.awt.*;
import java.util.ArrayList;

public class Board {

    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color BROWN = new Color(150, 75, 0);
    private static final Color YELLOW = new Color(255, 255, 0);

    private final ArrayList<Square> board = new ArrayList<>();

    private Square selectedSquare;

    public Board(int FRAME_XY) {
        int i = 1;  // Alternate color pattern
        int SQUARE_SIZE = FRAME_XY / 8;

        for (int y = 0; y < FRAME_XY; y += SQUARE_SIZE) {
            for (int x = 0; x < FRAME_XY; x += SQUARE_SIZE) {
                Color color;
                if (i++ % 2 == 0) {
                    color = WHITE; }
                else {  color = BROWN; }
                Square square = new Square(x, y, SQUARE_SIZE, color);
                board.add(square);
            }
            i++;
        }

    }

    public ArrayList<Square> getBoard() {
        return this.board;
    }

    /**
     * Paint playing field
     *
     * @param g paintComponent graphics
     */
    public void paintBoard(Graphics g) {
        board.forEach(square -> square.paintSquare(g));
    }

    /**
     * Gets square from squares list at coordinates X and Y
     *
     * @param x cursor on X axis
     * @param y cursor on Y axis
     *
     * @return Square object located at X and Y
     */
    public Square getSquareAt(int x, int y) {
        Point p = new Point(x, y);
        final Square[] square = new Square[1];

        board.forEach(s -> {
            if (s.contains(p)) {
                square[0] = s;
                System.out.println("i=" + board.indexOf(s));
            }
        });

        return square[0];
    }

    /**
     * Gets <code>Square</code> from squares list at position p
     *
     * @param p cursor at <code>Point</code>
     *
     * @return Square object contained at <code>Point</code>
     */
    public Square getSquareAt(Point p) {
        return getSquareAt(p.x, p.y);
    }

}
