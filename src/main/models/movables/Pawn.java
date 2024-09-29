package main.models.movables;


import main.models.*;

import java.awt.*;
import java.util.Collections;
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
    @Override
    public HashSet<Coordinate> getMoves(Figure figure) {
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
                if (! Coordinate.isLeftBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
                // Remove right-up
                if (! Coordinate.isRightBoundary(position)) { // left edge
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
                if (! Coordinate.isLeftBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
                // Remove right-down
                if (! Coordinate.isRightBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
                    if (isRemovable(figure, c)) {
                        moves.add(c);
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        switch (figure.getSide()) {
            case WHITE -> {
                // Control left-up
                if (! Coordinate.isLeftBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
                    if (c != null) {
                        moves.add(c);
                    }
                }
                // Control right-up
                if (! Coordinate.isRightBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
                    if (c != null) {
                        moves.add(c);
                    }
                }
            }
            case BLACK -> {
                // Control left-down
                if (! Coordinate.isLeftBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
                    if (c != null) {
                        moves.add(c);
                    }
                }
                // Control right-down
                if (! Coordinate.isRightBoundary(position)) { // left edge
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
    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = new HashSet<>();
        Coordinate c;

        switch (figure.getSide()) {
            case WHITE -> {
                // Check left-up
                if (! Coordinate.isLeftBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_UP);
                    if (c != null && isOppositeKing(figure, c)) {
                        moves.add(position);
                    }
                }
                // Check right-up
                if (! Coordinate.isRightBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_UP);
                    if (c != null && isOppositeKing(figure, c)) {
                        moves.add(position);
                    }
                }
            }
            case BLACK -> {
                // Check left-down
                if (! Coordinate.isLeftBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + LEFT_DOWN);
                    if (c != null && isOppositeKing(figure, c)) {
                        moves.add(position);
                    }
                }
                // Check right-down
                if (! Coordinate.isRightBoundary(position)) { // left edge
                    c = Coordinate.getCoordinate(position.ordinal() + RIGHT_DOWN);
                    if (c != null && isOppositeKing(figure, c)) {
                        moves.add(position);
                    }
                }
            }
        }

        return moves;
    }
}
