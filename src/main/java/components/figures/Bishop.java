package main.java.components.figures;

import main.java.components.models.Figure;
import main.java.components.utility.Rank;
import main.java.components.utility.Side;

import java.awt.*;

public class Bishop extends Figure {

    public Bishop(Side side, Rank rank, Point position, int resolution) {
        super(side, rank, position, resolution);
    }

    @Override
    public void move(Point position) {

    }

}
