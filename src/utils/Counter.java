// Nikita Grebenchuk 337762421

package utils;

/**
 * game.utils.Counter.
 */
public class Counter {
    private int num = 0;

    /**
     * add number to current count.
     * @param number
     */
    public void increase(int number) {
        num += number;
    }


    /**
     * subtract number from current count.
     * @param number
     */
    public void decrease(int number) {
        num -= number;
    }


    /**
     * get current count.
     * @return int
     */
    public int getValue() {
        return num;
    }
}