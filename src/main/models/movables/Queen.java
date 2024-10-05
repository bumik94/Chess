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
     * @param selectedFigure to be moved
     */
    @Override
    public HashSet<Coordinate> getMoves(Figure selectedFigure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.getMoves(selectedFigure));
        moves.addAll(rook.getMoves(selectedFigure));

        return moves;
    }

    @Override
    public HashSet<Coordinate> getControlledMoves(Figure selectedFigure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.getControlledMoves(selectedFigure));
        moves.addAll(rook.getControlledMoves(selectedFigure));

        return moves;
    }


    @Override
    public HashSet<Coordinate> getCheckMoves(Figure selectedFigure) {
        HashSet<Coordinate> moves = new HashSet<>();

        moves.addAll(bishop.getCheckMoves(selectedFigure));
        moves.addAll(rook.getCheckMoves(selectedFigure));

        return moves;
    }

}