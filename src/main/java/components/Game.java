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
public class Game {
    private final Board board;
    private final ArrayList<Figure> figures;


    //
    // Constructor
    //
    public Game(int resolution) {
        this.board = new Board(resolution);
        figures = new ArrayList<>();
        figures.addAll(initFigures(resolution));
    }

    //
    // Methods
    //
    private  ArrayList<Figure> initFigures(int resolution) {
        ArrayList<Figure> list = new ArrayList<>();

        // White
        // Pawns
        for (int i = Coordinate.B1.ordinal(); i < Coordinate.A1.ordinal(); i++) {
            list.add(new Pawn(Side.WHITE, Rank.PAWN, board.getPointAt(i), resolution, board.getCoordinates(), getFigures()));
        }
        // Rooks
        list.add(new Rook(Side.WHITE, Rank.ROOK,     board.getPointAt(Coordinate.A1), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.ROOK,     board.getPointAt(Coordinate.A8), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.KNIGHT,   board.getPointAt(Coordinate.A2), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.KNIGHT,   board.getPointAt(Coordinate.A7), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.BISHOP,   board.getPointAt(Coordinate.A3), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.BISHOP,   board.getPointAt(Coordinate.A6), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.QUEEN,    board.getPointAt(Coordinate.A4), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.WHITE, Rank.KING,     board.getPointAt(Coordinate.A5), resolution, board.getCoordinates(), getFigures()));

        // Black
        for (int i = Coordinate.G1.ordinal(); i < Coordinate.F1.ordinal(); i++) {
            list.add(new Pawn(Side.BLACK, Rank.PAWN, board.getPointAt(i), resolution, board.getCoordinates(), getFigures()));
        }
        list.add(new Rook(Side.BLACK, Rank.ROOK,     board.getPointAt(Coordinate.H1), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.ROOK,     board.getPointAt(Coordinate.H8), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.KNIGHT,   board.getPointAt(Coordinate.H2), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.KNIGHT,   board.getPointAt(Coordinate.H7), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.BISHOP,   board.getPointAt(Coordinate.H3), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.BISHOP,   board.getPointAt(Coordinate.H6), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.QUEEN,    board.getPointAt(Coordinate.H4), resolution, board.getCoordinates(), getFigures()));
        list.add(new Rook(Side.BLACK, Rank.KING,     board.getPointAt(Coordinate.H5), resolution, board.getCoordinates(), getFigures()));

        return list;
    }

    public Figure getFigure(Point p) {
        for (Figure figure : figures) {
            if (figure.getPosition().equals(p)) {
//                System.out.println(figure.getPosition());
                return figure;
            }
        }
        return null;
    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void drawFigures(Graphics g) {
        figures.forEach(figure -> figure.paintFigure(g));
    }


		/*
		import java.util.*;

class Main {
  
  public static void main(String[] args){
    char a1C = Coordinate.getCol(Coordinate.A1);
     int a1R = Coordinate.getRow(Coordinate.A1);
    System.out.printf("%c %d", a1C, a1R);
  }
  
  static void move(Coordinate orig, Coordinate dest) {
     int origRow = Coordinate.getRow(orig);
    char origCol = Coordinate.getCol(orig);
     int destRow = Coordinate.getRow(dest);
    char destCol = Coordinate.getCol(dest);
    // pawn white condition to kick opponent or one forward and right of itself
    if    (origRow + 1 == destRow && origCol + 1 == destCol && Board.getFigures.contains(Figure)
        || origRow + 1 == destRow && origCol - 1 == destCol && Board.getFigures.contains(Figure)) {
      if Figure.isRank(Rank.KING) {
        // Check = true
      }
      else {
        Figure.move(dest)
      }
    }
  }
}
		*/
}
