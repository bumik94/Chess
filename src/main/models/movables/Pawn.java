package main.models.movables;


import main.models.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Pawn implements Movable {
    private static final int UP = -8;
    private static final int DOWN = 8;
    private static final int LEFT_UP = -9;
    private static final int RIGHT_UP = -7;
    private static final int LEFT_DOWN = 7;
    private static final int RIGHT_DOWN = 9;

    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Pawn(HashMap<Coordinate, Figure> figures,
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
     * @param c <code>Coordinate</code> to move to
     * @return false when a <code>Figure</code> occupies <code>Coordinate</code>
     */
    private boolean isEmpty(Coordinate c) {
        return figures.get(c) == null;
    }

    /**
     * <p>Checks for valid moves for a given figure.</p>
     * @param figure to be moved
     */
    public HashSet<Coordinate> moves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        switch (figure.getSide()) {
            case WHITE -> {
                // Move up
                c = Coordinate.getCoordinate(position.ordinal() + UP);
                if (c != null && isEmpty(c)) {
                    moves.add(c);
                }
                // Remove left-up
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
                // Remove right-up
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
            }
            case BLACK -> {
                // Move down
                c = Coordinate.getCoordinate(position.ordinal() + DOWN);
                if (c != null && isEmpty(c)) {
                    moves.add(c);
                }
                // Remove left-down
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
                // Remove right-down
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
            }
        }

        return moves;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        switch (figure.getSide()) {
            case WHITE -> {
                // Remove left-up
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
                    if (c != null) {
                        moves.add(c);
                    }
                }
                // Remove right-up
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
                    if (c != null) {
                        moves.add(c);
                    }
                }
            }
            case BLACK -> {
                // Remove left-down
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
                    if (c != null) {
                        moves.add(c);
                    }
                }
                // Remove right-down
                if (! (Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
                    if (c != null) {
                        moves.add(c);
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public HashSet<Coordinate> check(Figure figure) {
        return null;
    }
}
