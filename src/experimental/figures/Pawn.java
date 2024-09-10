package experimental.figures;


import experimental.models.Coordinate;
import experimental.models.Figure;
import experimental.models.Movable;

import java.awt.*;
import java.util.HashMap;

public class Pawn implements Movable {
    private final HashMap<Coordinate, Figure> figures;
    private final HashMap<Point, Coordinate> coordinates;

    public Pawn(HashMap<Coordinate, Figure> figures,
                HashMap<Point, Coordinate> coordinates) {
        this.figures = figures;
        this.coordinates = coordinates;
    }

    public void move(Figure figure, Point destination) {
        // Check for valid move
        // If true, remove figure from map
        // If opposing figure occupied the destination, replace moving figure with the
        // opposing figure
    }

}
