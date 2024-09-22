package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Knight implements Movable {
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
        HashSet<Integer> leftIntervals = new HashSet<>(List.of(-17, -10, 6, 15));
        HashSet<Integer> rightIntervals = new HashSet<>(List.of(-15, -6, 10, 17));
        Coordinate c;

        if (Coordinate.isBoundary(position)) {
            // Boundary intervals
            if (position.ordinal() % 2 == 0) { // left edge
                for (int interval : rightIntervals) {
                    c = Coordinate.getCoordinate(position.ordinal() + (interval));
                    if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                        moves.add(c);
                    }
                }
            } else { // right edge
                for (int interval : leftIntervals) {
                    c = Coordinate.getCoordinate(position.ordinal() + (interval));
                    if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                        moves.add(c);
                    }
                }
            }
        } else {
            // Right intervals
            c = Coordinate.getCoordinate(position.ordinal() - 17);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            if (c != null && (! Coordinate.isBoundary(c))) {
                // Next to boundary
                c = Coordinate.getCoordinate(position.ordinal() - 10);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                c = Coordinate.getCoordinate(position.ordinal() + 6);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
            c = Coordinate.getCoordinate(position.ordinal() + 15);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            if (c != null && (! Coordinate.isBoundary(c))) {
                // Next to boundary
                c = Coordinate.getCoordinate(position.ordinal() - 10);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                c = Coordinate.getCoordinate(position.ordinal() + 6);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
            // Left intervals
            c = Coordinate.getCoordinate(position.ordinal() - 15);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            if (c != null && (! Coordinate.isBoundary(c))) {
                // Next to boundary
                c = Coordinate.getCoordinate(position.ordinal() - 6);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                c = Coordinate.getCoordinate(position.ordinal() + 10);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
            c = Coordinate.getCoordinate(position.ordinal() + 17);
            if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                moves.add(c);
            }
            if (c != null && (! Coordinate.isBoundary(c))) {
                // Next to boundary
                c = Coordinate.getCoordinate(position.ordinal() - 6);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
                c = Coordinate.getCoordinate(position.ordinal() + 10);
                if (c != null && (isRemovable(figure, c) || isEmpty(c))) {
                    moves.add(c);
                }
            }
        }

        return moves;
    }
}
