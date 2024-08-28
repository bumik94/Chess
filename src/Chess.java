import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Chess extends JPanel {

    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color BROWN = new Color(150, 75, 0);
    private static final Color YELLOW = new Color(255, 255, 0);
    private static final int FRAME_XY = 512;
    /*
     * Save reference of squares used to draw the playing board
     */
    private final ArrayList<Square> board = new ArrayList<>();

    private Square selectedSquare;

    public Chess() {
        //
        // Initialize board
        //
        int i = 1;  // Alternate color pattern
        int SQUARE_SIZE = FRAME_XY / 8;
        for (int y = 0; y < FRAME_XY; y += SQUARE_SIZE) {
            for (int x = 0; x < FRAME_XY; x += SQUARE_SIZE) {
                Color color;
                if (i++ % 2 == 0) {
                        color = WHITE; }
                else {  color = BROWN; }
                Square square = new Square(x, y, SQUARE_SIZE, color);
                board.add(square);
            }
            i++;
        }

        /*
         * Left-click: selects a single square on board
         * Right-click: deselects currently selected square
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton()) {
                    // Left-click
                    case MouseEvent.BUTTON1 -> {
                        Point p = new Point(e.getX(), e.getY());
                        Square square = getSquareAt(p);
                        setSelectedSquare(square);
                        System.out.println(square);
                    }
                    // Right-click
                    case MouseEvent.BUTTON3 -> {
                        if (selectedSquare != null) {
                            Rectangle r = selectedSquare.getBounds();
                            selectedSquare = null;
                            repaint(r);
                        }
                    }
                }
            }
        });
    }

    /**
     * Panel size
     *
     * @return dimension for the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FRAME_XY, FRAME_XY);
    }

    /**
     * Main painting method
     *
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        paintField(g);
        paintSelectedSquare(g);
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
     * @param selectedSquare from <code>mousePressed</code> method
     */
    private void setSelectedSquare(Rectangle selectedSquare) {
        if (this.selectedSquare != null) {
            if (this.selectedSquare.getLocation().equals(selectedSquare.getLocation())) {
                return;
            }
            repaint(this.selectedSquare);
        }
        this.selectedSquare = new Square(selectedSquare, YELLOW);
        repaint(this.selectedSquare);
    }
    /**
     * Pain playing field
     *
     * @param g paintComponent graphics
     */
    private void paintField(Graphics g) {
        board.forEach(square -> square.paintSquare(g));
    }

    /**
     * Gets <code>Square</code> from squares list at position p
     *
     * @param p cursor at <code>Point</code>
     *
     * @return Square object contained at <code>Point</code>
     */
    private Square getSquareAt(Point p) {
        return getSquareAt(p.x, p.y);
    }

    /**
     * Gets square from squares list at coordinates X and Y
     *
     * @param x cursor on X axis
     * @param y cursor on Y axis
     *
     * @return Square object located at X and Y
     */
    private Square getSquareAt(int x, int y) {
        Point p = new Point(x, y);
        final Square[] square = new Square[1];

        board.forEach(s -> {
            if (s.contains(p)) {
                square[0] = s;
                System.out.println("i=" + board.indexOf(s));
            }
        });

        return square[0];
    }

}


class Square extends Rectangle {

    private static final Color BLACK = new Color(0, 0, 0);

    private Color background = null;

    public Square(int x,int y, int size, Color background) {
        super(x, y, size, size);
        this.background = background;
    }

    public Square(Point p, int size, Color background) {
        this(p.x, p.y, size, background);
    }

    public Square(Rectangle r, Color background) {
        super(r);
        this.background = background;
    }

    /**
     * Paints individual square at position X and Y
     * @param g
     */
    protected void paintSquare(Graphics g){
        g.setColor(background);
        g.fillRect(x, y, width, height);
        g.setColor(BLACK);
        g.drawRect(x, y, width, height);
    }

    @Override
    public String toString() {
        return String.format("""
                X=%.1f Y=%.1f
                W=%.1f H=%.1f""",
                getX(), getY(),
                getWidth(), getHeight());
    }

}
