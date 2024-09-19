package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Game {
    private final Board board;
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Rank, Movable>      movables;

    public Game(int resolution) {
        this.board = new Board(resolution);
        this.figures = board.getFiguresMap();
        this.movables = setMovables(board.getCoordinates());
    }

    public Board getBoard() {
        return this.board;
    }

    /**
     * <p>Instantiates abstract representations of figures that will process
     * validating moves over the board.</p>
     * @return A map of Movable figure representations
     */
    private HashMap<Rank, Movable> setMovables(HashMap<Point, Coordinate> coordinates) {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN, new Pawn(figures, coordinates));
        map.put(Rank.KNIGHT, new Knight(figures, coordinates));
        map.put(Rank.ROOK, new Rook(figures, coordinates));
        map.put(Rank.BISHOP, new Bishop(figures, coordinates));
        map.put(Rank.QUEEN, new Queen(figures, coordinates));
        map.put(Rank.KING, new King(figures, coordinates));

        return map;
    }

    public HashMap<Coordinate, Figure> getFigures() {
        return this.figures;
    }

    public Figure getFigureAt(Coordinate c) {
        return figures.get(c);
    }

    /**
     * <p>Calculates available moves for a given figure.</p>
     * @param figure selected figure on board
     * @return a set of available moves
     */
    public HashSet<Coordinate> getMoves(Figure figure) {
        return movables.get(figure.getRank()).moves(figure);
    }
}
