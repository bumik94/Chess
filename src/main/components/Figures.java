package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Contains information about figures and their methods
 * for moving over the board
 */
public class Figures {
    private final HashMap<Point, Coordinate>  coordinates;
    private final HashMap<Coordinate, Figure> figuresMap;
    private final HashMap<Rank, Movable>      movables;

    public Figures(HashMap<Coordinate, Figure> figuresMap,
                   HashMap<Point, Coordinate> coordinates) {
        this.figuresMap = figuresMap;
        this.coordinates = coordinates;
        this.movables = setMovables();
    }

    /**
     * <p>Instantiates abstract representations of figures that will process
     * validating moves over the board.</p>
     * @return A map of Movable figure representations
     */
    private HashMap<Rank, Movable> setMovables() {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN, new Pawn(figuresMap, coordinates));
        map.put(Rank.KNIGHT, new Knight(figuresMap, coordinates));
        map.put(Rank.ROOK, new Rook(figuresMap, coordinates));
        map.put(Rank.BISHOP, new Bishop(figuresMap, coordinates));
        map.put(Rank.QUEEN, new Queen(figuresMap, coordinates));
        map.put(Rank.KING, new King(figuresMap, coordinates));

        return map;
    }

    /**
     * <p>Instantiates abstract representations of figures that will process
     * validating moves over the board.</p>
     * @return A map of Movable figure representations
     */
    public static HashMap<Rank, Movable> setMovables(
            HashMap<Coordinate, Figure> figuresMap,
            HashMap<Point, Coordinate>  coordinates) {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN, new Pawn(figuresMap, coordinates));
        map.put(Rank.KNIGHT, new Knight(figuresMap, coordinates));
        map.put(Rank.ROOK, new Rook(figuresMap, coordinates));
        map.put(Rank.BISHOP, new Bishop(figuresMap, coordinates));
        map.put(Rank.QUEEN, new Queen(figuresMap, coordinates));
        map.put(Rank.KING, new King(figuresMap, coordinates));

        return map;
    }

    public HashMap<Coordinate, Figure> getFiguresMap() {
        return this.figuresMap;
    }

    public Figure getFigureAt(Coordinate c) {
        return figuresMap.get(c);
    }

    /**
     * <p>Calculates available moves for a given figure.</p>
     * @param figure selected figure on board
     * @return a set of available moves
     */
    public HashSet<Coordinate> getMoves(Figure figure) {
        return movables.get(figure.getRank()).moves(figure);
    }
}
