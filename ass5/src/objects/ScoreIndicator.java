// Nikita Grebenchuk 337762421

package objects;

import biuoop.DrawSurface;
import ui.Game;
import sprites.Sprite;
import utils.ColorEnum;
import utils.Counter;

import java.awt.Color;

/**
 * Score indicator.
 */
public class ScoreIndicator implements Sprite {
    public static final int TEXT_PADDING = 10;

    private Counter score;

    /**
     * Constructor.
     * @param score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Add score indicator to game.
     * @param g
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d
     */
    @Override
    public void drawOn(DrawSurface d) {
        String str = "Score: " + score.getValue();
        int stringSize = str.length();
        int textWidth = stringSize * 10;
        int startLeft = Game.SCREEN_W / 2 - textWidth / 2;

        d.setColor(Color.WHITE);
        d.fillRectangle(startLeft - TEXT_PADDING + 2, 7, textWidth + TEXT_PADDING, 25);

        d.setColor(ColorEnum.BLACK.getColor());
        d.drawText(startLeft, 27, str, 20);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        // TODO
    }
}
