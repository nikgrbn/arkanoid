// Nikita Grebenchuk 337762421

import ui.Game;

/**
 * Main entry to the application.
 * @author Nikita Grebenchuk
 * @version 3.0
 * @since 03/07/2024 14:00
 */
public class Ass5Game {
    /**
     * Main.
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();

        game.finish();
    }
}
