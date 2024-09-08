package experimental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The main game class that draws UI and handles action events.
 * Selection is handled by this class and calls methods of the
 * components to change their state.
 */
public class Chess extends JPanel {

    // TODO Stockfish chess AI
    private static final Color YELLOW = new Color(255, 255, 0);

    private final int resolution;
    private final Game game;
    private final Board board;

    private Square selectedSquare;
    private Figure selectedFigure;

    public Chess(int resolution) {
        this.resolution = resolution;
        this.game = new Game(resolution);
        this.board = game.getBoard();

        /*
         * Left-click: selects a single square on the board
         * Right-click: deselects currently selected square
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton()) {
                    // Left-click
                    case MouseEvent.BUTTON1 -> {
                        // Draw selected square
                        Square square = board.getSquareAt(e.getX(), e.getY());
                        Point p = square.getLocation();

                        setSelectedSquare(square);

                        selectFigure(p);
                    }
                    // Right-click
                    case MouseEvent.BUTTON3 -> {
                        if (selectedSquare != null) {
                            Rectangle r = selectedSquare.getBounds();
                            selectedSquare = null;
                            selectedFigure = null;
                            repaint(r);
                        }
                    }
                }
            }
        });
    }

    /**
     * <p>Handles selection of figures. At first selects a figure on square
     * and then either moves to a free square or selects another figure.</p>
     *
     * @param p selected square origin
     */
    private void selectFigure(Point p) {
        Figure figure = getFigureAt(p);

        if (selectedFigure == null) {
            setSelectedFigure(figure);
        }
        else {
            selectedFigure.move(p);
        }


//        else if (figure == null) {
//            Square square = board.getSquareAt(selectedFigure.getPosition());
//            selectedFigure.setPosition(p);
//            selectedFigure = null;
//
//            repaint(square);
//            Rectangle r = selectedSquare.getBounds();
//            repaint(r);
//            selectedSquare = null;
//        }
//        else {
//            setSelectedFigure(figure);
//        }
    }

    /**
     * Board size
     *
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(resolution, resolution);
    }

    /**
     * Main painting method
     *
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        /*
        Order of draw:
            1) board
            2) selection
            3) figures
         */
        board.paintBoard(g);    // 1)
        paintSelectedSquare(g); // 2)
        game.drawFigures(g);    // 3)
    }

    /**
     * Paints a yellow square at the location of a selected square
     *
     * @param g paintComponent graphics
     */
    private void paintSelectedSquare(Graphics g) {
        if (selectedSquare != null) {
            selectedSquare.paintSquare(g);
        }
    }

    /**
     * Keep track of currently selected square
     *
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

    private Figure getFigureAt(Point p) {
        return game.getFigure(p);
    }

    private void setSelectedFigure(Figure figure) {
        selectedFigure = figure;
//        System.out.println("selected figure: " + selectedFigure);
    }

}
