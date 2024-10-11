// Nikita Grebenchuk 337762421

package objects;

import biuoop.DrawSurface;
import collision.CollisionInfo;
import events.HitListener;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import sprites.Sprite;
import ui.Game;
import collision.GameEnvironment;

import java.awt.Color;

/**
 * @author Nikita Grebenchuk
 * @version 1.0
 * @since 5/15/2024 12:00
 */
public class Ball implements Sprite, HitListener {
    private Point center;
    private int size;
    private java.awt.Color color;
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment environment;

    /**
     * Constructor.
     * @param center
     * @param size
     * @param color
     * @param environment
     */
    public Ball(Point center, int size, Color color, GameEnvironment environment) {
        this.center = center;
        this.size = size;
        this.color = color;
        this.environment = environment;
    }

    /**
     * Constructor.
     * @param center
     * @param r
     * @param color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.size = r;
        this.color = color;
    }

    /**
     * Removes a ball from the game.
     * @param game
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    /**
     * Add self to the game object.
     * @param g
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * @return x
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * @return y
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return color
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.drawCircle((int) center.getX(), (int) center.getY(), size);
        surface.fillCircle((int) center.getX(), (int) center.getY(), size);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * @param v velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * @param dx
     * @param dy
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }


    /**
     * Move ball to a next position according to velocity and game environment.
     */
    public void moveOneStep() {
        // The trajectory of the ball in given frame
        Point next = this.getVelocity().applyToPoint(this.center);
        int xInterval = velocity.getDx() > 0 ? size : -size;
        int yInterval = velocity.getDy() > 0 ? size : -size;
        next.setX(next.getX() + xInterval);
        next.setY(next.getY() + yInterval);
        Line trajectory = new Line(center, next);

        CollisionInfo collisionInfo = environment.getClosestCollision(trajectory);
        if (collisionInfo != null) {

            // If collided, snap to collidable
            double snapX = center.getX() < collisionInfo.collisionPoint().getX()
                    ? collisionInfo.collisionPoint().getX() - xInterval - 1
                    : collisionInfo.collisionPoint().getX() - xInterval + 1;
            double snapY = center.getY() < collisionInfo.collisionPoint().getY()
                    ? collisionInfo.collisionPoint().getY() - yInterval - 1
                    : collisionInfo.collisionPoint().getY() - yInterval + 1;
            this.center = new Point(snapX, snapY);

            // Update velocity
            Velocity newVelocity = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), velocity);
            this.velocity = newVelocity;
        }

        this.center = this.getVelocity().applyToPoint(this.center);
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
        // If I'm the one who hit
        if (hitter.equals(this)) {
            this.color = beingHit.getColor();
        }
    }
}