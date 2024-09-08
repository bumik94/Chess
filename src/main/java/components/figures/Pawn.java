package main.java.components.figures;

import main.java.components.models.Figure;
import main.java.components.utility.Coordinate;
import main.java.components.utility.Rank;
import main.java.components.utility.Side;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Pawn extends Figure {

    public Pawn(Side side, Rank rank, Point position, int resolution,
                HashMap<Point, Coordinate> coordinates, ArrayList<Figure> figures) {
        super(side, rank, position, resolution, coordinates, figures);
    }

    // TODO Coordinate ordinals range from 0-63, position can be evaluated by adding
    //  or subtracting an interval that would represent vertical, horizontal or diagonal
    //  square next to the previous.
    @Override
    public void move(Point destination) {
        int ord = getCoordinateOrdinal();

        switch (this.getSide()) {

case WHITE -> {
        for (Figure figure : getFigures()) {
            int ord2 = figure.getCoordinateOrdinal();

            switch (ord - ord2) {
                case 8 -> {
                    return;
                }
            }
        }
    }

        }
    }
}
