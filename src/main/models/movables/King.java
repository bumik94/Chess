package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class King implements Movable {
    private static final int UP = -8;
    private static final int DOWN = 8;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int LEFT_UP = -9;
    private static final int RIGHT_UP = -7;
    private static final int LEFT_DOWN = 7;
    private static final int RIGHT_DOWN = 9;

    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public King(HashMap<Coordinate, Figure> figures,
                HashMap<Point, Coordinate> coordinates) {
        this.figures = figures;
        this.coordinates = coordinates;
    }

    /**
     * <p>Evaluates if a figure occupies a coordinate
     * and is opposite side to the selected figure.
     * King cannot be removed.</p>
     *
     * @param selected <code>Figure</code> to move
     * @param c        <code>Coordinate</code> to move to
     * @return true if opposing figure occupies position
     */
    private boolean isRemovable(Figure selected, Coordinate c) {
        Figure contested = figures.get(c);

        return contested != null
                && !(contested.getSide().equals(selected.getSide()))
                && !(contested.getRank().equals(Rank.KING));
    }

    /**
     * Checks if any figure occupies a given position.
     *
     * @param c <code>Coordinate</code> to move to
     * @return false when a <code>Figure</code> occupies <code>Coordinate</code>
     */
    private boolean isEmpty(Coordinate c) {
        return figures.get(c) == null;
    }

    /**
     * <p>Checks for valid moves for a given figure.</p>
     *
     * @param figure to be moved
     */
    public HashSet<Coordinate> getMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Up
        c = Coordinate.getCoordinate(position.ordinal() + UP);
        if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
        }
        // Down
        c = Coordinate.getCoordinate(position.ordinal() + DOWN);
        if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
        }

        // Left boundary
        if (!Coordinate.isLeftBoundary(position)) {
            // Left
            c = Coordinate.getCoordinate(position.ordinal() + LEFT);
            if (isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Left-up
            c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
            if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
                moves.add(c);
            }
            // Left-down
            c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
            if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
                moves.add(c);
            }
        }

        // Right boundary
        if (!Coordinate.isRightBoundary(position)) {
            // Right
            c = Coordinate.getCoordinate(position.ordinal() + RIGHT);
            if (isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Right-up
            c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
            if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
                moves.add(c);
            }
            // Right-down
            c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
            if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
                moves.add(c);
            }
        }

        return moves;
    }


    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Up
        c = Coordinate.getCoordinate(position.ordinal() + UP);
        if (c != null) {
            moves.add(c);
        }
        // Down
        c = Coordinate.getCoordinate(position.ordinal() + DOWN);
        if (c != null) {
            moves.add(c);
        }

        // Left boundary
        if (! Coordinate.isLeftBoundary(position)) {
            // Left
            c = Coordinate.getCoordinate(position.ordinal() + LEFT);
            moves.add(c);
            // Left-up
            c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
            if (c != null) {
                moves.add(c);
            }
            // Left-down
            c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
            if (c != null) {
                moves.add(c);
            }
        }

        // Right boundary
        if (! Coordinate.isRightBoundary(position)) {
            // Right
            c = Coordinate.getCoordinate(position.ordinal() + RIGHT);
            moves.add(c);
            // Right-up
            c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
            if (c != null) {
                moves.add(c);
            }
            // Right-down
            c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
            if (c != null) {
                moves.add(c);
            }
        }

        return moves;
    }

    /**
     * King cannot check on his own.
     * @param figure selected figure
     * @return empty set
     */
    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        return new HashSet<>();
    }
}
