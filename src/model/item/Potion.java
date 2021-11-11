package model.item;

import model.Changeable;
import model.Usable;

import java.util.HashMap;
import java.util.Map;

/**
 * item, potion
 */
public class Potion extends Item implements Usable {
    private final int increase;
    private final String[] attribute;
    private static final Map<String, Integer> attMap;

    static {
        attMap = new HashMap<>();
        attMap.put("Health", Changeable.HP);
        attMap.put("Mana", Changeable.MP);
        attMap.put("Strength", Changeable.STRENGTH);
        attMap.put("Defense", Changeable.DEFENSE);
        attMap.put("Agility", Changeable.AGILITY);
        attMap.put("All", -1);
    }

    public Potion(String[] args) {
        this(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), args[4]);
    }

    public Potion(String name, int cost, int level, int increase, String attribute) {
//        super(name, cost, level);
        super(name, cost, 1);
        this.increase = increase;
        this.attribute = attribute.split("[\\s+]");
    }

    public int getIncrease() {
        return increase;
    }

    public String[] getAttribute() {
        return attribute;
    }

    public static String header() {
        return String.format("%20s%10s%10s%20s%10s", "Name", "Cost", "Level", "Attribute", "Increase");
    }

    @Override
    public void use(Changeable subject) {
        for (String att : attribute) {
            if (attMap.get(att) == -1) {
                subject.change(Changeable.HP, increase);
                subject.change(Changeable.MP, increase);
                subject.change(Changeable.STRENGTH, increase);
                subject.change(Changeable.DEXTERITY, increase);
                subject.change(Changeable.DEFENSE, increase);
                subject.change(Changeable.AGILITY, increase);
                return;
            }

            subject.change(attMap.get(att), increase);
        }
    }

    @Override
    public String toString() {
        return String.format("%20s%10d%10d%20s%10d", getName(), getCost(), getLevel(), String.join(",", attribute), increase);
    }
}
