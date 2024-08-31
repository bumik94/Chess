package main.java.components.figures;

import main.java.components.Board;
import main.java.components.models.Figure;

import java.awt.*;

public class Bishop extends Figure {

    public Bishop(Side side, Rank rank, Board.Coordinate position, int FRAME_XY) {
        super(side, rank, position, FRAME_XY);
    }

    @Override
    public void move(Board.Coordinate position) {

    }
}
