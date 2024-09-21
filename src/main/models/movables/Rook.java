package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Rook implements Movable {
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

        // Right
        c = Coordinate.getCoordinate(position.ordinal() + 1);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + 1);
        }

        // Left
        c = Coordinate.getCoordinate(position.ordinal() - 1);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() - 1);
        }

        // Down
        c = Coordinate.getCoordinate(position.ordinal() + 8);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
            if (isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + 8);
        }

        // Up
        c = Coordinate.getCoordinate(position.ordinal() - 8);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
            if (isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() - 8);
        }

        return moves;
    }
}
