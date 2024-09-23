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


        // Up
        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 8);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
            c = Coordinate.getCoordinate(c.ordinal() - 8);
        }
        // Down
        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 8);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
            moves.add(c);
            c = Coordinate.getCoordinate(c.ordinal() + 8);
        }
        // Left
        c = Coordinate.getCoordinate(position.ordinal() - 1);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() - 1);
        }
        // Right
        c = Coordinate.getCoordinate(position.ordinal() + 1);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) {
                break;
            }
            c = Coordinate.getCoordinate(c.ordinal() + 1);
        }

        return moves;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();
        Coordinate c;

        // Up
        c = Coordinate.getCoordinate(
                coordinates.get(figure.getLocation()).ordinal() - 8);
        while (c != null) {
            set.add(c);
            c = Coordinate.getCoordinate(c.ordinal() - 8);
        }
        // Down
        c = Coordinate.getCoordinate(
                coordinates.get(figure.getLocation()).ordinal() + 8);
        while (c != null) {
            set.add(c);
            c = Coordinate.getCoordinate(c.ordinal() + 8);
        }
        // Left
        c = coordinates.get(figure.getLocation());
        while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 == 0)) {
            c = Coordinate.getCoordinate(c.ordinal() - 1);
            set.add(c);
        }
        // Right
        c = coordinates.get(figure.getLocation());
        while (c != null && !(Coordinate.isBoundary(c) && c.ordinal() % 2 != 0)) {
            c = Coordinate.getCoordinate(c.ordinal() + 1);
            set.add(c);
        }

        set.remove(null);
        return set;
    }
}