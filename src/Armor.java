/***
 * Armor class
 */
public class Armor extends Item {
    private final int reduction;

    public Armor(String[] args) {
        this(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
    }

    public Armor(String name, int cost, int level, int damageReduction) {
        super(name, cost, level);
        this.reduction = damageReduction;
    }

    public int getReduction() {
        return reduction;
    }

    public static String header() {
        return String.format("%20s%10s%10s%10s", "Name", "Cost", "Level", "Reduction");
    }

    @Override
    public String toString() {
        return String.format("%20s%10d%10d%10d", getName(), getCost(), getLevel(), reduction);
    }

}
