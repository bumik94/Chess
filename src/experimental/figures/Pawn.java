package experimental.figures;

import experimental.Coordinate;
import experimental.Figure;
import experimental.Rank;
import experimental.Side;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Pawn extends Figure {

    public Pawn(Side side, Rank rank, Point position, int resolution) {
        super(side, rank, position, resolution);
    }

    // TODO Coordinate ordinals range from 0-63, position can be evaluated by adding
    //  or subtracting an interval that would represent vertical, horizontal or diagonal
    //  square next to the previous.
    @Override
    public void move(Point destination) {
//        int ord = getCoordinateOrdinal();

//        switch (this.getSide()) {
//
//case WHITE -> {
//        for (Figure figure : getFigures()) {
//            int ord2 = figure.getCoordinateOrdinal();
//
//            switch (ord - ord2) {
//                case 8 -> {
//                    return;
//                }
//            }
//        }
//    }
//
//        }
    }
}
