// Nikita Grebenchuk 337762421

package collision;

import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class GameEnvironment {

    private List<Collidable> collidables;

    /**
     * Constructor.
     * @param collidables
     */
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * Constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Add the given collidable to the environment.
     * @param c
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Removes collidable from list of collidables.
     * @param c
     */
    public void removeCollidable(Collidable c) {
        Iterator<Collidable> iter = collidables.iterator();

        while (iter.hasNext()) {
            if (iter.next().equals(c)) {
                iter.remove();
                break;
            }
        }
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory
     * @return collisionInfoList entry
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> collisionInfoList = new ArrayList<>();

        for (Collidable obj : collidables) {
            // Get the closest collision point to of an object
            Point p = trajectory.closestIntersectionToStartOfLine(obj.getCollisionRectangle());
            if (p != null) {
                collisionInfoList.add(new CollisionInfo(p, obj));
            }
        }

        // Sort collisions by the closets one to the start
        collisionInfoList.sort(new Comparator<CollisionInfo>() {
            @Override
            public int compare(CollisionInfo lhs, CollisionInfo rhs) {
                return Double.compare(lhs.collisionPoint().distance(trajectory.start()),
                        rhs.collisionPoint().distance(trajectory.start()));
            }
        });

        return collisionInfoList.isEmpty() ? null : collisionInfoList.get(0);
    }

    /**
     * @return collidables list
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }

    /**
     * @param collidables
     */
    public void setCollidables(List<Collidable> collidables) {
        this.collidables = collidables;
    }
}