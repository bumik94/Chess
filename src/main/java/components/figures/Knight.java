package main.java.components.figures;

import main.java.components.models.Figure;
import main.java.components.models.Rank;
import main.java.components.models.Side;

import java.awt.*;

public class Knight extends Figure {

    public Knight(Side side, Rank rank, Point position, int resolution) {
        super(side, rank, position, resolution);
    }

    @Override
    public void move(Point position) {

    }

}
