package main.java.components.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Figure {

    /**
     * Enum to set the color of the pieces:
     * -> white
     * -> black
     */
    public enum Side {
        WHITE,
        BLACK;
    }

    /**
     * Enum to hold information about each rank:
     * -> notation  = a letter for rank reference
     * -> value     = worth of the piece
     */
    public enum Rank {
        KING("K", 0),
        QUEEN("Q", 9),
        ROOK("R", 5),
        BISHOP("B", 3),
        KNIGHT("N", 3),
        PAWN("", 1);

        private final String notation;
        private final int value;

        Rank(String notation, int value) {
            this.notation = notation;
            this.value = value;
        }

        public String getNotation() {
            return notation;
        }

        public int getValue() {
            return value;
        }
    }

    int SQUARE_SIZE;
    Side side;
    Rank rank;
    BufferedImage figureImage;

    public Figure(Side side, Rank rank, int FRAME_XY) {
        this.side = side;
        this.rank = rank;
        this.SQUARE_SIZE = FRAME_XY / 8;

        //
        // Create path for the image based on the `side` and `rank` values
        // and initialize figureImage
        //
        StringBuilder path = new StringBuilder("src/main/resources/figure_images/");
        switch (side) {
            case WHITE -> path.append("w_");
            case BLACK -> path.append("b_");
        }
        path.append(rank.name().toLowerCase()).append(".png");
        setFigureImage(String.valueOf(path));
    }

    //
    // Abstract methods
    //
    public abstract void move(Rectangle r);

    //
    // Default methods
    //
    public Side getSide() {
        return side;
    }

    public Rank getRank() {
        return rank;
    }

    public BufferedImage getFigureImage() {
        return figureImage;
    }

    /**
     * Initialize a figure image based on subclass values.
     * Those are determined upon initialization where a
     * StringBuilder path is generated and passed to this method.
     *
     * @param path String containing path to the appropriate image
     */
    public void setFigureImage(String path) {

        try {
            figureImage = ImageIO.read(new File(path));
        } catch (IOException ignored) {}
    }

    public void paintFigure(Graphics g, Rectangle r) {

        // Destination where the image will be drawn
        int dstX = r.x;
        int dstY = r.y;
        int dstW = r.width;
        int dstH = r.height;

        // Original proportions of the image
        int srcX = figureImage.getMinX();
        int srcY = figureImage.getMinY();
        int srcW = figureImage.getWidth();
        int srcH = figureImage.getHeight();

        // Overloaded method that scales the image
        // to the current board scale
        g.drawImage(figureImage,
        dstX, dstY, dstW, dstH,
        srcX, srcY, srcW, srcH,
        null);
    }

}

















