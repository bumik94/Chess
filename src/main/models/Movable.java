package main.models;

import java.util.HashSet;

public interface Movable {

    HashSet<Coordinate> moves(Figure figure);
    HashSet<Coordinate> controlledMoves(Figure figure);
    HashSet<Coordinate> check(Figure figure);
}
