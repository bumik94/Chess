package experimental.figures;


import experimental.models.Coordinate;
import experimental.models.Figure;
import experimental.models.Side;

import java.util.HashMap;

public class Pawn {

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
