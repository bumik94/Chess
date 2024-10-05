package main.components;

import main.models.*;
import main.models.movables.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Contains information about figures and their methods
 * for moving over the board
 */
public class Figures {
    private final HashMap<Point, Coordinate>  coordinates;
    private final HashMap<Coordinate, Figure> figuresMap;
    private final HashMap<Rank, Movable>      movables;

    public Figures(HashMap<Coordinate, Figure> figuresMap,
                   HashMap<Point, Coordinate> coordinates) {
        this.figuresMap = figuresMap;
        this.coordinates = coordinates;
        this.movables = setMovables();
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

    public Figure getFigureAt(Point selectedPoint) {
        return getFigureAt(coordinates.get(selectedPoint));
    }

    /**
     * <p>Calculates available moves for a given figure.</p>
     * @param selectedFigure selected figure on board
     * @return a set of available moves
     */
    public HashSet<Coordinate> getMoves(Figure selectedFigure) {
        Coordinate position = coordinates.get(selectedFigure.getLocation());
        HashSet<Coordinate> moves = movables.get(selectedFigure.getRank()).getMoves(selectedFigure);
        HashSet<Coordinate> checkMoves = getCheckMoves(selectedFigure);

        // When king is selected, restrict moves from opponent controlled positions
        // and add Castle moves if applicable.
        if (selectedFigure.getRank().equals(Rank.KING)) {
            HashSet<Coordinate> controlledMoves = getControlledMoves(selectedFigure);
            HashSet<Coordinate> castleMoves = getCastleMoves(selectedFigure, controlledMoves, checkMoves);

            moves.addAll(castleMoves);
            moves.removeAll(controlledMoves); // Restrict from controlled moves
        }
        /*
        Evaluate if a check is imminent.
        When only one opponent checks, either the king can move,
        the check can be controlled or the opposite figure
        can be removed, otherwise the king must move.

        TODO when no moves are left for checked king, the game's over.
         */
        else if (! checkMoves.isEmpty()) {
            int imminentChecks = getImminentChecksCount(selectedFigure, checkMoves);
            int controlledChecks = getControlledChecksCount(selectedFigure, checkMoves);

            if (imminentChecks == 1) {
                // Restrict moves to the check trajectory when one opponent figure checks
                // and either the selected figure or none controls the check
                if (checkMoves.contains(position) || controlledChecks == 0) {
                    moves.retainAll(checkMoves);
                }
            }
            else if (imminentChecks > 1 && imminentChecks == controlledChecks) {
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

                    if (f != null && !(f.getSide().equals(selectedFigure.getSide()))) {
                        checkingFigures.add(f);
                    }
                }

                // Assess if a friendly figure controls the check move of the checking figure
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
     * @param selectedFigure a selected figure of the current turn
     * @return a set of all opponent's controlled moves
     */
    public HashSet<Coordinate> getControlledMoves(Figure selectedFigure) {
        HashSet<Coordinate> moves = new HashSet<>();

        for (Figure opponent : figuresMap.values()) {
            if (! opponent.getSide().equals(selectedFigure.getSide())) {
                moves.addAll(movables.get(opponent.getRank()).getControlledMoves(opponent));
            }
        }

        return moves;
    }

    /**
     * <p>Returns an opponents trajectory on the selected figure's king when
     * at most one friendly figure stands in the way to check.
     * Use <code>getControlledChecks</code> to compare if
     * the count of figures match on both sides. </p>
     * @param selectedFigure currently selected <code>Figure</code>
     * @return a set of check moves from the opposite side
     */
    public HashSet<Coordinate> getCheckMoves(Figure selectedFigure) {
        HashSet<Coordinate> moves = new HashSet<>();

        /*
        This method might require more granular control on returning check moves as it allows
        figures to move to illegal squares exposing king to actual check.
        Perhaps separating the check moves for singular figure?
         */
        for (Figure opponent : figuresMap.values()) {
            if (! opponent.getSide().equals(selectedFigure.getSide())) {
                moves.addAll(movables.get(opponent.getRank()).getCheckMoves(opponent));
            }
        }

        return moves;
    }

    /**
     * <p>Evaluates how many opposite figures are imminent threat to the King.</p>
     * @param selectedFigure side of the current turn
     * @param checkMoves all imminent checks on the current side's King
     * @return number of imminent checks
     */
    private int getImminentChecksCount(Figure selectedFigure, HashSet<Coordinate> checkMoves) {
        int imminentChecks = 0;

        for (Coordinate c : checkMoves) {
            Figure f = getFigureAt(c);
            if (f != null && !(f.getSide().equals(selectedFigure.getSide()) || f.getRank().equals(Rank.KING))) {
                imminentChecks++;
            }
        }

        return imminentChecks;
    }

    /**
     * <p>Evaluates how many checks are controlled by friendly figures.</p>
     * @param selectedFigure side of the current turn
     * @param checkMoves all imminent checks on the current side's King
     * @return number of controlled checks
     */
    private int getControlledChecksCount(Figure selectedFigure, HashSet<Coordinate> checkMoves) {
        int controlledChecks = 0;

        for (Coordinate c : checkMoves) {
            Figure f = getFigureAt(c);
            if (f != null && f.getSide().equals(selectedFigure.getSide())) {
                controlledChecks++;
            }
        }

        return controlledChecks;
    }

    /**
     * <p>Evaluates if King is in check</p>
     * @param selectedFigure side of the currently selected figure
     * @param checkMoves imminent checks on the current side's king
     * @return true if imminent checks count is greater than controlled checks count
     */
    public boolean isCheck(Figure selectedFigure, HashSet<Coordinate> checkMoves) {
        return getImminentChecksCount(selectedFigure, checkMoves)
                > getControlledChecksCount(selectedFigure, checkMoves);
    }

    public HashSet<Coordinate> getCastleMoves(Figure selectedFigure,
                                              HashSet<Coordinate> controlledMoves,
                                              HashSet<Coordinate> checkMoves) {
        HashSet<Coordinate> moves = new HashSet<>();

        if ( !selectedFigure.hasMoved() && !isCheck(selectedFigure, checkMoves) ) {
            King king = (King) movables.get(selectedFigure.getRank());

            moves.addAll(king.getBigCastleMove(selectedFigure, controlledMoves));
            moves.addAll(king.getSmallCastleMove(selectedFigure, controlledMoves));
        }

        return moves;
    }
}
