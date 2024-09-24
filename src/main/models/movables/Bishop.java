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
    public HashSet<Coordinate> moves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Diagonal left-down
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_DOWN);
        }

        // Diagonal left-up
        c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_UP);
        }

        // Diagonal right-down
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_DOWN);
        }

        // Diagonal right-up
        c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_UP);
        }


        return moves;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();
        Coordinate c;

        // Left-up
        c = coordinates.get(figure.getLocation());
        while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_UP);
            set.add(c);
        }
        // Left-down
        c = coordinates.get(figure.getLocation());
        while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
            c = Coordinate.getCoordinate(c.ordinal() + LEFT_DOWN);
            set.add(c);
        }
        // Right-up
        c = coordinates.get(figure.getLocation());
        while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_UP);
            set.add(c);
        }
        // Right-down
        c = coordinates.get(figure.getLocation());
        while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
            c = Coordinate.getCoordinate(c.ordinal() + RIGHT_DOWN);
            set.add(c);
        }

        set.remove(null);
        return set;
    }

    @Override
    public HashSet<Coordinate> check(Figure figure) {
        return null;
    }
}
