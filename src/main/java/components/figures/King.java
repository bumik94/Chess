package main.java.components.figures;

import main.java.components.models.Figure;
import main.java.components.utility.Coordinate;
import main.java.components.utility.Rank;
import main.java.components.utility.Side;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class King extends Figure {

    public King(Side side, Rank rank, Point position, int resolution,
                HashMap<Point, Coordinate> coordinates, ArrayList<Figure> figures) {
        super(side, rank, position, resolution, coordinates, figures);
    }

    // TODO Coordinate ordinals range from 0-63, position can be evaluated by adding
    //  or subtracting an interval that would represent vertical, horizontal or diagonal
    //  square next to the previous.
    @Override
    public void move(Point destination) {
    }

}
