// Nikita Grebenchuk 337762421

package utils;

import java.awt.Color;
import java.util.Random;

/**
 * Enum of colors.
 * @author Nikita Grebenchuk
 * @version 1.0
 * @since 5/15/2024 12:00
 */
public enum ColorEnum {
    GREEN(Color.decode("#7aad4b")),
    BLUE(Color.decode("#9bc4cb")),
    YELLOW(Color.decode("#ffb627")),
    PINK(Color.decode("#fecef1")),
    MAGENTA(Color.decode("#7d53de")),
    CYAN(Color.CYAN),
    ORANGE(Color.ORANGE),
    BLACK(Color.decode("#040D12")),
    RED(Color.decode("#e56b6f"));

    private final Color color;

    /**
     * Constructor.
     * @param color
     */
    ColorEnum(Color color) {
        this.color = color;
    }

    /**
     * @return color
     */
    public Color getColor() {
        return color;
    }

    private static final ColorEnum[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    /**
     * Generate random color from enum values.
     * @return color
     */
    public static ColorEnum getRandomColor() {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}