package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Knight implements Movable {
    private static final int UP_LEFT = -17;
    private static final int LEFT_UP = -10;
    private static final int LEFT_DOWN = 6;
    private static final int DOWN_LEFT = 15;
    private static final int UP_RIGHT = -15;
    private static final int RIGHT_UP = -6;
    private static final int RIGHT_DOWN = 10;
    private static final int DOWN_RIGHT = 17;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;

    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Knight(HashMap<Coordinate, Figure> figures,
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
     * @return true if the figure at the <code>Coordinate</code>
     * is opposite side and not a king
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

        // Left side
        if (! Coordinate.isLeftBoundary(position)) {
            // Up-left
            c = Coordinate.getCoordinate(position.ordinal() + UP_LEFT);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            // Down-left
            c = Coordinate.getCoordinate(position.ordinal() + DOWN_LEFT);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }

            c = Coordinate.getCoordinate(position.ordinal() + LEFT); // Offset
            if (c != null && !Coordinate.isLeftBoundary(position)) {
                // Left-up
                c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                // Left-down
                c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
        }

        // Right side
        if (! Coordinate.isRightBoundary(position)) {
            // Up-right
            c = Coordinate.getCoordinate(position.ordinal() + UP_RIGHT);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            // Down-right
            c = Coordinate.getCoordinate(position.ordinal() + DOWN_RIGHT);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }

            c = Coordinate.getCoordinate(position.ordinal() + RIGHT); // Offset
            if (c != null && !Coordinate.isRightBoundary(position)) {
                // Right-up
                c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                // Right-down
                c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
        }

        return moves;
    }

    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Left side
        if (! Coordinate.isLeftBoundary(position)) {
            // Up-left
            c = Coordinate.getCoordinate(position.ordinal() + UP_LEFT);
            if (c != null) {
                moves.add(c);
            }
            // Down-left
            c = Coordinate.getCoordinate(position.ordinal() + DOWN_LEFT);
            if (c != null) {
                moves.add(c);
            }

            c = Coordinate.getCoordinate(position.ordinal() + LEFT); // Offset
            if (c != null && !Coordinate.isLeftBoundary(c)) {
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
        }

        // Right side
        if (! Coordinate.isRightBoundary(position)) {
            // Up-right
            c = Coordinate.getCoordinate(position.ordinal() + UP_RIGHT);
            if (c != null) {
                moves.add(c);
            }
            // Down-right
            c = Coordinate.getCoordinate(position.ordinal() + DOWN_RIGHT);
            if (c != null) {
                moves.add(c);
            }

            c = Coordinate.getCoordinate(position.ordinal() + RIGHT); // Offset
            if (c != null && !Coordinate.isRightBoundary(c)) {
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
        }

        return moves;
    }

    @Override
    public HashSet<Coordinate> getCheck(Figure figure) {
        return null;
    }
}
