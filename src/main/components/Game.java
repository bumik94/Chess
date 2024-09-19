package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Game {
    private final Board board;
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Rank, Movable>      movables;

    public Game(int resolution) {
        this.board = new Board(resolution);
        this.figures = setFigures(board.getFiguresList(resolution));
        this.movables = setMovables();
    }

    /**
     * <p>Instantiates abstract representations of figures that will process
     * validating moves over the board.</p>
     * @return A map of Movable figure representations
     */
    private HashMap<Rank, Movable> setMovables() {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN, new Pawn(figures, board.getCoordinates()));
        map.put(Rank.KNIGHT, new Knight(figures, board.getCoordinates()));
        map.put(Rank.ROOK, new Rook(figures, board.getCoordinates()));
        map.put(Rank.BISHOP, new Bishop(figures, board.getCoordinates()));
        map.put(Rank.QUEEN, new Queen(figures, board.getCoordinates()));
        map.put(Rank.KING, new King(figures, board.getCoordinates()));

        return map;
    }

    /**
     * <p>Maps instantiated figures to their location's coordinate for direct access.</p>
     * @return Coordinates mapped to figures
     */
    public HashMap<Coordinate, Figure> setFigures(ArrayList<Figure> figureList) {
        HashMap<Coordinate, Figure> figureMap = new HashMap<>();

        figureList.forEach(figure -> figureMap.put(
                board.getCoordinate(figure.getLocation()), figure));

        return figureMap;
    }

    public HashMap<Coordinate, Figure> getFigures() {
        return this.figures;
    }

    public Figure getFigureAt(Point p) {
        Figure figure = figures.get(board.getCoordinate(p));
//        System.out.println(figure);

        return figure;
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
