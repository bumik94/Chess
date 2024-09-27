package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Rook implements Movable {
    private static final int UP = -8;
    private static final int DOWN = 8;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;

    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Rook(HashMap<Coordinate, Figure> figures,
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
     * <p>Evaluates if a figure occupies a coordinate
     * and is on the same side to the selected figure.
     * King cannot be removed.</p>
     *
     * @param selected <code>Figure</code> to move
     * @param c        <code>Coordinate</code> to move to
     * @return true if opposing figure occupies position
     */
    private boolean isProtected(Figure selected, Coordinate c) {
        Figure contested = figures.get(c);

        return contested != null
                && contested.getSide().equals(selected.getSide());
    }

    /**
     * <p>Evaluates if opposite side king occupies a coordinate.</p>
     * @param selected selected figure on board
     * @param c currently evaluated position
     * @return true if an opposite king occupies position
     */
    private boolean isOppositeKing(Figure selected, Coordinate c) {
        Figure contested = figures.get(c);

        return contested != null
                && contested.getRank().equals(Rank.KING)
                && !(contested.getSide().equals(selected.getSide()));
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
        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
            c = Coordinate.getCoordinate(c.ordinal() + UP);
        }
        // Down
        c = Coordinate.getCoordinate(position.ordinal() + DOWN);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
            c = Coordinate.getCoordinate(c.ordinal() + DOWN);
        }
        // Left
        c = Coordinate.getCoordinate(position.ordinal() + LEFT);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT);
        }
        // Right
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT);
        }

        return moves;
    }

    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Up
        c = Coordinate.getCoordinate(position.ordinal() + UP);
        while (c != null && (isEmpty(c) || isProtected(figure, c))) {
            moves.add(c);
            c = Coordinate.getCoordinate(c.ordinal() + UP);
        }
        // Down
        c = Coordinate.getCoordinate(position.ordinal() + DOWN);
        while (c != null && (isEmpty(c) || isProtected(figure, c))) {
            moves.add(c);
            c = Coordinate.getCoordinate(c.ordinal() + DOWN);
        }
        // Left
        c = Coordinate.getCoordinate(position.ordinal() + LEFT);
        while (c != null && (isEmpty(c) || isProtected(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isProtected(figure, c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT);
        }
        // Right
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT);
        while (c != null && (isEmpty(c) || isProtected(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isProtected(figure, c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT);
        }

        return moves;
    }

    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Up
        c = Coordinate.getCoordinate(position.ordinal() + UP);
        while (c != null) {
            if (isEmpty(c)) {
                moves.add(c);
            }
            else if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            c = Coordinate.getCoordinate(c.ordinal() + UP);
        }
        moves.clear();

        // Down
        c = Coordinate.getCoordinate(position.ordinal() + DOWN);
        while (c != null) {
            if (isEmpty(c)) {
                moves.add(c);
            }
            else if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            c = Coordinate.getCoordinate(c.ordinal() + DOWN);
        }
        moves.clear();

        // Left
        c = Coordinate.getCoordinate(position.ordinal() + LEFT);
        while (c != null && !Coordinate.isLeftBoundary(position)) {
            if (isEmpty(c)) {
                moves.add(c);
            }
            else if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            if (Coordinate.isBoundary(c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT);
        }
        moves.clear();

        // Right
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT);
        while (c != null && !Coordinate.isRightBoundary(position)) {
            if (isEmpty(c)) {
                moves.add(c);
            }
            else if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            if (Coordinate.isBoundary(c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT);
        }
        moves.clear();

        return moves;
    }
}
