package utils;

import bean.board.Marker;

import java.util.Random;

/**
 * Calss for random probability
 */
public class Dice {
    public static int roll(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }

    public static Marker rollMarker(boolean withBlock) {
        int type = Dice.roll(10);
        // 20% non-accessible cell
        if (type < 2 && withBlock) {
            return new Marker(LegendMarker.BLOCK);
        } else if (type < 5) {
            // 30% market cell
            return new Marker(LegendMarker.MARKET);
        }
        // default common cell
        return new Marker();
    }
}
