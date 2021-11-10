package bean;

import bean.Changeable;

/***
 * Base class for monster and hero
 */
public abstract class Character implements Changeable {
    private String name;
    private int level;
    private int hp;
    private int damage;
    private int defense;
    private int dodge;

    public Character(String name, int level, int hp, int damage, int defense, int dodge) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.damage = damage;
        this.defense = defense;
        this.dodge = dodge;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getDodge() {
        return dodge;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public static String header() {
        return String.format("%20s%10s%10s%10s%10s%10s", "Name", "HP", "Level", "Damage", "Defense", "Dodge");
    }

    @Override
    public void change(int attribute, int value) {
        switch (attribute) {
            case HP -> hp += value;
            case LEVEL -> level += value;
            case DAMAGE -> damage += value;
            case DEFENSE -> defense += value;
            case DODGE -> dodge += value;
        }
    }

    @Override
    public String toString() {
        return String.format("%20s%10d%10d%10d%10d%10d", name, hp, level, damage, defense, dodge);
    }
}
