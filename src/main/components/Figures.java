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
        HashSet<Coordinate> movesSet = movables.get(figure.getRank()).moves(figure);

        if (figure.getRank().equals(Rank.KING)) {
            HashSet<Coordinate> oppositeMovesSet = getOppositeMoves(figure);
            // TODO Doesn't beam behind King allowing for illegal move
            movesSet.removeAll(oppositeMovesSet);
        }

        return movesSet;
    }

    public HashSet<Coordinate> getOppositeMoves(Figure selected) {
        Coordinate c = null;
        HashSet<Coordinate> set = new HashSet<>();

        for (Figure opposite : figuresMap.values()) {
            if (! opposite.getSide().equals(selected.getSide())) {

                if (opposite.getRank().equals(Rank.PAWN)) {
                    c = coordinates.get(opposite.getLocation());
                    switch (opposite.getSide()) {
                        case WHITE -> {
                            if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                                // Left-up
                                set.add(Coordinate.getCoordinate(c.ordinal() - 9));
                            }
                            if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                                // Right-up
                                set.add(Coordinate.getCoordinate(c.ordinal() - 7));
                            }
                        }
                        case BLACK -> {
                            if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                                // Left-down
                                set.add(Coordinate.getCoordinate(c.ordinal() + 7));
                            }
                            if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                                // Right-down
                                set.add(Coordinate.getCoordinate(c.ordinal() + 9));
                            }
                        }
                    }
                }

                else if (opposite.getRank().equals(Rank.KING)) {
                    c = coordinates.get(opposite.getLocation());

                    // Up
                    set.add(Coordinate.getCoordinate(c.ordinal() + 8));
                    // Down
                    set.add(Coordinate.getCoordinate(c.ordinal() - 8));

                    if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        // Left
                        set.add(Coordinate.getCoordinate(c.ordinal() - 1));
                        // Left-up
                        set.add(Coordinate.getCoordinate(c.ordinal() - 9));
                        // Left-down
                        set.add(Coordinate.getCoordinate(c.ordinal() + 7));
                    }
                    if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                        // Right
                        set.add(Coordinate.getCoordinate(c.ordinal() + 1));
                        // Right-up
                        set.add(Coordinate.getCoordinate(c.ordinal() - 7));
                        // Right-down
                        set.add(Coordinate.getCoordinate(c.ordinal() + 9));
                    }

                } else if (opposite.getRank().equals(Rank.ROOK)
                        || opposite.getRank().equals(Rank.QUEEN)) {
                    // Up
                    c = Coordinate.getCoordinate(coordinates.get(opposite.getLocation()).ordinal() - 8);
                    while (c != null) {
                        set.add(c);
                        c = Coordinate.getCoordinate(c.ordinal() - 8);
                    }
                    // Down
                    c = Coordinate.getCoordinate(coordinates.get(opposite.getLocation()).ordinal() + 8);
                    while (c != null) {
                        set.add(c);
                        c = Coordinate.getCoordinate(c.ordinal() + 8);
                    }
                    // Left
                    c = coordinates.get(opposite.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() - 1);
                        set.add(c);
                    }
                    // Right
                    c = coordinates.get(opposite.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() + 1);
                        set.add(c);
                    }

                } else if (opposite.getRank().equals(Rank.BISHOP)
                        || opposite.getRank().equals(Rank.QUEEN)) {

                } else {
                    set.addAll(getMoves(opposite));
                }
            }
        }

        return set;
    }
}
