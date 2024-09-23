package main.models.movables;


import main.models.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Pawn implements Movable {
    private static final int LEFT_UP = -9;
    private static final int RIGHT_UP = -7;
    private static final int LEFT_DOWN = 7;
    private static final int RIGHT_DOWN = 9;
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Pawn(HashMap<Coordinate, Figure> figures,
                HashMap<Point, Coordinate> coordinates) {
        this.figures = figures;
        this.coordinates = coordinates;
    }

    /**
     * <p>Evaluates if a figure occupies a coordinate
     * and is opposite side to the selected figure.
     * King cannot be removed.</p>
     * @param selected <code>Figure</code> to move
     * @param c <code>Coordinate</code> to move to
     * @return
     */
    private boolean isRemovable(Figure selected, Coordinate c) {
        Figure contested = figures.get(c);

        return contested != null
                && !(contested.getSide().equals(selected.getSide()))
                && !(contested.getRank().equals(Rank.KING));
    }

    /**
     * Checks if any figure occupies a given position.
     * @param c <code>Coordinate</code> to move to
     * @return false when a <code>Figure</code> occupies <code>Coordinate</code>
     */
    private boolean isEmpty(Coordinate c) {
        return figures.get(c) == null;
    }

    /**
     * <p>Checks for valid moves for a given figure.</p>
     * @param figure to be moved
     */
    public HashSet<Coordinate> moves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        switch (figure.getSide()) {

            case WHITE -> {
                // Move up
                c = Coordinate.getCoordinate(position.ordinal() - 8);
                if (c == null) {
                    // TODO pawn promotion
                    System.out.println("Cannot move further");
                    return null;
                }

                if (isEmpty(c)) {
                    moves.add(c);
                }

                // Remove diagonally up
                if (Coordinate.isBoundary(position)) {
                    if (position.ordinal() % 2 == 0) { // left edge
                        // Remove one step forward and right
                        c = Coordinate.getCoordinate(position.ordinal() - 7);
                    } else { // right edge
                        // Remove one step forward and left
                        c = Coordinate.getCoordinate(position.ordinal() - 9);
                    }
                } else {
                    // Remove one step forward and right
                    c = Coordinate.getCoordinate(position.ordinal() - 7);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }

                    // Remove one step forward and left
                    c = Coordinate.getCoordinate(position.ordinal() - 9);
                }
                if (isRemovable(figure, c)) {
                    moves.add(c);
                }
            }

            case BLACK -> {
                // Move in additive way by 8
                c = Coordinate.getCoordinate(position.ordinal() + 8);
                if (c == null) {
                    // TODO pawn promotion
                    System.out.println("Cannot move further");
                    return null;
                }

                if (isEmpty(c)) {
                    moves.add(c);
                }

                // Remove in additive way by 7 or 9
                if (Coordinate.isBoundary(position)) {
                    if (position.ordinal() % 2 == 0) { // left edge
                        // Remove one step forward and right
                        c = Coordinate.getCoordinate(position.ordinal() + 9);
                    } else { // right edge
                        // Remove one step forward and left
                        c = Coordinate.getCoordinate(position.ordinal() + 7);
                    }
                } else {
                    // Remove one step forward and right
                    c = Coordinate.getCoordinate(position.ordinal() + 9);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }

                    // Remove one step forward and left
                    c = Coordinate.getCoordinate(position.ordinal() + 7);
                }
                if (isRemovable(figure, c)) {
                    moves.add(c);
                }
            }
        }

        return moves;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();
        Coordinate c;

        c = coordinates.get(figure.getLocation());
        switch (figure.getSide()) {
            case WHITE -> {
                if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                    // Left-up
                    set.add(Coordinate.getCoordinate(c.ordinal() + LEFT_UP));
                }
                if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                    // Right-up
                    set.add(Coordinate.getCoordinate(c.ordinal() + RIGHT_UP));
                }
            }
            case BLACK -> {
                if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                    // Left-down
                    set.add(Coordinate.getCoordinate(c.ordinal() + LEFT_DOWN));
                }
                if (! (Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                    // Right-down
                    set.add(Coordinate.getCoordinate(c.ordinal() + RIGHT_DOWN));
                }
            }
        }

        set.remove(null);
        return set;
    }
}
