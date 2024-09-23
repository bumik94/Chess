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
            HashSet<Coordinate> controlledMovesSet = getControlledMoves(figure.getSide());
            movesSet.removeAll(controlledMovesSet);
        }

        return movesSet;
    }

    /**
     * <p>Provides all positions of every figure for each possible direction.
     * Used in asymmetric difference on King's moves to determine safe move.</p>
     * @param side <code>Side</code> of the selected <code>Figure</code>
     * @return a set of all moves of the opposite side
     */
    public HashSet<Coordinate> getControlledMoves(Side side) {
        Coordinate c;// = null;
        HashSet<Coordinate> set = new HashSet<>();

        for (Figure figure : figuresMap.values()) {
            if (! figure.getSide().equals(side)) {

                if (figure.getRank().equals(Rank.PAWN)) {
                    c = coordinates.get(figure.getLocation());
                    switch (figure.getSide()) {
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

                else if (figure.getRank().equals(Rank.KING)) {
                    c = coordinates.get(figure.getLocation());

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

                }
                else if (figure.getRank().equals(Rank.ROOK)
                        || figure.getRank().equals(Rank.QUEEN)) {
                    // Up
                    c = Coordinate.getCoordinate(
                            coordinates.get(figure.getLocation()).ordinal() - 8);
                    while (c != null) {
                        set.add(c);
                        c = Coordinate.getCoordinate(c.ordinal() - 8);
                    }
                    // Down
                    c = Coordinate.getCoordinate(
                            coordinates.get(figure.getLocation()).ordinal() + 8);
                    while (c != null) {
                        set.add(c);
                        c = Coordinate.getCoordinate(c.ordinal() + 8);
                    }
                    // Left
                    c = coordinates.get(figure.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() - 1);
                        set.add(c);
                    }
                    // Right
                    c = coordinates.get(figure.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() + 1);
                        set.add(c);
                    }

                }
                else if (figure.getRank().equals(Rank.BISHOP)
                        || figure.getRank().equals(Rank.QUEEN)) {
                    // Left-up
                    c = coordinates.get(figure.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() - 9);
                        set.add(c);
                    }
                    // Left-down
                    c = coordinates.get(figure.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() + 7);
                        set.add(c);
                    }
                    // Right-up
                    c = coordinates.get(figure.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() - 7);
                        set.add(c);
                    }
                    // Right-down
                    c = coordinates.get(figure.getLocation());
                    while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                        c = Coordinate.getCoordinate(c.ordinal() + 9);
                        set.add(c);
                    }

                }
                else if (figure.getRank().equals(Rank.KNIGHT)) {
                    // Left side
                    c = coordinates.get(figure.getLocation());
                    if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                        // Up-left
                        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 17);
                        set.add(c);
                        // Down-left
                        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 15);
                        set.add(c);

                        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 1);
                        if (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                            // Left-up
                            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 10);
                            set.add(c);
                            // Left-down
                            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 6);
                            set.add(c);
                        }
                    }
                    // Right side
                    c = coordinates.get(figure.getLocation());
                    if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                        // Up-right
                        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 15);
                        set.add(c);
                        // Down-right
                        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 17);
                        set.add(c);

                        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 1);
                        if (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                            // Right-up
                            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 6);
                            set.add(c);
                            // Right-down
                            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 10);
                            set.add(c);
                        }
                    }
                }
            }
        }
        set.remove(null);
        return set;
    }
}
