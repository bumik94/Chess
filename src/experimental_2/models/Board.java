package experimental_2.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private static final Color WHITE = new Color(222, 221, 219);
    private static final Color BROWN = new Color(150, 75, 0);

    private final ArrayList<Square> board;
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;


    //
    // Constructor
    //
    public Board(int resolution) {
        this.board = setBoard(resolution);
        this.coordinates = setCoordinates();
        this.figures = setFigures(getFiguresList(resolution));
    }

    //
    // Field initializers
    //
    /**
     * <p>Initialize board for drawing.</p>
     * @param resolution resolution of the frame
     * @return <code>ArrayList</code> containing <code>Square</code> objects
     *         with specified position and dimension
     */
    private ArrayList<Square> setBoard(int resolution) {
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
    private HashMap<Point, Coordinate> setCoordinates() {
        final int BOARD_AREA = 64;
        HashMap<Point, Coordinate> map = new HashMap<>();
        Coordinate[] coordinateArray = Coordinate.values();

        for (int i = 0; i < BOARD_AREA; i++) {
            map.put(board.get(i).getLocation(),
                    coordinateArray[i]);
        }

        return map;
    }

    /**
     * <p>Maps instantiated figures to their location's coordinate for direct access.</p>
     * @param list A list of instantiated figures
     * @return Coordinates mapped to figures
     */
    private HashMap<Coordinate, Figure> setFigures(ArrayList<Figure> list) {
        HashMap<Coordinate, Figure> map = new HashMap<>();

        list.forEach(figure -> map.put(
                getCoordinate(figure.getLocation()), figure));

        return map;
    }

    /**
     * <p>Instantiates Figure objects to a default location on the board.</p>
     * @param resolution at which the figures will be drawn
     * @return a list of instantiated figures
     */
    private ArrayList<Figure> getFiguresList(int resolution) {
        ArrayList<Figure> list = new ArrayList<>();

        // White
        for (int i = Coordinate.B1.ordinal(); i < Coordinate.A1.ordinal(); i++) {
            list.add(new Figure(Side.WHITE, Rank.PAWN,  getPointAt(i), resolution));
        }
        list.add(new Figure(Side.WHITE, Rank.ROOK,      getPointAt(Coordinate.A1), resolution));
        list.add(new Figure(Side.WHITE, Rank.ROOK,      getPointAt(Coordinate.A8), resolution));
        list.add(new Figure(Side.WHITE, Rank.KNIGHT,    getPointAt(Coordinate.A2), resolution));
        list.add(new Figure(Side.WHITE, Rank.KNIGHT,    getPointAt(Coordinate.A7), resolution));
        list.add(new Figure(Side.WHITE, Rank.BISHOP,    getPointAt(Coordinate.A3), resolution));
        list.add(new Figure(Side.WHITE, Rank.BISHOP,    getPointAt(Coordinate.A6), resolution));
        list.add(new Figure(Side.WHITE, Rank.QUEEN,     getPointAt(Coordinate.A4), resolution));
        list.add(new Figure(Side.WHITE, Rank.KING,      getPointAt(Coordinate.A5), resolution));

        // Black
        for (int i = Coordinate.G1.ordinal(); i < Coordinate.F1.ordinal(); i++) {
            list.add(new Figure(Side.BLACK, Rank.PAWN,  getPointAt(i), resolution));
        }
        list.add(new Figure(Side.BLACK, Rank.ROOK,      getPointAt(Coordinate.H1), resolution));
        list.add(new Figure(Side.BLACK, Rank.ROOK,      getPointAt(Coordinate.H8), resolution));
        list.add(new Figure(Side.BLACK, Rank.KNIGHT,    getPointAt(Coordinate.H2), resolution));
        list.add(new Figure(Side.BLACK, Rank.KNIGHT,    getPointAt(Coordinate.H7), resolution));
        list.add(new Figure(Side.BLACK, Rank.BISHOP,    getPointAt(Coordinate.H3), resolution));
        list.add(new Figure(Side.BLACK, Rank.BISHOP,    getPointAt(Coordinate.H6), resolution));
        list.add(new Figure(Side.BLACK, Rank.QUEEN,     getPointAt(Coordinate.H4), resolution));
        list.add(new Figure(Side.BLACK, Rank.KING,      getPointAt(Coordinate.H5), resolution));

        return list;
    }

    //
    // Painting methods
    //
    /**
     * <p>Paint playing board</p>
     * @param g paintComponent graphics
     */
    public void paintBoard(Graphics g) {
        board.forEach(square -> square.paintSquare(g));
    }

    public void paintFigures(Graphics g) {
        figures.values()
                .forEach(figure -> figure.paintFigure(g));
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

    public Figure getFigureAt(Point p) {
        Figure figure = figures.get(getCoordinate(p));
//        System.out.println(figure);

        return figure;
    }
}
