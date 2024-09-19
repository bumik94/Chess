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
    // TODO Stockfish chess AI
    protected static final Color YELLOW = new Color(191, 191, 29);
    protected static final Color GREEN = new Color(50, 205, 50);

    protected final Board board;
    protected final Game game;
    private   final   int RESOLUTION;

    protected Square selectedSquare;
    protected Figure selectedFigure;
    private     Side turn;  // will be used in game loop
    private HashSet<Coordinate> moves;

    //
    // Constructor
    //
    public Chess(int RESOLUTION) {
        this.addMouseListener(new Listener());
        this.RESOLUTION = RESOLUTION;
        this.board = new Board(RESOLUTION);
        this.game = new Game(RESOLUTION);
        this.turn = Side.WHITE;
    }

    //
    // Methods
    //
    /**
     * <p>Main painting method</p>
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        /*
        Order of draw:
            1) board
            2) selection
            3) moves
            4) figures
         */
        paintBoard(g);
        paintSelectedSquare(g);
        paintMoves(g, moves);
        paintFigures(g);
    }

    /**
     * <p>Paint playing board</p>
     * @param g paintComponent graphics
     */
    private void paintBoard(Graphics g) {
        board.getBoard().forEach(square -> square.paintSquare(g));
    }

    /**
     * <p>Paint figures on board</p>
     * @param g paintComponent graphics
     */
    private void paintFigures(Graphics g) {
        game.getFigures().values().forEach(figure -> figure.paintFigure(g));
    }

    /**
     * <p>Paints the selected square yellow</p>
     * @param g paintComponent graphics
     */
    private void paintSelectedSquare(Graphics g) {
        if (selectedSquare != null) {
            selectedSquare.paintSquare(g);
        }
    }

    /**
     * <p>Paints available moves for currently selected figure.</p>
     * @param g paintComponent graphics
     * @param movesList a list returned from <code>Movable</code>
     */
    private void paintMoves(Graphics g, HashSet<Coordinate> movesList) {
        if (movesList != null) {
            movesList.forEach(coordinate -> {
                Point p = board.getPointAt(coordinate);
                Square s = new Square(board.getSquareAt(p), GREEN);
                s.paintSquare(g);
            });
        }
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
    }

    /**
     * <p>Repaint old <code>moves</code>, assigns new list</p>
     * to <code>moves</code> and repaints again
     * @param movesList a list returned from <code>Movable</code>
     */
    private void repaintMoves(HashSet<Coordinate> movesList) {
        repaintMoves();
        moves = movesList;
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

    /**
     * <p>Repaints selected square and its previous position.</p>
     * @param rectangle from <code>mousePressed</code> method
     */
    private void setSelectedSquare(Rectangle rectangle) {
        if (selectedSquare != null) {
            if (selectedSquare.getLocation().equals(rectangle.getLocation())) {
                return;
            }
            // Repaint old position
            repaint(selectedSquare);
        }
        // Assign and repaint new position
        selectedSquare = new Square(rectangle, YELLOW);
        repaint(selectedSquare);
    }

    private boolean setSelectedFigure(Point p) {
        Figure figure = game.getFigureAt(p);

        if (figure != null && getTurn().equals(figure.getSide())) {
            selectedFigure = figure;
            repaintMoves(game.getMoves(figure));

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
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RESOLUTION, RESOLUTION);
    }


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
                    Square square = board.getSquareAt(e.getX(), e.getY());
                    Point p = square.getLocation();
                    Coordinate c = board.getCoordinate(p);

                    if (setSelectedFigure(p)) {
                        setSelectedSquare(square);
                        System.out.println(selectedFigure);

                    } else if (moves.contains(c)) {
                        // remove figure from old position and repaint
                        Coordinate old = board.getCoordinate(selectedFigure.getLocation());
                        Figure f = game.getFigures().remove(old);
                        repaintMoves(null);
                        repaintSelectedSquare();
                        repaint(board.getSquareAt(old));

                        // Assign figure to new position and repaint
                        f.setLocation(p);
                        game.getFigures().put(c, f);
                        repaint(board.getSquareAt(c));

                        changeTurn();

                    } else {
                        System.out.println("invalid move");
                    }
                }


                // Right-click
                case MouseEvent.BUTTON3 -> {
                    // Clear selection
                    selectedFigure = null;
                    repaintMoves(null);
                    repaintSelectedSquare();
                }
            }
        }

    }
}
