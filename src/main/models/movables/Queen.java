package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Queen implements Movable {
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Queen(HashMap<Coordinate, Figure> figures,
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
    public ArrayList<Coordinate> moves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        ArrayList<Coordinate> moves = new ArrayList<>();
        Coordinate c;

        // Rook model
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

        // Bishop model
        // Diagonal left-down
        c = Coordinate.getCoordinate(position.ordinal() + 7);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + 7);
        }

        // Diagonal left-up
        c = Coordinate.getCoordinate(position.ordinal() - 9);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() - 9);
        }

        // Diagonal right-down
        c = Coordinate.getCoordinate(position.ordinal() + 9);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() + 9);
        }

        // Diagonal right-up
        c = Coordinate.getCoordinate(position.ordinal() - 7);
        while (c != null && (isEmpty(c) || isRemovable(figure, c))
                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
            moves.add(c);
            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
            c = Coordinate.getCoordinate(c.ordinal() - 7);
        }


        return moves;
    }
}