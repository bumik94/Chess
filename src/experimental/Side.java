package experimental;

/**
 * Enum to set the color of the pieces:
 *  <p><code>WHITE</code></p>
 *  <p><code>BLACK</code></p>
 */
public enum Side {
    WHITE("w_"),
    BLACK("b_");

    private final String side;

    Side(String side) {
        this.side = side;
    }

    public String getSide() {
        return side;
    }
}
