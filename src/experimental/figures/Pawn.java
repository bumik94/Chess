package experimental.figures;


import experimental.models.Coordinate;
import experimental.models.Figure;
import experimental.models.Movable;
import experimental.models.Rank;

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
        Coordinate destinationCoordinate = coordinates.get(destination);
        Coordinate figureCoordinate =coordinates.get(figure.getLocation());
        Figure destinationFigure = figures.get(destinationCoordinate);

        if (destinationFigure != null
                && (! destinationFigure.getSide().equals(figure.getSide()))) {
            if (destinationFigure.getRank().equals(Rank.KING)) {
                System.out.println("Cannot remove " + destinationFigure);
                return;
            } else {
                System.out.println("Removing " + destinationFigure);
                figures.replace(destinationCoordinate, figures.remove(figureCoordinate));
            }
        } else {
            figures.put(destinationCoordinate, figures.remove(figureCoordinate));
        }
    }

}
