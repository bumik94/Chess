package main.java.components.figures;

import main.java.components.Board;
import main.java.components.models.Coordinate;
import main.java.components.models.Figure;

import java.awt.*;

public class Pawn extends Figure {

    public Pawn(Side side, Rank rank, Coordinate position, int FRAME_XY) {
        super(side, rank, position, FRAME_XY);
    }

    @Override
    public void move(Coordinate position) {

    }

}
