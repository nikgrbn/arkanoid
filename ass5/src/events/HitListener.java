// Nikita Grebenchuk 337762421

package events;

import objects.Ball;
import objects.Block;

/**
 * Hit listener.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the game.objects.Ball that's doing the hitting.
     * @param beingHit
     * @param hitter
     */
    void hitEvent(Block beingHit, Ball hitter);
}