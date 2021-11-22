/**
 * Base class for Hero
 */
public abstract class Hero extends Character implements Cloneable {
    public static final int PALADIN = 0;
    public static final int WARRIOR = 1;
    public static final int SORCERER = 2;
    public static final String[] TYPE = {"Paladin", "Warrior", "Sorcerer"};

    private final int type;
    private int mana;
    private int strength;
    private int agility;
    private int dexterity;
    private int money;
    private int experience;

    public Hero(int type, String[] args) {
        this(type, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
    }

    public Hero(int type, String name, int mana, int strength, int agility, int dexterity, int money, int experience) {
        super(name, 1, 100, 500, 0, 0);
        this.type = type;
        this.mana = mana;
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.money = money;
        this.experience = experience;
    }

    public static String header() {
        return String.format("%20s%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s", "Name", "Type", "Level", "HP",
                "MP", "Damage", "Defense", "Dodge", "Strength", "Agility", "Dexterity", "Money", "Exp");
    }

    public int getType() {
        return type;
    }

    public int getMana() {
        return mana;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMoney() {
        return money;
    }

    public int getExperience() {
        return experience;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void levelUp() {
        if (getExperience() < getLevel() * 10)
            return;

        setExperience(getExperience() - getLevel() * 10);
        setLevel(getLevel() + 1);
        setMana((int) (getMana() * 1.1));
        setHp(getLevel() * 100);

        setDamage((int) (getDamage() * 1.1));
        setDefense((int) (getDefense() * 1.1));
//        setDodge((int) (getDodge() * 1.1));
    }

    @Override
    public void change(int attribute, int value) {
        super.change(attribute, value);
        switch (attribute) {
            case Changeable.MP -> mana += value;
            case Changeable.STRENGTH -> strength += value;
            case Changeable.AGILITY -> agility += value;
            case Changeable.DEXTERITY -> dexterity += value;
            case Changeable.MONEY -> money += value;
            case Changeable.EXPERIENCE -> experience += value;
        }
    }

    @Override
    public String toString() {
        return String.format("%20s%10s%10d%10d%10d%10d%10d%10d%10d%10d%10d%10d%10d",
                getName(), TYPE[type], getLevel(), getHp(), mana, getDamage(), getDefense(), getDodge(),
                strength, agility, dexterity, money, experience);
    }

    @Override
    public Hero clone() {
        try {
            Hero clone = (Hero) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
