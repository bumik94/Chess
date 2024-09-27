package main.models;

import java.util.HashSet;

public interface Movable {

    HashSet<Coordinate> getMoves(Figure figure);
    HashSet<Coordinate> getControlledMoves(Figure figure);
    HashSet<Coordinate> getCheckMoves(Figure figure);
}
