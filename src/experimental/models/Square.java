package experimental.models;

import java.awt.*;

public class Square extends Rectangle {

    private static final Color BLACK = new Color(0, 0, 0);
    private Color background = null;
//    private Coordinate coordinate;

    //
    // Constructors
    //
    public Square(int x,int y, int size, Color background) {
        super(x, y, size, size);
        this.background = background;
    }

    public Square(Point p, int size, Color background) {
        this(p.x, p.y, size, background);
    }

    public Square(Rectangle r, Color background) {
        this(r.x, r.y, r.width, background);

    }

    //
    // Methods
    //
    /**
     * <p>Paints individual square at position X and Y.</p>
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintSquare(Graphics g){
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
