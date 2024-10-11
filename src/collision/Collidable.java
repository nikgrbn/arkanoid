// Nikita Grebenchuk 337762421

package collision;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import objects.Ball;

/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return collision shape
     */
    Rectangle getCollisionRectangle();


    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param collisionPoint
     * @param currentVelocity
     * @param hitter
     * @return new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}