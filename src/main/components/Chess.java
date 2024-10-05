package main.components;

import main.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

/**
 * The main game class that draws UI and handles action events.
 * Selection is handled by this class and calls methods of the
 * components to change their state.
 */
public class Chess extends JPanel {

    ///////////////////////////////////////////////////////////////////////////////////////
    // Mouse Listener
    ///////////////////////////////////////////////////////////////////////////////////////

    class Listener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            switch (e.getButton()) {
                // Left-click
                case MouseEvent.BUTTON1 -> {
                    // Draw selection
                    Square selectedSquare = board.getSquareAt(e.getX(), e.getY());
                    Point selectedPoint = selectedSquare.getLocation();
                    Coordinate selectedCoordinate = board.getCoordinateAt(selectedPoint);

                    if (setSelectedFigure(selectedCoordinate)) {
                        setSelectedSquare(selectedSquare);

//                        System.out.println(selectedFigure.hasMoved());

                    } else if (moves != null && moves.contains(selectedCoordinate)) {
                        moveSelectedFigure(selectedPoint);
                        changeTurn();
                    }
                }
                // Right-click
                case MouseEvent.BUTTON3 -> {
                    // Clear selection
                    selectedFigure = null;
//                    repaintMoves(null);
                    // TEST
                    repaintControlledMoves(null, null);
                    repaintSelectedSquare();
                }
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // Chess Panel
    ///////////////////////////////////////////////////////////////////////////////////////

    // TODO Stockfish chess AI
    protected static final Color YELLOW = new Color(191, 191, 29);
    protected static final Color GREEN = new Color(50, 205, 50);

    protected final Figures figures;
    protected final   Board board;
    private   final     int resolution;

    protected Square selectedSquare;
    protected Figure selectedFigure;
    private Side turn;  // will be used in game loop
    private HashSet<Coordinate> moves;
    // TEST
    private HashSet<Coordinate> controlledMoves;

    //
    // Constructor
    //
    public Chess(int resolution) {
        this.addMouseListener(new Listener());
        this.resolution = resolution;
        this.board = new Board(resolution);
        this.figures = new Figures(
                board.getFiguresMap(),
                board.getCoordinates());
        this.turn = Side.WHITE;
    }

    //
    // Methods
    //

    /**
     * <p>Main painting method</p>
     *
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        /*
        Order of draw:
            1) board
            3) moves (optional)
            2) selection
            4) figures
         */
        paintBoard(g);
//        paintMoves(g, moves);
        // TEST
        paintControlledMoves(g, moves, controlledMoves);
//        paintCheckMoves(g, moves, checkMoves);
        paintSelectedSquare(g);
        paintFigures(g);
    }

    /**
     * <p>Paint playing board</p>
     *
     * @param g paintComponent graphics
     */
    private void paintBoard(Graphics g) {
        board.getSquares().forEach(square -> square.paintSquare(g));
    }

    /**
     * <p>Paint figures on board</p>
     *
     * @param g paintComponent graphics
     */
    private void paintFigures(Graphics g) {
        figures.getFiguresMap().values().forEach(figure -> figure.paintFigure(g));
    }

    /**
     * <p>Paints the selected square yellow</p>
     *
     * @param g paintComponent graphics
     */
    private void paintSelectedSquare(Graphics g) {
        if (selectedSquare != null) {
            selectedSquare.paintSquare(g);
        }
    }

    /**
     * <p>Paints available moves for currently selected figure.</p>
     *
     * @param g        paintComponent graphics
     * @param movesSet a list returned from <code>Movable</code>
     */
    private void paintMoves(Graphics g,
                            HashSet<Coordinate> movesSet) {
        if (movesSet != null) {
            movesSet.forEach(coordinate -> {
                Square s = new Square(board.getSquareAt(coordinate), GREEN);
                s.paintSquare(g);
            });
        }
    }

    private void paintControlledMoves(Graphics g,
                                      HashSet<Coordinate> movesSet,
                                      HashSet<Coordinate> controlledMovesSet) {
        if (controlledMovesSet != null) {
            controlledMovesSet.forEach(coordinate -> {
                Square s = new Square(board.getSquareAt(coordinate), Color.RED);
                s.paintSquare(g);
            });
        }

        paintMoves(g, movesSet);
    }

    /**
     * Repaint squares contained in <code>moves</code>
     */
    private void repaintMoves() {
        if (moves != null) {
            moves.forEach(coordinate -> {
                Square s = board.getSquareAt(coordinate);
                repaint(s);
            });
        }

        if (controlledMoves != null) {
            controlledMoves.forEach(coordinate -> {
                Square s = board.getSquareAt(coordinate);
                repaint(s);
            });
        }

    }

    /**
     * <p>Repaint old <code>moves</code>, assigns new list</p>
     * to <code>moves</code> and repaints again
     *
     * @param movesSet a list returned from <code>Movable</code>
     */
    private void repaintMoves(HashSet<Coordinate> movesSet) {
        repaintMoves();
        moves = movesSet;
        repaintMoves();
    }

    private void repaintControlledMoves(HashSet<Coordinate> movesSet,
                                        HashSet<Coordinate> controlledMovesSet) {
        repaintMoves();
        moves = movesSet;
        controlledMoves = controlledMovesSet;
        repaintMoves();
    }

    /**
     * <p>Repaint selected square and assign null</p>
     */
    private void repaintSelectedSquare() {
        if (selectedSquare != null) {
            repaint(selectedSquare);
            selectedSquare = null;
        }
    }

    public void moveSelectedFigure(Point SelectedPoint) {
        Coordinate selectedCoordinate = board.getCoordinateAt(SelectedPoint);

        // TODO assess if selected figure is a king and if it moved to a castling position
        //      and move appropriate rook to the castle as well

        // remove figure from old position and repaint
        Coordinate oldCoordinate = board.getCoordinateAt(selectedFigure.getLocation());
        Figure f = figures.getFiguresMap().remove(oldCoordinate);
        repaintMoves(null);
        // TEST
        repaintControlledMoves(null, null);
        repaintSelectedSquare();
        repaint(board.getSquareAt(oldCoordinate));

        // Assign figure to new position and repaint
        f.setLocation(SelectedPoint);
        figures.getFiguresMap().put(selectedCoordinate, f);
        repaint(board.getSquareAt(selectedCoordinate));

        selectedFigure.setMoved();
    }

    /**
     * <p>Repaints selected square and its previous position.</p>
     *
     * @param selectedSquare from <code>mousePressed</code> method
     */
    private void setSelectedSquare(Rectangle selectedSquare) {
        if (this.selectedSquare != null) {
            if (this.selectedSquare.getLocation().equals(selectedSquare.getLocation())) {
                return;
            }
            // Repaint old position
            repaint(this.selectedSquare);
        }
        // Assign and repaint new position
        this.selectedSquare = new Square(selectedSquare, YELLOW);
        repaint(this.selectedSquare);
    }

    /**
     * <p>Selects and repaints a square if it contains a <code>Figure</code>
     * of the current side on turn. Also repaints all square with legal
     * moves to green.</p>
     *
     * @param selectedCoordinate <code>Coordinate</code> position of the <code>Figure</code>
     *                           on the board
     * @return true when a square is occupied by friendly figure
     */
    private boolean setSelectedFigure(Coordinate selectedCoordinate) {
        Figure selectedFigure = figures.getFigureAt(selectedCoordinate);

        if (selectedFigure != null && getTurn().equals(selectedFigure.getSide())) {
            this.selectedFigure = selectedFigure;
//            repaintMoves(figures.getMoves(selectedFigure));
//             TEST
            repaintControlledMoves(
                    figures.getMoves(selectedFigure),
                    figures.getControlledMoves(selectedFigure));

            return true;
        }

        return false;
    }

    private void setTurn(Side side) {
        this.turn = side;
    }

    private Side getTurn() {
        return this.turn;
    }

    private void changeTurn() {
        switch (getTurn()) {
            case WHITE -> setTurn(Side.BLACK);
            case BLACK -> setTurn(Side.WHITE);
        }
    }

    /**
     * <p>Board size</p>
     *
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(resolution, resolution);
    }
}
