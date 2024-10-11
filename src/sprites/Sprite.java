// Nikita Grebenchuk 337762421

package sprites;

import biuoop.DrawSurface;

/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     * @param d
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}