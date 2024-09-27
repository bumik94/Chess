package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains information about figures and their methods
 * for moving over the board
 */
public class Figures {
    private final HashMap<Point, Coordinate>  coordinates;
    private final HashMap<Coordinate, Figure> figuresMap;
    private final HashMap<Rank, Movable>      movables;
    private Figure whiteKing;
    private Figure blackKing;

    public Figures(HashMap<Coordinate, Figure> figuresMap,
                   HashMap<Point, Coordinate> coordinates) {
        this.figuresMap = figuresMap;
        this.coordinates = coordinates;
        this.movables = setMovables();
        figuresMap.forEach((coordinate, figure) -> {
            if (figure.getRank().equals(Rank.KING)) {
                switch (figure.getSide()) {
                    case WHITE -> whiteKing = figure;
                    case BLACK -> blackKing = figure;
                }
            }
        });
    }

    /**
     * <p>Instantiates abstract representations of figures that will process
     * validating moves over the board.</p>
     * @return A map of Movable figure representations
     */
    private HashMap<Rank, Movable> setMovables() {
        HashMap<Rank, Movable> map = new HashMap<>();

        map.put(Rank.PAWN,
                new Pawn(
                        figuresMap,
                        coordinates));
        map.put(Rank.KNIGHT,
                new Knight(
                        figuresMap,
                        coordinates));
        map.put(Rank.ROOK,
                new Rook(
                        figuresMap,
                        coordinates));
        map.put(Rank.BISHOP,
                new Bishop(
                        figuresMap,
                        coordinates));
        map.put(Rank.QUEEN,
                new Queen(
                        map.get(Rank.BISHOP),
                        map.get(Rank.ROOK)));
        map.put(Rank.KING,
                new King(
                        figuresMap,
                        coordinates,
                        map.get(Rank.PAWN),
                        map.get(Rank.KNIGHT),
                        map.get(Rank.QUEEN)));

        return map;
    }

    public HashMap<Coordinate, Figure> getFiguresMap() {
        return this.figuresMap;
    }

    public Figure getFigureAt(Coordinate c) {
        return figuresMap.get(c);
    }

    /**
     * <p>Calculates available moves for a given figure.</p>
     * @param figure selected figure on board
     * @return a set of available moves
     */
    public HashSet<Coordinate> getMoves(Figure figure) {
        HashSet<Coordinate> movesSet = movables.get(figure.getRank()).getMoves(figure);
        HashSet<Coordinate> checkMoves = getCheckMoves(figure);

        if (figure.getRank().equals(Rank.KING)) {
            HashSet<Coordinate> controlledMovesSet = getControlledMoves(figure);
            movesSet.removeAll(controlledMovesSet);
        }
        else if (! checkMoves.isEmpty()) {
            int checks = 0;

            for (Coordinate c : checkMoves) {
                Figure f = getFigureAt(c);
                if (f != null && !(f.getSide().equals(figure.getSide()))) {
                    checks++;
                }
            }

            if (checks == 1) {
                movesSet.retainAll(checkMoves);
            } else {
                movesSet.clear();
            }
        }

        return movesSet;
    }

    /**
     * <p>Calculates available moves for all figures of the opposite side.</p>
     * @param figure a selected figure of the current turn
     * @return a set of all opponent's controlled moves
     */
    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        HashSet<Coordinate> set = new HashSet<>();

        for (Figure opponent : figuresMap.values()) {
            if (! opponent.getSide().equals(figure.getSide())) {
                set.addAll(movables.get(opponent.getRank()).getControlledMoves(opponent));
            }
        }

        return set;
    }

    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        for (Figure opponent : figuresMap.values()) {
            if (! opponent.getSide().equals(figure.getSide())) {

                moves.addAll(movables.get(opponent.getRank()).getCheckMoves(opponent));
            }
        }

        return moves;
    }

}
