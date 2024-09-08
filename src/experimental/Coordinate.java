package experimental;

public enum Coordinate {
    H1, H2, H3, H4, H5, H6, H7, H8,
    G1, G2, G3, G4, G5, G6, G7, G8,
    F1, F2, F3, F4, F5, F6, F7, F8,
    E1, E2, E3, E4, E5, E6, E7, E8,
    D1, D2, D3, D4, D5, D6, D7, D8,
    C1, C2, C3, C4, C5, C6, C7, C8,
    B1, B2, B3, B4, B5, B6, B7, B8,
    A1, A2, A3, A4, A5, A6, A7, A8;

//  00, 01, 02, 03, 04, 05, 06, 07, 
//	08, 09, 10, 11, 12, 13, 14, 15,
//  16, 17, 18, 19, 20, 21, 22, 23,
//	24, 25, 26, 27, 28, 29, 30, 31,
//	32, 33, 34, 35, 36, 37, 38, 39,
//	40, 41, 42, 43, 44, 45, 46, 47,
//  48, 49, 50, 51, 52, 53, 54, 55,
//  56, 57, 58, 59, 60, 61, 62, 63;

    private static final Coordinate[] coordinates = Coordinate.values();

    public static Coordinate getCoordinate(int ordinal) {
        return coordinates[ordinal];
    }
}
