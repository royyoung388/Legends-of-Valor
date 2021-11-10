package bean.item;

import bean.Attackable;
import bean.Changeable;

/**
 * weapon, item
 */
public class Weapon extends Item implements Attackable {
    private final int damage;
    private final int hands;

    public Weapon(String[] args) {
        this(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]));
    }

    public Weapon(String name, int cost, int level, int damage, int hands) {
        super(name, cost, level);
        this.damage = damage;
        this.hands = hands;
    }

    public int getDamage() {
        return damage;
    }

    public int getHands() {
        return hands;
    }

    public static String header() {
        return String.format("%20s%10s%10s%10s%10s", "Name", "Cost", "Level", "Damage", "Hands");
    }

    @Override
    public void attack(Changeable opponent) {

    }

    @Override
    public String toString() {
        return String.format("%20s%10d%10d%10d%10d", getName(), getCost(), getLevel(), damage, hands);
    }
}
