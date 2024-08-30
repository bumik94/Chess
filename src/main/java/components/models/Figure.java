package main.java.components.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Figure {

    public enum Side {
        WHITE,
        BLACK;
    }

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

    Side side;
    Rank rank;
    BufferedImage figureImage;

    public Figure(Side side, Rank rank) {
        this.side = side;
        this.rank = rank;

        StringBuilder path = new StringBuilder("src/main/resources/figure_images/");
        switch (side) {
            case WHITE -> path.append("w_");
            case BLACK -> path.append("b_");
        }
        path.append(rank.name().toLowerCase());
        path.append(".png");
    }

    public abstract void move(Rectangle r);

    public Side getSide() {
        return side;
    }

    public Rank getRank() {
        return rank;
    }

    public BufferedImage getFigureImage() {
        return figureImage;
    }

    public void setFigureImage(String path) {
        try {
            figureImage = ImageIO.read(new File(path));
        } catch (IOException ignored) {
        }
    }
}

















