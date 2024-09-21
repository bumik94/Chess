package main.models.movables;

import main.components.Figures;
import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class King implements Movable {
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

    private boolean isCheck() {
        return false;
    }

    /**
     * <p>Checks for valid moves for a given figure.</p>
     *
     * @param figure to be moved
     */
    public HashSet<Coordinate> moves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
//        HashMap<Rank, Movable> movables = Figures.setMovables(figures, coordinates);
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        // Move up
        c = Coordinate.getCoordinate(position.ordinal() + 8);
        if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
        }

        // Move down
        c = Coordinate.getCoordinate(position.ordinal() - 8);
        if (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
        }

        // Move left, right, diagonally
        if (Coordinate.isBoundary(position)) {
            // left edge
            if (position.ordinal() % 2 == 0) {
                // Right
                c = Coordinate.getCoordinate(position.ordinal() + 1);
                if (isEmpty(c) || isRemovable(figure, c)) {
                    moves.add(c);
                }
                // Right-up
                c = Coordinate.getCoordinate(position.ordinal() - 7);
                if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                    moves.add(c);
                }
                // Right-down
                c = Coordinate.getCoordinate(position.ordinal() + 9);
                if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                    moves.add(c);
                }
            }
            // Right edge
            else {
                // Left
                c = Coordinate.getCoordinate(position.ordinal() - 1);
                if (isEmpty(c) || isRemovable(figure, c)) {
                    moves.add(c);
                }
                // Left-up
                c = Coordinate.getCoordinate(position.ordinal() - 9);
                if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                    moves.add(c);
                }
                // Left-down
                c = Coordinate.getCoordinate(position.ordinal() + 7);
                if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                    moves.add(c);
                }
            }
        }
        else {
            // Right
            c = Coordinate.getCoordinate(position.ordinal() + 1);
            if (isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Right-up
            c = Coordinate.getCoordinate(position.ordinal() - 7);
            if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Right-down
            c = Coordinate.getCoordinate(position.ordinal() + 9);
            if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Left
            c = Coordinate.getCoordinate(position.ordinal() - 1);
            if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Left-up
            c = Coordinate.getCoordinate(position.ordinal() - 9);
            if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
            // Left-down
            c = Coordinate.getCoordinate(position.ordinal() + 7);
            if (c != null && isEmpty(c) || isRemovable(figure, c)) {
                moves.add(c);
            }
        }

        return moves;
    }

    /*
    King check

    Checks for moves of opposing figures and removes matching
    positions by set difference on King's moves.
     */
}
