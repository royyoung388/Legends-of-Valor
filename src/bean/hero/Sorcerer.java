package bean.hero;

/**
 * One type of hero
 */
public class Sorcerer extends Hero {
    public Sorcerer(String[] args) {
        super(SORCERER, args);
    }

    @Override
    public void levelUp() {
        super.levelUp();
        setStrength((int) (getStrength() * 1.05));
        setAgility((int) (getAgility() * 1.1));
        setDexterity((int) (getDexterity() * 1.1));
    }
}
