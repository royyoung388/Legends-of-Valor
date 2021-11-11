package model.hero;

/**
 * one type of hero
 */
public class Paladin extends Hero {

    public Paladin(String[] args) {
        super(PALADIN, args);
    }

    @Override
    public void levelUp() {
        super.levelUp();
        setStrength((int) (getStrength() * 1.1));
        setAgility((int) (getAgility() * 1.05));
        setDexterity((int) (getDexterity() * 1.1));
    }
}
