package controller;

import model.Castable;
import model.Character;

/**
 * Base class for character controller
 * Contains common method for monster and hero
 */
public interface CharacterController {

    int getLevel();

    int getDamage();

    int damageDealt(int damage);

    void spellDealt(Castable spell);

    boolean isDied();

    boolean isDodge();

    void revive();

    Character getCharacter();

    void show();
}
