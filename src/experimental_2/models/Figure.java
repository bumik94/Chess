package experimental_2.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Figure {
    final int SQUARE_SIZE;
    Side side;
    Rank rank;
    BufferedImage figureImage;
    Point position;


    //
    // Constructor
    //
    public Figure(Side side, Rank rank, Point position, int resolution) {
        this.side = side;
        this.rank = rank;
        this.position = position;
        this.SQUARE_SIZE = resolution / 8;

        String path = "src/main/resources/figure_images/" +
                side.getSide() +
                rank.name().toLowerCase() +
                ".png";
        setFigureImage(path);
    }

    //
    // Methods
    //
    /**
     * <p>Checks for valid moves</p>
     * @param destination <code>Coordinate</code> of destination
     */
    public void move(Coordinate destination,
                     HashMap<Coordinate, Figure> figures) {

    }

    public void setLocation(Point position) {
        this.position = position;
    }

    public Point getLocation() {
        return this.position;
    }

    public Side getSide() {
        return this.side;
    }

    public Rank getRank() {
        return this.rank;
    }

    /**
     * <p>Initialize a figure image according to subclass values.</p>
     * @param path String specifying a path to the appropriate image
     */
    public void setFigureImage(String path) {
        try {
            figureImage = ImageIO.read(new File(path));
        } catch (IOException ignored) {}
    }

    /**
     * <p>Draws the <code>figureImage</code> offset from edge by the <code>OFFSET</code> constant</p>
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintFigure(Graphics g) {
        int OFFSET = (int) (SQUARE_SIZE * 0.1);

        // Destination where the image will be drawn
        int dstX = (int) position.getX() + OFFSET;
        int dstY = (int) position.getY() + OFFSET;
        int dstW = (int) position.getX() + SQUARE_SIZE - OFFSET;
        int dstH = (int) position.getY() + SQUARE_SIZE - OFFSET;

        // Original proportions of the image
        int srcX = figureImage.getMinX();
        int srcY = figureImage.getMinY();
        int srcW = figureImage.getWidth();
        int srcH = figureImage.getHeight();

        // Overloaded method that scales the image
        // to the current board resolution
        g.drawImage(figureImage,
        dstX, dstY, dstW, dstH,
        srcX, srcY, srcW, srcH,
        null);
    }

    /**
     * <p>Draws the <code>figureImage</code> offset from edge by the <code>OFFSET</code> constant</p>
     * @param g the <code>Graphics</code> object to protect
     * @param p <code>Point</code> specifying the origin for the <code>figureImage</code>
     */
    public void paintFigure(Graphics g, Point p) {
        int OFFSET = (int) (SQUARE_SIZE * 0.1);

        // Destination where the image will be drawn
        int dstX = (int) p.getX() + OFFSET;
        int dstY = (int) p.getY() + OFFSET;
        int dstW = (int) p.getX() + SQUARE_SIZE - OFFSET;
        int dstH = (int) p.getY() + SQUARE_SIZE - OFFSET;

        // Original proportions of the image
        int srcX = figureImage.getMinX();
        int srcY = figureImage.getMinY();
        int srcW = figureImage.getWidth();
        int srcH = figureImage.getHeight();

        // Overloaded method that scales the image
        // to the current board resolution
        g.drawImage(figureImage,
        dstX, dstY, dstW, dstH,
        srcX, srcY, srcW, srcH,
        null);
    }

    @Override
    public String toString() {
        return "Figure{" +
                "side=" + side +
                ", rank=" + rank +
                ", position=" + position +
                '}';
    }
}
