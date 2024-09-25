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
    public HashSet<Coordinate> moves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.moves(figure));
        moves.addAll(rook.moves(figure));

        return moves;
    }

    public HashSet<Coordinate> controlledMoves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.controlledMoves(figure));
        moves.addAll(rook.controlledMoves(figure));

        return moves;
    }


    public HashSet<Coordinate> check(Figure figure) {
        return null;
    }

}