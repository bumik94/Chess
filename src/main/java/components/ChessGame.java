package main.java.components;

import main.java.components.figures.*;
import main.java.components.models.*;
import main.java.components.utility.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class should construct all objects
 * and handle game logic
 */
public class ChessGame {
    static final int MAX_PAIRS = 4;
    static final int MAX_PAWNS = 8;
    static final int WHITE_PAWN_ROW = 48;
    static final int WHITE_ROYAL_ROW = 56;
    static final int BLACK_PAWN_ROW = 8;
    static final int BLACK_ROYAL_ROW = 0;

    private final Board board;
    private final ArrayList<Figure> white;
    private final ArrayList<Figure> black;
//    private final ArrayList<Figure> figures;


    //
    // Constructor
    //
    public ChessGame(int resolution) {
        board = new Board(resolution);
        black = initializeFigures(resolution, Side.BLACK, BLACK_PAWN_ROW, BLACK_ROYAL_ROW);
        white = initializeFigures(resolution, Side.WHITE, WHITE_PAWN_ROW, WHITE_ROYAL_ROW);
    }

    //
    // Methods
    //
    private ArrayList<Figure> initializeFigures(
            int resolution, Side side,
            int PAWN_ROW, int ROYAL_ROW) {

        ArrayList<Figure> list = new ArrayList<>();
        Coordinate[] coordinates = Coordinate.values();

        for (int i = PAWN_ROW; i < MAX_PAWNS + PAWN_ROW; i++) {
            Pawn pawn = new Pawn(
                    side,
                    Rank.PAWN,
                    board.getCoordinate(coordinates[i]),
                    resolution);
            list.add(pawn);
        }
        int pair = 1;
        for (int i = ROYAL_ROW; i < MAX_PAIRS + ROYAL_ROW; i++) {
            switch (pair++) {
                case 1 -> {
                    Rook rook1 = new Rook(
                            side,
                            Rank.ROOK,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(rook1);

                    Rook rook2 = new Rook(
                            side,
                            Rank.ROOK,
                            board.getCoordinate(coordinates[i + 7]),
                            resolution);
                    list.add(rook2);
                }
                case 2 -> {
                    Knight knight1 = new Knight(
                            side,
                            Rank.KNIGHT,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(knight1);

                    Knight knight2 = new Knight(
                            side,
                            Rank.KNIGHT,
                            board.getCoordinate(coordinates[i + 5]),
                            resolution);
                    list.add(knight2);
                }
                case 3 -> {
                    Bishop bishop1 = new Bishop(
                            side,
                            Rank.BISHOP,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(bishop1);

                    Bishop bishop2 = new Bishop(
                            side,
                            Rank.BISHOP,
                            board.getCoordinate(coordinates[i + 3]),
                            resolution);
                    list.add(bishop2);
                }
                case 4 -> {
                    Queen queen = new Queen(
                            side,
                            Rank.QUEEN,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(queen);

                    King king = new King(
                            side,
                            Rank.KING,
                            board.getCoordinate(coordinates[i + 1]),
                            resolution);
                    list.add(king);
                }
            }
        }

        return list;
    }

    public Board getBoard() {
        return this.board;
    }

    public void drawFigures(Graphics g) {
        white.forEach(figure -> figure.paintFigure(g));
        black.forEach(figure -> figure.paintFigure(g));
    }

    public void moveFigure(Coordinate coordinate) {

    }
}
