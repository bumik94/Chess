package main.java.components.utility;

/**
 * Enum to hold information about each rank:
 * <p><code>notation</code> a letter for rank reference</p>
 * <p><code>value</code> worth of the piece</p>
 */
public enum Rank {
    KING("K", 0),
    QUEEN("Q", 9),
    ROOK("R", 5),
    BISHOP("B", 3),
    KNIGHT("N", 3),
    PAWN("", 1);

    private final String notation;
    private final int value;

    Rank(String notation, int value) {
        this.notation = notation;
        this.value = value;
    }

    public String getNotation() {
        return notation;
    }

    public int getValue() {
        return value;
    }
}