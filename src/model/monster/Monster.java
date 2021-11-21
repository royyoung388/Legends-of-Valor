package model.monster;

import model.Character;

/**
 * Base class for monster
 */
public abstract class Monster extends Character {
    public static final int DRAGON = 0;
    public static final int SPIRIT = 1;
    public static final int EXOSKELETON = 2;
    public static final String[] TYPE = {"Dragon", "Spirit", "Exoskeleton"};

    private final int type;


    public Monster(int type, String[] args) {
        this(type, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                Integer.parseInt(args[4]));
    }

    public Monster(int type, String name, int level, int damage, int defense, int dodge) {
        super(name, level, level * 100, damage, defense, dodge);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static String header() {
        return String.format("%20s%10s%10s%10s%10s%10s%10s", "Name", "Type", "HP", "Level", "Damage", "Defense", "Dodge");
    }

    @Override
    public String toString() {
        return String.format("%20s%10s%10d%10d%10d%10d%10d", getName(), TYPE[type], getHp(), getLevel(), getDamage(), getDefense(), getDodge());
    }
}
