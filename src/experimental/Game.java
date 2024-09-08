package experimental;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class should construct all objects
 * and handle game logic
 */
public class Game {
    private final Board board;
    private final HashMap<Coordinate, Figure> figuresMap;

    //
    // Constructor
    //
    public Game(int resolution) {
        this.board = new Board(resolution);
        this.figuresMap = putFigures(initFigures(resolution));
    }

    //
    // Methods
    //
    private HashMap<Coordinate, Figure> putFigures(ArrayList<Figure> list) {
        HashMap<Coordinate, Figure> map = new HashMap<>();

        list.forEach(figure -> map.put(
                board.getCoordinate(figure.getPosition()), figure));

        return map;
    }

    private ArrayList<Figure> initFigures(int resolution) {
        ArrayList<Figure> list = new ArrayList<>();

        // White
        for (int i = Coordinate.B1.ordinal(); i < Coordinate.A1.ordinal(); i++) {
            list.add(new Figure(Side.WHITE, Rank.PAWN, board.getPointAt(i), resolution));
        }
        list.add(new Figure(Side.WHITE, Rank.ROOK, board.getPointAt(Coordinate.A1), resolution));
        list.add(new Figure(Side.WHITE, Rank.ROOK, board.getPointAt(Coordinate.A8), resolution));
        list.add(new Figure(Side.WHITE, Rank.KNIGHT, board.getPointAt(Coordinate.A2), resolution));
        list.add(new Figure(Side.WHITE, Rank.KNIGHT, board.getPointAt(Coordinate.A7), resolution));
        list.add(new Figure(Side.WHITE, Rank.BISHOP, board.getPointAt(Coordinate.A3), resolution));
        list.add(new Figure(Side.WHITE, Rank.BISHOP, board.getPointAt(Coordinate.A6), resolution));
        list.add(new Figure(Side.WHITE, Rank.QUEEN, board.getPointAt(Coordinate.A4), resolution));
        list.add(new Figure(Side.WHITE, Rank.KING, board.getPointAt(Coordinate.A5), resolution));

        // Black
        for (int i = Coordinate.G1.ordinal(); i < Coordinate.F1.ordinal(); i++) {
            list.add(new Figure(Side.BLACK, Rank.PAWN, board.getPointAt(i), resolution));
        }
        list.add(new Figure(Side.BLACK, Rank.ROOK, board.getPointAt(Coordinate.H1), resolution));
        list.add(new Figure(Side.BLACK, Rank.ROOK, board.getPointAt(Coordinate.H8), resolution));
        list.add(new Figure(Side.BLACK, Rank.KNIGHT, board.getPointAt(Coordinate.H2), resolution));
        list.add(new Figure(Side.BLACK, Rank.KNIGHT, board.getPointAt(Coordinate.H7), resolution));
        list.add(new Figure(Side.BLACK, Rank.BISHOP, board.getPointAt(Coordinate.H3), resolution));
        list.add(new Figure(Side.BLACK, Rank.BISHOP, board.getPointAt(Coordinate.H6), resolution));
        list.add(new Figure(Side.BLACK, Rank.QUEEN, board.getPointAt(Coordinate.H4), resolution));
        list.add(new Figure(Side.BLACK, Rank.KING, board.getPointAt(Coordinate.H5), resolution));

        return list;
    }

    public Figure getFigure(Point p) {
        Figure figure = figuresMap.get(board.getCoordinate(p));

        System.out.println(figure);

        return figure;
    }

    public Board getBoard() {
        return this.board;
    }

    public void drawFigures(Graphics g) {
        figuresMap.values()
                .forEach(figure -> figure.paintFigure(g));
    }

}
