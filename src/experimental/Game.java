package experimental;

import experimental.figures.Pawn;
import experimental.models.*;

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
    private final HashMap<Rank, Movable> moves;

    private Side turn;  // will be used in game loop

    //
    // Constructor
    //
    public Game(int resolution) {
        this.board = new Board(resolution);
        this.figures = setFiguresMap(setFigures(resolution));
        this.moves = setMoves();
        // Temporary - implement loop to change player turns
        this.turn = Side.WHITE;
    }

    //
    // Methods
    //
    public void move(Figure figure, Point destination) {
        Movable movable;
        switch (figure.getRank()) {
            case PAWN -> {
                movable = moves.get(Rank.PAWN);
                movable.move(figure, );
            }
        }

    }

    public Side getTurn() {
        return this.turn;
    }

    HashMap<Rank, Movable> setMoves() {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN, new Pawn(getFiguresMap(), getCoordinatesMap()));

        return map;
    }

    private HashMap<Point, Coordinate> getCoordinatesMap() {
        return this.board.getCoordinates();
    }

    private HashMap<Coordinate, Figure> setFiguresMap(ArrayList<Figure> list) {
        HashMap<Coordinate, Figure> map = new HashMap<>();

        list.forEach(figure -> map.put(
                board.getCoordinate(figure.getPosition()), figure));

        return map;
    }

    private HashMap<Coordinate, Figure> getFiguresMap() {
        return this.figures;
    }

    private ArrayList<Figure> setFigures(int resolution) {
        ArrayList<Figure> list = new ArrayList<>();

        // White
        for (int i = Coordinate.B1.ordinal(); i < Coordinate.A1.ordinal(); i++) {
            list.add(new Figure(Side.WHITE, Rank.PAWN,  board.getPointAt(i), resolution));
        }
        list.add(new Figure(Side.WHITE, Rank.ROOK,      board.getPointAt(Coordinate.A1), resolution));
        list.add(new Figure(Side.WHITE, Rank.ROOK,      board.getPointAt(Coordinate.A8), resolution));
        list.add(new Figure(Side.WHITE, Rank.KNIGHT,    board.getPointAt(Coordinate.A2), resolution));
        list.add(new Figure(Side.WHITE, Rank.KNIGHT,    board.getPointAt(Coordinate.A7), resolution));
        list.add(new Figure(Side.WHITE, Rank.BISHOP,    board.getPointAt(Coordinate.A3), resolution));
        list.add(new Figure(Side.WHITE, Rank.BISHOP,    board.getPointAt(Coordinate.A6), resolution));
        list.add(new Figure(Side.WHITE, Rank.QUEEN,     board.getPointAt(Coordinate.A4), resolution));
        list.add(new Figure(Side.WHITE, Rank.KING,      board.getPointAt(Coordinate.A5), resolution));

        // Black
        for (int i = Coordinate.G1.ordinal(); i < Coordinate.F1.ordinal(); i++) {
            list.add(new Figure(Side.BLACK, Rank.PAWN,  board.getPointAt(i), resolution));
        }
        list.add(new Figure(Side.BLACK, Rank.ROOK,      board.getPointAt(Coordinate.H1), resolution));
        list.add(new Figure(Side.BLACK, Rank.ROOK,      board.getPointAt(Coordinate.H8), resolution));
        list.add(new Figure(Side.BLACK, Rank.KNIGHT,    board.getPointAt(Coordinate.H2), resolution));
        list.add(new Figure(Side.BLACK, Rank.KNIGHT,    board.getPointAt(Coordinate.H7), resolution));
        list.add(new Figure(Side.BLACK, Rank.BISHOP,    board.getPointAt(Coordinate.H3), resolution));
        list.add(new Figure(Side.BLACK, Rank.BISHOP,    board.getPointAt(Coordinate.H6), resolution));
        list.add(new Figure(Side.BLACK, Rank.QUEEN,     board.getPointAt(Coordinate.H4), resolution));
        list.add(new Figure(Side.BLACK, Rank.KING,      board.getPointAt(Coordinate.H5), resolution));

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
