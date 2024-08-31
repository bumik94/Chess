package main.java.components.models;

import main.java.components.Board;

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
        WHITE("w_"),
        BLACK("b_");

        private final String side;

        Side(String side) {
            this.side = side;
        }

        public String getSide() {
            return side;
        }
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
    Coordinate position;


    //
    // Constructor
    //
    public Figure(Side side, Rank rank, Coordinate position, int FRAME_XY) {
        this.side = side;
        this.rank = rank;
        this.position = position;
        this.SQUARE_SIZE = FRAME_XY / 8;

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
    public abstract void move(Coordinate position);

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
     * Initialize a figure image according to subclass values.
     *
     * @param path String specifying a path to the appropriate image
     */
    public void setFigureImage(String path) {
        try {
            figureImage = ImageIO.read(new File(path));
        } catch (IOException ignored) {}
    }

//    public void paintFigure(Graphics g, Rectangle r) {
//        int OFFSET = (int) (SQUARE_SIZE * 0.05);
//
//        // Destination where the image will be drawn
//        r.grow(-OFFSET, -OFFSET); // Shrink image
//        int dstX = r.x + OFFSET;
//        int dstY = r.y + OFFSET;
//        int dstW = r.width;
//        int dstH = r.height;
//
//        // Original proportions of the image
//        int srcX = figureImage.getMinX();
//        int srcY = figureImage.getMinY();
//        int srcW = figureImage.getWidth();
//        int srcH = figureImage.getHeight();

//        // Overloaded method that scales the image
//        // to the current board resolution
//        g.drawImage(figureImage,
//        dstX, dstY, dstW, dstH,
//        srcX, srcY, srcW, srcH,
//        null);
//    }

    // TODO the drawImage method accepts Point1, Point2 instead of Point, Dimension!
    //      must rewrite the whole coordinate map
    public void paintFigure(Graphics g, Rectangle r) {
        int OFFSET = (int) (SQUARE_SIZE * 0.05);

        // Destination where the image will be drawn
        r.grow(-OFFSET, -OFFSET); // Shrink image
        int dstX = r.x;
        int dstY = r.y;
        int dstW = dstX + SQUARE_SIZE - OFFSET * 4;
        int dstH = dstY + SQUARE_SIZE - OFFSET * 4;

        // Original proportions of the image
        int srcX = figureImage.getMinX();
        int srcY = figureImage.getMinY();
        int srcW = figureImage.getWidth();
        int srcH = figureImage.getHeight();
//
//        // Original proportions of the image
//        int srcX = figureImage.getMinX();
//        int srcY = figureImage.getMinY();
//        int srcW = figureImage.getWidth();
//        int srcH = figureImage.getHeight();

        // Overloaded method that scales the image
        // to the current board resolution
        g.drawImage(figureImage, dstX, dstY, dstW, dstH, srcX, srcY, srcW, srcH, null);
//        System.out.printf("""
//                %d %d
//                %d %d
//                """,
//                dstX, dstY,
//                dstW, dstH);
    }

}
