/**
 * Base class for character controller.
 * Contains common method for monster and hero
 */
public abstract class CharacterControllerImpl implements CharacterController {

    @Override
    public int getLevel() {
        return getCharacter().getLevel();
    }

    @Override
    public int getDamage() {
        return (int) (getCharacter().getDamage() * 0.05);
    }

    @Override
    public int damageDealt(int damage) {
        int dealt = damage - (int) (getCharacter().getDefense() * 0.05);
        if (dealt > 0)
            getCharacter().setHp(getCharacter().getHp() - dealt);
        else
            dealt = 0;
        return dealt;
    }

    @Override
    public void spellDealt(Castable spell) {
        spell.cast(getCharacter());
    }

    @Override
    public boolean isDied() {
        return getCharacter().getHp() <= 0;
    }

    @Override
    public boolean isDodge() {
        // attack dodge
        return Dice.roll(1000) <= getCharacter().getDodge() * 10;
    }

    @Override
    public void revive() {
        getCharacter().setHp(getCharacter().getLevel() * 50);
    }
}
