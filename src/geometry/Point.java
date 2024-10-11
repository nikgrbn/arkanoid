// Nikita Grebenchuk 337762421

package geometry;

/**
 * @author Nikita Grebenchuk
 * @version 1.0
 * @since 5/15/2024 12:00
 */
public class Point {
    private static final double EPSILON = 0.00001;

    private double x;
    private double y;

    /**
     * Constructor.
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other Second point
     * @return return the distance of this point to the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * @param other Second point.
     * @return return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        // Due to rounding errors two same numbers might be stored as different values in memory,
        // hence epsilon is required.
        return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
    }

    /**
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return x
     */
    public double getX() {
        return x; }

    /**
     * @return y
     */
    public double getY() {
        return y; }

    @Override
    public String toString() {
        return "game.geometry.Point{"
                + "x=" + x
                + ", y="
                + y + '}';
    }
}
