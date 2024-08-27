import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Chess extends JPanel {
    private static final Color BROWN = new Color(150, 75, 0);
    private static final Color WHITE = new Color(255, 255, 255);

    private final int FRAME_XY = 512;
    private final int SQUARE_SIZE = FRAME_XY / 8;
    private final ArrayList<Square> field = new ArrayList<Square>();

    public Chess() {
        // Initialize field
        int i = 1;  // Alternate color pattern
        for (int y = 0; y < FRAME_XY; y += SQUARE_SIZE) {
            for (int x = 0; x < FRAME_XY; x += SQUARE_SIZE) {
                Color color;
                if (i++ % 2 == 0) {
                        color = WHITE; }
                else {  color = BROWN; }
                Square square = new Square(x, y, SQUARE_SIZE, color);
                field.add(square);
            }
            i++;
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                Square square = getSquareAt(p);
                System.out.println(square);
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
    }

    /**
     * Pain playing field
     *
     * @param g paintComponent graphics
     */
    private void paintField(Graphics g) {
        field.forEach(square -> square.paintSquare(g));
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

        field.forEach(s -> {
            if (s.contains(p)) {
                square[0] = s;
                System.out.println("i=" + field.indexOf(s));
            }
        });

        return square[0];
    }

}

class Square extends Rectangle {
    private Color background = null;

    public Square(int x,int y, int size, Color background) {
        super(x, y, size, size);
        this.background = background;
    }

    public Square(Point p, int size, Color background) {
        this(p.x, p.y, size, background);
    }

    /**
     * Paints individual square at position X and Y
     * @param g
     */
    protected void paintSquare(Graphics g){
        g.setColor(background);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
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
