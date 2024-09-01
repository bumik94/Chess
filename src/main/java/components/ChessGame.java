package main.java.components;

import main.java.components.figures.*;
import main.java.components.models.Coordinate;
import main.java.components.models.Figure;
import main.java.components.models.Rank;
import main.java.components.models.Side;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class should construct all objects
 * and handle game logic
 */
public class ChessGame {
    private static final int MAX_PAWNS = 8;
    private static final int WHITE_PAWN_ROW = 48;
    private static final int WHITE_ROYAL_ROW = 56;
    private static final int MAX_PAIRS = 4;

    private final ChessBoard board;
    private final ArrayList<Figure> white;
//    private final ArrayList<Figure> black;
//    private final ArrayList<Figure> figures;


    //
    // Constructor
    //
    public ChessGame(int resolution) {
        board = new ChessBoard(resolution);
        white = initWhite(resolution);
        /*
        Construct pieces for each side and put them inside a container
        Create a method to loop trough this container and draw each figure
        determined by its position field
         */


    }

    //
    // Methods
    //

    private ArrayList<Figure> initWhite(int resolution) {
        ArrayList<Figure> list = new ArrayList<>();
        Coordinate[] coordinates = Coordinate.values();

        for (int i = WHITE_PAWN_ROW; i < MAX_PAWNS + WHITE_PAWN_ROW; i++) {
            Pawn pawn = new Pawn(
                    Side.WHITE,
                    Rank.PAWN,
                    board.getCoordinate(coordinates[i]),
                    resolution);
            list.add(pawn);
        }
        int pair = 1;
        for (int i = WHITE_ROYAL_ROW; i < MAX_PAIRS + WHITE_ROYAL_ROW; i++) {

            switch (pair++) {
                case 1 -> {
                    Rook rook1 = new Rook(
                            Side.WHITE,
                            Rank.ROOK,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(rook1);

                    Rook rook2 = new Rook(
                            Side.WHITE,
                            Rank.ROOK,
                            board.getCoordinate(coordinates[i + 7]),
                            resolution);
                    list.add(rook2);
                }
                case 2 -> {
                    Knight knight1 = new Knight(
                            Side.WHITE,
                            Rank.KNIGHT,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(knight1);

                    Knight knight2 = new Knight(
                            Side.WHITE,
                            Rank.KNIGHT,
                            board.getCoordinate(coordinates[i + 5]),
                            resolution);
                    list.add(knight2);
                }
                case 3 -> {
                    Bishop bishop1 = new Bishop(
                            Side.WHITE,
                            Rank.BISHOP,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(bishop1);

                    Bishop bishop2 = new Bishop(
                            Side.WHITE,
                            Rank.BISHOP,
                            board.getCoordinate(coordinates[i + 3]),
                            resolution);
                    list.add(bishop2);
                }
                case 4 -> {
                    Queen queen = new Queen(
                            Side.WHITE,
                            Rank.QUEEN,
                            board.getCoordinate(coordinates[i]),
                            resolution);
                    list.add(queen);

                    King king = new King(
                            Side.WHITE,
                            Rank.KING,
                            board.getCoordinate(coordinates[i + 1]),
                            resolution);
                    list.add(king);
                }
            }
        }

        return list;
    }

    public ChessBoard getBoard() {
        return this.board;
    }

    public void drawFigures(Graphics g) {
        white.forEach(figure -> figure.paintFigure(g));
    }
}
