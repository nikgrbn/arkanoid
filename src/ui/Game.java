// 337762421 Nikita Grebenchuk

package ui;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import collision.Collidable;
import collision.GameEnvironment;
import events.BallRemover;
import events.BlockRemover;
import events.ScoreTrackingListener;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import objects.Ball;
import objects.Block;
import objects.Paddle;
import objects.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;
import utils.ColorEnum;
import utils.Counter;

import java.awt.Color;

/**
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class Game {
    public static final int SCREEN_W = 800;
    public static final int SCREEN_H = 600;
    public static final int BORDER_S = 20;

    public static final Color BORDER_COLOR = Color.decode("#31363F");
    public static final Color BACKGROUND_COLOR = Color.decode("#222831");
    public static final Color PADDLE_COLOR = Color.decode("#EEEEEE");

    private GUI gui = new GUI("Arkanoid", SCREEN_W, SCREEN_H);

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blockCounter = new Counter();
    private Counter ballCounter = new Counter();
    private Counter scoreCounter = new Counter();

    /**
     * Constructor.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    /**
     * Removes collidable from list of collidables.
     * @param c
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }


    /**
     * Removes sprite from list of sprites.
     * @param s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Add collidable to list.
     * @param c
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add collidable to list.
     * @param s
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }


    /**
     * Initialize a new game: create the Blocks and game.objects.Ball (and game.objects.Paddle)
     * and add them to the game.
     */
    public void initialize() {
        // Background
        Block bg = new Block(new Rectangle(new Point(0, 0), SCREEN_W, SCREEN_H), BACKGROUND_COLOR);
        sprites.addSprite(bg);

        // Removers
        BlockRemover blockRemover = new BlockRemover(this, blockCounter);
        BallRemover ballRemover = new BallRemover(this, ballCounter);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(scoreCounter);

        // Border
        Block top = new Block(new Rectangle(new Point(0, 0), SCREEN_W, BORDER_S + BORDER_S), BORDER_COLOR);
        Block left = new Block(new Rectangle(new Point(0, BORDER_S), BORDER_S, SCREEN_H - BORDER_S), BORDER_COLOR);
        Block right = new Block(new Rectangle(new Point(SCREEN_W - BORDER_S, BORDER_S),
                BORDER_S, SCREEN_H - BORDER_S), BORDER_COLOR);
        top.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);

        // Death region
        Block deathRegion = new Block(new Rectangle(new Point(-SCREEN_W, SCREEN_H + BORDER_S),
                SCREEN_W * 2, BORDER_S), Color.GRAY);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);

        // Add balls
        Ball ball1 = new Ball(new Point(500, 450), 10, Color.WHITE, environment);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(270, 4));
        ball1.addToGame(this);
        ballCounter.increase(1);

        Ball ball2 = new Ball(new Point(500, 50), 10, Color.WHITE, environment);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(210, 6));
        ball2.addToGame(this);
        ballCounter.increase(1);

        Ball ball3 = new Ball(new Point(500, 50), 10, Color.WHITE, environment);
        ball3.setVelocity(Velocity.fromAngleAndSpeed(280, 4));
        ball3.addToGame(this);
        ballCounter.increase(1);

        // Blocks
        int w = 50, h = 30;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10 - i; j++) {
                Block block = new Block(new Rectangle(new Point(SCREEN_W - w - j * w - BORDER_S,
                        150 + i * h), w, h), ColorEnum.values()[i].getColor(), true);
                block.addHitListener(ball1);
                block.addHitListener(ball2);
                block.addHitListener(ball3);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreListener);
                block.addToGame(this);
                blockCounter.increase(1);
            }
        }

        // game.objects.Paddle
        Paddle paddle = new Paddle(new Rectangle(new Point(350, SCREEN_H - BORDER_S - 10), 150, 10), gui);
        paddle.addToGame(this);

        // Score Indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreCounter);
        scoreIndicator.addToGame(this);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // If none of the blocks left
            if (blockCounter.getValue() == 0) {
                scoreCounter.increase(100);
                return;
            }
            // If none of the balls left
            if (ballCounter.getValue() == 0) {
                return;
            }

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Finish the game.
     */
    public void finish() {
        gui.close();
    }
}