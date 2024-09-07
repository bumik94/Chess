package main.java.components.models;

import main.java.components.utility.*;

import main.java.components.utility.Rank;
import main.java.components.utility.Side;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Figure {
    int SQUARE_SIZE;
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

        // Create path for the image based on
        // the `side` and `rank` values
        // and initialize figureImage
        String path = "src/main/resources/figure_images/" +
                side.getSide() +
                rank.name().toLowerCase() +
                ".png";
        setFigureImage(path);
    }

    //
    // Abstract methods
    //
    /**
     * Checks conditions to make valid move and then calls <code>setPosition</code>
     * @param position <code>Point</code> that will evaluate new position
     */
    public abstract void move(Point position);

    //
    // Default methods
    //
    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return this.position;
    }

    public Side getSide() {
        return side;
    }

    public Rank getRank() {
        return rank;
    }

    /**
     * Initialize a figure image according to subclass values.
     *
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
