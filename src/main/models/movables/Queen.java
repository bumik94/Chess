package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;

import java.util.HashSet;

public class Queen implements Movable {
    private final Movable bishop;
    private final Movable rook;

    public Queen(Movable bishop, Movable rook) {
        this.bishop = bishop;
        this.rook = rook;
    }


    /**
     * <p>Checks for valid moves for a given figure.</p>
     *
     * @param figure to be moved
     */
    public HashSet<Coordinate> getMoves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.getMoves(figure));
        moves.addAll(rook.getMoves(figure));

        return moves;
    }

    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.getControlledMoves(figure));
        moves.addAll(rook.getControlledMoves(figure));

        return moves;
    }


    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        return null;
    }

}