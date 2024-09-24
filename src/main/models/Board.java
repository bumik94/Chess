package main.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * contains information about layout and positions of the board.
 */
public class Board {
    private static final Color WHITE = new Color(222, 221, 219);
    private static final Color BROWN = new Color(150, 75, 0);

    private final int resolution;
    private final ArrayList<Square> squares;
    private final HashMap<Point, Coordinate>  coordinates;


    //
    // Constructor
    //
    public Board(int resolution) {
        this.resolution = resolution;
        this.squares = setSquares();
        this.coordinates = setCoordinates();
    }

    //
    // Field initializers
    //
    /**
     * <p>Initialize board for drawing.</p>
     * @return <code>ArrayList</code> containing <code>Square</code> objects
     *         with specified position and dimension
     */
    private ArrayList<Square> setSquares() {
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

    public ArrayList<Square> getSquares() {
        return this.squares;
    }

    /**
     * <p>Initialize <code>Coordinate</code> mapped to corresponding
     * <code>Square</code> on the <code>board</code>.
     * <code>Rectangle</code> holds data about the position
     * and dimension of <code>Square</code>.</p>
     * @return map of <code>Coordinate, Rectangle</code>
     */
    private HashMap<Point, Coordinate> setCoordinates() {
        final int BOARD_AREA = 64;
        HashMap<Point, Coordinate> map = new HashMap<>();
        Coordinate[] coordinateArray = Coordinate.values();

        for (int i = 0; i < BOARD_AREA; i++) {
            map.put(squares.get(i).getLocation(),
                    coordinateArray[i]);
        }

        return map;
    }

    public HashMap<Point, Coordinate> getCoordinates() {
        return this.coordinates;
    }

    /**
     * <p>Maps instantiated figures to their location's coordinate for direct access.</p>
     * @return Coordinates mapped to figures
     */
    public HashMap<Coordinate, Figure> getFiguresMap() {
        HashMap<Coordinate, Figure> figureMap = new HashMap<>();
        ArrayList<Figure> figureList = getFiguresList();

        figureList.forEach(figure -> figureMap.put(
                getCoordinateAt(figure.getLocation()), figure));

        return figureMap;
    }

    /**
     * <p>Instantiates Figure objects to a default location on the board.</p>
     * @return a list of instantiated figures
     */
    private ArrayList<Figure> getFiguresList() {
        ArrayList<Figure> list = new ArrayList<>();

        // TEST
//        list.add(new Figure(Side.BLACK, Rank.KING,      getPointAt(Coordinate.B4), resolution));
//        list.add(new Figure(Side.BLACK, Rank.PAWN,      getPointAt(Coordinate.F5), resolution));
//        list.add(new Figure(Side.BLACK, Rank.KNIGHT,      getPointAt(Coordinate.G5), resolution));
//        list.add(new Figure(Side.BLACK, Rank.KING,      getPointAt(Coordinate.G2), resolution));
        list.add(new Figure(Side.BLACK, Rank.PAWN,      getPointAt(Coordinate.F7), resolution));
        list.add(new Figure(Side.WHITE, Rank.PAWN,      getPointAt(Coordinate.E6), resolution));
        list.add(new Figure(Side.WHITE, Rank.PAWN,      getPointAt(Coordinate.E8), resolution));


        // White
//        for (int i = Coordinate.B1.ordinal(); i < Coordinate.A1.ordinal(); i++) {
//            list.add(new Figure(Side.WHITE, Rank.PAWN,  getPointAt(i), resolution));
//        }
//        list.add(new Figure(Side.WHITE, Rank.ROOK,      getPointAt(Coordinate.A1), resolution));
//        list.add(new Figure(Side.WHITE, Rank.ROOK,      getPointAt(Coordinate.A8), resolution));
//        list.add(new Figure(Side.WHITE, Rank.KNIGHT,    getPointAt(Coordinate.A2), resolution));
//        list.add(new Figure(Side.WHITE, Rank.KNIGHT,    getPointAt(Coordinate.A7), resolution));
//        list.add(new Figure(Side.WHITE, Rank.BISHOP,    getPointAt(Coordinate.A3), resolution));
//        list.add(new Figure(Side.WHITE, Rank.BISHOP,    getPointAt(Coordinate.A6), resolution));
//        list.add(new Figure(Side.WHITE, Rank.QUEEN,     getPointAt(Coordinate.A4), resolution));
//        list.add(new Figure(Side.WHITE, Rank.KING,      getPointAt(Coordinate.A5), resolution));
//
//        // Black
//        for (int i = Coordinate.G1.ordinal(); i < Coordinate.F1.ordinal(); i++) {
//            list.add(new Figure(Side.BLACK, Rank.PAWN,  getPointAt(i), resolution));
//        }
//        list.add(new Figure(Side.BLACK, Rank.ROOK,      getPointAt(Coordinate.H1), resolution));
//        list.add(new Figure(Side.BLACK, Rank.ROOK,      getPointAt(Coordinate.H8), resolution));
//        list.add(new Figure(Side.BLACK, Rank.KNIGHT,    getPointAt(Coordinate.H2), resolution));
//        list.add(new Figure(Side.BLACK, Rank.KNIGHT,    getPointAt(Coordinate.H7), resolution));
//        list.add(new Figure(Side.BLACK, Rank.BISHOP,    getPointAt(Coordinate.H3), resolution));
//        list.add(new Figure(Side.BLACK, Rank.BISHOP,    getPointAt(Coordinate.H6), resolution));
//        list.add(new Figure(Side.BLACK, Rank.QUEEN,     getPointAt(Coordinate.H4), resolution));
//        list.add(new Figure(Side.BLACK, Rank.KING,      getPointAt(Coordinate.H5), resolution));

        return list;
    }

    //
    // Location methods
    //
    /**
     * <p>Retrieves square with origin at X and Y</p>
     *
     * @param x Square origin X
     * @param y Square origin Y
     * @return Square located at X and Y
     */
    public Square getSquareAt(int x, int y) {
        Point p = new Point(x, y);
        Square square = null;

        for (Square s : squares) {
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

    /**
     * <p>Retrieves square with origin at Coordinate</p>
     * @param c Square origin <code>Coordinate</code>
     * @return Square located at <code>Coordinate</code>
     */
    public Square getSquareAt(Coordinate c) {
        return getSquareAt(getPointAt(c));
    }

    public Coordinate getCoordinateAt(Point p) {
        return coordinates.get(p);
    }

    public Point getPointAt(int i) {
        return squares.get(i).getLocation();
    }

    public Point getPointAt(Coordinate c) {
        return getPointAt(c.ordinal());
    }

}
