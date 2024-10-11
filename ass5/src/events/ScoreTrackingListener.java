// Nikita Grebenchuk 337762421

package events;

import objects.Ball;
import objects.Block;
import utils.Counter;

/**
 * Score tracker.
 */
public class ScoreTrackingListener implements HitListener {
    private static final int HIT_SCORE = 5;

    private Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }


    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the game.objects.Ball that's doing the hitting.
     *
     * @param beingHit
     * @param hitter
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(HIT_SCORE);
    }
}
