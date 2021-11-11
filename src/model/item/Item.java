package model.item;

import model.Merchantable;

/**
 * Base class for item
 */
public abstract class Item implements Merchantable, Cloneable {
    private final String name;
    private final int cost;
    private final int level;

    public Item(String name, int cost, int level) {
        this.name = name;
        this.cost = cost;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int buyPrice() {
        return cost;
    }

    @Override
    public int sellPrice() {
        return cost / 2;
    }

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
