package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Queen implements Movable {
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;
    private final Movable bishop;
    private final Movable rook;

    public Queen(HashMap<Coordinate, Figure> figures,
                HashMap<Point, Coordinate> coordinates,
                 Movable bishop, Movable rook) {
        this.figures = figures;
        this.coordinates = coordinates;
        this.bishop = bishop;
        this.rook = rook;
    }

//
//    /**
//     * <p>Evaluates if a figure occupies a coordinate
//     * and is opposite side to the selected figure.
//     * King cannot be removed.</p>
//     *
//     * @param selected <code>Figure</code> to move
//     * @param c        <code>Coordinate</code> to move to
//     * @return true if opposing figure occupies position
//     */
//    private boolean isRemovable(Figure selected, Coordinate c) {
//        Figure contested = figures.get(c);
//
//        return contested != null
//                && !(contested.getSide().equals(selected.getSide()))
//                && !(contested.getRank().equals(Rank.KING));
//    }
//
//    /**
//     * Checks if any figure occupies a given position.
//     *
//     * @param c <code>Coordinate</code> to move to
//     * @return false when a <code>Figure</code> occupies <code>Coordinate</code>
//     */
//    private boolean isEmpty(Coordinate c) {
//        return figures.get(c) == null;
//    }
//

    /**
     * <p>Checks for valid moves for a given figure.</p>
     *
     * @param figure to be moved
     */
    public HashSet<Coordinate> moves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();

        set.addAll(bishop.moves(figure));
        set.addAll(rook.moves(figure));

        return set;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();

        set.addAll(bishop.controlledMoves(figure));
        set.addAll(rook.controlledMoves(figure));

        return set;
    }

//    public HashSet<Coordinate> moves(Figure figure) {
//        Coordinate position = coordinates.get(figure.getLocation());
//        HashSet<Coordinate> moves = new HashSet<>();
//        Coordinate c;
//
//        // Rook model
//        // Up
//        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() - 8);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
//            moves.add(c);
//            c = Coordinate.getCoordinate(c.ordinal() - 8);
//        }
//        // Down
//        c = Coordinate.getCoordinate(coordinates.get(figure.getLocation()).ordinal() + 8);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))) {
//            moves.add(c);
//            c = Coordinate.getCoordinate(c.ordinal() + 8);
//        }
//        // Left
//        c = Coordinate.getCoordinate(position.ordinal() - 1);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))
//                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
//            moves.add(c);
//            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
//            c = Coordinate.getCoordinate(c.ordinal() - 1);
//        }
//        // Right
//        c = Coordinate.getCoordinate(position.ordinal() + 1);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))
//                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
//            moves.add(c);
//            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
//            c = Coordinate.getCoordinate(c.ordinal() + 1);
//        }
//
//        // Bishop model
//        // Left-up
//        c = Coordinate.getCoordinate(position.ordinal() - 9);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))
//                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
//            moves.add(c);
//            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
//            c = Coordinate.getCoordinate(c.ordinal() - 9);
//        }
//        // Left-down
//        c = Coordinate.getCoordinate(position.ordinal() + 7);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))
//                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 == 0)) {
//            moves.add(c);
//            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
//            c = Coordinate.getCoordinate(c.ordinal() + 7);
//        }
//        // Right-up
//        c = Coordinate.getCoordinate(position.ordinal() - 7);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))
//                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
//            moves.add(c);
//            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
//            c = Coordinate.getCoordinate(c.ordinal() - 7);
//        }
//        // Right-down
//        c = Coordinate.getCoordinate(position.ordinal() + 9);
//        while (c != null && (isEmpty(c) || isRemovable(figure, c))
//                && !(Coordinate.isBoundary(position) && position.ordinal() % 2 != 0)) {
//            moves.add(c);
//            if (Coordinate.isBoundary(c) || isRemovable(figure, c)) { break; }
//            c = Coordinate.getCoordinate(c.ordinal() + 9);
//        }
//
//        return moves;
//    }
}