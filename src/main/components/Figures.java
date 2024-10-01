package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

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
                        coordinates));

        return map;
    }

    public HashMap<Coordinate, Figure> getFiguresMap() {
        return this.figuresMap;
    }

    public Figure getFigureAt(Coordinate selectedCoordinate) {
        return figuresMap.get(selectedCoordinate);
    }

    /**
     * <p>Evaluates how many checks are blocked by friendly figures.</p>
     * @param figure currently selected figure
     * @param checkMoves set of all check moves
     * @return number of blocked checks
     */
    private int getControlledChecks(Figure figure, HashSet<Coordinate> checkMoves) {
        int controlledChecks = 0;

        for (Coordinate c : checkMoves) {
            Figure f = getFigureAt(c);
            if (f != null && f.getSide().equals(figure.getSide())) {
                controlledChecks++;
            }
        }

        return controlledChecks;
    }

    /**
     * <p>Calculates available moves for a given figure.</p>
     * @param figure selected figure on board
     * @return a set of available moves
     */
    public HashSet<Coordinate> getMoves(Figure figure) {
        Coordinate position = coordinates.get(figure.getLocation());
        HashSet<Coordinate> moves = movables.get(figure.getRank()).getMoves(figure);
        HashSet<Coordinate> checkMoves = getCheckMoves(figure);

        if (figure.getRank().equals(Rank.KING)) {
            HashSet<Coordinate> controlledMoves = getControlledMoves(figure);
            moves.removeAll(controlledMoves);
        }
        /*
        Evaluates if any opposite figure checks the king.
        When only one figure checks, either king can move
        or the opposite figure can be blocked or removed,
        otherwise the king must move.

        TODO when no moves are left for checked king, the game's over.
         */
        else if (! checkMoves.isEmpty()) {
            int checks = 0;
            int controlledChecks = getControlledChecks(figure, checkMoves);

            for (Coordinate c : checkMoves) {
                Figure f = getFigureAt(c);
                if (f != null && !(f.getSide().equals(figure.getSide()) || f.getRank().equals(Rank.KING))) {
                    checks++;
                }
            }
            if (checks == 1) {
                // Restrict moves to check trajectory when one opponent figure checks
                // and either the selected figure or none controls the check
                if (checkMoves.contains(position) || controlledChecks == 0) {
                    moves.retainAll(checkMoves);
                }
            }
            else if ((checks > 1) && (checks == controlledChecks)) {
                /*
                Process every single figure that checks and determine if it's blocked.
                If it is and the blocking figure is the selected, restrict its moves
                to the check trajectory.
                 */

                // Get all checking figure's positions
                HashSet<Figure> checkingFigures = new HashSet<>();
                HashSet<Coordinate> tempMoves = new HashSet<>();

                for (Coordinate c : checkMoves) {
                    Figure f = getFigureAt(c);

                    if (f != null && !(f.getSide().equals(figure.getSide()))) {
                        checkingFigures.add(f);
                    }
                }

                // Assess if a friendly figure controls the check move of the checking figure
//                HashSet<Coordinate> tempMoves = new HashSet<>();

                for (Figure f : checkingFigures) {
                    tempMoves.addAll(movables.get(f.getRank()).getCheckMoves(f));
                    if (tempMoves.contains(position)) {
                        break;
                    }
                    tempMoves.clear();
                }

                if (! tempMoves.isEmpty()) {
                    moves.retainAll(tempMoves);
                }
            }
            else {
                moves.clear();
            }
        }

        return moves;
    }

    /**
     * <p>Calculates available moves for all figures of the opposite side.</p>
     * @param figure a selected figure of the current turn
     * @return a set of all opponent's controlled moves
     */
    public HashSet<Coordinate> getControlledMoves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        for (Figure opponent : figuresMap.values()) {
            if (! opponent.getSide().equals(figure.getSide())) {
                moves.addAll(movables.get(opponent.getRank()).getControlledMoves(opponent));
            }
        }

        return moves;
    }

    /**
     * <p>Evaluates moves that lead to check from opposite figure. When one friendly figure controls
     * the path to check, it still returns the path and is considered a check.</p>
     * @param figure currently selected <code>Figure</code>
     * @return a set of check moves from the opposite side
     */
    public HashSet<Coordinate> getCheckMoves(Figure figure) {
        HashSet<Coordinate> moves = new HashSet<>();

        /*
        This method might require more granular control on returning check moves as it allows
        figures to move to illegal squares exposing king to actual check.
        Perhaps separating the check moves for singular figure?
         */
        for (Figure opponent : figuresMap.values()) {
            if (! opponent.getSide().equals(figure.getSide())) {
                moves.addAll(movables.get(opponent.getRank()).getCheckMoves(opponent));
            }
        }

        return moves;
    }

}
