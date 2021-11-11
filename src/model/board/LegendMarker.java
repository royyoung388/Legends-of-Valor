package model.board;

/**
 * Mpa marker for legend game
 */
public class LegendMarker {
    public static final String PLAYER = "\033[31;5;1;43m P \033[0m";
    public static final String BLOCK = "\033[41mXXX\033[0m";
    public static final String MARKET = "\033[34m M \033[0m";
    public static final String COMMON = "\033[0m";

    // without color
//    public static final String PLAYER = " P ";
//    public static final String BLOCK = "XXX";
//    public static final String MARKET = " M ";
//    public static final String COMMON = "";
}
