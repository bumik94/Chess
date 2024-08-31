package main.java.components;

import main.java.components.models.Coordinate;
import main.java.components.models.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color BROWN = new Color(150, 75, 0);

    private final ArrayList<Square> board;
    private final HashMap<Coordinate, Rectangle> coordinates;

// TODO problem with this coordinates map changing values on mouse action

    public Board(int FRAME_XY) {
        this.board = initBoard(FRAME_XY);
        this.coordinates = initCoordinates();
    }

    /**
     * <p>Initialize board for drawing.</p>
     * @param FRAME_XY resolution of the frame
     * @return <code>ArrayList</code> containing <code>Square</code> objects
     *         with specified position and dimension
     */
    private ArrayList<Square> initBoard(int FRAME_XY) {
        ArrayList<Square> list = new ArrayList<>();
        int SQUARE_SIZE = FRAME_XY / 8;
        int alt = 1;  // Alternate color pattern

        for (int y = 0; y < FRAME_XY; y += SQUARE_SIZE) {
            for (int x = 0; x < FRAME_XY; x += SQUARE_SIZE) {
                Color color;
                if (alt++ % 2 == 0) {
                        color = WHITE; }
                else {  color = BROWN; }
                Square square = new Square(x, y, SQUARE_SIZE, color);
                list.add(square);
            }
            alt++;
        }

        return list;
    }

    /**
     * Initialize <code>Coordinate</code> mapped to corresponding <code>Square</code> on the <code>board</code>.
     * <code>Rectangle</code> holds data about the position and dimension of <code>Square</code>.
     * @return map of <code>Coordinate, Rectangle</code>
     */
    private HashMap<Coordinate, Rectangle> initCoordinates() {
        HashMap<Coordinate, Rectangle> map = new HashMap<>();
        Coordinate[] coordinateArray = Coordinate.values();

        for (int i = 0; i < coordinateArray.length; i++) {
            map.put(coordinateArray[i], board.get(i).getBounds());
        }

        return map;
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

    public Rectangle getCoordinate(Coordinate c) {
        System.out.println("Coordinate " + coordinates.get(c));
        return coordinates.get(c);
    }

}
