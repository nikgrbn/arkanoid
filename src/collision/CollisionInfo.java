// Nikita Grebenchuk 337762421

package collision;

import geometry.Point;

/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructor.
     * @param collisionPoint
     * @param collisionObject
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * the point at which the collision occurs.
     * @return collisionPoint
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * the collidable object involved in the collision.
     * @return collisionObject
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}