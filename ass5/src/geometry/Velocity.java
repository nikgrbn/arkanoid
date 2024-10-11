// Nikita Grebenchuk 337762421

package geometry;
/**
 * game.geometry.Velocity specifies the change in position on the `x` and the `y` axes.
 * @author Nikita Grebenchuk
 * @version 1.0
 * @since 5/15/2024 12:00
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     * @param dx
     * @param dy
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Static method which converts given angle and a speed into dx, dy velocity.
     * @param angle angle of a movement.
     * @param speed speed of a movement.
     * @return new velocity object.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Convert angle to radians
        double radians = Math.toRadians(angle);

        // Calculate dx and dy using trigonometric functions
        double dx = speed * Math.cos(radians);
        double dy = speed * Math.sin(radians);

        return new Velocity(dx, dy);
    }

    /**
     * Static method which converts given dx and dy components into the speed.
     * @param dx change in x direction.
     * @param dy change in y direction.
     * @return speed of the movement.
     */
    public static double toSpeed(double dx, double dy) {
        // Calculate speed using the Pythagorean theorem
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * @return dx
     */
    public double getDx() {
        return dx;
    }

    /**
     * @return dy
     */
    public double getDy() {
        return dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * @param p point to apply velocity to.
     * @return new point
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}