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
        // 40% plain cell
        if (type < 4 && withBlock) {
            return new Marker(LegendMarker.PLAIN);
        } else if (type < 6) {
            // 20% Bush cell
            return new Marker(LegendMarker.BUSH);
        } else if (type < 8){
            // 20% Cave cell
            return new Marker(LegendMarker.CAVE);
        } else{
            // 20% Koulou cell
            return new Marker(LegendMarker.KOULOU);
        }
    }
}
