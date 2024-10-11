// Nikita Grebenchuk 337762421

package objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Sprite;
import ui.Game;

import java.awt.Color;

/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class Paddle implements Sprite, Collidable {
    private static final int SPEED = 8;

    private biuoop.KeyboardSensor keyboard;
    private Rectangle shape;

    /**
     * Constructor.
     * @param gui
     * @param collisionRectangle
     */
    public Paddle(Rectangle collisionRectangle, biuoop.GUI gui) {
        this.shape = collisionRectangle;
        this.keyboard = gui.getKeyboardSensor();
    }

    /**
     * Changes paddle coordinates to imitate movement to the left.
     */
    public void moveLeft() {
        shape.setUpperLeft(new Point(shape.getUpperLeft().getX() - SPEED, shape.getUpperLeft().getY()));

        // If reached the edge jump to next side
        if (shape.getUpperLeft().getX() < Game.BORDER_S) {
            shape.setUpperLeft(new Point(Game.SCREEN_W - Game.BORDER_S - shape.getWidth(),
                    shape.getUpperLeft().getY()));
        }
    }

    /**
     * Changes paddle coordinates to imitate movement to the right.
     */
    public void moveRight() {
        shape.setUpperLeft(new Point(shape.getUpperLeft().getX() + SPEED, shape.getUpperLeft().getY()));

        // If reached the edge jump to next side
        if (shape.getDownRight().getX() > Game.SCREEN_W - Game.BORDER_S) {
            shape.setUpperLeft(new Point(Game.BORDER_S, shape.getUpperLeft().getY()));
        }
    }

    /**
     * draw the sprite to the screen.
     * @param d
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Game.PADDLE_COLOR);
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
    }


    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * @return collisionRectangle
     */
    public Rectangle getCollisionRectangle() {
        return shape;
    }


    private static final int INIT_REGIONS = 5;
    private static final int SHIFT_REGIONS = 2;
    private static final int INIT_ANGLE = 270;
    private static final int SHIFT_ANGLE = 30;
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

        // Get the region of the hit
        int region = ((int) ((collisionPoint.getX() - shape.getUpperLeft().getX())
                / (shape.getWidth() / INIT_REGIONS))) - SHIFT_REGIONS;
        double speed = Velocity.toSpeed(currentVelocity.getDx(), currentVelocity.getDy()); // Get current ball speed

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
            // Update velocity according to the region of a hit
            currentVelocity = region == 0
                    ? new Velocity(currentVelocity.getDx(), -currentVelocity.getDy())
                    : Velocity.fromAngleAndSpeed(INIT_ANGLE + SHIFT_ANGLE * region, speed);
        }

        return currentVelocity;
    }


    /**
     * Add this paddle to the game.
     * @param g
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}