package experimental.figures;


import experimental.models.Coordinate;
import experimental.models.Figure;
import experimental.models.Side;

import java.awt.*;
import java.util.HashMap;

public class Pawn {
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Pawn(HashMap<Coordinate, Figure> figures, HashMap<Point, Coordinate> coordinates) {
        this.figures = figures;
        this.coordinates = coordinates;
    }

    public static void move(Figure figure,
                            HashMap<Coordinate, Figure> figures,
                            Coordinate destination) {

        switch (figure.getSide()) {
            case WHITE -> {
                // Valid move is one step ahead from current position
//                if (figure.getPosition())
            }
        }

    }

}
