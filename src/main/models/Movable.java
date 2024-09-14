package main.models;

import java.util.ArrayList;

public interface Movable {

    ArrayList<Coordinate> moves(Figure figure);
}
