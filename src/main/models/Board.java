package main.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private static final Color WHITE = new Color(222, 221, 219);
    private static final Color BROWN = new Color(150, 75, 0);

    private final ArrayList<Square> board;
//    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate>  coordinates;
//    private final HashMap<Rank, Movable>      movables;


    //
    // Constructor
    //
    public Board(int resolution) {
        this.board = setBoard(resolution);
        this.coordinates = setCoordinates();
//        this.figures = getFigures(resolution);
//        this.movables = setMovables();
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
    public HashMap<Point, Coordinate> setCoordinates() {
        final int BOARD_AREA = 64;
        HashMap<Point, Coordinate> map = new HashMap<>();
        Coordinate[] coordinateArray = Coordinate.values();

        for (int i = 0; i < BOARD_AREA; i++) {
            map.put(board.get(i).getLocation(),
                    coordinateArray[i]);
        }

        return map;
    }
//
//    /**
//     * <p>Maps instantiated figures to their location's coordinate for direct access.</p>
//     * @param resolution size at which to draw figures
//     * @return Coordinates mapped to figures
//     */
//    public HashMap<Coordinate, Figure> getFigures(int resolution) {
//        ArrayList<Figure> figureList = getFiguresList(resolution);
//        HashMap<Coordinate, Figure> figureMap = new HashMap<>();
//
//        figureList.forEach(figure -> figureMap.put(
//                getCoordinate(figure.getLocation()), figure));
//
//        return figureMap;
//    }

    /**
     * <p>Instantiates Figure objects to a default location on the board.</p>
     * @param resolution at which the figures will be drawn
     * @return a list of instantiated figures
     */
    public ArrayList<Figure> getFiguresList(int resolution) {
        ArrayList<Figure> list = new ArrayList<>();

        // TEST
//        list.add(new Figure(Side.WHITE, Rank.QUEEN,      getPointAt(Coordinate.G4), resolution));
//        list.add(new Figure(Side.BLACK, Rank.KNIGHT,      getPointAt(Coordinate.C4), resolution));

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
//    /**
//     * <p>Instantiates abstract representations of figures that will process
//     * validating moves over the board.</p>
//     * @return A map of Movable figure representations
//     */
//    private HashMap<Rank, Movable> setMovables() {
//        HashMap<Rank, Movable> map = new HashMap<>();
//
//        map.put(Rank.PAWN, new Pawn(figures, coordinates));
//        map.put(Rank.KNIGHT, new Knight(figures, coordinates));
//        map.put(Rank.ROOK, new Rook(figures, coordinates));
//        map.put(Rank.BISHOP, new Bishop(figures, coordinates));
//        map.put(Rank.QUEEN, new Queen(figures, coordinates));
//        map.put(Rank.KING, new King(figures, coordinates));
//
//        return map;
//    }

    //
    // Getters
    //
    public ArrayList<Square> getBoard() {
        return this.board;
    }

    public HashMap<Point, Coordinate> getCoordinates() {
        return this.coordinates;
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

    /**
     * <p>Retrieves square with origin at Coordinate</p>
     * @param c Square origin <code>Coordinate</code>
     * @return Square located at <code>Coordinate</code>
     */
    public Square getSquareAt(Coordinate c) {
        return getSquareAt(getPointAt(c));
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
//
//    public Figure getFigureAt(Point p) {
//        Figure figure = figures.get(getCoordinate(p));
////        System.out.println(figure);
//
//        return figure;
//    }
//
//    /**
//     * <p>Calculates available moves for a given figure.</p>
//     * @param figure selected figure on board
//     * @return a list of available moves
//     */
//    public HashSet<Coordinate> getMoves(Figure figure) {
//        return movables.get(figure.getRank()).moves(figure);
//    }
}
