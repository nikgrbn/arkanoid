// Nikita Grebenchuk 337762421

package objects;

import biuoop.DrawSurface;
import collision.Collidable;
import events.HitListener;
import events.HitNotifier;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Sprite;
import ui.Game;
import utils.ColorEnum;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private Color color = Color.GRAY;;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean hasBorder;

    /**
     * Constructor.
     * @param shape
     */
    public Block(Rectangle shape) {
        this.shape = shape;
    }

    /**
     * Constructor.
     * @param shape
     * @param color
     */
    public Block(Rectangle shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    /**
     * Constructor.
     * @param shape
     * @param color
     * @param isBorder
     */
    public Block(Rectangle shape, Color color, boolean isBorder) {
        this.shape = shape;
        this.color = color;
        this.hasBorder = isBorder;
    }

    /**
     * Removes a block from the game.
     * @param game
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Check if colors of a ball matches the block.
     * @param ball
     * @return boolean
     */
    public Boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }

    /**
     * Add self to the game object.
     * @param g
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Return the "collision shape" of the object.
     * @return collision shape
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return shape;
    }

    /**
     * Notifies all registered game.events.HitListener objects by calling their hitEvent method.
     * @param hitter
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

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
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        boolean updateDx = false;
        boolean updateDy = false;
        double marginDistX = 0;
        double marginDistY = 0;

        // If ball on the same Y coordinates as obstacle check for horizontal collisions
        if (collisionPoint.getY() >= shape.getUpperLeft().getY()
                && collisionPoint.getY() <= shape.getDownRight().getY()) {

            // Hit the obstacle horizontally with positive velocity
            if (currentVelocity.getDx() > 0 && collisionPoint.getX() >= shape.getUpperLeft().getX() - 1
                    && collisionPoint.getX() <= shape.getDownRight().getX() + 1) {
                updateDx = true;
                marginDistX = collisionPoint.getX() - shape.getUpperLeft().getX();

                // Hit the obstacle horizontally with negative velocity
            } else if (currentVelocity.getDx() < 0 && collisionPoint.getX() <= shape.getDownRight().getX() + 1
                    && collisionPoint.getX() >= shape.getUpperLeft().getX() - 1) {
                updateDx = true;
                marginDistX = shape.getDownRight().getX() - collisionPoint.getX();

            }
        }
        // If ball on the same X coordinates as obstacle check for vertical collisions
        if (collisionPoint.getX() >= shape.getUpperLeft().getX()
                && collisionPoint.getX() <= shape.getDownRight().getX()) {
            // Hit the obstacle vertically with positive velocity
            if (currentVelocity.getDy() > 0 && collisionPoint.getY() >= shape.getUpperLeft().getY() - 1
                    && collisionPoint.getY() <= shape.getDownRight().getY() + 1) {
                updateDy = true;
                marginDistY = collisionPoint.getY() - shape.getUpperLeft().getY();

                // Hit the obstacle vertically with negative velocity
            } else if (currentVelocity.getDy() < 0 && collisionPoint.getY() <= shape.getDownRight().getY() + 1
                    && collisionPoint.getY() >= shape.getUpperLeft().getY() - 1) {
                updateDy = true;
                marginDistY = shape.getDownRight().getY() - collisionPoint.getY();
            }
        }

        // We want to update only one component at a time, therefore compare by distance from margin
        if (updateDx && updateDy) {
            if (marginDistX < marginDistY) {
                updateDy = false;
            } else {
                updateDx = false;
            }
        }
        if (updateDx) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        if (updateDy) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }

        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }

        return currentVelocity;
    }

    /**
     * Draw rectangle on the given DrawSurface.
     * @param surface surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        if (hasBorder) {
            surface.setColor(ColorEnum.BLACK.getColor());
            surface.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                    (int) shape.getWidth(), (int) shape.getHeight());
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        // TODO
    }


    /**
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Add hl as a listener to hit events.
     *
     * @param hl
     */
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl
     */
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}
