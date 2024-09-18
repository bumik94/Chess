package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private final PlayingField board;
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate>  coordinates;
    private final HashMap<Rank, Movable>      movables;

    public Game(int resolution) {
        this.board = new PlayingField(resolution);
        this.coordinates = setCoordinates();
        this.figures = setFigures(getFiguresList(resolution));
        this.movables = setMovables();
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
     * @param figureList A list of instantiated figures
     * @return Coordinates mapped to figures
     */
    private HashMap<Coordinate, Figure> setFigures(ArrayList<Figure> figureList) {
        HashMap<Coordinate, Figure> figureMap = new HashMap<>();

        figureList.forEach(figure -> figureMap.put(
                getCoordinate(figure.getLocation()), figure));

        return figureMap;
    }

    public HashMap<Coordinate, Figure> getFigures() {
        return this.figures;
    }

    /**
     * <p>Instantiates Figure objects to a default location on the board.</p>
     * @param resolution at which the figures will be drawn
     * @return a list of instantiated figures
     */
    private ArrayList<Figure> getFiguresList(int resolution) {
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

    /**
     * <p>Instantiates abstract representations of figures that will process
     * validating moves over the board.</p>
     * @return A map of Movable figure representations
     */
    private HashMap<Rank, Movable> setMovables() {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN, new Pawn(figures, coordinates));
        map.put(Rank.KNIGHT, new Knight(figures, coordinates));
        map.put(Rank.ROOK, new Rook(figures, coordinates));
        map.put(Rank.BISHOP, new Bishop(figures, coordinates));
        map.put(Rank.QUEEN, new Queen(figures, coordinates));
        map.put(Rank.KING, new King(figures, coordinates));

        return map;
    }

}
