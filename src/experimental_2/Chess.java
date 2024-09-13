package experimental_2;

import experimental_2.models.Board;
import experimental_2.models.Square;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The main game class that draws UI and handles action events.
 * Selection is handled by this class and calls methods of the
 * components to change their state.
 */
public class Chess extends JPanel {

    // TODO Stockfish chess AI
    private static final Color YELLOW = new Color(191, 191, 29);
    private final int RESOLUTION;
    private final Listener listener;

    private Square selectedSquare;

    //
    // Constructor
    //
    public Chess(int RESOLUTION) {
        this.RESOLUTION = RESOLUTION;
        this.listener = new Listener(RESOLUTION);
        this.addMouseListener(listener);
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
            3) figures
         */
        paintBoard(g, listener.getBoard());
    }

    /**
     * <p>Paint playing board</p>
     * @param g paintComponent graphics
     */
    public void paintBoard(Graphics g, ArrayList<Square> board) {
        board.forEach(square -> square.paintSquare(g));
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
     * <p>Board size</p>
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RESOLUTION, RESOLUTION);
    }

}
