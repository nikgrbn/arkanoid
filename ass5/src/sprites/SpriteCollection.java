// Nikita Grebenchuk 337762421

package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructor.
     * @param sprites
     */
    public SpriteCollection(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Add sprite to list.
     * @param s
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Removes sprite from list of sprites.
     * @param s
     */
    public void removeSprite(Sprite s) {
        Iterator<Sprite> iter = sprites.iterator();

        while (iter.hasNext()) {
            if (iter.next().equals(s)) {
                iter.remove();
                break;
            }
        }
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);

        for (Sprite sprite : spritesCopy) {
            sprite.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }
}