package experimental;

import experimental.models.Coordinate;
import experimental.models.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private static final int   AREA = 64;
    private static final Color WHITE = new Color(222, 221, 219);
    private static final Color BROWN = new Color(150, 75, 0);

    private final ArrayList<Square> board;
    private final HashMap<Point, Coordinate> coordinates;


    public Board(int resolution) {
        this.board = initBoard(resolution);
        this.coordinates = initCoordinates();
    }

    /**
     * <p>Initialize board for drawing.</p>
     * @param resolution resolution of the frame
     * @return <code>ArrayList</code> containing <code>Square</code> objects
     *         with specified position and dimension
     */
    private ArrayList<Square> initBoard(int resolution) {
        ArrayList<Square> list = new ArrayList<>();
        int SQUARE_SIZE = resolution / 8;
        int alt = 1;  // Alternate color pattern

        for (int y = 0; y < resolution; y += SQUARE_SIZE) {
            for (int x = 0; x < resolution; x += SQUARE_SIZE) {

                Color color;
                if (alt++ % 2 == 0) {
                        color = BROWN; }
                else {  color = WHITE; }

                Square square = new Square(x, y, SQUARE_SIZE, color);
                list.add(square);
            }
            alt++;
        }

        return list;
    }

    /**
     * <p>Initialize <code>Coordinate</code> mapped to corresponding
     * <code>Square</code> on the <code>board</code>.
     * <code>Rectangle</code> holds data about the position
     * and dimension of <code>Square</code>.</p>
     * @return map of <code>Coordinate, Rectangle</code>
     */
    private HashMap<Point, Coordinate> initCoordinates() {
        HashMap<Point, Coordinate> map = new HashMap<>();
        Coordinate[] coordinateArray = Coordinate.values();

        for (int i = 0; i < AREA; i++) {
            map.put(board.get(i).getLocation(),
                    coordinateArray[i]);
        }

        return map;
    }

    /**
     * <p>Paint playing board</p>
     * @param g paintComponent graphics
     */
    public void paintBoard(Graphics g) {
        board.forEach(square -> square.paintSquare(g));
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

    public Coordinate getCoordinate(Point p) {
        return coordinates.get(p);
    }

    public Point getPointAt(int index) {
        return board.get(index).getLocation();
    }

    public Point getPointAt(Coordinate c) {
        return getPointAt(c.ordinal());
    }

    public HashMap<Point, Coordinate> getCoordinates() {
        return coordinates;
    }

}
