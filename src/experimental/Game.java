package experimental;

import experimental.figures.Pawn;
import experimental.models.Coordinate;
import experimental.models.Figure;
import experimental.models.Rank;
import experimental.models.Side;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

// TODO instantiate figure classes, pass them fields of this class and develop move methods
/**
 * This class should construct all objects
 * and handle game logic
 */
public class Game {
    private final Board board;
    private final HashMap<Coordinate, Figure> figures;

    private final Pawn pawn;
    private Side turn;  // will be used in game loop

    //
    // Constructor
    //
    public Game(int resolution) {
        this.board = new Board(resolution);
        this.figures = setFigures(initializeFigures(resolution));
        this.pawn = new Pawn(getFigures(), getCoordinates());
    }

    //
    // Methods
    //
    private HashMap<Coordinate, Figure> setFigures(ArrayList<Figure> list) {
        HashMap<Coordinate, Figure> map = new HashMap<>();

        list.forEach(figure -> map.put(
                board.getCoordinate(figure.getPosition()), figure));

        return map;
    }

    private HashMap<Coordinate, Figure> getFigures() {
        return this.figures;
    }

    private ArrayList<Figure> initializeFigures(int resolution) {
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

    public Figure getFigureAt(Point p) {
        Figure figure = figures.get(board.getCoordinate(p));
//        System.out.println(figure);

        return figure;
    }

    public Board getBoard() {
        return this.board;
    }

    public void drawFigures(Graphics g) {
        figures.values()
                .forEach(figure -> figure.paintFigure(g));
    }

}
