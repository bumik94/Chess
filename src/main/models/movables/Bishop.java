package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Bishop implements Movable {
    private static final int LEFT_UP = -9;
    private static final int RIGHT_UP = -7;
    private static final int LEFT_DOWN = 7;
    private static final int RIGHT_DOWN = 9;

    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Bishop(HashMap<Coordinate, Figure> figures,
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
     * <p>Evaluates if a figure occupies a coordinate
     * and is on the same side to the selected figure.
     * King cannot be removed.</p>
     *
     * @param selected <code>Figure</code> to move
     * @param c        <code>Coordinate</code> to move to
     * @return true if opposing figure occupies position
     */
    private boolean isFriendly(Figure selected, Coordinate c) {
        Figure contested = figures.get(c);

        return contested != null
                && contested.getSide().equals(selected.getSide());
    }

    public HashSet<Coordinate> Check(Figure selected, Coordinate c) {
        HashSet<Coordinate> moves = new HashSet<>();


        return moves;
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

        // Left-up
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_UP);
        }

        // Left-down
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_DOWN);
        }

        // Right-up
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_UP);
        }

        // Right-down
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_DOWN);
        }

        return moves;
    }

    /**
     * <p>Checks for valid moves for a given figure.</p>
     *
     * @param figure to be moved
     */
    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Left-up
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
        while (c != null && (isEmpty(c) || isFriendly(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isFriendly(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_UP);
        }

        // Left-down
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
        while (c != null && (isEmpty(c) || isFriendly(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isFriendly(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_DOWN);
        }

        // Right-up
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
        while (c != null && (isEmpty(c) || isFriendly(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isFriendly(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_UP);
        }

        // Right-down
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
        while (c != null && (isEmpty(c) || isFriendly(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isFriendly(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_DOWN);
        }

        return moves;
    }

    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Left-up
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
        while (c != null && (isEmpty(c) || isOppositeKing(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c)) {
                break;
            }
            if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_UP);
        }
        moves.clear();

        // Left-down
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
        while (c != null && (isEmpty(c) || isOppositeKing(figure, c))
                && !Coordinate.isLeftBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c)) {
                break;
            }
            if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_DOWN);
        }
        moves.clear();

        // Right-up
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
        while (c != null && (isEmpty(c) || isOppositeKing(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c)) {
                break;
            }
            if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_UP);
        }
        moves.clear();

        // Right-down
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
        while (c != null && (isEmpty(c) || isOppositeKing(figure, c))
                && !Coordinate.isRightBoundary(position)) {
            moves.add(c);
            if (Coordinate.isBoundary(c)) {
                break;
            }
            if (isOppositeKing(figure, c)) {
                moves.add(position);
                return moves;
            }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_DOWN);
        }
        moves.clear();

        return moves;
    }
}
