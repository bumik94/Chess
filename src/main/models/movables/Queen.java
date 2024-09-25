package main.models.movables;

import main.models.Coordinate;
import main.models.Figure;
import main.models.Movable;
import main.models.Rank;

import java.awt.*;
import java.util.HashMap;
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


    public HashSet<Coordinate> check(Figure figure) {
        return null;
    }

}