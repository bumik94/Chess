package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Knight implements Movable {
    private static final int UP_LEFT = -8;
    private static final int LEFT_UP = -9;
    private static final int LEFT_DOWN = 7;
    private static final int DOWN_LEFT = 8;
    private static final int DOWN_RIGHT = 1;
    private static final int RIGHT_DOWN = 9;
    private static final int LEFT = -1;
    private static final int RIGHT_UP = -7;

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
//        HashSet<Integer> leftIntervals = new HashSet<>(List.of(-17, -10, 6, 15));
//        HashSet<Integer> rightIntervals = new HashSet<>(List.of(-15, -6, 10, 17));
        Coordinate c;


        // Left side
        c = coordinates.get(figure.getLocation());
        if (!(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
            // Up-left
            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 17);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            // Down-left
            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 15);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }

            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 1);
            if (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
                // Left-up
                c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 10);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                // Left-down
                c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 6);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
        }
        // Right side
        c = coordinates.get(figure.getLocation());
        if (!(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
            // Up-right
            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 15);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            // Down-right
            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 17);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }

            c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 1);
            if (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
                // Right-up
                c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 6);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                // Right-down
                c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 10);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
        }

        return moves;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();
        Coordinate c;

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

        set.remove(null);
        return set;
    }
}
